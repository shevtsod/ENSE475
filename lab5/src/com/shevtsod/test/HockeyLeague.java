/**
* HockeyLeague.java
*
* @author Trevor Douglas
*   <A HREF="mailto:douglatr@uregina.ca"> (douglatr@uregina.ca) </A>
*
* Original code copyright © Mar 15, 2010 Trevor Douglas.  Modifications can be made
* with Author's consent.
* @version Mar 15, 2010
*
**/

package com.shevtsod.test;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author tdouglas, Daniel Shevtsov (SID: 200351253)
 *
 */
public class HockeyLeague {

	    ArrayList<HockeyTeam> teams = new ArrayList<HockeyTeam>();
	    private static final int ROSTER_SIZE = 16;

	    /**
	     * HockeyLeague
	     * This constructor reads in the 6 files and fills in the teams array
	     * @pre Names of the teams MUST be the same name as the file (minus extension)
	     * The extension for the files MUST be ".txt"...otherwise it won't work
	     *
	     */
	    public HockeyLeague() {

	        //Just get a list of all the files with .txt in the directory
	        File dir = new File("./");
	        FilenameFilter filter = new FilenameFilter() {
	            public boolean accept(File dir, String name) {
	                return name.endsWith(".txt");
	            }
	        };
	        String[] files = dir.list(filter);

	        for (int j = 0; j < files.length ; j++) {
	            try {
	                //Use my CSVReader I made in another class...works good :)
	                File file = new File(files[j]);
	                CSVReader csvReader = new CSVReader(new FileReader(file));
	                ArrayList<String[]> allLines = csvReader.readAll();
	                csvReader.close();

	                //Get the team name from the filename
	                String teamName = files[j].substring(0, files[j].length()-4);
	                HockeyTeam ht = new HockeyTeam(teamName);


	                //Now add each player to the team we just made
	                for (int i = 0; i < allLines.size() ; i++) {
	                    int rating = Integer.parseInt(allLines.get(i)[3]);
	                    //Add the player
	                    ht.addPlayer(new HockeyPlayer(allLines.get(i)[0],
	                    		allLines.get(i)[1], allLines.get(i)[2], rating));
	                }
	                teams.add(ht);  //add newly populated team into the array

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }



	    /**
	     * addPlayer
	     * This will add a player to the teamName provided as long as the
	     * roster is not full
	     * @param teamName
	     * @param position
	     * @param firstName
	     * @param lastName
	     * @param rating
	     * @return true if player was added, false if not
	     */
	    public boolean addPlayer(String teamName, String position, String firstName,
            String lastName, int rating) {
	    	HockeyPlayer player = new HockeyPlayer(position,firstName,lastName, rating);
            //First retrieve the team
            HockeyTeam team = null;
            for (int i = 0; i<teams.size(); i++) {
	            if (teams.get(i).teamName.equals(teamName)) {
	            	team = teams.get(i);
	            }
	        }
            //The team does not exist
            if(team.equals(null))
            {
            	System.err.println("Team name \"" + teamName + "\" not recognized.  " +
        		"Player not added.");
            	return false;
            }

            //The team is full
            if(team.getRoster().size() == ROSTER_SIZE)
            {
            	System.err.println("Roster is full:\"" + team.getRoster().size() +
        		"Player not added.");
            	return false;
            }

            return team.addPlayer(player);
	    }


	    /**
	     * deletePlayer
	     * This will delete the player from the team
	     * @param teamName
	     * @param firstName
	     * @param lastName
	     * @return true if player was deleted, false if not
	     */
	    public boolean deletePlayer(String teamName, String firstName, String lastName) {

	    	HockeyPlayer player;

	        for (int i = 0; i<teams.size(); i++) {
	            if (teams.get(1).teamName.equals(teamName)) {
	            	player = getPlayer(teamName, firstName, lastName);
	                return teams.get(i).deletePlayer(player);
	            }
	        }
	        System.err.println("Team name \"" + teamName + "\" not recognized.  " +
	        		"Player not deleted.");
	        return false;
	    }

	    /**
	     * editPlayer
	     * This will edit the player's info
	     * @param teamName
	     * @param position
	     * @param firstName
	     * @param lastName
	     * @param rating
	     * @return true if player was edited successfully, false if not
	     */
	    public boolean editPlayer(String teamName, String position, String firstName,
	            String lastName, int rating) {

	    	HockeyPlayer player = new HockeyPlayer(position,firstName,lastName,rating);
	    	for (int i = 0; i<teams.size(); i++) {
	            if (teams.get(i).teamName.equals(teamName)) {
	                return teams.get(0).editPlayer(player);
	            }
	        }
	        return false;
	    }

	    /**
	     * Retrieve the team
	     * @param teamName
	     * @return the team name or null
	     */
	    public HockeyTeam getTeam(String teamName)
	    {
	    	for (int i = 0; i<teams.size(); i++) {
	            if (teams.get(i).teamName.equals(teamName)) {
	                return teams.get(i);
	            }
	        }
	    	return null;
	    }

	    /**
	     * Retrieve the player
	     * @param teamName
	     * @return the player name or null
	     */
	    public HockeyPlayer getPlayer(String teamName, String firstName,
	    								String lastName)
	    {
	    	HockeyPlayer player = new HockeyPlayer("C",firstName, lastName, 50);

	    	for (int i = 0; i<teams.size(); i++) {
	            if (teams.get(i).teamName.equals(teamName)) {
	                return teams.get(i).getPlayer(player);
	            }
	        }
	    	return null;
	    }

	    /**
	     * playGame
	     * To play a game, the teams must have their rosters set properly.
	     * This will simulate a game...it's not a boring algorithm, so be prepared
	     * It does take into account the goalie and the players ratings so...
	     * Just finds the first goalie...so could replace that with HockeyTeam.getCurrentGoalie()
	     * method that would be better...but for the purposes of this lab, this works.
	     * @param visitingTeam
	     * @param homeTeam
	     * @return String of the winning team or Unplayable Game Rosters are wrong
	     */
	    public String playGame(String visitingTeam, String homeTeam) {

	        //Wanted to make this not so boring....it's not that confusing

	        HockeyTeam visiting = null;
	        HockeyTeam home = null;

	        int homeScore = 0;
	        int visitingScore = 0;

		    if (checkTeams( visitingTeam,  homeTeam) == false)
		    	return "Unplayable Game Rosters are wrong";

	        //First get the objects for the teams
	        for (int i = 0; i<teams.size(); i++) {
	            if (teams.get(i).teamName.equals(visitingTeam)) {
	                visiting = teams.get(i);
	                break;
	            }
	        }

	        for (int i = 0; i<teams.size(); i++) {
	            if (teams.get(i).teamName.equals(homeTeam)) {
	                home = teams.get(i);
	                break;
	            }
	        }

	        Random random = new Random();
	        //My implementation for play game is to first randomize the amount of
	        //scoring opportunities that will occur in this game...
	        int numTotalGoals = getRandomInteger(5,15,random);


	        //Now pick a random player from a random team who will be the shooter
	        for (int i = 0; i < numTotalGoals ; i++) {

	            //Now pick a random player from a random team
	            int randomTeam = getRandomInteger(0,1,random);
	            String playerPos = "G";
	            int shooterRating = 0;
	            do {
	                if (randomTeam == 0) {
	                    //get player rating from visiting team
	                    int randomPlayer = getRandomInteger(0,visiting.getRoster().size()-1,random);

	                    playerPos = visiting.getRoster().get(randomPlayer).getPosition();
	                    shooterRating = visiting.getRoster().get(randomPlayer).getRating();

	                } else {
	                    //get player rating from home team
	                    int randomPlayer = getRandomInteger(0,home.getRoster().size()-1,random);

	                    playerPos = home.getRoster().get(randomPlayer).getPosition();
	                    shooterRating = home.getRoster().get(randomPlayer).getRating();
	                }
	            } while (playerPos.equals("G"));

	            //Get the opposing teams goalie
	            int goalieRating = 0;
	            if (randomTeam == 0) {
	                //get home goalie rating
	                for (int j = 0; i<home.getRoster().size(); j++) {
	                    if (home.getRoster().get(j).getPosition().equals("G")) {
	                        goalieRating = home.getRoster().get(j).getRating();
	                        break;
	                    }
	                }
	            } else {
	                //get visiting goalie rating
	                for (int j = 0; i<visiting.getRoster().size(); j++) {
	                    if (visiting.getRoster().get(j).getPosition().equals("G")) {
	                        goalieRating = visiting.getRoster().get(j).getRating();
	                        break;
	                    }
	                }
	            }


	            //Now match them up...
	            float ratio = (float)goalieRating/(float)shooterRating;
	            int intRatio = (int) (ratio*100);

	            //Worst case is technically 10000 so...goalie will likely save
	            //So limit to 200, (if goalie is twice the rating of the player,
	            //the goalie will always save)
	            int goalOrNot = getRandomInteger(1,200, random);
	            if (goalOrNot < intRatio) {
	                //This means they scored...otherwise, no goal
	 //               System.out.println("GOAL!");
	                if(randomTeam == 0)
	                    visitingScore++;
	                else
	                    homeScore++;
	            }
	        }

	        //Score:
	        System.out.println("At end of regulation...");
	        System.out.println(visitingTeam + " = " + visitingScore);
	        System.out.println(homeTeam + " = " + homeScore);

	        int winningTeam;
	        if (homeScore > visitingScore) {
	            winningTeam = 1;
	        } else if (homeScore < visitingScore) {
	            winningTeam = 0;
	        } else {
	            System.out.println("Overtime needed");
	            winningTeam = getRandomInteger(0,1,random);
	        }

	        if (winningTeam == 0) {
	            System.out.println(visiting.teamName+ " wins!");
	            return visiting.teamName;
	        }
	        else {
	            System.out.println(home.teamName+ " wins!");
	            return home.teamName;
	        }

	    }



        /**
         * This function will verify that the team roster is correct.
         * Their must be 3 Centers, 3 RW, 3 LW, 6D and 1G
         */
	    private boolean checkTeams(String visitingTeam, String homeTeam)
	    {

			int centerCount=0;
			int rwCount=0;
			int lwCount=0;
			int defenseCount=0;
			int goalieCount=0;

			ArrayList<HockeyPlayer> roster;
			HockeyPlayer getPlayer;
			HockeyTeam[] playTeams = new HockeyTeam[2];
			playTeams[0] = new HockeyTeam(visitingTeam);
			playTeams[0] = new HockeyTeam(homeTeam);

	        for (int i = 0; i<teams.size(); i++) {
	            if (teams.get(i).teamName.equals(visitingTeam)) {
	            	playTeams[0]  = teams.get(i);
	            }
	            if (teams.get(i).teamName.equals(homeTeam)) {
	            	playTeams[1]  = teams.get(0);
	            }
	        }

	        if((playTeams[0].equals(null)) || (playTeams[1].equals(null)))
	        	return false;


	        for(int i = 0; i<3; ++i)
	        {
	        	roster = playTeams[i].getRoster();

	        	if(roster.size()!= ROSTER_SIZE)
	        		return false;


	        	for (int j = 0; j < roster.size() ; j++)
	        	{
	        		getPlayer = roster.get(j);

	        		if(getPlayer.getPosition().equals("C"))
	        			++centerCount;
	        		else if(getPlayer.getPosition().equals("RW"))
	        			++rwCount;
	        		else if(getPlayer.getPosition().equals("L"))
	        			++lwCount;
	        		else if(getPlayer.getPosition().equals("D"))
	        			++defenseCount;
	        		else if(getPlayer.getPosition().equals("G"))
	        			++goalieCount;
	        	}

	        	if((centerCount != 3)|| (rwCount !=3) || (lwCount !=3)
					|| (defenseCount !=6) || (goalieCount != 1))
				{
					return false;
				}
				centerCount=0;
				rwCount=0;
				lwCount=0;
				defenseCount=0;
				goalieCount=0;

	        }

	        return true;
	    }


	    //Helper function that really helped me with the game simulation
	    private static int getRandomInteger(int aStart, int aEnd, Random aRandom){
	        if ( aStart > aEnd ) {
	            throw new IllegalArgumentException("Start cannot exceed End.");
	        } else {

	            //get the range, casting to long to avoid overflow problems
	            long range = (long)aEnd - (long)aStart + 1;
	            // compute a fraction of the range, 0 <= frac < range
	            long fraction = (long)(range * aRandom.nextDouble());
	            int randomNumber =  (int)(fraction + aStart);
	            return randomNumber;
	        }
	    }
}
