#include <ArduinoJson.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <asyncHTTPrequest.h>
#include <Ticker.h>

//WIFI postavke
const char* ssid = "1FDB41"; //Enter SSID
const char* password = "EVW32C0S00019080"; //Enter Password

//IFTTT API
const char* notificationHost = "maker.ifttt.com";
const int notificationPort = 80;
const char* apiKey = "iwXcWXZ99nZJvkK8IJX-SvhqVbJ2o3Ljuw_ynvWDQB6";
//const char* host = "192.168.0.15";
//const int port = 8080;

//backend rute
const char* checkSensorAPI = "http://192.168.0.15:8080/checkSensorState";
const char* logMotionAPI = "http://192.168.0.15:8080/motionDetected";

//hardware pinovi
const int buzzerPin = 2;
const int pirSensor = 12;
const int ledPin = 13;

//stanje senzora (radi/neradi)
bool pirPower = 1;
bool buzzerPower = 1;

StaticJsonDocument<200> doc;
WiFiClient client;
HTTPClient http;
asyncHTTPrequest request;
Ticker ticker;

void checkPower(){
  //asinkroni zahtjev za stanje senzora
  if(request.readyState() == 0 || request.readyState() == 4){
    request.open("GET",checkSensorAPI);
    request.send();
  }
}

void requestCB(void* optParam,asyncHTTPrequest* request, int readyState){
  //callback funkcija za provjeru stanja senzora
  if(readyState == 4){
    deserializeJson(doc,(request->responseText()));
    pirPower = doc["pirSensor"].as<bool>();
    buzzerPower = doc["alarm"].as<bool>();
    Serial.print("PIR sensor on: ");Serial.println(pirPower);
    Serial.print("buzzer on: ");Serial.println(buzzerPower);
  }
}

void logMotion(){
  //http zahtjev za upis u bazu
  Serial.println("");
  Serial.println("zapisujem pokret...");
  http.begin(client,logMotionAPI);
  http.addHeader("Content-Type", "application/json");
  int httpCode = http.GET();
  String payload = http.getString();
  Serial.println(httpCode);
  Serial.println(payload);
  http.end();
  delay(1000);
}

void sendNotification(){
  //http zahtjev za slanje notifikacije
  WiFiClient notifyClient;
  Serial.println("");
  Serial.println("Šaljem notifikaciju");
  if(notifyClient.connect(notificationHost,80)){
    Serial.println("Spojeni na server");
    notifyClient.print("POST /trigger/");
    notifyClient.print("motion_detected");
    notifyClient.print("/with/key/");
    notifyClient.print(apiKey);
    notifyClient.println(" HTTP/1.1");

    notifyClient.println("Host: maker.ifttt.com");
    notifyClient.println("User-Agent: Arduino/1.0");
    notifyClient.println("Connection: close");
    notifyClient.println();
    notifyClient.stop();
  }else{
    Serial.println("Greška pri spajanju");
  }
}

void turnOff(){
  noTone(buzzerPin);
  digitalWrite(ledPin,HIGH);
}

void setup(){
  Serial.begin(115200);
  delay(200);
  Serial.println();
  
  pinMode(ledPin, OUTPUT);
  pinMode(buzzerPin, OUTPUT);
  pinMode(pirSensor, INPUT);

  digitalWrite(ledPin,HIGH);
  
  WiFi.mode(WIFI_STA);
  Serial.printf("Spajanje na WiFi mrežu: %s",ssid);
  unsigned long startTime = millis();
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED && startTime + 30*1000 >= millis()) {
    delay(1000);
    Serial.print(".");
  }
  if(WiFi.status() == WL_CONNECTED){
    Serial.println("");
    Serial.println("WiFi veza uspostavljena");
    Serial.println(WiFi.localIP());
  }else{
    WiFi.disconnect();
    Serial.println("");
    Serial.println("WiFi nedostupan!");
  }
  Serial.print("WiFi veza uspostavljena: ");
  Serial.println(WiFi.localIP());

  request.onReadyStateChange(requestCB);
  ticker.attach(5,checkPower);
  
  delay(30000);
}

void loop() {
  if (WiFi.status() == WL_CONNECTED) { //Check WiFi connection status
    if(pirPower){
      if(digitalRead(pirSensor)){
        Serial.println("motion detected");
        if(buzzerPower){
          tone(buzzerPin,440);
        }else{
          noTone(buzzerPin);
        }
        digitalWrite(ledPin, LOW);
        sendNotification();
        delay(10);
        logMotion();
        delay(10);
        while(digitalRead(pirSensor));
      }else{
        turnOff();
      }
    }else{
       turnOff();
    }
  }else{
    turnOff();
  }
}
