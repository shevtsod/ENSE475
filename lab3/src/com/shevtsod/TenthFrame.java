package com.shevtsod;

/**
 * @author Daniel Shevtsov
 */
public class TenthFrame extends Frame {
	private Roll roll3;
	
	public TenthFrame() {
		super();
		roll3 = new Roll();
	}
	
	@Override
	public int score() {
		return super.score() + roll3.getPins();
	}
	
	@Override
	public boolean isDone() {
		return ((score() != 10 && roll2.getPins() != 10) || roll3.getPins() != 0);
	}
	
	public Roll getRoll3() {
		return roll3;
	}

}
