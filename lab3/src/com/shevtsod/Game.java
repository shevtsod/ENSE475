package com.shevtsod;

/**
 * @author Daniel Shevtsov
 */
public class Game {
	public static final int NUM_FRAMES = 10;
	public static final int NUM_PINS = 10;
	
	private int score = 0;
	private int currentFrame = 0;
	private Frame[] frame;
	private TenthFrame tf;
	
	public Game() {
		frame = new Frame[NUM_FRAMES - 1];
		
		for(int i = 0; i < NUM_FRAMES - 1; i++) {
			frame[i] = new Frame();
		}
		
		tf = new TenthFrame();
	}
	
	/**
	 * Roll a ball and save the pins that were scored
	 * @param pins Number of pins scored in the roll
	 */
	public void roll(int pins) {
		//Final frame
		if(currentFrame == NUM_FRAMES - 1) {
			if(tf.getRoll1().getPins() == 0) {
				tf.getRoll1().setPins(pins);
			} else if (tf.getRoll1().getPins() != 0) {
				tf.getRoll2().setPins(pins);
			} else if (tf.score() == NUM_PINS || tf.getRoll2().getPins() == NUM_PINS){
				tf.getRoll3().setPins(pins);
			}
		} 
		
		//Any other frame
		else if (frame[currentFrame].getRoll1().getPins() == 0) {
			frame[currentFrame].getRoll1().setPins(pins);
			if(pins == 10)
				currentFrame++;
		} else if (frame[currentFrame].getRoll2().getPins() == 0) {
			frame[currentFrame].getRoll2().setPins(pins);
			currentFrame++;
		}
	}
	
	/**
	 * At the end of the game, calculate the final score.
	 * @return int final score
	 */
	public int score() {
		for (int i = 0; i < NUM_FRAMES - 1; i++) {
			// Check if previous frame was a spare or strike
			if(i > 0 && i < NUM_FRAMES - 2 && frame[i - 1].score() == NUM_PINS) {
				// If previous frame was a strike
				if(frame[i - 1].getRoll1().getPins() == NUM_PINS) {
					//If this roll is a strike, add this roll and add roll 1 of the next frame
					if(frame[i].getRoll1().getPins() == NUM_PINS) {
						score += frame[i].getRoll1().getPins();
						score += frame[i + 1].getRoll1().getPins();
					//If this roll is not a strike, add the score of this frame (both rolls)
					} else
						score += frame[i].score();
				} 
				
				// If previous frame was a spare
				else {
					score += frame[i].getRoll1().getPins();
				}
				
			}
			
			// Add the score of this frame
			score += frame[i].score();
		}
		
		// Check if previous frame was a spare or strike
		if(frame[NUM_FRAMES - 2].score() == NUM_PINS) {
			// If previous frame was a strike
			if(frame[NUM_FRAMES - 2].getRoll1().getPins() == NUM_PINS) {
				score += frame[NUM_FRAMES - 2].getRoll1().getPins();
				score += tf.getRoll1().getPins();
				
				// If roll 1 was a strike
				if(tf.getRoll1().getPins() == NUM_PINS) {
					score += tf.getRoll1().getPins();
					score += tf.getRoll2().getPins();
				}
				
				//If roll 2 was a strike
				if(tf.getRoll2().getPins() == NUM_PINS)
					score += tf.getRoll2().getPins();
			}
		} 
		
		//Score of final frame
		score += tf.score();
		
		return score;
	}
}
