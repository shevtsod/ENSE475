/**
 * 
 */
package com.shevtsod;
import ense475.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author shevtsod
 *
 */
public class CoffeeMakerTest {
	
	private CoffeeMaker coffeeTester;
	
	/**
	 * Create a new CoffeeMaker instance to test
	 */
	@Before
	public void createCoffeeMaker() {
		coffeeTester = new CoffeeMaker();
	}
	
	/**
	 * Test method CoffeeMaker() to make sure Inventory is properly initialized
	 */
	@Test
	public void test_Constructor() {
		assertEquals(coffeeTester.getInventory().getCoffee(), CoffeeMaker.MAX_INVENTORY);
		assertEquals(coffeeTester.getInventory().getMilk(), CoffeeMaker.MAX_INVENTORY);
		assertEquals(coffeeTester.getInventory().getSugar(), CoffeeMaker.MAX_INVENTORY);
	}

	/**
	 * Test method addRecipe()
	 */
	@Test
	public void test_addRecipe() {
		Recipe rAdd1 = new Recipe("Test", 0, 20, 1);
		Recipe rAdd2 = new Recipe("Test2", 20, 0, 0);
		Recipe rAdd3 = new Recipe("Test3", 0, 0, 20);
		Recipe rAdd4 = new Recipe("Test4", 0, 20, 0);
		Recipe rAdd5 = new Recipe("TestOverflow", 0, 0, 0);
		
		assertTrue(coffeeTester.addRecipe(rAdd1));
		
		assertTrue(coffeeTester.addRecipe(rAdd2));
		assertEquals(coffeeTester.getRecipe(rAdd2.getRecipeName()).getRecipeName(), rAdd2.getRecipeName());
		
		assertTrue(coffeeTester.addRecipe(rAdd3));
		assertEquals(coffeeTester.getRecipe(rAdd3.getRecipeName()).getMilkLevel(), rAdd3.getMilkLevel());
		
		assertTrue(coffeeTester.addRecipe(rAdd4));
		assertEquals(coffeeTester.getRecipe(rAdd4.getRecipeName()).getCoffeeLevel(), rAdd4.getCoffeeLevel());
		
		assertFalse(coffeeTester.addRecipe(rAdd5));
		
	}
	
	/**
	 * Test method deleteRecipe()
	 */
	@Test
	public void test_deleteRecipe() {
		Recipe rAdd1 = new Recipe("Test", 0, 20, 1);
		Recipe rAdd5 = new Recipe("TestOverflow", 0, 0, 0);
		
		coffeeTester.addRecipe(rAdd1);
		assertTrue(coffeeTester.deleteRecipe(rAdd1.getRecipeName()));
		assertFalse(coffeeTester.deleteRecipe(rAdd1.getRecipeName()));
		assertFalse(coffeeTester.deleteRecipe(rAdd5.getRecipeName()));
		
	}
	
	/**
	 * Test method makeCoffee()
	 */
	@Test
	public void test_makeCoffee() {
		Recipe rAdd1 = new Recipe("Test", 0, 20, 1);
		
		int originalCoffee = coffeeTester.getInventory().getCoffee();
		int originalMilk = coffeeTester.getInventory().getMilk();
		int originalSugar = coffeeTester.getInventory().getSugar();
		
		coffeeTester.addRecipe(rAdd1);
		assertTrue(coffeeTester.makeCoffee(rAdd1.getRecipeName()));
		assertEquals(coffeeTester.getInventory().getCoffee(), originalCoffee - rAdd1.getCoffeeLevel());
		assertEquals(coffeeTester.getInventory().getMilk(), originalMilk - rAdd1.getMilkLevel());
		assertEquals(coffeeTester.getInventory().getSugar(), originalSugar - rAdd1.getSugarLevel());
		
		assertFalse(coffeeTester.makeCoffee("test"));
	}
	
	/**
	 * Test method addInventory()
	 */
	@Test
	public void test_addInventory() {
		Recipe rAdd1 = new Recipe("Test", 0, 20, 1);
		coffeeTester.addRecipe(rAdd1);
		coffeeTester.makeCoffee(rAdd1.getRecipeName());
		
		assertEquals(coffeeTester.getInventory().getMilk(), 20);
		assertEquals(coffeeTester.getInventory().getSugar(), 0);
		assertEquals(coffeeTester.getInventory().getCoffee(), 19);
		
		Inventory addedInventory = new Inventory();
		addedInventory.setMilk(5);
		addedInventory.setSugar(10);
		addedInventory.setCoffee(5);
		coffeeTester.addInventory(addedInventory);
		
		assertEquals(coffeeTester.getInventory().getMilk(), 20);
		assertEquals(coffeeTester.getInventory().getSugar(), 10);
		assertEquals(coffeeTester.getInventory().getCoffee(), 20);
		
		coffeeTester.addInventory(addedInventory);
		
		assertEquals(coffeeTester.getInventory().getMilk(), 20);
		assertEquals(coffeeTester.getInventory().getSugar(), 20);
		assertEquals(coffeeTester.getInventory().getCoffee(), 20);
		
	}
	
	/**
	 * Test method editRecipe()
	 */
	@Test
	public void test_editRecipe() {
		Recipe rAdd1 = new Recipe("Test", 0, 20, 1);
		coffeeTester.addRecipe(rAdd1);
		
		assertEquals(coffeeTester.getRecipe(rAdd1.getRecipeName()).getMilkLevel(), 0);
		assertEquals(coffeeTester.getRecipe(rAdd1.getRecipeName()).getSugarLevel(), 20);
		assertEquals(coffeeTester.getRecipe(rAdd1.getRecipeName()).getCoffeeLevel(), 1);
		
		Recipe rAdd2 = new Recipe("Test", 1, 19, 2);
		
		assertTrue(coffeeTester.editRecipe(rAdd2));
		assertEquals(coffeeTester.getRecipe(rAdd1.getRecipeName()).getMilkLevel(), 1);
		assertEquals(coffeeTester.getRecipe(rAdd1.getRecipeName()).getSugarLevel(), 19);
		assertEquals(coffeeTester.getRecipe(rAdd1.getRecipeName()).getCoffeeLevel(), 2);
		
		Recipe rAdd3 = new Recipe("Test 2", 1, 19, 2);
		
		assertFalse(coffeeTester.editRecipe(rAdd3));
		
	}

}
