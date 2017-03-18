package com.shevtsod;

import com.shevtsod.Hockey.HockeyPlayer;
import com.shevtsod.Hockey.HockeyTeam;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Daniel Shevtsov (SID: 200351253)
 */
public class HockeyTeamTest {
	
	private HockeyTeam ht;

	/**
	 * Set up before each test
	 */
	@Before
	public void setUp() throws Exception {
		ht = new HockeyTeam("Test Team");
	}

	/**
	 * Test method for {@link HockeyTeam#getRoster()}.
	 */
	@Test
	public void testGetRoster() {
		
		assertNotNull(ht.getRoster());
		
		//Get an empty roster
		assertTrue(ht.getRoster().isEmpty());
		
		//Get a full roster
		for(int i = 0; i < HockeyTeam.ROSTER_SIZE; i++) {
			ht.addPlayer(new HockeyPlayer("pos" + i, "first" + i, "last" + i, i));
		}
		
		for(int i = 0; i < HockeyTeam.ROSTER_SIZE; i++) {
			HockeyPlayer h = ht.getRoster().get(i);
			assertEquals("pos" + i, h.getPosition());
			assertEquals("first" + i, h.getFirstName());
			assertEquals("last" + i, h.getLastName());
			assertEquals(i, h.getRating());
		}
	}

	/**
	 * Test method for {@link HockeyTeam#addPlayer(HockeyPlayer)}.
	 */
	@Test
	public void testAddPlayer() {
		//Add 14 different players
		for(int i = 0; i < HockeyTeam.ROSTER_SIZE - 2; i++) {
			assertTrue(ht.addPlayer(new HockeyPlayer("pos" + i, "first" + i, "last" + i, i)));
		}
		
		//Attempt to add 2 identical named players
		assertTrue(ht.addPlayer(new HockeyPlayer("position", "John", "Smith", 50)));
		assertFalse(ht.addPlayer(new HockeyPlayer("position2", "john", "smITh", 49)));
		
		//Add another with the same first name but a different last name
		assertTrue(ht.addPlayer(new HockeyPlayer("position3", "John", "Doe", 51)));
		
		//Attempt to add a 17th player
		assertFalse(ht.addPlayer(new HockeyPlayer("position", "first", "last", 100)));
	}

	/**
	 * Test method for {@link HockeyTeam#deletePlayer(HockeyPlayer)}.
	 */
	@Test
	public void testDeletePlayer() {
		HockeyPlayer hp1 = new HockeyPlayer("position", "John", "Smith", 50);
		HockeyPlayer hp2 = new HockeyPlayer("position2", "John", "Doe", 50);
		
		ht.addPlayer(hp1);
		
		//Attempt to delete a player that does not exist
		assertFalse(ht.deletePlayer(hp2));
		
		//Delete a player that exists
		assertTrue(ht.deletePlayer(hp1));
		
		//Attempt to delete from an empty roster
		assertFalse(ht.deletePlayer(hp1));
	}

	/**
	 * Test method for {@link HockeyTeam#getPlayer(HockeyPlayer)}.
	 */
	@Test
	public void testGetPlayer() {
		HockeyPlayer hp1 = new HockeyPlayer("position", "John", "Smith", 50);
		
		//Try to get a player in an empty roster
		assertNull(ht.getPlayer(hp1));
		
		//Get an existing player
		ht.addPlayer(hp1);
		HockeyPlayer hpget = ht.getPlayer(hp1);
		assertNotNull(hpget);
		assertEquals(hp1.getPosition(), hpget.getPosition());
		assertEquals(hp1.getFirstName(), hpget.getFirstName());
		assertEquals(hp1.getLastName(), hpget.getLastName());
		assertEquals(hp1.getRating(), hpget.getRating());
		
		//Get an existing player with different character case for name
		hp1 = new HockeyPlayer("position", "joHn", "SMiTH", 50);
		hpget = ht.getPlayer(hp1);
		assertNotNull(hpget);
		assertEquals(hp1.getPosition(), hpget.getPosition());
		assertEquals(hp1.getFirstName().toLowerCase(), hpget.getFirstName().toLowerCase());
		assertEquals(hp1.getLastName().toLowerCase(), hpget.getLastName().toLowerCase());
		assertEquals(hp1.getRating(), hpget.getRating());
		
		//Try to get a non-existing player
		hpget = ht.getPlayer(new HockeyPlayer("test", "test", "test", 1));
		assertNull(hpget);
	}

	/**
	 * Test method for {@link HockeyTeam#editPlayer(HockeyPlayer)}.
	 */
	@Test
	public void testEditPlayer() {
		HockeyPlayer hp1 = new HockeyPlayer("position", "John", "Smith", 50);
		HockeyPlayer hp2 = new HockeyPlayer("position2", "John", "Doe", 40);
		
		//Attempt to edit a non-existing player
		assertFalse(ht.editPlayer(hp1));
		
		//Edit an existing player
		ht.addPlayer(hp2);
		ht.addPlayer(hp1);
		assertEquals(hp1.getRating(), ht.getPlayer(hp1).getRating());
		assertEquals(hp1.getPosition(), ht.getPlayer(hp1).getPosition());
		
		hp1.setRating(60);
		hp1.setPosition("position2");
		
		assertTrue(ht.editPlayer(hp1));
		assertEquals(60, ht.getPlayer(hp1).getRating());
		assertEquals("position2", ht.getPlayer(hp1).getPosition());
	}
}
