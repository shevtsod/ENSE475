package com.shevtsod;

/**
 * @author Daniel Shevtsov
 */
public class Frame {
	protected Roll roll1, roll2;
	
	public Frame() {
		roll1 = new Roll();
		roll2 = new Roll();
	}
	
	public int score() {
		return roll1.getPins() + roll2.getPins();
	}
	
	public boolean isDone() {
		return (roll2.getPins() != 0);
	}
	
	public Roll getRoll1() {
		return roll1;
	}
	
	public Roll getRoll2() {
		return roll2;
	}

}
