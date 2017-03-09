/**
 * 
 */
package com.shevtsod;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Shevtsov (SID: 200351253)
 *
 */
public class HockePlayerTest {

	private HockeyPlayer hp;

	/**
	 * Test method for {@link com.shevtsod.HockeyPlayer#getFirstName()}.
	 */
	@Test
	public void testGetFirstName() {
		hp = new HockeyPlayer("position", "first", "last", 50);
		assertEquals("first", hp.getFirstName());
	}

	/**
	 * Test method for {@link com.shevtsod.HockeyPlayer#getLastName()}.
	 */
	@Test
	public void testGetLastName() {
		hp = new HockeyPlayer("position", "first", "last", 50);
		assertEquals("last", hp.getLastName());
	}

	/**
	 * Test method for {@link com.shevtsod.HockeyPlayer#getPosition()}.
	 */
	@Test
	public void testGetPosition() {
		hp = new HockeyPlayer("position", "first", "last", 50);
		assertEquals("position", hp.getPosition());
	}

	/**
	 * Test method for {@link com.shevtsod.HockeyPlayer#getRating()}.
	 */
	@Test
	public void testGetRating() {
		hp = new HockeyPlayer("position", "first", "last", 50);
		assertEquals(50, hp.getRating());
	}

	/**
	 * Test method for {@link com.shevtsod.HockeyPlayer#setPosition(java.lang.String)}.
	 */
	@Test
	public void testSetPosition() {
		hp = new HockeyPlayer();
		hp.setPosition("position");
		assertEquals("position", hp.getPosition());
	}

	/**
	 * Test method for {@link com.shevtsod.HockeyPlayer#setRating(int)}.
	 */
	@Test
	public void testSetRating() {
		hp = new HockeyPlayer();
		hp.setRating(50);
		assertEquals(50, hp.getRating());
	}

	/**
	 * Test method for {@link com.shevtsod.HockeyPlayer#toString()}.
	 */
	@Test
	public void testToString() {
		String position = "position";
		String first = "first";
		String last = "last";
		int rating = 50;
		
		hp = new HockeyPlayer(position, first, last, rating);
		assertEquals(position+" "+last+", "+first+" "+rating, hp.toString());
	}

}
