public class Tank
{
  // Keyboard input
  boolean left,right,forward,reverse=false;

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


  public Tank(double positionX, double positionY, double velocity, double turnSpeed, double turretSpeed){
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
  }

  public void turnRight(double dt) {
    hullAngle += turnSpeed * dt;
			if (hullAngle > 360) {
				setHullAngle(0);
			}
  }

  public void turnLeft(double dt) {
    hullAngle -= turnSpeed * dt;
			if(hullAngle < -360) {
				setHullAngle(0);
			}
  }

  public void moveForward(double dt) {
    positionX += Math.cos(Math.toRadians(hullAngle-90)) * velocityX * dt;
    positionY += Math.sin(Math.toRadians(hullAngle-90)) * velocityY * dt;
  }

  public void moveBackward(double dt) {
    positionX -= Math.cos(Math.toRadians(hullAngle-90)) * velocityX * dt;
    positionY -= Math.sin(Math.toRadians(hullAngle-90)) * velocityY * dt;
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
}
