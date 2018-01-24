import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import java.awt.event.*;

public class hud extends GameEngine {
	// Main Function
	public static void main(String args[]) {
		// Warning: Only call createGame in this function
		createGame(new hud());
	}
	
	//-------------------------------------------------------
	// Game
	//-------------------------------------------------------
	
	
	// Booleans to keep track of whether a key is pressed or not
	boolean gameOver;
	
	//Game variables
	int health;
	
	// Function to initialise the game
	public void init() {
		setWindowSize(1024, 1024);
	}
	
	// Updates the display
	public void update(double dt) {
		// If the game is over
		if(gameOver == true) {
			// Don't try to update anything.
			return;
		}
	}
	
	//----
	// Wave
	//----
	int Wave = 0;
	public void updateWave(){
		Wave+=1;
	}
	
	public void drawWaveLevel(int Wave){
		changeColor(black);
		drawText(452, 20, "Wave:", "Arial", 20);
		drawText(512, 20, Integer.toString(Wave), "Arial", 20);
	}
	
	//----
	// Health
	//----
	
	int Health = 100;
	public void updateHealth(){
		Health-=1;
	}
	
	public void drawHealthBar(int Health){
		changeColor(black);
		drawText(20, 20, "Health:", "Arial", 20);
		drawText(80, 20, Integer.toString(Health), "Arial", 20);
	}
	
	
	//----
	// Score Board
	//----
	int Score = 0;
	public void updateScore(){
		Score+=1;
	}
	
	public void drawScoreBoard(int Score){
		changeColor(black);
		drawText(914, 20, "Score:", "Arial", 20);
		drawText(974, 20, Integer.toString(Score), "Arial", 20);
	}

	// This gets called any time the Operating System
	// tells the program to paint itself
	public void paintComponent() {
		//Clear the background to black
		changeBackgroundColor(white);
		clearBackground(width(), height());
		
		// Paint the health bar
		drawHealthBar(Health);
		
		// Paint the wave level
		drawWaveLevel(Wave);
		
		// Paint the score board
		drawScoreBoard(Score);
		
	}

	public void paintHUD() {
		changeColor(red);

		if(width() == 500) {
			drawSolidRectangle(431, 488 - (57*health)/100, 57, (57*health)/100);
		} else if(width() == 750) {
			drawSolidRectangle(646, 732 - (85*health)/100, 85, (85*health)/100);
		}
	}
}