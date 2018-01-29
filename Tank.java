import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.*;

public class Tank
{
  // Tank corners used for
  private double topLeftCornerX,topLeftCornerY;

  private double topRightCornerX,topRightCornerY;
  private double bottomLeftCornerX,bottomLeftCornerY;
  private double bottomRightCornerX,bottomRightCornerY;

  // On game VALUE
  private int health = 100;
  // Tanks
  private boolean moving = false;
  private boolean shooting = false;
  // Bullet
  private Bullet bullet = new Bullet(0,0,0,0);
  // Keyboard input
  private boolean left,right,forward,reverse=false;

  // Tank position
  private double positionX;
  private double positionY;

  // Tank velocity
  private double velocityX;
  private double velocityY;

  // Tank angle
  private double hullAngle;
  private double turretAngle;

  // Turret
  private boolean turretMovingLeft;
  private boolean turretMovingRight;
  private double turretSpeed;
  private double turnSpeed;

  private boolean patrolling;
  private boolean chasing;

  private int height;
  private int width;
  public Tank(double positionX, double positionY, double velocity, double turnSpeed, double turretSpeed,int height, int width){
    this.positionX = positionX;
    this.positionY = positionY;
    this.velocityX = velocity;
    this.velocityY = velocity;
    this.turretAngle = 0;
    this.hullAngle = 0;
    this.turretSpeed = turretSpeed;
    this.turnSpeed = turnSpeed;
    this.turretMovingLeft = false;
    this.turretMovingRight = false;
    this.height = height;
    this.width = width;
  }

  public void turnRight(double dt) {
    hullAngle += turnSpeed * dt;
			if (hullAngle > 360) {
				setHullAngle(0);
			}
      setCorners(positionX,positionY,hullAngle);
  }

  public void turnLeft(double dt) {
    hullAngle -= turnSpeed * dt;
			if(hullAngle < -360) {
				setHullAngle(0);
			}
      setCorners(positionX,positionY,hullAngle);

  }

  public void moveForward(double dt) {
    positionX += Math.cos(Math.toRadians(hullAngle-90)) * velocityX * dt;
    positionY += Math.sin(Math.toRadians(hullAngle-90)) * velocityY * dt;
    setCorners(positionX,positionY,hullAngle);

  }

  public void moveBackward(double dt) {
    positionX -= Math.cos(Math.toRadians(hullAngle-90)) * velocityX * dt;
    positionY -= Math.sin(Math.toRadians(hullAngle-90)) * velocityY * dt;
    setCorners(positionX,positionY,hullAngle);

  }

  // Positions
  public void setPositionX(double positionX){
    this.positionX = positionX;
  }
  public double getPositionX(){
    return positionX;
  }

  public void setPositionY(double positionY){
    this.positionY = positionY;
  }
  public double getPositionY(){
    return positionY;
  }
  // Velocities
  public void setVelocityX(double velocityX){
    this.velocityX = velocityX;
  }
  public double getVelocityX(){
    return velocityX;
  }
  public void setVelocityY(double velocityY){
    this.velocityY = velocityY;
  }
  public double getVelocityY(){
    return velocityY;
  }
  // Angles
  public void setHullAngle(double hullAngle){
    this.hullAngle = hullAngle;
  }
  public double getHullAngle(){
    return hullAngle;
  }
  public void setTurretAngle(double turretAngle){
    this.turretAngle = turretAngle;
  }
  public double getTurretAngle(){
    return turretAngle;
  }
  public void setTurretMovingLeft(boolean turretMovingLeft){
    this.turretMovingLeft = turretMovingLeft;
  }
  public boolean getTurretMovingLeft(){
    return turretMovingLeft;
  }
  public void setTurretMovingRight(boolean turretMovingRight){
    this.turretMovingRight = turretMovingRight;
  }
  public boolean getTurretMovingRight(){
    return turretMovingRight;
  }
  public void setTurretSpeed(int turretSpeed){
    this.turretSpeed = turretSpeed;
  }
  public double getTurretSpeed(){
    return turretSpeed;
  }
  public void setTurnSpeed(int turnSpeed){
    this.turnSpeed = turnSpeed;
  }
  public double getTurnSpeed(){
    return turnSpeed;
  }

  // Setters and getters of the keyboard input
  public void setLeft(boolean left){
    this.left = left;
  }
  public boolean getLeft(){
    return left;
  }
  public void setRight(boolean right){
    this.right = right;
  }
  public boolean getRight(){
    return right;
  }
  public void setForward(boolean forward){
    this.forward = forward;
  }
  public boolean getForward(){
    return forward;
  }
  public void setReverse(boolean reverse){
    this.reverse = reverse;
  }
  public boolean getReverse(){
    return reverse;
  }

  public Bullet getBullet(){
    return bullet;
  }
  public void setBullet(Bullet bullet){
    this.bullet = bullet;
  }

  public int getHeight(){
    return height;
  }
  public void setHeight(int height){
    this.height = height;
  }
  public int getWidth(){
    return width;
  }
  public void setWidth(int width){
    this.width = width;
  }
  public int getHealth(){
    return health;
  }
  public void setHealth(int health){
    this.health = health;
  }
  public boolean getMovement(){
    return moving;
  }
  public void setMovement(boolean moving){
    this.moving = moving;
  }
  public boolean getShooting(){
    return shooting;
  }
  public void setShooting(boolean shooting){
    this.shooting = shooting;
  }




  // public double getTopLeftCorner(){
  //   return topLeftCorner;
  // }
  // // public void setTopLeftCorner(double x,double y){
  // //
  // //   this.topLeftCorner = topLeftCorner;
  // // }
  // public double getTopRightCorner(){
  //   return topRightCorner;
  // }
  // public double setTopRightCorner(double x, double y){
  //
  // }
  public void setCorners(double x, double y,double angle){
    // Top Left
    this.topLeftCornerX = (y - height/2) * Math.sin(angle) + (x-width/2) * Math.cos(angle);
    this.topLeftCornerY = (y- height/2)  * Math.cos(angle) - (x -width/2)* Math.sin(angle);
    // Top Right
    this.topRightCornerX = (y- height/2) * Math.sin(angle) + (x+width/2) * Math.cos(angle);
    this.topRightCornerY = (y - height/2)* Math.cos(angle) - (x+width/2) * Math.sin(angle);
    // Bottom Left
    this.bottomLeftCornerX = (y + height/2) * Math.sin(angle)+ (x-width/2) * Math.cos(angle);
    this.bottomLeftCornerY = (y+ height/2)  * Math.cos(angle) - (x -width/2)* Math.sin(angle);
    // Bottom Right
    this.bottomRightCornerX = (y+ height/2) * Math.sin(angle) + (x+width/2) * Math.cos(angle);
    this.bottomRightCornerY = (y +height/2)* Math.cos(angle) - (x+width/2) * Math.sin(angle);
    // this.topLeftCornerX = (y - height/2)+ (x-width/2) ;
    // this.topLeftCornerY = (y- height/2) - (x -width/2);
    // // Top Right
    // this.topRightCornerX = (y- height/2) + (x+width/2) ;
    // this.topRightCornerY = (y - height/2) - (x+width/2) ;
    // // Bottom Left
    // this.bottomLeftCornerX = (y + height/2) + (x-width/2) ;
    // this.bottomLeftCornerY = (y+ height/2)   - (x -width/2);
    // // Bottom Right
    // this.bottomRightCornerX = (y+ height/2)  + (x+width/2) ;
    // this.bottomRightCornerY = (y +height/2) - (x+width/2) ;

    System.out.println("------------------------------");
    System.out.println("X: " + positionX + "  Y : " + positionY);
    System.out.println("Width: " + width);

    System.out.println("X plus half of width: " + (positionX+ width/2)) ;


    System.out.println("Top Left Corner = (" +  topLeftCornerX +"," +topLeftCornerY +")" );
    System.out.println("Top Right Corner = (" + topRightCornerX + "," +topRightCornerY +")");

    System.out.println("Bottom Left Corner = ("+ bottomLeftCornerX +"," +bottomLeftCornerY +")");
    System.out.println("Bottom Right Corner = (" + bottomRightCornerX + "," +bottomRightCornerY +")");

  }
  public boolean collision (Shape shape){
    if (shape.contains(topLeftCornerX,topLeftCornerY)){
      return true;
    }
    else if (shape.contains(topRightCornerX,topRightCornerY)){
          return true;
    }
    else if (shape.contains(bottomRightCornerX,bottomRightCornerY)){
          return true;
    }
    else if (shape.contains(bottomLeftCornerX,bottomLeftCornerY)){
      return true;
    }
    else{
      return false;
    }
  }
  public double getTopLeftCornerX(){
    return topLeftCornerX;
  }
  public double getTopLeftCornerY(){
    return topLeftCornerY;
  }
  // public void set(double bottomLeftCorner){
  //   this.bottomLeftCorner = bottomLeftCorner;
  // }
  // public double getBottomRightCorner(){
  //   return bottomRightCorner;
  // }
  // public void set(double bottomRightCorner){
  //   this.bottomRightCorner = bottomRightCorner;
  // }
}
