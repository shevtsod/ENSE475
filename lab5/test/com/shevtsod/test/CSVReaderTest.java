package com.shevtsod.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

/**
 * @author Daniel Shevtsov (SID: 200351253)
 */
public class CSVReaderTest {
	private CSVReader cr;

	/**
	 * Creates a CSVReader for a known existing file
	 */
	public void newCSVReader() {
		try {
			assertNotNull(cr = new CSVReader(new FileReader(new File(TestConstants.PATH_CANADIENS_TXT))));
		} catch (FileNotFoundException e) {
			System.out.println("newCSVReader() - File does not exist!");
		}
	}
	
	/**
	 * Test method for {@link CSVReader#readAll()}.
	 */
	@Test
	public void testReadAll() {
		newCSVReader();
		
		try {
			assertNotNull(cr.readAll());
		} catch (IOException e) {
			System.out.println("Error in testReadAll() - IOException"); 
		} catch (NullPointerException e) {
		    System.out.println("testReadAll() - File does not exist!");
        }
		
		//TODO: PARSE LINE TEST
		
	}

	/**
	 * Test method for {@link CSVReader#close()}.
	 */
	@Test
	public void testClose() {
		FileReader reader;
		
		try {
			reader = new FileReader(new File(TestConstants.PATH_CANADIENS_TXT));
		} catch (FileNotFoundException e) {
			System.out.println("testClose() - File does not exist!");
			return;
		}
		
		cr = new CSVReader(reader);
		
		try {
			cr.close();
			assertFalse(reader.ready());
		} catch(IOException e) {
			//Do nothing
		}
	}

}
