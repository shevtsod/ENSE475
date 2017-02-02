package com.shevtsod;

import java.util.InputMismatchException;
import java.util.Scanner;

import ense475.*;

/**
 * @author Daniel Shevtsov (SID: 200351253)
 */
public class Main {
	
	private CoffeeMaker myCoffeeMaker;
	
	/**
	 * Constructor for class Main, initializes a full CoffeeMaker
	 */
	public Main() {
		myCoffeeMaker = new CoffeeMaker(true);
	}
	
	public static void main(String[] args) {
		System.out.println("COFFEE MAKER SIMULATION");
		System.out.println("This coffee maker allows you to add and edit recipes, add inventory, "
				+ "and make coffee.");
		System.out.println("Your coffee maker has a capacity of 20 units of coffee, milk, and sugar.\n");
		
		Main main = new Main();
		
		//Loop until inventory is empty
		main.programLoop();
		
		System.out.println("Program completed successfully");
	}
	
	/**
	 * The program loop in which the user makes coffee until the coffee maker is empty
	 */
	private void programLoop() {
		
		Scanner input = new Scanner(System.in, "UTF-8");
		boolean correctInput;
		int userInput = 0;
		
		//Loop until inventory is empty
		while(userInput != -1) {
			
			//Print current coffee maker status
			myCoffeeMaker.printCurrentInventory();
			myCoffeeMaker.printCurrentRecipeList();
			
			//Choose to add a recipe or make coffee with a saved recipe
	        System.out.println("COMMANDS:" +
	        		"\n\t0\t- Add a recipe" +
	        		"\n\t1\t- Edit a recipe" + 
	        		"\n\t2\t- Delete a recipe" +
	        		"\n\t3\t- Add Inventory" +
	        		"\n\t4\t- Make Coffee" +
	        		"\n\t-1\t- Exit Program" + 
	        		"\n");
	        
	        correctInput = false;
	        do {
	            System.out.print("\tINPUT: ");
	            try {
	                userInput = input.nextInt();
	                if(userInput >= -1 && userInput <= 4)
	                	correctInput = true;
	                else
	                	System.out.println("ERROR: Invalid input");
	                input.nextLine();
	            } catch(InputMismatchException e) {
	                System.out.println("ERROR: Invalid input");
	                input.nextLine();
	            }
	        } while(!correctInput);
			
	        switch(userInput) {
	        //Add a recipe
	        case 0: optionAddRecipe(input);
	        	break;
	        //Edit a recipe
	        case 1: optionEditRecipe(input);
	        	break;
	        //Delete a recipe
	        case 2: optionDeleteRecipe(input);
	        	break;	
	        //Add Inventory
	        case 3: optionAddInventory(input);
	        	break;
	        //Make Coffee
	        case 4: optionMakeCoffee(input);
	        	break;
	        default:
	        	break;
	        }
		}
		
		input.close();
		
	}

	
	/**
	 * Helper method to populate a recipe to the coffee maker from user input
	 * @param input
	 * 		A Scanner of an input stream
	 */
	private void optionAddRecipe(Scanner input) {
		myCoffeeMaker.addRecipe(populateNewRecipe(input));
	}

	
	
	
	/**
	 * Helper method to handle editing an existing recipe from user input
	 * @param input
	 * 		Input stream Scanner object
	 */
	private void optionEditRecipe(Scanner input) {
		myCoffeeMaker.editRecipe(populateNewRecipe(input));
	}
	

	/**
	 * Helper method to handle deleting a recipe from user input
	 * @param input
	 * 		Input stream Scanner object
	 */
	private void optionDeleteRecipe(Scanner input) {
		System.out.println("Enter the name of the recipe to delete: ");

		if(myCoffeeMaker.deleteRecipe(getStringFromScanner(input)))
			System.out.println("Deleted recipe successfully");
	}
	
	
	/**
	 * Helper method to handle deleting a recipe from user input
	 * @param input
	 * 		Input stream Scanner object
	 */
	private void optionAddInventory(Scanner input) {
		Inventory add = new Inventory();
		
		add.setMilk(getMilkLevelFromScanner(input));
		add.setSugar(getSugarLevelFromScanner(input));
		add.setCoffee(getCoffeeLevelFromScanner(input));
		
		if(myCoffeeMaker.addInventory(add))
			System.out.println("Added inventory successfully");
		else
			System.out.println(" - ERROR: Could not add inventory");
	}
	
	
	/**
	 * Helper method to handle making coffee
	 * @param input
	 * 		Input stream Scanner Object
	 */
	private void optionMakeCoffee(Scanner input) {
		String userInputString = "";
		boolean correctInput;
		System.out.println("Enter the name of the recipe to make coffee from: ");
		correctInput = false;
		do {
		    System.out.print("\tINPUT: ");
		    try {
		        userInputString = input.nextLine();
		        if(myCoffeeMaker.getRecipe(userInputString) != null)
		        	correctInput = true;
		        else
		        	System.out.println("This recipe does not exist");
		    } catch(InputMismatchException e) {
		        System.out.println("ERROR: Invalid input");
		        input.nextLine();
		    }
		} while(!correctInput);
		
		myCoffeeMaker.makeCoffee(userInputString);
	}
	
	/**
	 * Populates and returns a new Recipe object from input Scanner
	 * @param input
	 * 		Input stream Scanner
	 * @return
	 * 		Recipe object
	 */
	private Recipe populateNewRecipe(Scanner input) {
		String userRecipeName = "";
		
		//Recipe name
        System.out.println("Enter name of recipe: ");
        userRecipeName = getStringFromScanner(input);
        
        return new Recipe(userRecipeName, 
				getMilkLevelFromScanner(input), 
				getSugarLevelFromScanner(input),
				getCoffeeLevelFromScanner(input));
	}
	
	/**
	 * method getStringFromScanner
	 * @param input
	 * 		Input stream Scanner object
	 * @return
	 * 		String from single line of user input
	 */
	private String getStringFromScanner(Scanner input) {
		String userInputString = "";
		boolean correctInput;
		correctInput = false;
		do {
		    System.out.print("\tINPUT: ");
		    try {
		        userInputString = input.nextLine();
		        correctInput = true;
		    } catch(InputMismatchException e) {
		        System.out.println("ERROR: Invalid input");
		        input.nextLine();
		    }
		} while(!correctInput);
		
		return userInputString;
	}

	/**
	 * method getMilkLevelFromScanner
	 * 
	 * @param input
	 * 		Input stream Scanner object
	 * @return
	 * 		Milk level from user input
	 */
	private int getMilkLevelFromScanner(Scanner input) {
		boolean correctInput;
		int milk = 0;
		
        System.out.println("Enter number of milk units: ");
        correctInput = false;
        do {
            System.out.print("\tINPUT: ");
            try {
                milk = input.nextInt();
                if(milk >= 0 && milk <= CoffeeMaker.MAX_INVENTORY) {
                    correctInput = true;
                } else {
                    System.out.println(
                            "ERROR: The number of milk units must be between 0 and "
                    		+ Integer.toString(CoffeeMaker.MAX_INVENTORY)
                    );
                    input.nextLine();
                }
            } catch(InputMismatchException e) {
                System.out.println(
                        "ERROR: Invalid input"
                );
                input.nextLine();
            }
        } while(!correctInput);
		return milk;
	}

	/**
	 * method getSugarLevelFromScanner
	 * 
	 * @param input
	 * 		Input stream Scanner object
	 * @return
	 * 		Sugar level from user input
	 */
	private int getSugarLevelFromScanner(Scanner input) {
		boolean correctInput;
		int sugar = 0;
		
        System.out.println("Enter number of sugar units: ");
        correctInput = false;
        do {
            System.out.print("\tINPUT: ");
            try {
                sugar = input.nextInt();
                if(sugar >= 0 && sugar <= CoffeeMaker.MAX_INVENTORY) {
                    correctInput = true;
                } else {
                    System.out.println(
                            "ERROR: The number of sugar units must be between 0 and "
                    		+ Integer.toString(CoffeeMaker.MAX_INVENTORY)
                    );
                    input.nextLine();
                }
            } catch(InputMismatchException e) {
                System.out.println(
                        "ERROR: Invalid input"
                );
                input.nextLine();
            }
        } while(!correctInput);
		return sugar;
	}

	/**
	 * method getCoffeeLevelFromScanner
	 * 
	 * @param input
	 * 		Input stream Scanner object
	 * @return
	 * 		Coffee level from user input
	 */
	private int getCoffeeLevelFromScanner(Scanner input) {
		boolean correctInput;
		int coffee = 0;
		
        System.out.println("Enter number of coffee units: ");
        correctInput = false;
        do {
            System.out.print("\tINPUT: ");
            try {
                coffee = input.nextInt();
                if(coffee >= 0 && coffee <= CoffeeMaker.MAX_INVENTORY) {
                    correctInput = true;
                } else {
                    System.out.println(
                            "ERROR: The number of coffee units must be between 0 and "
                    		+ Integer.toString(CoffeeMaker.MAX_INVENTORY)
                    );
                    input.nextLine();
                }
            } catch(InputMismatchException e) {
                System.out.println(
                        "ERROR: Invalid input"
                );
                input.nextLine();
            }
        } while(!correctInput);
		return coffee;
	}
	
}
