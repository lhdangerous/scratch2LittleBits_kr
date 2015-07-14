/*
 *  electronicadivertida.com 의 scratch2 offline 스크래치 프로젝트 (http://littlebits.c/projects/scrstch-offline)에
 *  기반하여 '도구의 인간'에서 한글화 및 수정 변경한 프로그램입니다.
 *  https://github.com/lhdangerous/scratch2LittleBits_kr으로 프로젝트에 기여/수정요청 해주세요
 *  July 13, 2015
 *  
 */

const int READ_PINS = 1;
const int WRITE_ANALOG = 2;
const int WRITE_DIGITAL = 3;

// Analog input smoothing
// http://arduino.cc/en/Tutorial/Smoothing

const int sensorChannels = 20;
const int maxNumReadings = 3;

int smoothingValues[sensorChannels][maxNumReadings];
int smoothingIndex[sensorChannels];
int smoothingTotal[sensorChannels];

int analoglizedD0 = 0; // 딪지털인풋을 안날록극값을로출력학기윟해... high>>255 로출력...
int averageA1;
int averageA0;


int mlUltValor[3];

void setup() {
  
  // Set the Serial baud rate to 38400
  Serial.begin(38400);
  
  // Set up digital pins 1, 5, and 9 as outputs
  pinMode(1, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(9, OUTPUT);

  mlUltValor[0]=-1;
  mlUltValor[1]=-1;
  mlUltValor[2]=-1;
  
  setupSmoothing();

}

void setupSmoothing() {
     averageA1 = 0;
     averageA0 = 0;
  
     for(int i = 0; i < sensorChannels; i++) {
       for(int j = 0 ; j < maxNumReadings ; j++) {
         smoothingValues[i][j]=0;
       }
       smoothingTotal[i]=0;
       smoothingIndex[i]=0;
     }
     
}

void loop() {
  int incomingByte = 0;
  
  // Check if there are bytes on the Serial port
  if (Serial.available() > 0) {
    
      // Get first available byte
      incomingByte = Serial.read();
      
      if (incomingByte == READ_PINS) {
      
        // Read digital pin 0
        Serial.write(analoglizedD0);
        
        // Get averages for analog pins 0 and 1
        Serial.write(averageA0);
        Serial.write(averageA1);
        
      } else if (incomingByte == WRITE_ANALOG) {
      
        // Next byte from Scratch is pin number
        int outputPin = Serial.read();
        
        // Next byte from Scratch is pin value
        int outputVal = Serial.read();
        
        if(outputPin==5 && mlUltValor[1]!=outputVal){
            mlUltValor[1]=outputVal;
            analogWrite(outputPin, outputVal);
        }
        if(outputPin==9 && mlUltValor[2]!=outputVal){
            mlUltValor[2]=outputVal;
            analogWrite(outputPin, outputVal);
        }
      } else if (incomingByte == WRITE_DIGITAL) {
      
        // Next byte from Scratch is pin number
        int outputPin = Serial.read();
        
        // Next byte from Scratch is pin value
        int outputVal = Serial.read();
        
        if(mlUltValor[0]!=outputVal){
           mlUltValor[0]=outputVal;
           digitalWrite(outputPin, outputVal);
        }
      
    }
  }
  //  딪짙턾 d0 핀값이0임면0, 1임면255 
   analoglizedD0 = digitalRead(0)? 255: 0;
   
  // Analog input smoothing
  // http://arduino.cc/en/Tutorial/Smoothing
  averageA0 = readAnalogPort(A0);
  averageA1 = readAnalogPort(A1);
  
  delay(16);
}


int smoothingValue(int channel, int value) {
    int total;
    int index = smoothingIndex[channel];
    total = smoothingTotal[channel] - smoothingValues[channel][index];
    smoothingValues[channel][index] = value;
    smoothingTotal[channel] = total + value;
    smoothingIndex[channel]++;
    if(smoothingIndex[channel] >=maxNumReadings) {
      smoothingIndex[channel]=0;
    }

    return int(round((double)smoothingTotal[channel] / ((double)maxNumReadings)));
}

int readAnalogPort(int plPin) {
  int value;
  value = analogRead(plPin);
  value = smoothingValue(plPin, value);
  if (value >= 1020) value = 1020;//pq la division por 4 no debe de superar 255
  return ((double)value)/4.0;
}

