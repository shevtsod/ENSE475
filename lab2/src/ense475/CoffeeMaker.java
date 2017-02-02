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
	 * Constructor for the coffee maker that sets 4 predefined recipes
	 * @param predefinedRecipes
	 * 		Sets predefined recipes if true, empty if false
	 */
	public CoffeeMaker(boolean predefinedRecipes) {
		this();
		if(predefinedRecipes)  setPredefinedRecipes();
	}
	
	/**
	 * Helper method to initialize the coffee maker with 4 built in recipes. 
	 * The user may edit or delete these
	 */
	private void setPredefinedRecipes() {
		//The 4 predefined recipes can be defined and edited here
		//The numbers are fake, just for demonstration purposes
		//Note: The coffee maker will not allow adding more than 4 recipes
		addRecipe(new Recipe("Cappuccino", 4, 1, 2));
		addRecipe(new Recipe("Latte", 2, 3, 4));
		addRecipe(new Recipe("Expresso", 5, 3, 1));
		addRecipe(new Recipe("Mocha", 5, 1, 5));
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
	 * @param Recipe
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
		
		if(recipeArray != null && numRecipes < MAX_NUM_RECIPES) {
			for(int i = 0; i < MAX_NUM_RECIPES; i++) {
				//Find an empty array cell to place this new recipe in
				if(recipeArray[i] == null) {
					recipeArray[i] = rAdd;
					numRecipes++;
					return true;
				}
			}
		}
		
		System.out.println("Could not add recipe " + rAdd.getRecipeName() + 
					". Maximum number of recipes exceeded (" + MAX_NUM_RECIPES + ")");
		return false;
	}

	/**
	 * getRecipe Method
	 *
	 * @param Recipe
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
	 * @param Recipe
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
			if(
					recipeArray[i] != null &&
					recipeArray[i].getRecipeName().toUpperCase().equals(recipeName.toUpperCase())) 
			{
				recipeArray[i] = null;
				numRecipes--;
				return true;
			}
		
		System.out.println("Could not delete recipe " + recipeName + ". This recipe is not"
				+ " saved in the coffee maker");
		return false;
	}
	
	/**
	 * editRecipe Method
	 * 		This method allows an edited Recipe to be passed in
	 * 		If there is a recipe with the same name in the coffee maker, its
	 * 		ingredient numbers are changed to match the passed in edited ingredients.
	 * 
	 * @param recipe
	 * 			Pass in the edited recipe
	 * @return
	 * 		true if edited successfully, false otherwise
	 */
	public boolean editRecipe(Recipe recipe) {
		//Find the recipe in the coffee maker
		if (recipeArray != null && numRecipes > 0) {
			for (int i = 0; i < MAX_NUM_RECIPES; i++) {
				if(
						recipeArray[i] != null && 
						recipeArray[i].getRecipeName().toUpperCase().equals(recipe.getRecipeName().toUpperCase()))
				{
					recipeArray[i] = recipe;
					return true;
				}
			}
		}
		
		System.out.println("The recipe" + recipe.getRecipeName() + " is not saved in the coffee maker");
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
		
		return true;
	}
	
	/**
	 * addInventory method
	 * 
	 * @param inventory
	 * 			Additional inventory to refill the coffee maker
	 * @return
	 * 			Addition of inventory succeeded or failed
	 */
	public boolean addInventory(Inventory inventory) {
		if(inventory == null)
			return false;
		
		if(this.getInventory().getMilk() + inventory.getMilk() <= MAX_INVENTORY)
			this.getInventory().setMilk(this.getInventory().getMilk() + inventory.getMilk());
		else
			this.getInventory().setMilk(MAX_INVENTORY);
		
		if(this.getInventory().getSugar() + inventory.getSugar() <= MAX_INVENTORY)
			this.getInventory().setSugar(this.getInventory().getSugar() + inventory.getSugar());
		else
			this.getInventory().setSugar(MAX_INVENTORY);
		
		if(this.getInventory().getCoffee() + inventory.getCoffee() <= MAX_INVENTORY)
			this.getInventory().setCoffee(this.getInventory().getCoffee() + inventory.getCoffee());
		else
			this.getInventory().setCoffee(MAX_INVENTORY);
		
		return true;
	}

	
	/**
	 * Method to print to console a list of all currently saved recipes
	 */
	public void printCurrentRecipeList() {
		System.out.println("CURRENT RECIPE LIST: ");
		int currentRecipe = 1;
		
		if (recipeArray != null && numRecipes > 0) {
			for (Recipe r : recipeArray) {
				if(r != null)
					System.out.println(" " + currentRecipe++ + " - " + 
						r.getRecipeName() + 
						"\t\t(Milk: " + r.getMilkLevel() + 
						", Sugar: " + r.getSugarLevel() + 
						", Coffee: " + r.getCoffeeLevel() + ")");
			}
		} else
			System.out.println(" - None");
	}
	
	/**
	 * Method to print to console the list of remaining inventory in the coffee maker
	 */
	public void printCurrentInventory() {
		System.out.println("CURRENT INVENTORY:\n" +
				"Milk: " + this.getInventory().getMilk() +
				"\tSugar: " + this.getInventory().getSugar() +
				"\tCoffee: " + this.getInventory().getCoffee());
	}

}
