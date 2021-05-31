// insert library
#include <Arduino.h>
#include <DHT.h>
#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>
#include <Adafruit_Sensor.h>

// initialize pin
#define DHTPIN 32  // pin of DHT sensor
#define SOILPIN 33 // pin of soil moisture sensor
#define RELAY_1 16 // pin for relay 1
#define RELAY_2 17 // pin for relay 2

// relay state
String pump_state;

// value for calibrating soil moisture sensor
int soil_in_air = 4095;
int soil_in_water = 1800;

// variable for web switch
const char *PARAM_INPUT_1 = "output";
const char *PARAM_INPUT_2 = "state";

// initialize the DHT sensor
DHT dht(DHTPIN, DHT11);

// initialize WiFi connection
const char *ssid = "Really Realme";
const char *password = "20002002";

// Create AsyncWebServer object on port 80
AsyncWebServer server(80);

// Create an Event Source on /events
AsyncEventSource events("/events");

// Timer variables
unsigned long lastTime = 0;
unsigned long timerDelay = 2000;

// sensor reading variable
float temperature;
float humidity;
float pressure;
int analog_pressure;

void getSensorReadings()
{
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
  analog_pressure = analogRead(SOILPIN);
  pressure = map(analog_pressure, soil_in_air, soil_in_water, 0, 100);
  if (pressure > 100)
    pressure = 100;
}

// Initialize WiFi
void initWiFi()
{
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.print("Connecting to WiFi ..");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print('.');
    delay(1000);
  }
  Serial.println(WiFi.localIP());
}

String outputState(int output)
{
  if (digitalRead(output))
  {
    return "checked";
  }
  else
  {
    return "";
  }
}

String processor(const String &var)
{
  getSensorReadings();
  //Serial.println(var);
  if (var == "TEMPERATURE")
  {
    return String(temperature);
  }
  else if (var == "HUMIDITY")
  {
    return String(humidity);
  }
  else if (var == "PRESSURE")
  {
    return String(pressure);
  }
  else if (var == "WATERPUMP")
  {
    return String(pump_state);
  }
  else if (var == "BUTTONPLACEHOLDER")
  {
    String buttons = "";
    buttons += "<h4>LIGHT SWITCH</h4><label class=\"switch\"><input type=\"checkbox\" onchange=\"toggleCheckbox(this)\" id=\"17\" " + outputState(17) + "><span class=\"slider\"></span></label>";
    return buttons;
  }
  return String();
}

const char index_html[] PROGMEM = R"rawliteral(
<!DOCTYPE HTML><html>
<head>
  <title>ESP Web Server</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
  <link rel="icon" href="data:,">
  <style>
    html {font-family: Arial; display: inline-block; text-align: center;}
    p { font-size: 1.2rem;}
    body {  margin: 0;}
    .topnav { overflow: hidden; background-color: #50B8B4; color: white; font-size: 1rem; }
    .content { padding: 20px; }
    .card { background-color: white; box-shadow: 2px 2px 12px 1px rgba(140,140,140,.5); }
    .cards { max-width: 800px; margin: 0 auto; display: grid; grid-gap: 2rem; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); }
    .reading { font-size: 1.4rem; }
    .switch {position: relative; display: inline-block; width: 120px; height: 68px} 
    .switch input {display: none}
    .slider {position: absolute; top: 0; left: 0; right: 0; bottom: 0; background-color: #ccc; border-radius: 6px}
    .slider:before {position: absolute; content: ""; height: 52px; width: 52px; left: 8px; bottom: 8px; background-color: #fff; -webkit-transition: .4s; transition: .4s; border-radius: 3px}
    input:checked+.slider {background-color: #b30000}
    input:checked+.slider:before {-webkit-transform: translateX(52px); -ms-transform: translateX(52px); transform: translateX(52px)}
  </style>
</head>
<body>
  <div class="topnav">
    <h1>SENSOR TEST (WITH LOCAL SERVER)</h1>
  </div>
  <div class="content">
    <div class="cards">
      <div class="card">
        <p><i class="fas fa-thermometer-half" style="color:#059e8a;"></i> TEMPERATURE</p><p><span class="reading"><span id="temp">%TEMPERATURE%</span> &deg;C</span></p>
      </div>
      <div class="card">
        <p><i class="fas fa-tint" style="color:#00add6;"></i> HUMIDITY</p><p><span class="reading"><span id="hum">%HUMIDITY%</span> h</span></p>
      </div>
      <div class="card">
        <p><i class="fas fa-poop" style="color:#a52a2a;"></i> SOIL MOISTURE</p><p><span class="reading"><span id="pres">%PRESSURE%</span> &percnt;</span></p>
      </div>
      <div class="card">
        <p>WATER PUMP</p><p><span class="reading"><span id="pump">%WATERPUMP%</span></span></p>
      </div>
    </div>
  </div>
  %BUTTONPLACEHOLDER%
<script>
function toggleCheckbox(element) {
  var xhr = new XMLHttpRequest();
  if(element.checked){ xhr.open("GET", "/update?output="+element.id+"&state=0", true); }
  else { xhr.open("GET", "/update?output="+element.id+"&state=1", true); }
  xhr.send();
}
if (!!window.EventSource) {
 var source = new EventSource('/events');
 
 source.addEventListener('open', function(e) {
  console.log("Events Connected");
 }, false);
 source.addEventListener('error', function(e) {
  if (e.target.readyState != EventSource.OPEN) {
    console.log("Events Disconnected");
  }
 }, false);
 
 source.addEventListener('message', function(e) {
  console.log("message", e.data);
 }, false);
 
 source.addEventListener('temperature', function(e) {
  console.log("temperature", e.data);
  document.getElementById("temp").innerHTML = e.data;
 }, false);
 
 source.addEventListener('humidity', function(e) {
  console.log("humidity", e.data);
  document.getElementById("hum").innerHTML = e.data;
 }, false);
 
 source.addEventListener('pressure', function(e) {
  console.log("pressure", e.data);
  document.getElementById("pres").innerHTML = e.data;
 }, false);

 source.addEventListener('waterpump', function(e) {
  console.log("waterpump", e.data);
  document.getElementById("pump").innerHTML = e.data;
 }, false);
}
</script>
</body>
</html>)rawliteral";

void setup()
{
  Serial.begin(9600);
  initWiFi();
  pinMode(SOILPIN, INPUT);
  pinMode(RELAY_1, OUTPUT);
  pinMode(RELAY_2, OUTPUT);

  // activate DHT sensor
  dht.begin();

  // put relay off first
  digitalWrite(RELAY_1, HIGH);
  digitalWrite(RELAY_2, HIGH);

  // Handle Web Server
  server.on("/", HTTP_GET, [](AsyncWebServerRequest *request)
            { request->send_P(200, "text/html", index_html, processor); });

  // Handle Web Server Events
  events.onConnect([](AsyncEventSourceClient *client)
                   {
                     if (client->lastId())
                     {
                       Serial.printf("Client reconnected! Last message ID that it got is: %u\n", client->lastId());
                     }
                     // send event with message "hello!", id current millis
                     // and set reconnect delay to 1 second
                     client->send("hello!", NULL, millis(), 10000);
                   });
  server.on("/update", HTTP_GET, [](AsyncWebServerRequest *request)
            {
              String inputMessage1;
              String inputMessage2;
              // GET input1 value on <ESP_IP>/update?output=<inputMessage1>&state=<inputMessage2>
              if (request->hasParam(PARAM_INPUT_1) && request->hasParam(PARAM_INPUT_2))
              {
                inputMessage1 = request->getParam(PARAM_INPUT_1)->value();
                inputMessage2 = request->getParam(PARAM_INPUT_2)->value();
                digitalWrite(inputMessage1.toInt(), inputMessage2.toInt());
              }
              else
              {
                inputMessage1 = "No message sent";
                inputMessage2 = "No message sent";
              }
              Serial.print("GPIO: ");
              Serial.print(inputMessage1);
              Serial.print(" - Set to: ");
              Serial.println(inputMessage2);
              request->send(200, "text/plain", "OK");
            });
  server.addHandler(&events);
  server.begin();
}

void loop()
{
  if ((millis() - lastTime) > timerDelay)
  {
    getSensorReadings();
    if (pressure < 40)
    {
      digitalWrite(RELAY_1, LOW);
      pump_state = "ON";
    }
    else
    {
      digitalWrite(RELAY_1, HIGH);
      pump_state = "OFF";
    }

    Serial.printf("Temperature = %.2f ºC \n", temperature);
    Serial.printf("Humidity = %.2f \n", humidity);
    Serial.printf("pressure (analog) = %d \n", analog_pressure);
    Serial.printf("Pressure = %.2f hPa \n", pressure);
    Serial.printf("water pump %s \n", pump_state);
    Serial.println();

    // Send Events to the Web Server with the Sensor Readings
    events.send("ping", NULL, millis());
    events.send(String(temperature).c_str(), "temperature", millis());
    events.send(String(humidity).c_str(), "humidity", millis());
    events.send(String(pressure).c_str(), "pressure", millis());
    events.send(pump_state.c_str(), "waterpump", millis());
    lastTime = millis();
  }
}