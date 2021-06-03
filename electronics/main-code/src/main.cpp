// insert library
#include <Arduino.h>
#include <DHT.h>
#include <WiFi.h>
#include <Adafruit_Sensor.h>
#include <ESPAsyncWebServer.h>
#include <HTTPClient.h>

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

// initialize the DHT sensor
DHT dht(DHTPIN, DHT11);

// initialize WiFi connection
const char *ssid = "Really Realme";
const char *password = "20002002";
const char *serverName = "http://192.168.43.209:5000/auth/update";

// Timer variables
unsigned long lastTime = 0;
// delay each post by 10 seccond
unsigned long timerDelay = 5000;

// sensor reading variable
float temperature = 0;
float humidity = 0;
float soil = 0;
int analog_soil = 0;

void getSensorReadings()
{
    temperature = 10;
    humidity += 5;
    analog_soil = analogRead(SOILPIN);
    soil = map(analog_soil, soil_in_air, soil_in_water, 0, 100);
    if (soil > 100)
        soil = 100;
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
}

void loop()
{
    if ((millis() - lastTime) > timerDelay)
    {
        getSensorReadings();
        // if soil humidity less than 40, turn on the pump
        if (soil < 40)
        {
            digitalWrite(RELAY_1, LOW);
            pump_state = "ON";
        }
        else
        {
            digitalWrite(RELAY_1, HIGH);
            pump_state = "OFF";
        }
        // add sensor reading to json file
        String json_file = "username=10&password=" + String(humidity);

        if (WiFi.status() == WL_CONNECTED)
        {
            HTTPClient http;
            http.begin(serverName);

            http.addHeader("Content-Type", "application/x-www-form-urlencoded");
            int httpResponseCode = http.POST(json_file);

            Serial.print("HTTP Response code: ");
            Serial.println(httpResponseCode);
            http.end();
        }
        else
        {
            Serial.println("WiFi Disconnected");
        }
        lastTime = millis();
    }
}