
// package massey;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class TankGame extends GameEngine {
// Main Function
public static void main(String args[]) {
	// Warning: Only call createGame in this function
	createGame(new TankGame());
}

/* GameState
public GameState getGameState() {
	return state;
}
public void setGameState(GameState par1State) {
	this.state = par1State;
} */



int width = 1024;
int height = 1024;
// Tank types
int tankHeightE100 = 190;
int tankWidthE100 = 96;

boolean explosion;
int explosionX,explosionY;
//-------------------------------------------------------
// Tank Objects
//-------------------------------------------------------
// Image of the 1st player
Image playerTankImage;
Image playerTurretImage;
Tank playerOne;
// Image of the 2nd player1
Tank playerTwo;
// Tank sound effects
AudioClip TankMovingMusic =  loadAudio("Music\\tankDriving.wav");
AudioClip TankFire =  loadAudio("Music\\fire.wav");
AudioClip TankExplosion =  loadAudio("Music\\explosion.wav");

// Explosion animation

// Image explosionSheet;
// Image[] frames;
// int currentFrame;
// double animTime;

// Tank[] playerTankList;

;
// Init player Function
public void initPlayerTank() {
// 	explosionSheet = loadImage("Bullets\\explosion.png");
// 	frames = new Image[100];
//
// 	// Load Images
// 	for(int iy = 0; iy < 7; iy++) {
// 		for(int ix = 0; ix < 3; ix++) {
// //			frames[iy*6 + ix] = subImage(explosionSheet, ix*230, iy*296, 234, 181);
// 			frames[iy*6 + ix] = subImage(explosionSheet, ix*22, iy*28, 22, 28);
//
// 		}
// 	}

	// Start Animation Time
	// animTime = 0;
	// Load the player Tank sprite

	playerTankImage   = subImage(E100SpriteSheet,99, 91, 90, 110);

	playerTurretImage = subImage(E100SpriteSheet, 11, 24, 67, 127);
	playerOne = new Tank(200,300, 100, 75, 125,94,162);
	playerTwo = new Tank(width()/2-100, height()/2, 100, 75, 125,tankHeightE100,tankWidthE100);
	// bulletImage = subImage(bulletImageSprite,0,0,16,16);
}

// Health
public void drawHealthBar(Tank tank){
	changeColor(white);
	drawText(20, 20, "Health:", "Arial", 20);
	changeColor(red);
	drawSolidRectangle(90, 0, tank.getHealth(), 25);
}

// Score Board
int Score = 0;
public void updateScore(){
	Score+=1;
}

public void drawScoreBoard(int Score){
	changeColor(white);
	drawText(900, 20, "Score:", "Arial", 20);
	drawText(960, 20, Integer.toString(Score), "Arial", 20);
}
// Draw the tank body
public void drawTank(Tank tank) {
	if (tank.getHealth()>0){
	// Save the current transform
	saveCurrentTransform();
	translate(tank.getPositionX(), tank.getPositionY());
	rotate(tank.getHullAngle());

	// Draw the tank
	drawImage(playerTankImage, -45, -60.5);
	// Restore last transform to undo the rotate and translate transforms
	restoreLastTransform();
}
}
// Draw the tank turret
public void drawTurret(Tank tank) {
	if (tank.getHealth()>0){
	saveCurrentTransform();
	translate(tank.getPositionX(), tank.getPositionY());
	rotate(tank.getTurretAngle());
	//rotate(playerTurretAngle+90);
	drawImage(playerTurretImage, -32, -60.5);
	restoreLastTransform();
}
}
// Update the tank body
// if updateTank is false it means that it crashed
public void updateTank(double dt, Tank tank) {
	// Wrap the left side to the right side
	if (tank.getPositionX()+tank.getRadius()<0){
		tank.setPositionX(tank.getPositionX()+width);
	}
	// Wrap the right side to the left side
	if (tank.getPositionX()+tank.getRadius()>=width){
		tank.setPositionX(tank.getPositionX()-width);
	}
	// Collision detection with the top
	if (tank.getPositionY()+tank.getRadius()<=0){
		tank.setPositionY(tank.getPositionY()+height);
	}

	// Collision detection with the bottom
	if (tank.getPositionY()+tank.getRadius()>=height){
		tank.setPositionY(tank.getPositionY()-height);
	}
	if (tank.getHealth()<0){
		// Make tank explode
		System.out.println("Tank Exploded");
	}
	if(tank.getForward() == true) {
		TankMoveSound(playerOne);
		tank.moveForward(dt);
	}
	if (tank.getReverse() == true) {
		TankMoveSound(playerOne);
		tank.moveBackward(dt);
	}
	// If the user is holding down the left arrow key
	if(tank.getLeft() == true) {
		TankMoveSound(playerOne);
		tank.turnLeft(dt);
	}
	// If the user is holding down the right arrow key
	if(tank.getRight() == true) {
		TankMoveSound(playerOne);
		tank.turnRight(dt);
	}
}
// Update the tank turret
public void updateTurret(double dt, Tank playerOne) {
	double targetAngle = workOutAngle(playerOne.getPositionX(), playerOne.getPositionY(), mouseX, mouseY);
	//This makes the turret track exactly to the cursor
	playerOne.setTurretAngle(targetAngle + 90);
}
//Player 2 turret
    public void updateTurretP2(double dt, Tank playerTwo) {
        double targetAngle = workOutAngle(playerTwo.getPositionX(), playerTwo.getPositionY(), playerTwo.getPositionX(), playerTwo.getPositionY());

        // If the user is holding down the left arrow key
        if(playerTwo.getTurretMovingLeft() == true) {
            playerTwo.setTurretAngle(playerTwo.getTurretAngle() - playerTwo.getTurretSpeed() * dt);
            if(playerTwo.getTurretAngle() < -360) {
                playerTwo.setTurretAngle(0);
            }
        }
        // If the user is holding down the right arrow key
        if(playerTwo.getTurretMovingRight() == true) {
            playerTwo.setTurretAngle(playerTwo.getTurretAngle() + playerTwo.getTurretSpeed() * dt);
            if(playerTwo.getTurretAngle() > 360) {
                playerTwo.setTurretAngle(0);
            }
        }
    }


// Function to draw the laser
public void drawBullet(Tank object) {
	Bullet bullet = object.getBullet();

	// Save the current transform
	saveCurrentTransform();

	translate(bullet.getPositionX()-8, bullet.getPositionY());

	// Rotate the drawing context around the angle of the asteroid
	rotate(bullet.getAngle());

	// Draw the actual laser
	drawImage(bulletImageSprite,-7,-10);
	// Restore last transform to undo the rotate and translate transforms
	restoreLastTransform();
}
// Update bullet
// TODO
public void updateLaser(double dt, Tank object){
	Bullet laser = object.getBullet();
	// Update the laser

	if(laser.getFire() == true) {
		// Increase the velocity of the spaceship
		// as determined by the angle
		laser.setVelocityX (laser.getVelocityX() + sin(laser.getAngle()) * 250 * dt);
		laser.setVelocityY (laser.getVelocityY() - cos(laser.getAngle()) * 250 * dt);
	}
	laser.setPositionX(laser.getPositionX()+ laser.getVelocityX() * dt);
	laser.setPositionY(laser.getPositionY()+ laser.getVelocityY() * dt);
	object.setBullet(laser);

	for (Tank enemy: enemyTankList){
		if ((enemy.getHealth()>0)&& laser.getFire()){
		if (distance (enemy.getPositionX(),enemy.getPositionY(),laser.getPositionX(),laser.getPositionY()) < enemy.getRadius()){
			updateScore();
			explosion = true;

			explosionX = (int)enemy.getPositionX();
			explosionY = (int)enemy.getPositionY();

			laser.setPositionX(0);
			laser.setPositionY(0);
			laser.setVelocityX(0);
			laser.setVelocityY(0);
			laser.setFire(false);

			enemy.setHealth(0);
			// Add explosion animation
		}
	}
	}
}
// Used in the turret
public double workOutAngle(double originX, double originY, double targetX, double targetY) {
	double angle = atan2(targetY - originY, targetX - originX);
	/* if (angle < 0) {
		angle += 360;
	} */
	return angle;
}
// Fire Laser
public void fireLaser(Tank object){
	// Bullet bullet = new Bullet();
	// object.setBullet(bullet);
	Bullet bullet = object.getBullet();
	while(!bullet.getFire()){

		bullet.setPositionX(object.getPositionX());
		bullet.setPositionY(object.getPositionY());

		bullet.setVelocityX (sin(object.getTurretAngle())* 250);
		bullet.setVelocityY (-cos(object.getTurretAngle())* 250);

		bullet.setAngle(object.getTurretAngle());

		// Set it to active
		bullet.setFire(true);

		}
}

// Detect collision
public void detectCollision(){
		System.out.println("--------------------------");

		System.out.println("Center position (" + playerOne.getPositionX()+ " , "+ playerOne.getPositionY()+")");

}

//-------------------------------------------------------
// AI Tanks
//-------------------------------------------------------

Tank[] enemyTankList;
Image[] enemyTankImageList;
Image[] enemyTurretImageList;

//Background
Image backgroundImage;
int enemyTankSpeed;
int enemyTurretSpeed;
int enemyTurnSpeed;
double randX;
double randY;
boolean tooClose;

//Create tanks and set properties
private void initEnemyTankList() {
	enemyTankList = new Tank[numberOfEnemyTanks];
	enemyTankImageList = new Image[numberOfEnemyTanks];
	enemyTurretImageList = new Image[numberOfEnemyTanks];
	enemyTankSpeed = 100;
	enemyTurretSpeed = 125;
	enemyTurnSpeed = 75;
	tooClose = true;

	for (int i = 0; i < enemyTankList.length; i++) {
		randX = rand(1024);
		randY = rand(1024);
		//Make enemies not too close to player or each other
		while (distance(randX, randY, playerOne.getPositionX(), playerOne.getPositionY()) < 100 && tooClose) {
			randX = rand(1024);
			randY = rand(1024);
			for(int j = 0; j < i; j++) {
				if (enemyTankList[j] != null) {
					if(distance(enemyTankList[i].getPositionX(), enemyTankList[i].getPositionY(), enemyTankList[j].getPositionX(), enemyTankList[j].getPositionY()) < 100) {
						tooClose = true;
					} else {
						tooClose = false;
					}
				}
			}
		}
		enemyTankList[i] = new Tank(randX, randY, enemyTankSpeed, enemyTurnSpeed, enemyTurretSpeed, tankHeightE100, tankWidthE100);
		enemyTankList[i].setHullAngle(rand(360));
		enemyTankList[i].setTurretAngle(rand(360));
	}

	for (int i = 0; i < enemyTankImageList.length; i++) {

//		 playerTankImage   = subImage(E100SpriteSheet,99, 91, 90, 110);
//		 playerTurretImage = subImage(E100SpriteSheet, 11, 24, 67, 127);
		enemyTankImageList[i] = subImage(E100SpriteSheet,99, 91, 90, 110);
	}

	for (int i = 0; i < enemyTurretImageList.length; i++) {
		enemyTurretImageList[i] = subImage(E100SpriteSheet, 11, 24, 67, 127);
	}
}

private void updateEnemyTankList(double dt) {
	for (Tank tank : enemyTankList) {
//		Bullet bullet = tank.getBullet();
//		// Update the laser
//
//		if(tank.getFire() == true) {
//			// Increase the velocity of the spaceship
//			// as determined by the angle
//			bullet.setVelocityX (bullet.getVelocityX() + sin(bullet.getAngle()) * 250 * dt);
//			bullet.setVelocityY (bullet.getVelocityY() - cos(bullet.getAngle()) * 250 * dt);
//		}
//		tank.setBullet(bullet);


		if (tank.getHealth() > 0) {
			tank.setForward(true);
			//Keep within bounds
			if (tank.getPositionX() > 1024 + tank.getHeight()) {
				tank.setHullAngle(workOutAngle(tank.getPositionX(), tank.getPositionY(), 512, 512) + 90);
			}
			if (tank.getPositionY() > 1024 + tank.getHeight()) {
				tank.setHullAngle(workOutAngle(tank.getPositionX(), tank.getPositionY(), 512, 512) + 90);
			}
			if (tank.getPositionX() < 0 - tank.getHeight()) {
				tank.setHullAngle(workOutAngle(tank.getPositionX(), tank.getPositionY(), 512, 512) + 90);
			}
			if (tank.getPositionY() < 0 - tank.getHeight()) {
				tank.setHullAngle(workOutAngle(tank.getPositionX(), tank.getPositionY(), 512, 512) + 90);
			}
			updateTank(dt, tank);
//			 if (touch != null){
//				 System.out.println("Enemy touched at the" + touch);
//
//			 }

			double angleToPlayer = workOutAngle(tank.getPositionX(), tank.getPositionY(), playerOne.getPositionX(), playerOne.getPositionY());
			if(checkTargetInSight(tank, playerOne) == true) {
				tank.setHullAngle(angleToPlayer);
			}
			if(checkTargetInFiringRange(tank, playerOne) == true) {
				tank.setTurretAngle(angleToPlayer + 90);
//				Random rand = new Random();
//
//				int  n = rand.nextInt(51) + 1;
//				if (n == 50){
//				Bullet bullet = tank.getBullet();
//				bullet.setFire(true);
//				tank.setBullet(bullet);
//				fireLaser(tank);
//				playAudio(TankFire);
//				playAudio(TankExplosion);
//				}
				}
		}

	}
}

private void drawEnemyTankList() {
	for (int i = 0; i < numberOfEnemyTanks; i++) {

		if (enemyTankList[i].getHealth()>0){

		saveCurrentTransform();
		translate(enemyTankList[i].getPositionX(),enemyTankList[i].getPositionY());
		rotate(enemyTankList[i].getHullAngle());
		// Draw the tank
		drawImage(enemyTankImageList[i], -45, -60.5);
		// Restore last transform to undo the rotate and translate transforms
		restoreLastTransform();
		}
	}
}

private void drawEnemyTurretList() {
	for (int i = 0; i < numberOfEnemyTanks; i++) {
		if (enemyTankList[i].getHealth()>0){

			saveCurrentTransform();
			translate(enemyTankList[i].getPositionX(), enemyTankList[i].getPositionY());
			rotate(enemyTankList[i].getTurretAngle());
			drawImage(enemyTurretImageList[i], -32, -60.5);
			restoreLastTransform();
		}
	}
}

private boolean checkTargetInSight(Tank aiTank, Tank targetTank) {
	if (distance(aiTank.getPositionX(), aiTank.getPositionY(), targetTank.getPositionX(), targetTank.getPositionY()) < 600) {
		return true;
	}
	return false;
}

private boolean checkTargetInFiringRange(Tank aiTank, Tank targetTank) {
	if (distance(aiTank.getPositionX(), aiTank.getPositionY(), targetTank.getPositionX(), targetTank.getPositionY()) < 500) {
		return true;
	}
	return false;
}

//-------------------------------------------------------
// Game
//-------------------------------------------------------
// Spritesheet
Image E100SpriteSheet;
Image T34;
Image kv2Spritesheet;
Image m6Spritesheet;
Image pz4Spritesheet;
Image pz4gSpritesheet;
Image t34Spritesheet;
Image tiger2Spritesheet;
Image vk3601hSpritesheet;
//Menu Screen
Image menuImage;
//Paused Screen
Image pausedImage;
//Game Over screen
Image gameOverImage;
// Bullets
Image bulletImageSprite;
// Keep track of keys
boolean playerOneLeft, playerOneRight, playerOneForward, playerOneReverse;
boolean playerTwoLeft, playerTwoRight, playerTwoForward, playerTwoReverse;

//boolean gameOver, menuState, gamePause;
boolean player1, player2;

int numberOfEnemyTanks;

int mouseX;
int mouseY;

//GameState
private void TankMoveSound(Tank object){
	if (object.getMovement() == false){
		// start audioClip
		startAudioLoop(TankMovingMusic,5);
		object.setMovement(true);
	}
}
private void TankStopSound(Tank object){
	if (object.getMovement() == true){
		// start audioClip
		object.setMovement(false);
		stopAudioLoop(TankMovingMusic);
	}
}
private GameState state = GameState.MENU;

//
// Function to initialise the game
public void init() {

	setWindowSize(width, height);
	// Load sprites
	E100SpriteSheet = loadImage("Tanks\\E100b.png");

	T34 = loadImage("Tanks\\T34.png");
	//Load Menu Image
	menuImage = loadImage("Menu\\TankGame.png");
	//Load Paused Image
	pausedImage = loadImage("Paused\\PausedImage.png");
	//Load Game Over Image
	gameOverImage = loadImage("GameOver\\GameOverImage.png");
	// Load bullet Image

	bulletImageSprite = loadImage("Bullets\\bullet.png");

	//Load Background Image
	backgroundImage = loadImage("Background\\Background.png");
	//Load and play Menu Music
	AudioClip menuMusic = loadAudio("Music\\MenuMusic.wav");

	startAudioLoop(menuMusic,1);
	// load tank driving Sound

	numberOfEnemyTanks = 7;

	// Setup Game booleans
	//gameOver = true;
	//menuState = true;
	mouseX = 0;
	mouseY = 0;
	player1 = false;
	player2 = false;

	// Initialise player
	initPlayerTank();
	initEnemyTankList();

}
// Calculates the
// public int getFrame(double d, int num_frames) {
// 	return (int)Math.floor(((animTime % d) / d) * num_frames);
// }
public void detectContact(String object1, String object2){
	// this will detect the contact between two things
	if (object1 == "players" && object2 == "enemies"){
//		 System.out.println("Here");
		// it will take the enemies and will compare it with the first player
		for (int j = 0; j < enemyTankList.length;j++){
		Tank enemy = enemyTankList[j];
		if (enemy.getHealth()>0){
		if(distance(enemy.getPositionX(), enemy.getPositionY(), playerOne.getPositionX(), playerOne.getPositionY()) < playerOne.getRadius()*1.2) {
			System.out.println("Player is touching an enemy");
			enemy.setHullAngle(workOutAngle(enemy.getPositionX(), enemy.getPositionY(), 512, 512) + 180);

			if (playerOne.getForward() == true){
				playerOne.setForward(false);
 //			 enemy. (true);

 //			 enemy.setPositionY(tank.getPositionY()-1);
			}
			else if (playerOne.getReverse()==true){
				playerOne.setReverse(false);
 //			 enemy.setForward(true);
 //			 enemy.setPositionY(tank.getPositionY()-1);
				}
			}
		}
		}

	}
//	 else if(object1 == "players" && object2 == "players"){
////		 System.out.println("Here");
//		 // it will take the enemies and will compare it with the first player
//		 for (int j = 0; j < enemyTankList.length;j++){
//		 Tank enemy = enemyTankList[j];
//		 if(distance(enemy.getPositionX(), enemy.getPositionY(), playerOne.getPositionX(), playerOne.getPositionY()) < playerOne.getRadius()*1.2) {
//			 System.out.println("Player is touching an enemy");
//			 enemy.setHullAngle(workOutAngle(enemy.getPositionX(), enemy.getPositionY(), 512, 512) + 180);
//
//			 if (playerOne.getForward() == true){
//				 playerOne.setForward(false);
//	//			 enemy. (true);
//
//	//			 enemy.setPositionY(tank.getPositionY()-1);
//			 }
//			 else if (playerOne.getReverse()==true){
//				 playerOne.setReverse(false);
//	//			 enemy.setForward(true);
//	//			 enemy.setPositionY(tank.getPositionY()-1);
//				 }
//			 }
//		 }
//	 }
	else if(object1 == "enemies" && object2 == "enemies"){
//		 System.out.println("Here");
		// it will take the enemies and will compare it with the first player
		for (int j = 0; j < enemyTankList.length;j++){
			Tank enemy1 = enemyTankList[j];
			if (enemy1.getHealth()>0){

			for (int jx = 0; jx < enemyTankList.length;jx++){

				if (j!=jx){
					Tank enemy2 = enemyTankList[jx];
					if (enemy2.getHealth()>0){
					if(distance(enemy1.getPositionX(), enemy1.getPositionY(), enemy2.getPositionX(), enemy2.getPositionY()) < enemy1.getRadius()*1.2) {
						enemy1.setHullAngle(workOutAngle(enemy1.getPositionX(), enemy1.getPositionY(), 512, 512) + 180);
						enemy2.setHullAngle(workOutAngle(enemy2.getPositionX(), enemy2.getPositionY(), 512, 512) + 90);

					}
				}
				}
			}
		}
		}
	}
}
//
// Updates the display
public void update(double dt) {
	// animTime += dt;
	// currentFrame = (currentFrame + 1) % 30;
	// If the game is over
	if(state == GameState.GAMEOVER) {
		// Don't try to update anything.
		return;
	}
	if (state == GameState.PLAYING) {

		// Update the players
		updateTank(dt, playerOne);

		updateTurret(dt,playerOne);
		updateTank(dt, playerTwo);
		updateTurretP2(dt,playerTwo);
		updateLaser(dt,playerOne);
		updateLaser(dt,playerTwo);
		updateEnemyTankList(dt);
		// detectCollision();
		// if (tankCollision!= null){
		// 	System.out.println("Stop bullet");
		// 	Bullet bulletToStop = tankCollision.getBullet();
		// 	bulletToStop.setPositionX(0);
		// 	bulletToStop.setPositionY(0);
		// 	bulletToStop.setVelocityX(0);
		// 	bulletToStop.setVelocityY(0);
		// }
		detectContact("players","enemies");
		detectContact("enemies","enemies");

	}
	if (state == GameState.MENU) {
		initPlayerTank();
	}
}
// This gets called any time the Operating System
// tells the program to paint itself
public void paintComponent() {

	// Clear the background to black
	changeBackgroundColor(black);
	clearBackground(width(), height());
	// If the game is not over yet
	if(state == GameState.PLAYING) {
		drawImage(backgroundImage, width()-1024, height()-1024);
		if (explosion==true){
			//TODO
			System.out.println("BOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOM");
		}
		explosion = false;

		//Display pause info in game
		changeColor(105,105,105);
		drawBoldText(width()-195, height()-998, "Press Esc to Pause Game", "Arial", 15);
		changeColor(white);

		// Draw the players
		//If only 1 player
		if (player1 == true) {
			drawBullet(playerOne);
			drawTank(playerOne);
			drawTurret(playerOne);

		//If 2 player
		} else if (player2 == true) {
			drawBullet(playerOne);
			drawBullet(playerTwo);
			drawTank(playerOne);
			drawTurret(playerOne);
			drawTank(playerTwo);
			drawTurret(playerTwo);


		}

		drawEnemyTankList();
		drawEnemyTurretList();
		drawScoreBoard(Score);

	//
		drawHealthBar(playerOne);
//		drawBullets();
		// Draw "Trees"


	// If the game is at menu
	} else if(state == GameState.MENU) {
		//Insert Menu screen
		drawImage(menuImage, width()-1024, height()-1024);
		player1 = false;
		player2 = false;

		//If the game is over
	} else if (state == GameState.GAMEOVER) {
		// Display GameOver text
		if (player1 == true) {
			//Display player1 score here
			//Display Game Over Image
			drawImage(gameOverImage, width()-1024, height()-1024);
		} else if (player2 == true) {
			//Display both player scores
			//Display Game Over Image
			drawImage(gameOverImage, width()-1024, height()-1024);
		}

	//If game is paused
	} else  if (state == GameState.PAUSE) {
		//Insert Paused screen
		drawImage(pausedImage, width()-1024, height()-1024);
	}
}
// Called whenever a key is pressed
public void keyPressed(KeyEvent e) {
	// Keys to move the tank
	// First player
	// Go left
	if(e.getKeyCode() == KeyEvent.VK_A) {
		playerOne.setLeft(true);
		TankMoveSound(playerOne);
	}
	// Go right
	if(e.getKeyCode() == KeyEvent.VK_D) {
		playerOne.setRight(true);
		TankMoveSound(playerOne);
	}
	// Go forwards
	if(e.getKeyCode() == KeyEvent.VK_W) {
		playerOne.setForward(true);
		TankMoveSound(playerOne);

	}
	// Go backwards
	if(e.getKeyCode() == KeyEvent.VK_S) {
		playerOne.setReverse(true);
		TankMoveSound(playerOne);

	}
	// The user fired
	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		// To fire
		Bullet bullet = playerOne.getBullet();
		bullet.setFire(true);
		playerOne.setBullet(bullet);
		fireLaser(playerOne);
		playAudio(TankFire);
		playAudio(TankExplosion);
	}

	// Second player
	// Go left
	if(e.getKeyCode() == KeyEvent.VK_J) {
		playerTwo.setLeft(true);
		// TankMoveSound(playerTwo);

	}
	// Go right
	if(e.getKeyCode() == KeyEvent.VK_L) {
		playerTwo.setRight(true);
		// TankMoveSound(playerTwo);

	}
	// Go forwards
	if(e.getKeyCode() == KeyEvent.VK_I) {
		playerTwo.setForward(true);
		// TankMoveSound(playerTwo);
	}
	// Go backwards
	if(e.getKeyCode() == KeyEvent.VK_K) {
		playerTwo.setReverse(true);
	}
	// The user fired
	if(e.getKeyCode() == KeyEvent.VK_M) {
		// To fire
		Bullet bullet = playerTwo.getBullet();
		bullet.setFire(true);
		playerTwo.setBullet(bullet);
		fireLaser(playerTwo);
	}
	//Turret
    if(e.getKeyCode() == KeyEvent.VK_LEFT) {
        playerTwo.setTurretMovingLeft(true);
        playerTwo.setTurretMovingRight(false);

    }
    if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
        playerTwo.setTurretMovingRight(true);
        playerTwo.setTurretMovingLeft(false);
    }


	//GameState Buttons
	// The user pressed Escape key - PAUSE
	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)  {
		state = GameState.PAUSE;
	}

	//If in Pause GameState
	if (state == GameState.PAUSE) {
		// The user pressed Q - QUIT/GAMEOVER
		if(e.getKeyCode() == KeyEvent.VK_Q)  {
			state = GameState.GAMEOVER;
		}
		// The user pressed R - RESUME
		if(e.getKeyCode() == KeyEvent.VK_R)  {
			state = GameState.PLAYING;
		}
		// The user pressed M - MENU
		if(e.getKeyCode() == KeyEvent.VK_M)  {
			state = GameState.MENU;
		}
	}

	//If in Menu GameState
	if (state == GameState.MENU) {
		// The user pressed 1 - 1 PLAYER
		if(e.getKeyCode() == KeyEvent.VK_1)  {
			player1	= true;
			state = GameState.PLAYING;
		// The user pressed 2 - 2 PLAYER
		} else if(e.getKeyCode() == KeyEvent.VK_2)  {
			player2	= true;
			state = GameState.PLAYING;
		}
	}
	if (state == GameState.GAMEOVER) {
		// The user pressed M - MENU
		if(e.getKeyCode() == KeyEvent.VK_M)  {
			state = GameState.MENU;
		}
	}
}
// Called whenever a key is released
public void keyReleased(KeyEvent e) {

	if (playerOne.getMovement() == true){
		// start audioClip
		TankStopSound(playerOne);
		playerOne.setMovement(false);
	}
	if (playerTwo.getMovement() == true){
		// start audioClip
		TankStopSound(playerOne);
		playerTwo.setMovement(false);
	}


	// Go left button released
	if(e.getKeyCode() == KeyEvent.VK_A) {
		playerOne.setLeft(false);
		TankStopSound(playerOne);

	}
	// Go right button released
	if(e.getKeyCode() == KeyEvent.VK_D) {
		playerOne.setRight(false);
		TankStopSound(playerOne);

	}
	// Go forwards button released
	if(e.getKeyCode() == KeyEvent.VK_W) {
		playerOne.setForward(false);
		TankStopSound(playerOne);

	}
	// Go backwards button released
	if(e.getKeyCode() == KeyEvent.VK_S) {
		playerOne.setReverse(false);
		TankStopSound(playerOne);

	}
	// The user fired button released
	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		Bullet bullet = playerOne.getBullet();
		bullet.setFire(false);
		playerOne.setBullet(bullet);
		fireLaser(playerOne);
	}

	// Second player
	// Go left button released
	if(e.getKeyCode() == KeyEvent.VK_J) {
		playerTwo.setLeft(false);
		TankStopSound(playerTwo);

	}
	// Go right button released
	if(e.getKeyCode() == KeyEvent.VK_L) {
		playerTwo.setRight(false);
		TankStopSound(playerTwo);

	}
	// Go forwards button released
	if(e.getKeyCode() == KeyEvent.VK_I) {
		playerTwo.setForward(false);
		TankStopSound(playerTwo);

	}
	// Go backwards button released
	if(e.getKeyCode() == KeyEvent.VK_K) {
		playerTwo.setReverse(false);
		TankStopSound(playerTwo);

	}
	// The user fired button released
	if(e.getKeyCode() == KeyEvent.VK_M) {
		// To fire
		Bullet bullet = playerTwo.getBullet();
		bullet.setFire(false);
		playerTwo.setBullet(bullet);
		fireLaser(playerTwo);
	}
	// Go left turret button released
  if(e.getKeyCode() == KeyEvent.VK_LEFT) {
      playerTwo.setTurretMovingLeft(false);
      playerTwo.setTurretMovingRight(false);
  }
  // Go right turret button released
  if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
      playerTwo.setTurretMovingRight(false);
      playerTwo.setTurretMovingLeft(false);
  }
	// Clear the background to black
	changeBackgroundColor(black);
	clearBackground(width(), height());

	// If the game is not over yet
	if(state == GameState.PLAYING) {
		// Draw player 1
		drawTank(playerOne);
		drawTurret(playerOne);

		//Draw player 2
		drawTank(playerTwo);
		drawTurret(playerTwo);

	// If the game is over
	} else if (state == GameState.GAMEOVER){
		// Display GameOver text
		changeColor(white);
		drawText(width()/2-165, height()/2, "GAME OVER!", "Arial", 50);
	}
}
// Caled when mouse is moved
public void mouseMoved(MouseEvent event) {
	mouseX = event.getX();
	mouseY = event.getY();
}


}
