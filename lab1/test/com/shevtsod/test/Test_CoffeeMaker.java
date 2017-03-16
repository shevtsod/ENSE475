/*
 * AUTHOR: Daniel Shevtsov (SID: 200351253
 */
package com.shevtsod.test;
import ense475.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author shevtsod
 *
 */
public class Test_CoffeeMaker {
	
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
		assertEquals(coffeeTester.getInventory().getMilk(), originalCoffee - rAdd1.getMilkLevel());
		assertEquals(coffeeTester.getInventory().getSugar(), originalCoffee - rAdd1.getSugarLevel());
		
		assertFalse(coffeeTester.makeCoffee("test"));
	}

}
