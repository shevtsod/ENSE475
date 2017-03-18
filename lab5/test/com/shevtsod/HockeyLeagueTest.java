package com.shevtsod;

import com.shevtsod.Hockey.HockeyLeague;
import com.shevtsod.Hockey.HockeyPlayer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Daniel Shevtsov (SID: 200351253)
 */
public class HockeyLeagueTest {

	HockeyLeague hl;

    /**
     * Set up before tests
     */
	@Before
    public void setUp() {
	    hl = new HockeyLeague();
    }

    /**
     * Test method for {@link HockeyLeague#HockeyLeague()}.
     */
	@Test
    public void testHockeyLeague() {
	    //Ensure that a new HockeyLeague object initializes correctly
        assertNotNull(hl);
    }

	/**
	 * Test method for {@link HockeyLeague#addPlayer(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testAddPlayer() {
		//Try to add a player to a mon existing team
        assertFalse(hl.addPlayer(
                "someteam",
                "pos1",
                "John",
                "Doe",
                50));

        //Try to add a player to a full team
        assertFalse(hl.addPlayer(
                "Canadiens",
                "pos1",
                "John",
                "Doe",
                50));

        //Try to add a player after removing a player
        hl.deletePlayer("Canadiens", "Hal", "Gill");
        assertTrue(hl.addPlayer(
                "Canadiens",
                "pos1",
                "John",
                "Doe",
                50));
	}

	/**
	 * Test method for {@link HockeyLeague#deletePlayer(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeletePlayer() {
		//Try to delete an existing player
        assertTrue(hl.deletePlayer("Canadiens", "Hal", "Gill"));

        //Try to delete a non-existing player
        assertFalse(hl.deletePlayer("Team", "ABC", "DEF"));
	}

	/**
	 * Test method for {@link HockeyLeague#editPlayer(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testEditPlayer() throws IOException {
		//Edit an existing player
        String teamName = "Maple Leafs";
        String position = "LW";
        String firstName = "Fredrik";
        String lastName = "Sjostrom";

        Random random = new Random();
        //Get a random int from 0 to 100 to always store a different rating for this player
        int rating = random.nextInt(101);

        assertTrue(hl.editPlayer(teamName, position, firstName, lastName, rating));
        HockeyPlayer hp = hl.getPlayer(teamName, firstName, lastName);
        //Ensure that the rating and position changed
        assertEquals(rating, hp.getRating());
        assertEquals(position, hp.getPosition());

        //Try to edit a non-existing player
        assertFalse(hl.editPlayer(teamName, position, "Test", "Player", rating));
	}

	/**
	 * Test method for {@link HockeyLeague#getTeam(java.lang.String)}.
	 */
	@Test
	public void testGetTeam() {
		//Test that all 6 teams were loaded correctly
        assertNotNull(hl.getTeam("Blackhawks"));
        assertNotNull(hl.getTeam("Bruins"));
        assertNotNull(hl.getTeam("Canadiens"));
        assertNotNull(hl.getTeam("Maple Leafs"));
        assertNotNull(hl.getTeam("Rangers"));
        assertNotNull(hl.getTeam("Red Wings"));

        //Test a team that does not exist
        assertNull(hl.getTeam("TestTeam"));
	}

	/**
	 * Test method for {@link HockeyLeague#getPlayer(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetPlayer() {
		//Try to get some known existing players
        assertNotNull(hl.getPlayer(
                "Canadiens",
                "Tomas",
                "Plekanec"));
        assertNotNull(hl.getPlayer(
                "Rangers",
                "Brandon",
                "Dubinsky"));

        //Try to get some known non-existing players
        assertNull(hl.getPlayer(
                "Canadiens",
                "John",
                "Doe"));
        assertNull(hl.getPlayer(
                "TestTeam",
                "Tomas",
                "Plekanec"));
	}

	/**
	 * Test method for {@link HockeyLeague#playGame(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testPlayGame() {
	    try {
            String returnString;
            //Test if method returns false when teams don't exist
            returnString = hl.playGame("Canadiens", "TestTeam");
            assertEquals("Unplayable Game Rosters are wrong", returnString);

            //Play 15 games to increase chances of visiting most lines since they rely on a random number generator
            for (int i = 0; i < 5; i++) {
                //Ensure that the method completes without errors and returns true
                returnString = hl.playGame("Canadiens", "Red Wings");
                assertTrue(returnString.equals("Canadiens") || returnString.equals("Red Wings"));

                returnString = hl.playGame("Rangers", "Maple Leafs");
                assertTrue(returnString.equals("Rangers") || returnString.equals("Maple Leafs"));

                returnString = hl.playGame("Bruins", "Blackhawks");
                assertTrue(returnString.equals("Bruins") || returnString.equals("Blackhawks"));
            }

        } catch (IOException e) {
            System.out.println("testPlayGame() - IOException!");
        }
	}

}
