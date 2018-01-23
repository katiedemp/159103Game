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
	// Keep track of keys
	boolean left, right, forward, down;
	boolean gameOver;

	int maxLasers;
	int mouseX;
	int mouseY;

	// Function to initialise the game
	public void init() {
		setWindowSize(1024, 1024);
		// Load sprites
		playerSpritesheet = loadImage("Tanks\\E-100_strip2.png");
		// Setup booleans
		left  = false;
		right = false;
		forward = false;
		down  = false;
		gameOver = false;
		maxLasers = 5;
		mouseX = 0;
		mouseY = 0;

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

	// Called whenever a key is pressed
	public void keyPressed(KeyEvent e) {
		//T he user pressed A
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
