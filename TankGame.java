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
	// Image of the 1st player
	Image playerTankImage;
	Image playerTurretImage;
	Tank playerOne;
	// Image of the 2nd player1
	Tank playerTwo;
	// Init player Function
	public void initPlayerTank() {
		// Load the player Tank sprite
		playerTankImage   = subImage(playerSpritesheet,96, 0, 96, 207);
		playerTurretImage = subImage(playerSpritesheet, 0, 0, 96, 207);
    playerOne = new Tank(width()/2, height()/2, 100, 75, 125);
		playerTwo = new Tank(width()/2, height()/2, 100, 75, 125);
	}
	// Draw the tank body
	public void drawTank(Tank playerOne) {
		// Save the current transform
		saveCurrentTransform();
		translate(playerOne.getPositionX(), playerOne.getPositionY());
		rotate(playerOne.getHullAngle());

		// Draw the tank
		drawImage(playerTankImage, -48, -103.5);
		// Restore last transform to undo the rotate and translate transforms
		restoreLastTransform();
	}
	// Draw the tank turret
	public void drawTurret(Tank playerOne) {
		saveCurrentTransform();
		translate(playerOne.getPositionX(), playerOne.getPositionY());
		rotate(playerOne.getTurretAngle());
		//rotate(playerTurretAngle+90);
		drawImage(playerTurretImage, -48, -103.5);
		restoreLastTransform();
	}
	// Update the tank body
	public void updateTank(double dt, Tank playerOne) {
		if(playerOne.getForward() == true) {
			playerOne.moveForward(dt);
		}
		if (playerOne.getReverse() == true) {
			playerOne.moveBackward(dt);
		}
		// If the user is holding down the left arrow key
		if(playerOne.getLeft() == true) {
			playerOne.turnLeft(dt);
		}
		// If the user is holding down the right arrow key
		if(playerOne.getRight() == true) {
			playerOne.turnRight(dt);
		}



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
	boolean playerOneLeft, playerOneRight, playerOneForward, playerOneReverse;
	boolean playerTwoLeft, playerTwoRight, playerTwoForward, playerTwoReverse;

	//boolean gameOver, menuState, gamePause;
	boolean player1, player2;

	int mouseX;
	int mouseY;

	//GameState
	
	private GameState state = GameState.MENU;

	//
	// Function to initialise the game
	public void init() {
		setWindowSize(1024, 1024);
		// Load sprites
		playerSpritesheet = loadImage("Tanks\\E100.png");
		//Load Menu Image
		menuImage = loadImage("Menu\\TankGame.png");
		//Load Paused Image
		pausedImage = loadImage("Paused\\PausedImage.png");
		//Load Game Over Image
		gameOverImage = loadImage("GameOver\\GameOverImage.png");

		//Load and play Menu Music
		AudioClip menuMusic = loadAudio("Music\\MenuMusic.wav");
		startAudioLoop(menuMusic);

		// Setup Game booleans
		//gameOver = true;
		//menuState = true;
		mouseX = 0;
		mouseY = 0;
		player1 = false;
		player2 = false;

		// Initialise player
		initPlayerTank();
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
				drawTank(playerOne);
				drawTurret(playerOne);
				
			//If 2 player
			} else if (player2 == true) {
				drawTank(playerOne);
				drawTurret(playerOne);
				drawTank(playerTwo);
				drawTurret(playerTwo);
			}
			
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
		}
		// Go right
		if(e.getKeyCode() == KeyEvent.VK_D) {
			playerOne.setRight(true);
		}
		// Go forwards
		if(e.getKeyCode() == KeyEvent.VK_W) {
			playerOne.setForward(true);
		}
		// Go backwards
		if(e.getKeyCode() == KeyEvent.VK_S) {
			playerOne.setReverse(true);
		}
		// The user fired
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			// To fire
		}

		// Second player
		// Go left
		if(e.getKeyCode() == KeyEvent.VK_J) {
			playerTwo.setLeft(true);
		}
		// Go right
		if(e.getKeyCode() == KeyEvent.VK_L) {
			playerTwo.setRight(true);
		}
		// Go forwards
		if(e.getKeyCode() == KeyEvent.VK_I) {
			playerTwo.setForward(true);
		}
		// Go backwards
		if(e.getKeyCode() == KeyEvent.VK_K) {
			playerTwo.setReverse(true);
		}
		// The user fired
		if(e.getKeyCode() == KeyEvent.VK_M) {
			// To fire
		}

		//GameState Buttons
		// The user pressed Escape key - PAUSE
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)  {
			state = GameState.PAUSE;
		}

		//If in Pause GameState
		if (state == GameState.PAUSE) {
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
	}


	// Called whenever a key is released
	public void keyReleased(KeyEvent e) {
		// Go left button released
		if(e.getKeyCode() == KeyEvent.VK_A) {
			playerOne.setLeft(false);
		}
		// Go right button released
		if(e.getKeyCode() == KeyEvent.VK_D) {
			playerOne.setRight(false);
		}
		// Go forwards button released
		if(e.getKeyCode() == KeyEvent.VK_W) {
			playerOne.setForward(false);
		}
		// Go backwards button released
		if(e.getKeyCode() == KeyEvent.VK_S) {
			playerOne.setReverse(false);
		}
		// The user fired button released
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			// To fire
		}

		// Second player
		// Go left button released
		if(e.getKeyCode() == KeyEvent.VK_J) {
			playerTwo.setLeft(false);
		}
		// Go right button released
		if(e.getKeyCode() == KeyEvent.VK_L) {
			playerTwo.setRight(false);
		}
		// Go forwards button released
		if(e.getKeyCode() == KeyEvent.VK_I) {
			playerTwo.setForward(false);
		}
		// Go backwards button released
		if(e.getKeyCode() == KeyEvent.VK_K) {
			playerTwo.setReverse(false);
		}
		// The user fired button released
		if(e.getKeyCode() == KeyEvent.VK_M) {
			// To fire
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
