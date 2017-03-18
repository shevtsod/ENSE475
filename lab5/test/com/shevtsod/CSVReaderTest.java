package com.shevtsod;

import com.shevtsod.Hockey.CSVReader;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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

		//Check if CSVReader read operation does not return null
		try {
			assertNotNull(cr.readAll());

			//Read a text file with known contents and make sure the reader parses it correctly
            cr = new CSVReader(new FileReader(new File(TestConstants.PATH_WRITE_TEST_TXT)));

            String[] line1 = {"Test!"};
            String[] line2 = {"Test1", "Test2", "Test3"};

            assert(Arrays.equals(line1, cr.readNextLine()));
            assert(Arrays.equals(line2, cr.readNextLine()));

		} catch (IOException e) {
			System.out.println("Error in testReadAll() - IOException");
		} catch (NullPointerException e) {
            System.out.println("testReadAll() - File does not exist!");
        }
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
