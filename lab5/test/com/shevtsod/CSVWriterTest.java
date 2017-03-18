package com.shevtsod;

import com.shevtsod.Hockey.CSVReader;
import com.shevtsod.Hockey.CSVWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author Daniel Shevtsov (SID: 200351253)
 */
public class CSVWriterTest {

	private CSVWriter cw;

	/**
	 * Test method for {@link CSVWriter#CSVWriter(java.io.Writer)}.
	 */
	@Test
	public void testCSVWriterWriter() {
		//Test that CSVWriter initializes an object without issues
        try {
            cw = new CSVWriter(new FileWriter(TestConstants.PATH_WRITE_TEST_TXT));
        } catch (IOException e) {
            System.out.println("Error in testCSWWriterWriter() - IOException");
        }

        assertNotNull(cw);
	}


	/**
	 * Test method for {@link CSVWriter#writeAll(java.util.List)}.
	 */
	@Test
	public void testWriteAll() {
	    //Test that CSVWriter can write a non-empty file successfully
		FileWriter writer;
		File writeText = new File(TestConstants.PATH_WRITE_TEST_TXT);

		//Write 2 lines to Write_Test.txt
        List<String[]> text = new ArrayList<>();

        String[] line1 = {"Test!"};
        String[] line2 = {"Test1", "Test2", "Test3"};

        text.add(line1);
        text.add(line2);

		try {
		    //Create a new file
            if(!writeText.exists())
                writeText.createNewFile();

            writer = new FileWriter(writeText);
		    cw = new CSVWriter(writer);
		    cw.writeAll(text);

		    //Check contents of file, assumes CSVReader works correctly
            CSVReader read = new CSVReader(new FileReader(writeText));
            assert(Arrays.equals(text.get(0), read.readNextLine()));
            assert(Arrays.equals(text.get(1), read.readNextLine()));

        } catch (IOException e) {
            System.out.println("testWriteAll() - IOException!");
        }
    }

	/**
	 * Test method for {@link CSVWriter#close()}.
	 */
	@Test
	public void testClose() {
        FileWriter writer;

        try {
            writer = new FileWriter(new File(TestConstants.PATH_WRITE_TEST_TXT));
        } catch (IOException e) {
            System.out.println("testClose() - IOException!");
            return;
        }

        cw = new CSVWriter(writer);

        try {
            cw.close();
        } catch(IOException e) {
            System.out.println("testClose() - IOException!");
        }
	}

}
