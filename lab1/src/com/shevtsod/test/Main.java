package com.shevtsod.test;

import java.util.InputMismatchException;
import java.util.Scanner;

import ense475.*;

public class Main {
	
	private CoffeeMaker myCoffeeMaker;
	
	/**
	 * Constructor for class Main, initializes a full CoffeeMaker
	 */
	public Main() {
		myCoffeeMaker = new CoffeeMaker();
	}
	
	public static void main(String[] args) {
		System.out.println("COFFEE MAKER SIMULATION");
		System.out.println("This program allows you to create coffee from recipes");
		System.out.println("Your coffee maker only has 20 units of coffee, milk, and sugar");
		System.out.println("It cannot be refilled\n");
		
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
		String userInputString = "";
		
		//Loop until inventory is empty
		while(
			myCoffeeMaker.getInventory().getCoffee() > 0 &&
			myCoffeeMaker.getInventory().getMilk() > 0 &&
			myCoffeeMaker.getInventory().getSugar() > 0
				) {
			
			//Choose to add a recipe or make coffee with a saved recipe
	        System.out.println("Enter 0 to enter a recipe, 1 to make coffee, 2 to delete a recipe: ");
	        correctInput = false;
	        do {
	            System.out.print("\tINPUT: ");
	            try {
	                userInput = input.nextInt();
	                if(userInput == 0 || userInput == 1 || userInput == 2)
	                	correctInput = true;
	                else
	                	System.out.println("ERROR: Invalid input");
	                input.nextLine();
	            } catch(InputMismatchException e) {
	                System.out.println("ERROR: Invalid input");
	                input.nextLine();
	            }
	        } while(!correctInput);
			
			//Populate a recipe from user input and add it to the coffee maker
	        switch(userInput) {
	        case 0:
	        	myCoffeeMaker.addRecipe(populateRecipeFromScanner(input));
	        	break;
	        case 1:
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
		        
	        	break;
	        case 2:
	        	System.out.println("Enter the name of the recipe to delete: ");
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
		        
	        	myCoffeeMaker.deleteRecipe(userInputString);
	        	
	        	break;
	        }		
		}
		
		input.close();
		
	}
	
	/**
	 * Helper method to populate a recipe instance from a scanner
	 * @param input
	 * 		A Scanner of an input stream
	 * @return 
	 * 		The populated Recipe
	 */
	private Recipe populateRecipeFromScanner(Scanner input) {
		Recipe inputRecipe;
		String userRecipeName = "";
		int coffee = 0, 
			milk = 0, 
			sugar = 0;
		boolean correctInput;
		
		//Recipe name
        System.out.println("Enter name of recipe: ");
        correctInput = false;
        do {
            System.out.print("\tINPUT: ");
            try {
                userRecipeName = input.nextLine();
                correctInput = true;
            } catch(InputMismatchException e) {
                System.out.println("ERROR: Invalid input");
                input.nextLine();
            }
        } while(!correctInput);
        
        //Milk units
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
        
        //Sugar units
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
        
        
        //Coffee units
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
		
        
		inputRecipe = new Recipe(userRecipeName, milk, sugar, coffee);
		
		return inputRecipe;
	}

}
