#include <Arduino.h>
#include <WiFi.h>
#include <ESPAsyncWebServer.h>
#include <HTTPClient.h>
#include <EEPROM.h>
#include <DHT.h>
#include <Adafruit_Sensor.h>

// initialize pin
#define DHTPIN 32  // pin of DHT sensor
#define SOILPIN 33 // pin of soil moisture sensor
#define RELAY_1 16 // pin for relay 1
#define RELAY_2 17 // pin for relay 2
#define EEPROM_SIZE 105

// sensor reading variable
float temperature = 0;
float humidity = 0;
float soil = 0;
int analog_soil = 0;

// value for calibrating soil moisture sensor
int soil_in_air = 4095;
int soil_in_water = 1800;
// initialize the DHT sensor
DHT dht(DHTPIN, DHT11);

// Timer variables
unsigned long lastTime = 0;
// delay each post by 5 seccond
unsigned long timerDelay = 1000;

// relay state
String pump_state = "off", lamp_state = "off";

//condition (server or client)
int condition;

// client variable
String username, password, sta_ssid_str, sta_pass_str;
// char *sta_ssid, *sta_pass;

// server ssid and password
const char *server_ssid = "HAMS IoT Device";
const char *server_password = "123456789";
//cloud server for update variable
const char *server_update = "http://34.101.94.5:5000/sensor/update";
//cloud server for update lamp
const char *server_lamp = "http://34.101.94.5:5000/sensor/update";
// cloud server for update pump
const char *server_pump = "http://34.101.94.5:5000/sensor/update";
//test local server
// const char *serverName = "http://192.168.43.209:5000/sensor/update";

// parameter for server
const char *PARAM_INPUT_1 = "username";
const char *PARAM_INPUT_2 = "password";
const char *PARAM_INPUT_3 = "sta_ssid";
const char *PARAM_INPUT_4 = "sta_pass";

AsyncWebServer server(80);

// html code for the server
const char server_html[] PROGMEM = R"rawliteral(
<!DOCTYPE HTML><html><head>
  <title>HAMS IoT Input Form</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <style>
    html {font-family: Arial; display: inline-block; text-align: center;}
    p { font-size: 1.2rem;}
    body {  margin: 0;}
    .topnav { overflow: hidden; background-color: #50B8B4; color: white; font-size: 1rem; }
  </style>
  </head><body>
  <div class="topnav">
    <h1>HAMS IOT SETUP FORM</h1>
  </div>
  <form action="/get">
    <pre>username       : <input type="text" name="username"></pre>
    <pre>password       : <input type="text" name="password"></pre>
    <pre>Wi-Fi SSID     : <input type="text" name="sta_ssid"></pre>
    <pre>Wi-Fi password : <input type="text" name="sta_pass"></pre>
    <input type="submit" value="Submit">
  </form>
</body></html>)rawliteral";

// read sensor function
void getSensorReadings()
{
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
  analog_soil = analogRead(SOILPIN);
  soil = map(analog_soil, soil_in_air, soil_in_water, 0, 100);
  if (soil > 100)
    soil = 100;
}

// function to get data from cloud
String httpGETRequest(const char *serverName)
{
  HTTPClient http;

  // Your IP address with path or Domain name with URL path
  http.begin(serverName);

  // Send HTTP POST request
  int httpResponseCode = http.GET();

  String payload = "{}";

  if (httpResponseCode > 0)
  {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    payload = http.getString();
  }
  else
  {
    Serial.print("Error code: ");
    Serial.println(httpResponseCode);
  }
  // Free resources
  http.end();

  return payload;
}

void setup()
{
  Serial.begin(9600);
  pinMode(SOILPIN, INPUT);
  pinMode(RELAY_1, OUTPUT);
  pinMode(RELAY_2, OUTPUT);

  // activate DHT sensor
  dht.begin();
  // put relay off first
  digitalWrite(RELAY_1, HIGH);
  digitalWrite(RELAY_2, HIGH);

  // start eeprom to save data permanently
  if (!EEPROM.begin(EEPROM_SIZE))
  {
    Serial.println("Failed to initialise EEPROM");
    Serial.println("Restarting in 10 seconds...");
    delay(10000);
    ESP.restart();
  }

  // setup the eeprom, just run this once then comment!!!
  // for (int i = 0; i < EEPROM_SIZE; i++)
  // { //clearing eeprom
  //   EEPROM.write(i, 0);
  // }
  // EEPROM.writeByte(100, 1);
  // EEPROM.commit();

  // check eeprom 99, if '1' then it in server mode, if '2' it in client mode
  condition = EEPROM.readByte(100);
  Serial.println(condition);
  if (condition == 1) //server mode(default, to configure)
  {
    // init AP
    WiFi.softAP(server_ssid, server_password);
    Serial.print("IP address: ");
    Serial.println(WiFi.softAPIP());

    server.on("/", HTTP_GET, [](AsyncWebServerRequest *request)
              { request->send_P(200, "text/html", server_html); });

    // Send a GET request
    server.on("/get", HTTP_GET, [](AsyncWebServerRequest *request)
              {
                String username_in;
                String password_in;
                String sta_ssid_in;
                String sta_pass_in;
                // GET username, password, ssid, and ssid pasword
                if (request->hasParam(PARAM_INPUT_1) && request->hasParam(PARAM_INPUT_2) && request->hasParam(PARAM_INPUT_3) && request->hasParam(PARAM_INPUT_4))
                {
                  username_in = request->getParam(PARAM_INPUT_1)->value();
                  password_in = request->getParam(PARAM_INPUT_2)->value();
                  sta_ssid_in = request->getParam(PARAM_INPUT_3)->value();
                  sta_pass_in = request->getParam(PARAM_INPUT_4)->value();

                  request->send(200, "text/html", "Terima kasih, data yang anda masukkan adalah : <br>" + username_in + "<br>" + password_in + "<br>" + sta_ssid_in + "<br>" + sta_pass_in);

                  // write username to eeprom
                  EEPROM.writeString(0, username_in);
                  // write password to eeprom
                  EEPROM.writeString(25, password_in);
                  // write sta_ssid to eeprom
                  EEPROM.writeString(50, sta_ssid_in);
                  // write sta_pass to eeprom
                  EEPROM.writeString(75, sta_pass_in);
                  // put condition to client mode
                  EEPROM.writeInt(100, 2);
                  EEPROM.commit();
                  Serial.println("setup done, restarting.......");
                  ESP.restart();
                }
                else
                {
                  request->send(200, "text/html", "tolong masukkan data dengan benar");
                }
              });
    server.begin();
  }

  else if (condition == 2) // client mode(to send data to cloud)
  {
    // read value from eeprom
    username = EEPROM.readString(0);
    password = EEPROM.readString(25);
    sta_ssid_str = EEPROM.readString(50);
    sta_pass_str = EEPROM.readString(75);
    // convert sta ssid and password (why? i don't know, it only works this way)
    const char *sta_ssid = sta_ssid_str.c_str();
    const char *sta_pass = sta_pass_str.c_str();
    // init STA
    WiFi.mode(WIFI_STA);
    WiFi.begin(sta_ssid, sta_pass);
    Serial.print("Connecting to ");
    Serial.print(sta_ssid_str);
    while (WiFi.status() != WL_CONNECTED)
    {
      Serial.print('.');
      delay(1000);
    }
    Serial.println(WiFi.localIP());
  }
}

void loop()
{
  if (condition == 2)
  {
    if ((millis() - lastTime) > timerDelay)
    {
      //first update lamp and pump data from cloud
      //cloud server address for update lamp
      String temp = "http://34.101.94.5:5000/sensor/get-button/user=" + username + "/button=lamp";
      const char *server_lamp = temp.c_str();
      lamp_state = httpGETRequest(server_lamp);
      Serial.println(lamp_state);
      // cloud server address for update pump
      temp = "http://34.101.94.5:5000/sensor/get-button/user=" + username + "/button=pump";
      const char *server_pump = temp.c_str();
      pump_state = httpGETRequest(server_pump);
      Serial.println(pump_state);
      //get data from cloud

      //control lamp and pump according to data (for manual mode)
      if (lamp_state == "on")
        digitalWrite(RELAY_1, LOW);
      else
        digitalWrite(RELAY_1, HIGH);
      if (pump_state == "on")
        digitalWrite(RELAY_2, LOW);
      else
        digitalWrite(RELAY_2, HIGH);

      // if soil humidity less than 40, turn on the pump (for auto mode)
      // if (soil < 40)
      // {
      //   digitalWrite(RELAY_1, LOW);
      //   pump_state = "ON";
      // }
      // else
      // {
      //   digitalWrite(RELAY_1, HIGH);
      //   pump_state = "OFF";
      // }

      // read sensor
      getSensorReadings();

      // add sensor reading to json file
      String send_data = "username=" + String(username) + "&temperature=" + String(temperature) +
                         "&humidity=" + String(humidity) + "&soil_moist=" + String(soil);
      Serial.println(send_data);

      // send file to server if there's connection
      if (WiFi.status() == WL_CONNECTED)
      {
        HTTPClient http;
        http.begin(server_update);

        http.addHeader("Content-Type", "application/x-www-form-urlencoded");
        int httpResponseCode = http.POST(send_data);

        Serial.print("HTTP Response code: ");
        Serial.println(httpResponseCode);
        http.end();
      }
      else
      {
        Serial.println("WiFi Disconnected");
        delay(500);
      }
      lastTime = millis();
    }
  }
}