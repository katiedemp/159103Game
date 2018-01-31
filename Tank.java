public class Tank
{
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

  private String tankType;

  private int height;
  private int width;
  public Tank(double positionX, double positionY, double velocity, double turnSpeed, double turretSpeed, String tankType){
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
    this.tankType = tankType;
    if (tankType == "E100") {
		this.height = 207;
    	this.width = 96;
	}
	if (tankType == "KV2") {
		this.height = 254;
    	this.width = 132;
	}
	if (tankType == "M6") {
		this.height = 270;
    	this.width = 109;
	}
	if (tankType == "PZ4G") {
		this.height = 253;
    	this.width = 129;
  }
  if (tankType == "PZ4") {
		this.height = 241;
    	this.width = 123;
  }
  if (tankType == "T34") {
		this.height = 178;
    	this.width = 90;
  }
  if (tankType == "VK3601h") {
		this.height = 243;
    	this.width = 127;
	}


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
}
