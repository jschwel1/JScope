void setup() {
  // put your setup code here, to run once:
  pinMode(A0, INPUT);
  pinMode(A2, INPUT);

  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  Serial.begin(128000);
}

void loop() {
  // put your main code here, to run repeatedly:
  static char v = 0;
  static int i = 0;
  
  Serial.print(analogRead(A0));
  Serial.print(",");
  Serial.println(analogRead(A1));

  if (i++ == 30){
    i = 0;
    digitalWrite(7, v);
    digitalWrite(8, !v);
    v=!v;
  }
  if (i == 15){
    digitalWrite(9, v);
  }
}
