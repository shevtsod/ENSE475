package ense475;

/**
 * Authors: tdouglas, Daniel Shevtsov
 */

/**
 * CoffeeMaker constructor 
 * 
 * The inventory should be created and filled up.
 */

public class CoffeeMaker {
	
	
	public static final int MAX_NUM_RECIPES = 4; // Maximum number of recipes
	public static final int MAX_INVENTORY = 20;
	private Recipe[] recipeArray; // This is the array of recipes
	private int numRecipes = 0;
	private Inventory inventory; // This is our inventory in the coffee maker
	
	/**
	 * Constructor for the coffee maker
	 * The inventory should be created and filled.
	 */
	public CoffeeMaker() {
		
		//Setup inventory
		inventory = new Inventory();
		inventory.setMilk(MAX_INVENTORY);
		inventory.setCoffee(MAX_INVENTORY);
		inventory.setSugar(MAX_INVENTORY);
		
		recipeArray = new Recipe[MAX_NUM_RECIPES];
	}

	
	/**
	 * Getter of the property <tt>inventory</tt>
	 *
	 * @return Returns the inventory.
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * addRecipe Method
	 *
	 * @param rAdd
	 *            Will add a recipe if possible
	 * @return true or false
	 */
	public boolean addRecipe(Recipe rAdd) {
		
		if(rAdd == null) {
			System.out.println("Could not add recipe. Incorrect Recipe format");
			return false;
		}
		
		if(this.getRecipe(rAdd.getRecipeName()) != null) {
			System.out.println("Could not add recipe " + rAdd.getRecipeName() +
					 ". Recipe already exists");
			return false;
		}
		
		if(numRecipes < MAX_NUM_RECIPES) {
			recipeArray[numRecipes++] = new Recipe(
					rAdd.getRecipeName(),
					rAdd.getMilkLevel(), 
					rAdd.getSugarLevel(), 
					rAdd.getCoffeeLevel());
			System.out.println("Added recipe " + rAdd.getRecipeName() + " successfully");
			return true;
		} else {
			System.out.println("Could not add recipe " + rAdd.getRecipeName() + 
					". Maximum number of recipes exceeded (" + MAX_NUM_RECIPES + ")");
			return false;
		}

	}

	/**
	 * getRecipe Method
	 *
	 * @param recipeName
	 *            name String
	 * @return Recipe or NULL
	 */
	public Recipe getRecipe(String recipeName) {
		
		if(numRecipes == 0) {
			return null;
		}
		
		for(int i = 0; i < MAX_NUM_RECIPES; i++) {
			if(
			recipeArray[i] != null && 
			recipeArray[i].getRecipeName().toUpperCase().equals(recipeName.toUpperCase())) {
				return new Recipe(
						recipeArray[i].getRecipeName(),
						recipeArray[i].getMilkLevel(),
						recipeArray[i].getSugarLevel(),
						recipeArray[i].getCoffeeLevel()
						);
			}
		}
		return null;
	}

	/**
	 * deleteRecipe Method
	 *
	 * @param recipeName
	 *            Will delete a recipe if possible
	 * @return true or false
	 */
	public boolean deleteRecipe(String recipeName) {
		
		if(numRecipes == 0) {
			System.out.println("Could not delete recipe " + recipeName + ". This coffee maker"
					+ " has no saved recipes");
			return false;
		}
		
		for(int i = 0; i < MAX_NUM_RECIPES; i++)
			if(recipeArray[i] != null &&
			recipeArray[i].getRecipeName().toUpperCase().equals(recipeName.toUpperCase())) {
			recipeArray[i] = null;
				numRecipes--;
				System.out.println("Delete recipe " + recipeName + " successfully");
				return true;
			}
		
		System.out.println("Could not delete recipe " + recipeName + ". This recipe is not"
				+ " saved in the coffee maker");
		return false;
	}


	/**
	 * makeCoffee Method
	 *
	 * @param recipeName
	 *            Will make the coffee if the recipe is there
	 * @return true or false
	 */
	public boolean makeCoffee(String recipeName) {
		
		Recipe recipeToUse;
		
		if((recipeToUse = this.getRecipe(recipeName)) == null)
			return false;
		
		int coffee = recipeToUse.getCoffeeLevel();
		int milk = recipeToUse.getMilkLevel();
		int sugar = recipeToUse.getSugarLevel();
		
		if(
			coffee > MAX_INVENTORY || 
			milk > MAX_INVENTORY || 
			sugar > MAX_INVENTORY ||
			coffee > this.getInventory().getCoffee() ||
			milk > this.getInventory().getMilk() ||
			sugar > this.getInventory().getSugar()
			) {
			
			System.out.println("Could not make coffee. Recipe resources"
					+ " exceed inventory levels.");
			return false;
		}
		
		this.getInventory().setCoffee(this.getInventory().getCoffee() - coffee);
		this.getInventory().setMilk(this.getInventory().getMilk() - milk);
		this.getInventory().setSugar(this.getInventory().getSugar() - sugar);
		
		System.out.println("Successfully created coffee from recipe \"" + recipeName + "\"");
		System.out.println("Resources left in the coffee maker: \n" +
							"Cofeee: " + this.getInventory().getCoffee() +
							"\nMilk: " + this.getInventory().getMilk() +
							"\nSugar: " + this.getInventory().getSugar());
		
		return true;
	}


}
