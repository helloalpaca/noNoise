#include <SoftwareSerial.h>
 
#define BT_RXD 8
#define BT_TXD 7
#define MEASURE_TERM 10000

SoftwareSerial bluetooth(BT_RXD, BT_TXD);

int soundSensor = A0;
int vibSensor = 2;
int redLed = 4;
int greenLed = 5;
int blueLed = 6;
long time_prev_vib ;
long time_now_vib ;
long time_prev_sound ;
long time_now_sound ;

long time_prev;
long time_now;

int count_vib = 0;
int count_sound = 0;

long m_vib = 0;
long m_sound = 0;

long sounds[65] = {0};
long vibs[65] = {0};

char ch[100] = {0};

boolean led_checked = false;

int testCount = 0;
void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);
  pinMode(soundSensor, INPUT);
  pinMode(vibSensor, INPUT);
  pinMode(redLed, OUTPUT);
  pinMode(greenLed, OUTPUT);
  pinMode(blueLed, OUTPUT);
  
  digitalWrite(redLed, HIGH);
  digitalWrite(greenLed, HIGH);
  digitalWrite(blueLed, HIGH);
  
  Serial.println("setup");

  time_prev_vib = millis();
  time_now_vib = millis();
  time_prev_sound = millis();
  time_now_sound = millis();
  time_prev = millis();
  time_now = millis();
}

void loop() {
  String ledStatus = "";
  long vib = TP_init();
  long sound = analogRead(soundSensor);

  middleOfSound(sound);
  middleOfVib(vib);
  
  String str = "";
  str.concat(String(m_sound));
  str.concat(",");
  str.concat(String(m_vib));
  str.concat(",  ");
  str.toCharArray(ch, str.length());

  time_now = millis();
  if(time_now - time_prev > MEASURE_TERM){
    bluetooth.write(ch);
    time_prev = time_now;
    led_checked = true;
  }

  if(led_checked){
    if(m_sound>500 or m_vib > 50){
      Serial.println("red");
      digitalWrite(redLed, LOW);
      digitalWrite(greenLed, HIGH);
      digitalWrite(blueLed, HIGH);
    }
    else if(m_sound>500 or m_vib > 25){
      Serial.println("yellow");
      digitalWrite(redLed, LOW);
      digitalWrite(greenLed, LOW);
      digitalWrite(blueLed, HIGH);
    }
    else{
      Serial.println("none");
      digitalWrite(redLed, HIGH);
      digitalWrite(greenLed, HIGH);
      digitalWrite(blueLed, HIGH);
    }

    led_checked = false;
  }
  
  delay(1000);
  
}

void middleOfSound(long sound){
  
  sounds[count_sound] = sound;
  count_sound++;
  time_now_sound = millis();

  if(time_now_sound - time_prev_sound > MEASURE_TERM){

   
    int term = MEASURE_TERM/1000;
    SortDec(sounds, count_sound);
    m_sound = sounds[count_sound/2];
    count_sound = 0;
    Serial.println("m_sound : " + String(m_sound));
    
    time_prev_sound = time_now_sound;
  }
}

void middleOfVib(long vib){
  vibs[count_vib] = vib ;
  count_vib++;

  time_now_vib = millis();
  if(time_now_vib - time_prev_vib > MEASURE_TERM){
    
    int term = MEASURE_TERM/1000;
    SortDec(vibs, count_vib);
    m_vib = vibs[count_vib/2];
    count_vib = 0;
    Serial.println("m_vib : " + String(m_vib));

    time_prev_vib = time_now_vib;
  }
}


long FindMax(long ARRAY[], byte START, byte END){
  long MAXIMUM;
  byte LOCATION;
  
  MAXIMUM = ARRAY[START];
  LOCATION = START;
  for(byte i = START + 1; i < END; i++){
    if(ARRAY[i] > MAXIMUM){
    MAXIMUM = ARRAY[i];
    LOCATION = i;
    }
  }
  return LOCATION;
}

void Swap(long ARRAY[], long a, long b){
  long temp, location;
  
  temp = ARRAY[a];
  ARRAY[a] = ARRAY[b];
  ARRAY[b] = temp;
}


void SortDec(long ARRAY[], byte SIZE){
  byte location;
 
  for(byte i = 0; i < SIZE - 1; i++){
    location = FindMax(ARRAY, i, SIZE);
    Swap(ARRAY, i, location);
  }
}

long TP_init(){
  delay(10);
  long measurement = pulseIn(vibSensor, LOW);
  return measurement;
}
