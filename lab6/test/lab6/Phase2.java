package lab6;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import com.shevtsod.CSVReader;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Daniel Shevtsov (SID: 200351253)
 */
public class Phase2 {
    public static final String FILENAME_INPUT = "RMSLreg-20170227.csv";
    public static final String FILENAME_OUTPUT = "log.txt";

    @Before
    public void prepare() {
        setBaseUrl("http://uregina.ca/~douglatr/RMSLZoneCheck");
        beginAt("RMSLZoneCheck.html");
    }

    /**
     * Reads the given CSV file using CSVReader and writes any errors to log.txt
     * @throws IOException Exception thrown when input file does not exist
     */
    @Test
    public void readCSVtoLogFile() throws IOException {
        //Assert that required elements exist
        assertButtonPresentWithText("Submit");

        //File to read from
        CSVReader fin = new CSVReader(new FileReader(FILENAME_INPUT));
        //File to write to (log.txt)
        BufferedWriter fout = new BufferedWriter(new FileWriter(FILENAME_OUTPUT));

        ArrayList<String[]> fileContents = fin.readAll();
        //Line 0 is the header of the table, it should not be part of the data so it is removed
        fileContents.remove(0);
        int lineNumber = 2;

        //Write log header
        fout.write("WEB APPLICATION ERRORS LOG AT " + new Date().toString().toUpperCase() + ": \n\n");

        //Loop through all lines
        for(String[] line : fileContents) {
            /*
            Each line has 5 values:
            0 - Address
            1 - City
            2 - State/Province
            3 - Postal Code  (NOTE: Disregarded because the form does not have this input)
            4 - Zone
            */

            //NOTE: Some lines in the CSV cause the web app to return an error
            //e.g. enter "RR2 Site 200 Box 180, Regina, Saskatchewan,
            //The browser's console returns 'Cannot read property 'geometry' of undefined
            //This program simply outputs this error if it occurs
            try {
                String expectedOutput = line[4];
                String actualOutput = testForm(line[0], line[1], line[2]);

                //If expected output and actual output are different, log this line to log.txt
                if(!expectedOutput.equals(actualOutput)) {
                    String logLine = lineNumber +
                            ". CSV File Output: " + expectedOutput
                            + "\tForm Output: " + actualOutput
                            + "\n";
                    fout.write(logLine);
                    prepare();
                } else {
                    fout.write(lineNumber + ". OK\n");
                }

            } catch(RuntimeException e) {
                String logLine = lineNumber + ". " + e.getMessage() + "\n";
                fout.write(logLine);
                prepare();
            }

            lineNumber++;
        }

        //Close the input and output files
        fin.close();
        fout.close();
    }

    /**
     * Helper method to return output from the form on the page based on inputs to the form
     * @param address String address input
     * @param city String city input
     * @param province String province input
     */
    private String testForm(String address, String city, String province) throws RuntimeException {
        setTextField("address", address);
        setTextField("city", city);
        setTextField("province", province);
        assertButtonPresentWithText("Submit");
        clickButtonWithText("Submit");
        return getElementAttributeByXPath("//*[@id='idTextZone']", "value");
    }
}
