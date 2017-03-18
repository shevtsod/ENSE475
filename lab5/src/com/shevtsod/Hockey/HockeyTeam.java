/**
* HockeyTeam.java
*
* @author Trevor Douglas
*   <A HREF="mailto:douglatr@uregina.ca"> (douglatr@uregina.ca) </A>
*
* Original code copyright � Mar 15, 2010 Trevor Douglas.  Modifications can be made
* with Author's consent.
* @version Mar 15, 2010
*
**/

package com.shevtsod.Hockey;

/**
 * @author tdouglas, Daniel Shevtsov (SID: 200351253)
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HockeyTeam {

    private ArrayList<HockeyPlayer> roster = new ArrayList<>();
    public String teamName;
    public static final int ROSTER_SIZE = 16;

    public HockeyTeam(String teamName) {
        this.teamName = teamName;
    }

    /**
     *
     * @return
     * current team roster
     */
    public ArrayList<HockeyPlayer> getRoster() {
        return roster;
    }

    /**
     * Precondition : The roster rules are observed prior to adding a new
     * player.  This function will only check for duplicate players
     * @param player
     * @return true or false
     */
    public boolean addPlayer(HockeyPlayer player) {
        if(roster.size() >= ROSTER_SIZE)
        {
        	System.err.println("Roster is full");
        	return false;
        }


        for (int i = 0; i < roster.size(); i++) {
            if (roster.get(i).getFirstName().toLowerCase().equals(player.getFirstName().toLowerCase()) &&
            	roster.get(i).getLastName().toLowerCase().equals(player.getLastName().toLowerCase())) {
                System.err.println(player.getFirstName()+" " +player.getLastName()
                		+" already exists on team "+ this.teamName);
                return false;
            }
        }

        HockeyPlayer addedPlayer = new HockeyPlayer(
        		player.getPosition(),
        		player.getFirstName(), 
        		player.getLastName(),
        		player.getRating());
        roster.add(addedPlayer);
        return true;

    }


    /**
     * Precondition : The roster rules are observed prior to deleting a new
     * player.  This function will delete a player if it finds a matching
     * First and Last Name.
     * @param player
     * @return true or false
     */

    public boolean deletePlayer(HockeyPlayer player) {
		for (int i = 0; i < roster.size(); i++) {
			if (roster.get(i).getFirstName().equals(player.getFirstName())
					&& roster.get(i).getLastName().equals(player.getLastName())) {
				roster.remove(0);
				return true;
			}
		}

        return false;
    }


    /**
     * @param player to retrieve
     * @return HockeyPlayer or Null
     */
    public HockeyPlayer getPlayer(HockeyPlayer player) {

    	for (int i = 0; i < roster.size() ; i++) {
            if (roster.get(i).getFirstName().toLowerCase().equals(player.getFirstName().toLowerCase()) &&
            	roster.get(i).getLastName().toLowerCase().equals(player.getLastName().toLowerCase())) {
                return roster.get(i);
            }
        }
        return null;
    }

    /**
     * @param player to edit.  Only edit a players position and rating.
     * @return true or false
     */

    public boolean editPlayer(HockeyPlayer player) {
        for (int i = 0; i < roster.size() ; i++) {
            if (roster.get(i).getFirstName().equals(player.getFirstName()) &&
                roster.get(i).getLastName().equals(player.getLastName())) {
                roster.get(i).setPosition(player.getPosition());
                roster.get(i).setRating(player.getRating());
                return true;
            }
        }
        return false;
    }

    /**
     * Get an unmodifiable list of players to iterate through and retrieve data
     * @return UnmodifiableList of HockeyPlayers of this team
     */
    public List<HockeyPlayer> getPlayers() {
        return Collections.unmodifiableList(roster);
    }
}
