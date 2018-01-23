package gameClasses;
import gameClasses.*;
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
	//-------------------------------------------------------
	// Tank Objects
	//-------------------------------------------------------
	// Image of the player
	Image playerTankImage;
	Image playerTurretImage;
	Player playerOne;
	// Init player Function
	public void initPlayerTank() {
		// Load the player Tank sprite
		playerTankImage   = subImage(playerSpritesheet,96, 0, 96, 207);
		playerTurretImage = subImage(playerSpritesheet, 0, 0, 96, 207);
    playerOne = new Player(width()/2,height()/2);
	}
	// Draw the tank body
	public void drawPlayerTank(Player object) {
		// Save the current transform
		saveCurrentTransform();

		translate(object.getPlayerPositionX(), object.getPlayerPositionY());
		rotate(object.getPlayerAngle());

		// Draw the tank
		drawImage(playerTankImage, -48, -103.5);
		// Restore last transform to undo the rotate and translate transforms
		restoreLastTransform();
	}
	// Draw the tank turret
	public void drawPlayerTurret(Player object) {
		saveCurrentTransform();
		translate(object.getPlayerPositionX(), object.getPlayerPositionY());
		rotate(object.getPlayerTurretAngle());
		//rotate(playerTurretAngle+90);
		drawImage(playerTurretImage, -48, -103.5);
		restoreLastTransform();
	}
	// Update the tank body
	public void updatePlayerTank(double dt, Player object) {
		if(forward == true) {
			// Increase the velocity of the player
			// as determined by the angle
			object.setPlayerVelocityX(object.getPlayerVelocityX() + sin(object.getPlayerAngle()) * 200 * dt);
			object.setPlayerVelocityY(object.getPlayerVelocityY() - cos(object.getPlayerAngle()) * 200 * dt);
		}
		if (forward == false) {
			object.setPlayerVelocityX(0);
			object.setPlayerVelocityY(0);
		}

		// If the user is holding down the left arrow key
		if(left == true) {
			// Make the player rotate anti-clockwise
			object.setPlayerAngle(object.getPlayerAngle() - 100 * dt);
			if(object.getPlayerAngle() < -360) {
				object.setPlayerAngle(0);
			}
		}

		// If the user is holding down the right arrow key
		if(right == true) {
			// Make the player rotate clockwise
      object.setPlayerAngle(object.getPlayerAngle() + 100 * dt);
			if(object.getPlayerAngle() > 360) {
				object.setPlayerAngle(0);
			}
		}

		// Make the player move forward
		object.setPlayerPositionX(object.getPlayerPositionX() + object.getPlayerVelocityX() * dt);
    object.setPlayerPositionY(object.getPlayerPositionY() + object.getPlayerVelocityY() * dt);

		// If the player reaches the right edge of the screen
		// 'Warp' it back to the left edge
		// if(playerPositionX > width())  {playerPositionX -= width();}

		// If the player reaches the left edge of the screen
		// 'Warp' it back to the right edge
		// if(playerPositionX < 0)        {playerPositionX += width();}

		// If the player reaches the top edge of the screen
		// 'Warp' it back to the bottom edge
		// if(playerPositionY > height()) {playerPositionY -= height();}

		// If the player reaches the bottom edge of the screen
		// 'Warp' it back to the top edge
		// if(playerPositionY < 0)        {playerPositionY += height();}
	}
	// Update the tank turret
	public void updatePlayerTurret(double dt, Player object) {
		double targetAngle = workOutAngle(object.getPlayerPositionX(), object.getPlayerPositionY(), mouseX, mouseY);

		//This is to have the turret track to the cursor slowly, not perfect
		/* if (playerTurretAngle > targetAngle) {
			playerTurretAngle -= turretSpeed * dt;
		}
		if (playerTurretAngle < targetAngle) {
			playerTurretAngle += turretSpeed * dt;
		} */

		//This makes the turret track exactly to the cursor
		object.setPlayerTurretAngle(targetAngle + 90);
	}
	//-------------------------------------------------------
	// Game
	//-------------------------------------------------------
	// Spritesheet
	Image playerSpritesheet;
	//Menu Screen
	Image menuImage;
	//Paused Screen
	Image pausedImage;
	//Game Over screen
	Image gameOverImage;
	// Keep track of keys
	boolean left, right, forward, down;
	boolean gameOver, menuState, gamePause;
	boolean player1, player2;

	int maxLasers;
	int mouseX;
	int mouseY;

	// Function to initialise the game
	public void init() {
		setWindowSize(1024, 1024);
		// Load sprites
		playerSpritesheet = loadImage("Tanks\\E-100_strip2.png");
		//Load Menu Image
		menuImage = loadImage("Menu\\TankGame.png");
		//Load Paused Image
		pausedImage = loadImage("Paused\\PausedImage.png");
		//Load Game Over Image
		gameOverImage = loadImage("GameOver\\GameOverImage.png");

		//Load and play Menu Music
		AudioClip menuMusic = loadAudio("Music\\MenuMusic.wav");
		startAudioLoop(menuMusic);

		// Setup booleans
		left  = false;
		right = false;
		forward = false;
		down  = false;
		gameOver = true;
		menuState = true;
		maxLasers = 5;
		mouseX = 0;
		mouseY = 0;
		player1 = false;

		// Initialise player
		initPlayerTank();
	}
	// Updates the display
	public void update(double dt) {
		// If the game is over
		if(gameOver == true) {
			// Don't try to update anything.
			return;
		}
		// Update the player
		updatePlayerTank(dt,playerOne);
		updatePlayerTurret(dt,playerOne);
	}
	// This gets called any time the Operating System
	// tells the program to paint itself
	public void paintComponent() {
		// Clear the background to black
		changeBackgroundColor(black);
		clearBackground(width(), height());
		// If the game is not over yet
		if(gameOver == false) {
			//Display pause info in game
			changeColor(105,105,105);
			drawBoldText(width()-195, height()-998, "Press Esc to Pause Game", "Arial", 15);
			changeColor(white);

			//Paused Game
			if (gamePause == true) {
				//Insert Paused screen
				drawImage(pausedImage, width()-1024, height()-1024);

			} else if (gamePause == false) {
				// Draw the player
				//If only 1 player
				if (player1 == true) {
					drawPlayerTank(playerOne);
					drawPlayerTurret(playerOne);

				//If 2 player
				} else if (player2 == true) {
					//Insert code for 2 player
				}
			}
		} else if(gameOver == true) {
			// If the game is at menu
			if (menuState == true) {
				//Insert Menu screen
				drawImage(menuImage, width()-1024, height()-1024);

			//If the game is over
			} else if (menuState == false) {
				// Display GameOver text
				if (player1 == true) {
					//Display player1 score here

					//Insert Game Over Image
					drawImage(gameOverImage, width()-1024, height()-1024);
				} else if (player2 == true) {
					//Display both player scores

					//Display Game Over Image
					drawImage(gameOverImage, width()-1024, height()-1024);
				}
			}
		}
	}
	// Called whenever a key is pressed
	public void keyPressed(KeyEvent e) {
		//The user pressed A
		if(e.getKeyCode() == KeyEvent.VK_A) {

			left = true;
		}
		// The user pressed D
		if(e.getKeyCode() == KeyEvent.VK_D) {

			right = true;
		}
		// The user pressed W
		if(e.getKeyCode() == KeyEvent.VK_W) {
			forward = true;
		}
		// The user pressed space bar
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {

		}
		// The user pressed 1
		if(e.getKeyCode() == KeyEvent.VK_1)  {
			player1	= true;
			menuState = false;
			gameOver = false;
		}
		// The user pressed 2
		if(e.getKeyCode() == KeyEvent.VK_2)  {
			player2	= true;
			menuState = false;
			gameOver = false;
		}
		// The user pressed Escape key
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)  {
			gamePause  = true;
		}
		// The user pressed Q
		if(e.getKeyCode() == KeyEvent.VK_Q)  {
			menuState = false;
			gameOver = true;
			gamePause = false;
		}
		// The user pressed R
		if(e.getKeyCode() == KeyEvent.VK_R)  {
			menuState = false;
			gameOver = false;
			gamePause = false;
		}
		// The user pressed M
		if(e.getKeyCode() == KeyEvent.VK_M)  {
			menuState = true;
			gameOver = true;
			gamePause = false;
		}
	}
	// Called whenever a key is released
	public void keyReleased(KeyEvent e) {
		// The user released left arrow
		if(e.getKeyCode() == KeyEvent.VK_A) {
			left = false;
		}
		// The user released right arrow
		if(e.getKeyCode() == KeyEvent.VK_D) {
			right = false;
		}
		// The user released up arrow
		if(e.getKeyCode() == KeyEvent.VK_W) {
			forward = false;
		}

		// Clear the background to black
		changeBackgroundColor(black);
		clearBackground(width(), height());

		// If the game is not over yet
		if(gameOver == false) {
			// Draw the player
			drawPlayerTank(playerOne);
			drawPlayerTurret(playerOne);
		} else {
			// If the game is over
			// Display GameOver text
			changeColor(white);
			drawText(width()/2-165, height()/2, "GAME OVER!", "Arial", 50);
		}
	}
	public void mouseMoved(MouseEvent event) {
		mouseX = event.getX();
		mouseY = event.getY();
	}
	public double workOutAngle(double originX, double originY, int targetX, int targetY) {
		double angle = atan2(targetY - originY, targetX - originX);
		/* if (angle < 0) {
			angle += 360;
		} */
		return angle;
	}
}
