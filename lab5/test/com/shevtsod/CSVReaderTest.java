/**
 * 
 */
package com.shevtsod;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author shevtsod
 *
 */
public class CSVReaderTest {
	private CSVReader cr;

	/**
	 * Creates a CSVReader for a known existing file
	 */
	public void newCSVReader() {
		try {
			assertNotNull(cr = new CSVReader(new FileReader(new File("Canadiens.txt"))));
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist!");
		}
	}
	
	/**
	 * Test method for {@link com.shevtsod.CSVReader#readAll()}.
	 */
	@Test
	public void testReadAll() {
		newCSVReader();
		
		try {
			assertNotNull(cr.readAll());
		} catch (IOException e) {
			System.out.println("Error in testReadAll() - IOException"); 
		}
		
		//TODO: PARSE LINE TEST
		
	}

	/**
	 * Test method for {@link com.shevtsod.CSVReader#close()}.
	 */
	@Test
	public void testClose() {
		FileReader reader;
		
		try {
			reader = new FileReader(new File("Canadiens.txt"));
		} catch (FileNotFoundException e1) {
			System.out.println("File does not exist!");
			return;
		}
		
		cr = new CSVReader(reader);
		
		try {
			cr.close();
			reader.ready();
		} catch(IOException e) {
			//Do nothing
		}
	}

}
