package gameClasses;

public class Player
{
  // Player position
  private double playerPositionX;
  private double playerPositionY;

  // Player velocity
  private double playerVelocityX;
  private double playerVelocityY;

  // player angle
  private double playerAngle;
  private double playerTurretAngle;

  // Turret
  private boolean turretMovingLeft;
  private boolean turretMovingRight;
  private int turretSpeed;

  public Player(double playerPositionX, double playerPositionY){
    this.playerPositionX = playerPositionX;
    this.playerPositionY = playerPositionY;
    this.playerVelocityX = 0;
    this.playerVelocityY = 0;
    this.playerTurretAngle = 90;
    this.playerAngle = 0;
    this.turretSpeed = 120;
    this.turretMovingLeft = false;
    this.turretMovingRight = false;
  }

  // Positions
  public void setPlayerPositionX(double playerPositionX){
    this.playerPositionX = playerPositionX;
  }
  public double getPlayerPositionX(){
    return playerPositionX;
  }

  public void setPlayerPositionY(double playerPositionY){
    this.playerPositionY = playerPositionY;
  }
  public double getPlayerPositionY(){
    return playerPositionY;
  }
  // Velocities
  public void setPlayerVelocityX(double playerVelocityX){
    this.playerVelocityX = playerVelocityX;
  }
  public double getPlayerVelocityX(){
    return playerVelocityX;
  }
  public void setPlayerVelocityY(double playerVelocityY){
    this.playerVelocityY = playerVelocityY;
  }
  public double getPlayerVelocityY(){
    return playerVelocityY;
  }
  // Angles
  public void setPlayerAngle(double playerAngle){
    this.playerAngle = playerAngle;
  }
  public double getPlayerAngle(){
    return playerAngle;
  }
  public void setPlayerTurretAngle(double playerTurretAngle){
    this.playerTurretAngle = playerTurretAngle;
  }
  public double getPlayerTurretAngle(){
    return playerTurretAngle;
  }
  public void setTurretMovingLeft(boolean turretMovingLeft){
  // Moving objects
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
  public int getTurretSpeed(){
    return turretSpeed;
  }


}
