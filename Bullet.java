public class Bullet
{
  // Angles
  private double angle;
  // Bullet
  private boolean fire;

  // position
  private double positionX = 0;
  private double positionY = 0;

  // velocity
  private double velocityX = 0;
  private double velocityY = 0;

  private int radius = 16;
  public Bullet(double positionX, double positionY,double velocityX , double velocityY){
    this.positionX = positionX;
    this.positionY = positionY;
    this.velocityX = velocityX;
    this.velocityY = velocityY;
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
  public void setAngle(double angle){
    this.angle = angle;
  }
  public double getAngle(){
    return angle;
  }
  public void setFire(boolean fire){
    this.fire = fire;
  }
  public boolean getFire(){
    return fire;
  }
  public void setRadius(int radius){
    this.radius = radius;
  }
  public int getRadius(){
    return radius;
  }
}
