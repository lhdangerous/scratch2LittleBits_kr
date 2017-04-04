/*
 *  electronicadivertida.com 의 scratch2 offline 스크래치 프로젝트 (http://littlebits.c/projects/scrstch-offline)에
 *  기반하여 '도구의 인간'에서 한글화 및 수정 변경한 프로그램입니다.
 *  https://github.com/lhdangerous/scratch2LittleBits_kr으로 프로젝트에 기여/수정요청 해주세요
 *  July 13, 2015
 *  
 *  시리얼 통신을 통해 값 전달시 상위바이트가 잘리고 하위바이트만 전달되는 오류 수정.
 *  경기교육지원청 김슬기 선생님이 도와주셨습니다.
 *  Sep. 10, 2015
 *  
 */

// Status codes sent from Scratch
const int READ_PINS = 1;
const int WRITE_ANALOG = 2;
const int WRITE_DIGITAL = 3;

// Analog input smoothing
// http://arduino.cc/en/Tutorial/Smoothing
const int NUM_READINGS = 10;
int index = 0;
int readingsA1[NUM_READINGS];
int readingsA0[NUM_READINGS];
int averageA1 = 0;
int averageA0 = 0;
int totalA1 = 0;
int totalA0 = 0;

// Reading from Serial
// http://arduino.cc/en/Serial/read
const int NUM_OUTPUT_PINS = 3;
int incomingByte = 0;
int inputPins[NUM_OUTPUT_PINS];
int outputPin;
int outputVal;

void setup() {
  
  // Set the Serial baud rate to 38400
  Serial.begin(38400);
  
  // Set up digital pins 1, 5, and 9 as outputs
  pinMode(1, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(9, OUTPUT);
  
  // Initialize the readings array with 0's
  for (int i = 0; i < NUM_READINGS; i++) {
    readingsA1[i] = 0;
    readingsA0[1] = 0;
  }

}

void loop() {
  
  // Check if there are bytes on the Serial port
  if (Serial.available() > 0) {
    
    // Get first available byte
    incomingByte = Serial.read();
    
    if (incomingByte == READ_PINS) {
    
      // Read digital pin 0
       // 딪지털인풋을 안날록극값을로출력학기윟해
      inputPins[0] = digitalRead(0)? 1023: 0;  //  딪짙턾 d0 핀값이0임면0, 1임면1023 
      
      // Get averages for analog pins 0 and 1
      inputPins[1] = averageA0;
      inputPins[2] = averageA1;
      
      // Send value 
      for (int i = 0; i < NUM_OUTPUT_PINS; i++)
        Serial.write(inputPins[i]/4);               //   Serial.write() 는 1 byte 만 전송함믈로  max 값이1방잍틀로푷현간능한255 가됭엉야함
      
    
    } else if (incomingByte == WRITE_ANALOG) {
    
      // Next byte from Scratch is pin number
      outputPin = Serial.read();
      
      // Next byte from Scratch is pin value
      outputVal = Serial.read();
      
      analogWrite(outputPin, outputVal);
    
    } else if (incomingByte == WRITE_DIGITAL) {
    
      // Next byte from Scratch is pin number
      outputPin = Serial.read();
      
      // Next byte from Scratch is pin value
      outputVal = Serial.read();
      
      digitalWrite(outputPin, outputVal);
    
    }
    
  }
  
  // Analog input smoothing
  // http://arduino.cc/en/Tutorial/Smoothing
  totalA0 = totalA0 - readingsA0[index];
  totalA1 = totalA1 - readingsA1[index];
  readingsA0[index] = analogRead(A0);
  readingsA1[index] = analogRead(A1);
  totalA0 = totalA0 + readingsA0[index];
  totalA1 = totalA1 + readingsA1[index];
  averageA0 = totalA0 / NUM_READINGS;
  averageA1 = totalA1 / NUM_READINGS;
  index = index + 1;
  if (index >= NUM_READINGS)
    index = 0;
  
  // Slight delay between loop
  delay(1);
}
