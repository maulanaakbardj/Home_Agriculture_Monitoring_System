# Electronic
The electronic part consists of a microcontroller and sensors that will be connected to the cloud server and apps via Wi-Fi. The sensors used include the DHT11 sensor to determine temperature and soil moisture sensor to monitor soil conditions. This device will read the sensors and then update each value to the cloud via HTTP and store it in the database, so the app can see each sensor value in real-time.

## Main Component
* ESP32 dev module (or other ESP32 microcontroller)
* DHT11 sensor, to measure temperature and humidity
* Soil moisture sensor, to measure soil moisture
* 2 Channel Relay (or more) to control AC-voltage devices with microcontroller

## Additional Component
* Hi-Link 5v AC-DC adapter (you can use any 5v adapter)

## Circuit Diagram
the circuit diagram can be seen at `IoT prototype schematics.jpg` file

## How to Copy
1. make sure you have [Visual Studio Code](https://code.visualstudio.com/) with [PlatformIO](https://platformio.org/) extension installed
2. run VS code, open PlatformIO home, click 'open project'
3. choose `main-code` folder
4. connect ESP32 module to your computer
5. upload the code
