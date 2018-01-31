//-------------------------------------------------------
// Explosion
//-------------------------------------------------------
public class Explosion {
	
	// Images for the explosion
	Image[] explosionImages = new Image[32];

	// Position of the explosion
	double explosionPositionX;
	double explosionPositionY;

	// Timer for the explosion
	double explosionTimer;
	double explosionDuration;

	boolean explosionActive;

	// Initialise Variables for explosion
	public void initExplosion() {
		// Load explosion sprites
		int n = 0;
		for(int y = 960; y < 1920; y += 240) {
			for(int x = 0; x < 1920; x += 240) {
				explosionImages[n] = subImage(spritesheet, x, y, 240, 240);
				n++;
			}
		}
	}

	// Create an explosion at position x,y
	public void createExplosion(double x, double y) {
		// Position the explosion
		explosionPositionX = x;
		explosionPositionY = y;

		// Start new explosion
		explosionTimer = 0;
		explosionDuration = 1.0;
		explosionActive = true;
	}

	// Function to update the explosion
	public void updateExplosion(double dt) {
		// If the explosion is active
		if(explosionActive) {
			// Increment timer
			explosionTimer += dt;

			// Check to see if explosion has finished
			if(explosionTimer >= explosionDuration) {
				explosionActive = false;
			}
		}
	}

	// Function to get frame of animation
	public int getAnimationFrame(double timer, double duration, int numFrames) {
		// Get frame
		int i = (int)floor(((timer % duration) / duration) * numFrames);
		// Check range
		if(i >= numFrames) {
			i = numFrames-1;
		}
		// Return
		return i;
	}

	// Function to draw the explosion
	public void drawExplosion() {
		// Select the right image
		if(explosionActive) {
			// Save the current transform
			saveCurrentTransform();

			// translate to the position of the asteroid
			translate(explosionPositionX, explosionPositionY);

			// Draw the explosion frame
			int i = getAnimationFrame(explosionTimer, explosionDuration, 30);
			drawImage(explosionImages[i], -30, -30, 60, 60);

			// Restore last transform to undo the rotate and translate transforms
			restoreLastTransform();
		}
	}
}