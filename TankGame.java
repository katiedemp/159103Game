import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;



// import javafx.scene.shape.*;


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
	// Tank types
	int tankHeightE100 = 207;
	int tankWidthE100 = 96;
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
;
	// Init player Function
	public void initPlayerTank() {
		// Load the player Tank sprite
		playerTankImage   = subImage(E100SpriteSheet,96, 0, tankWidthE100, tankHeightE100);
		playerTurretImage = subImage(E100SpriteSheet, 0, 0, tankWidthE100, tankHeightE100);
		playerOne = new Tank(width()/2, height()/2, 100, 75, 125,tankHeightE100,tankWidthE100);
		playerTwo = new Tank(width()/2-100, height()/2, 100, 75, 125,tankHeightE100,tankWidthE100);
		// bulletImage = subImage(bulletImageSprite,0,0,16,16);
	}
	// Draw the tank body
	public void drawTank(Tank tank) {
		// Save the current transform
		saveCurrentTransform();
		translate(tank.getPositionX(), tank.getPositionY());
		rotate(tank.getHullAngle());

		// Draw the tank
		drawImage(playerTankImage, -48, -103.5);
		// Restore last transform to undo the rotate and translate transforms
		restoreLastTransform();
	}
	// Draw the tank turret
	public void drawTurret(Tank tank) {
		saveCurrentTransform();
		translate(tank.getPositionX(), tank.getPositionY());
		rotate(tank.getTurretAngle());
		//rotate(playerTurretAngle+90);
		drawImage(playerTurretImage, -48, -103.5);
		restoreLastTransform();
	}
	// Update the tank body
	public void updateTank(double dt, Tank tank) {
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

		//This is to have the turret track to the cursor slowly, not perfect
		/* if (playerTurretAngle > targetAngle) {
			playerTurretAngle -= turretSpeed * dt;
		}
		if (playerTurretAngle < targetAngle) {
			playerTurretAngle += turretSpeed * dt;
		} */

		//This makes the turret track exactly to the cursor
		playerOne.setTurretAngle(targetAngle + 90);
	}
	// Draw Bullet
	// Function to draw the laser
	public void drawLaser(Tank object) {
		Bullet bullet = object.getBullet();
		// Draw the laser

		// Save the current transform
		saveCurrentTransform();

		translate(bullet.getPositionX()-8, bullet.getPositionY());

		// Rotate the drawing context around the angle of the asteroid
		rotate(bullet.getAngle());

		// Draw the actual laser
		drawImage(bulletImageSprite,-7,-100);
		// Restore last transform to undo the rotate and translate transforms
		restoreLastTransform();
	}
	// Update bullet
	// TODO change the keyword 'laser' to 'bullet'
	public void updateLaser(double dt, Tank object){
		Bullet laser = object.getBullet();
		// Update the laser

		if(laser.getFire() == true) {
			// Increase the velocity of the spaceship
			// as determined by the angle
			laser.setVelocityX (laser.getVelocityX() + sin(laser.getAngle()) * 250 * dt);
			laser.setVelocityY (laser.getVelocityY() - cos(laser.getAngle()) * 250 * dt);
		}

		// If the user is holding down the enter  key
		// Make the laser shoot multiple times
		// if(object.getLeft() == true) {
		// 	laser.setAngle(laser.getAngle() - 180 * dt);
		// }

		// If the user is holding down the right arrow key
		// Make the spaceship rotate clockwise
		// if(object.getRight() == true) {
		// 	laser.setAngle(laser.getAngle() + 180 * dt);
		// }

		// Make the spaceship move forward
		laser.setPositionX(laser.getPositionX()+ laser.getVelocityX() * dt);
		laser.setPositionY(laser.getPositionY()+ laser.getVelocityY() * dt);
    //
		// if (laserPositionY>500){
		// 	laserActive = false;
    //
		// }
		// else if (laserPositionY<0){
		// 	laserActive = false;
    //
		// }
		// else if (laserPositionX<0){
		// 	laserActive = false;
		// }
		// else if (laserPositionX>500){
		// 	laserActive = false;
    //
		object.setBullet(laser);
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
	// Overlap two rectangles
	public boolean overlaps (Rectangle first,Rectangle second) {
    return (second.x < first.x + first.width && second.x + second.width > first.x && second.y < first.y + first.height && second.y + second.height > first.y);
}
	// Detect collision
	public Tank detectCollision(){
		int tankE100Height = playerOne.getHeight();
		int tankE100Width = playerOne.getWidth();

		Rectangle playerOneHitArea = new Rectangle((int)playerOne.getPositionX(),(int)playerOne.getPositionY(),(int)playerOne.getWidth(),(int)playerOne.getHeight());

		Bullet playerOneBullet = playerOne.getBullet();
		// Circle playerOneBulletHitArea = new Circle ((int)playerOneBullet.getPositionX(),(int)playerOneBullet.getPositionY(),playerOneBullet.getRadius());
		Rectangle playerOneBulletHitArea = new Rectangle((int)playerOneBullet.getPositionX(),(int)playerOneBullet.getPositionY(),playerOneBullet.getRadius(),playerOneBullet.getRadius());

		Rectangle playerTwoHitArea = new Rectangle((int) playerTwo.getPositionX(),(int)playerTwo.getPositionY(),playerTwo.getWidth(),tankE100Height);
		Bullet playerTwoBullet = playerTwo.getBullet();
		Rectangle playerTwoBulletHitArea = new Rectangle((int)playerTwoBullet.getPositionX(),(int)playerTwoBullet.getPositionY(),playerTwoBullet.getRadius(),playerTwoBullet.getRadius());

    // Assuming there is an intersect method, otherwise just handcompare the values
    if (overlaps(playerOneHitArea,playerTwoBulletHitArea))
    {
			System.out.println("Player One was hit by the second player bullet.");
			playerOne.setHealth(playerOne.getHealth()-10);
			return playerTwo;
       // A Collision!
    }
		if (overlaps(playerTwoHitArea,playerOneBulletHitArea))
		{
			System.out.println("Player Two was hit by the second player bullet.");
			playerTwo.setHealth(playerTwo.getHealth()-10);
			return playerOne;
		}
		else{
			return null;
		}
		// if (playerOneHitArea.Intersects(playerTwoHitArea))
		// {
		// 	System.out.println("Player One was hit.");
		// 	 // A Collision!
		// }
	}

	//-------------------------------------------------------
	// AI Tanks
	//-------------------------------------------------------

	Tank[] enemyTankList;
	Image[] enemyTankImageList;
	Image[] enemyTurretImageList;
	int enemyTankSpeed;
	int enemyTurretSpeed;
	int enemyTurnSpeed;
	double randX;
	double randY;
	boolean tooClose;

	//Create tanks and set properties
	//TODO: code for selecting type of tank(what sprite to use, health etc)
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
			enemyTankImageList[i] = subImage(E100SpriteSheet, 96, 0, tankWidthE100, tankHeightE100);	
		}

		for (int i = 0; i < enemyTurretImageList.length; i++) {
			enemyTurretImageList[i] = subImage(E100SpriteSheet, 0, 0, tankWidthE100, tankHeightE100);			
		}	
	}

	private void updateEnemyTankList(double dt) {
		for (Tank tank : enemyTankList) {
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
				double angleToPlayer = workOutAngle(tank.getPositionX(), tank.getPositionY(), playerOne.getPositionX(), playerOne.getPositionY());
				if(checkTargetInSight(tank, playerOne) == true) {
					tank.setHullAngle(angleToPlayer);
				}
				if(checkTargetInFiringRange(tank, playerOne) == true) {
					tank.setTurretAngle(angleToPlayer + 90);
				}
			}
		}
	}

	private void drawEnemyTankList() {
		for (int i = 0; i < numberOfEnemyTanks; i++) {
			saveCurrentTransform();
			translate(enemyTankList[i].getPositionX(), enemyTankList[i].getPositionY());
			rotate(enemyTankList[i].getHullAngle());
			// Draw the tank
			drawImage(enemyTankImageList[i], -48, -103.5);
			// Restore last transform to undo the rotate and translate transforms
			restoreLastTransform();
		}
	}

	private void drawEnemyTurretList() {
		for (int i = 0; i < numberOfEnemyTanks; i++) {
			saveCurrentTransform();
			translate(enemyTankList[i].getPositionX(), enemyTankList[i].getPositionY());
			rotate(enemyTankList[i].getTurretAngle());
			drawImage(enemyTurretImageList[i], -48, -103.5);
			restoreLastTransform();
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
		setWindowSize(1024, 1024);
		// Load sprites
		E100SpriteSheet = loadImage("Tanks\\E100.png");
		//Load Menu Image
		menuImage = loadImage("Menu\\TankGame.png");
		//Load Paused Image
		pausedImage = loadImage("Paused\\PausedImage.png");
		//Load Game Over Image
		gameOverImage = loadImage("GameOver\\GameOverImage.png");
		// Load bullet Image

		bulletImageSprite = loadImage("Bullets\\bullet.png");
		//Load and play Menu Music
		AudioClip menuMusic = loadAudio("Music\\MenuMusic.wav");

		// startAudioLoop(menuMusic,5);
		// load tank driving Sound

		numberOfEnemyTanks = 3;

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

	//
	// Updates the display
	public void update(double dt) {
		// If the game is over
		if(state == GameState.GAMEOVER) {
			// Don't try to update anything.
			return;
		}
		if (state == GameState.PLAYING) {
			// Update the players
			updateTank(dt,playerOne);
			updateTurret(dt,playerOne);
			updateTank(dt,playerTwo);
			updateTurret(dt,playerTwo);
			updateLaser(dt,playerOne);
			updateLaser(dt,playerTwo);
			updateEnemyTankList(dt);
			Tank tankCollision = detectCollision();
			if (tankCollision!= null){
				System.out.println("Stop bullet");
				Bullet bulletToStop = tankCollision.getBullet();
				bulletToStop.setPositionX(0);
				bulletToStop.setPositionY(0);
				bulletToStop.setVelocityX(0);
				bulletToStop.setVelocityY(0);
			}
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
			//Display pause info in game
			changeColor(105,105,105);
			drawBoldText(width()-195, height()-998, "Press Esc to Pause Game", "Arial", 15);
			changeColor(white);

			// Draw the players
			//If only 1 player
			if (player1 == true) {
				drawLaser(playerOne);
				drawTank(playerOne);
				drawTurret(playerOne);

			//If 2 player
			} else if (player2 == true) {
				drawLaser(playerOne);
				drawLaser(playerTwo);
				drawTank(playerOne);
				drawTurret(playerOne);
				drawTank(playerTwo);
				drawTurret(playerTwo);


			}

			drawEnemyTankList();
			drawEnemyTurretList();

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
			// TankMoveSound(playerOne);
		}
		// Go right
		if(e.getKeyCode() == KeyEvent.VK_D) {
			playerOne.setRight(true);
			// TankMoveSound(playerOne);
		}
		// Go forwards
		if(e.getKeyCode() == KeyEvent.VK_W) {
			playerOne.setForward(true);

		}
		// Go backwards
		if(e.getKeyCode() == KeyEvent.VK_S) {
			playerOne.setReverse(true);
			// TankMoveSound(playerOne);

		}
		// The user fired
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			// To fire
			Bullet bullet = playerOne.getBullet();
			bullet.setFire(true);
			playerOne.setBullet(bullet);
			fireLaser(playerOne);
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
