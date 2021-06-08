#include "WiFi.h"
#include "ESPAsyncWebServer.h"
#include "serverlib.h"
#include <Arduino.h>

const char *ssid = "MyESP32AP";
const char *password = "testpassword";

const char *PARAM_INPUT_1 = "input1";
const char *PARAM_INPUT_2 = "input2";
const char *PARAM_INPUT_3 = "input3";
const char *PARAM_INPUT_4 = "input4";

const char server_html[] PROGMEM = R"rawliteral(
<!DOCTYPE HTML><html><head>
  <title>ESP Input Form</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  </head><body>
  <form action="/get">
    input1: <input type="text" name="input1">
    input2: <input type="text" name="input2">
    input3: <input type="text" name="input3">
    input4: <input type="text" name="input4">
    <input type="submit" value="Submit">
  </form>
</body></html>)rawliteral";

void make_server()
{
  AsyncWebServer server(80);
  WiFi.softAP(ssid, password);
  Serial.print("IP address: ");
  Serial.println(WiFi.softAPIP());

  server.on("/", HTTP_GET, [](AsyncWebServerRequest *request)
            { request->send_P(200, "text/html", server_html); });
  // Send a GET request to <ESP_IP>/get?input1=<inputMessage>
  server.on("/get", HTTP_GET, [](AsyncWebServerRequest *request)
            {
              String inputMessage_1 = "";
              String inputMessage_2 = "";
              String inputMessage_3 = "";
              String inputMessage_4 = "";
              String inputParam;
              // GET input1 value on <ESP_IP>/get?input1=<inputMessage>
              if (request->hasParam(PARAM_INPUT_1))
              {
                inputMessage_1 = request->getParam(PARAM_INPUT_1)->value();
                inputParam = PARAM_INPUT_1;
              }
              // GET input2 value on <ESP_IP>/get?input2=<inputMessage>
              if (request->hasParam(PARAM_INPUT_2))
              {
                inputMessage_2 = request->getParam(PARAM_INPUT_2)->value();
                inputParam = PARAM_INPUT_2;
              }
              // GET input3 value on <ESP_IP>/get?input3=<inputMessage>
              if (request->hasParam(PARAM_INPUT_3))
              {
                inputMessage_3 = request->getParam(PARAM_INPUT_3)->value();
                inputParam = PARAM_INPUT_3;
              }
              if (request->hasParam(PARAM_INPUT_4))
              {
                inputMessage_4 = request->getParam(PARAM_INPUT_4)->value();
                inputParam = PARAM_INPUT_4;
              }
              else
              {
                inputMessage_1 = "No message sent";
                inputParam = "none";
              }
              Serial.println(inputMessage_1);
              request->send(200, "text/html", "HTTP GET : " + inputMessage_1 + ", " + inputMessage_2 + ", " + inputMessage_3 + ", " + inputMessage_4);
            });
  server.begin();
}