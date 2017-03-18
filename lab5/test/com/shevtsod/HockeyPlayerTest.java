package com.shevtsod;

import com.shevtsod.Hockey.HockeyPlayer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Daniel Shevtsov (SID: 200351253)
 */
public class HockeyPlayerTest {

	private HockeyPlayer hp;

	/**
	 * Test method for {@link HockeyPlayer#getFirstName()}.
	 */
	@Test
	public void testGetFirstName() {
		hp = new HockeyPlayer("position", "first", "last", 50);
		assertEquals("first", hp.getFirstName());
	}

	/**
	 * Test method for {@link HockeyPlayer#getLastName()}.
	 */
	@Test
	public void testGetLastName() {
		hp = new HockeyPlayer("position", "first", "last", 50);
		assertEquals("last", hp.getLastName());
	}

	/**
	 * Test method for {@link HockeyPlayer#getPosition()}.
	 */
	@Test
	public void testGetPosition() {
		hp = new HockeyPlayer("position", "first", "last", 50);
		assertEquals("position", hp.getPosition());
	}

	/**
	 * Test method for {@link HockeyPlayer#getRating()}.
	 */
	@Test
	public void testGetRating() {
		hp = new HockeyPlayer("position", "first", "last", 50);
		assertEquals(50, hp.getRating());
	}

	/**
	 * Test method for {@link HockeyPlayer#setPosition(java.lang.String)}.
	 */
	@Test
	public void testSetPosition() {
		hp = new HockeyPlayer();
		hp.setPosition("position");
		assertEquals("position", hp.getPosition());
	}

	/**
	 * Test method for {@link HockeyPlayer#setRating(int)}.
	 */
	@Test
	public void testSetRating() {
		hp = new HockeyPlayer();
		hp.setRating(50);
		assertEquals(50, hp.getRating());
	}

	/**
	 * Test method for {@link HockeyPlayer#toString()}.
	 */
	@Test
	public void testToString() {
		String position = "position";
		String first = "first";
		String last = "last";
		int rating = 50;
		
		hp = new HockeyPlayer(position, first, last, rating);
		assertEquals(position+" "+first+", "+last+" "+rating, hp.toString());
	}

}
