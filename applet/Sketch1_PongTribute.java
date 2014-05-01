import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Sketch1_PongTribute extends PApplet {

Paddle pLeft;
Paddle pRight;
Ball pong;

public void setup(){
  size(1000, 600);
  pLeft = new Paddle(6, 200, 200, 200, 20);
  pRight = new Paddle(-15, 700, 300, 160, 14);
  float rX = random (100, 900);
  float rY = random (100, 500);
  pong = new Ball((int)rX, (int)rY, 16, -12, 12);
  
  frameRate(30);
  rectMode(CENTER);
  ellipseMode(CENTER);
}

public void draw(){
  background(25);
  pong.update();
  pLeft.update();
  pRight.update();
}
  

class Paddle{
  byte dir;
  float speed;
  float x, y, w, h;
  
  Paddle(float s, float x, float y, float h, float w){
    speed = s;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  public void update(){
    y += speed;
    if (y < (h/2)){
       y = 0 + h/2;
       speed *= -1;
    }
     else if (y > (height-(h/2))){
        y = height - (h/2);
        speed *= -1;
     }
     rect(x, y, w, h);
  }
  
  public boolean contains(int bX, int bY, int r){
    if ((bY >= (y-(h/2)) && bY <= (y+(h/2))) && (bX >= (x-(w/2)) && bX <= (x+(w/2)))){
      return true;
    }
    else{
      return false;
    }
  }
      
}

class Ball{
  int xVector;
  int yVector;
  int x, y, r;
  boolean canCollide;
  int recharge;

  Ball(int x, int y, int r, int xVector, int yVector){
    this.x = x;
    this.y = y;
    this.r = r;
    this.xVector = xVector;
    this.yVector = yVector;
    canCollide = true; 
    recharge = 0;
  }  
  
  public void update(){
    if ((pLeft.contains(x, y, r) || pRight.contains(x, y, r)) && canCollide){
     xVector *= -1;
     canCollide = false;
    }
    else if ((((y+r) > height || (y-r) < 0) && ((x+r) > width || (x-r) < 0)) && canCollide){
      xVector *= -1;
      yVector *= -1;
      canCollide = false;
    }
    
    else if (((y+r) > height || (y-r) < 0) && canCollide){
     yVector *= -1;
     canCollide = false;
    }
   
    else if (((x+r) > width || (x-r) < 0) && canCollide){
     xVector *= -1;
     canCollide = false;
    }
    
    if (!canCollide){
      recharge++;
    }
    if (!canCollide && recharge > 2){
      canCollide = true;
      recharge = 0;
    }
    x += xVector;
    y += yVector;
    ellipse(x, y, r, r);
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#F0F0F0", "Sketch1_PongTribute" });
  }
}
