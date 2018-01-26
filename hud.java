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
	int Health = 100;
	
	// Function to initialise the game
	public void init() {
		setWindowSize(1024, 1024);
	}
	
	// Updates the display
	public void update(double dt) {
		Health--;
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
	public void updateHealth(){
	}
	
	public void drawHealthBar(int Health){
		changeColor(black);
		drawText(20, 20, "Health:", "Arial", 20);
		drawSolidRectangle(90, 0, (60*Health)/100, 25);
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
}