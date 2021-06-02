#include <serverlib.h>

const char *server_ssid = "test";
const char *server_pass = "123456789";

const char *PARAM_INPUT_1 = "input1";
const char *PARAM_INPUT_2 = "input2";
const char *PARAM_INPUT_3 = "input3";

void initWiFi()
{
    WiFi.softAP(server_ssid, server_pass);
    Serial.print("Creating WiFi ..");
    //   while (WiFi.status() != WL_CONNECTED)
    //   {
    //     Serial.print('.');
    //     delay(1000);
    //   }
    Serial.println("kontol");
    Serial.println(WiFi.softAPIP());
}

void notFound(AsyncWebServerRequest *request)
{
    request->send(404, "text/plain", "Not found");
}

void make_server()
{
    Serial.begin(9600);
    Serial.println("mulai");
    // Create AsyncWebServer object on port 80
    AsyncWebServer server(80);

    // Create an Event Source on /events
    AsyncEventSource events("/events");

    initWiFi();

    server.on("/", HTTP_GET, [](AsyncWebServerRequest *request)
              { request->send_P(200, "text/html", server_html); });

    // Send a GET request to <ESP_IP>/get?input1=<inputMessage>
    server.on("/get", HTTP_GET, [](AsyncWebServerRequest *request)
              {
                  String inputMessage;
                  String inputParam;
                  // GET input1 value on <ESP_IP>/get?input1=<inputMessage>
                  if (request->hasParam(PARAM_INPUT_1))
                  {
                      inputMessage = request->getParam(PARAM_INPUT_1)->value();
                      inputParam = PARAM_INPUT_1;
                  }
                  // GET input2 value on <ESP_IP>/get?input2=<inputMessage>
                  else if (request->hasParam(PARAM_INPUT_2))
                  {
                      inputMessage = request->getParam(PARAM_INPUT_2)->value();
                      inputParam = PARAM_INPUT_2;
                  }
                  // GET input3 value on <ESP_IP>/get?input3=<inputMessage>
                  else if (request->hasParam(PARAM_INPUT_3))
                  {
                      inputMessage = request->getParam(PARAM_INPUT_3)->value();
                      inputParam = PARAM_INPUT_3;
                  }
                  else
                  {
                      inputMessage = "No message sent";
                      inputParam = "none";
                  }
                  Serial.println(inputMessage);
                  request->send(200, "text/html", "HTTP GET request sent to your ESP on input field (" + inputParam + ") with value: " + inputMessage + "<br><a href=\"/\">Return to Home Page</a>");
              });
    server.onNotFound(notFound);
    server.begin();
}
