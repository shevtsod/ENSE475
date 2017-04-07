/**
* CSVReader.java
*
* @author Trevor Douglas
*   <A HREF="mailto:douglatr@uregina.ca"> (douglatr@uregina.ca) </A>
*
* Original code copyright � Mar 15, 2010 Trevor Douglas.  Modifications can be made
* with Author's consent.
* @version Mar 15, 2010
*
**/

package com.shevtsod;

/**
 * @author tdouglas, Daniel Shevtsov (SID: 200351253)
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class CSVReader {

    private BufferedReader br;

    private char separator;
    private static final char DEFAULT_SEPARATOR = ',';
    private char quoteChar;
    private static final char DEFAULT_QUOTE_CHAR = '"';
    private char escapeChar;
    private static final char DEFAULT_ESCAPE_CHAR = '"';

    /*
     * Constructor method that provides the bare minimum as far as arguments go
     * @param reader - the reader that reads the csv
     */
    public CSVReader(Reader reader) {
        this(reader, DEFAULT_SEPARATOR);

    }

    /*
     * Constructor method with a separator character provided
     * @param reader - the reader that reads the csv
     * @param separater - the separator character used for the csv
     */
    public CSVReader(Reader reader, char separator) {
        this(reader, separator, DEFAULT_QUOTE_CHAR);

    }
    /*
     * Constructor method with a separator and quote character provided
     * @param reader - the reader that reads the csv
     * @param separater - the separator character used for the csv
     */
    public CSVReader(Reader reader, char separator, char quoteChar) {
        this(reader, separator, quoteChar, DEFAULT_ESCAPE_CHAR);
    }

    /*
     * Constructor method with a separator, quote and escape character provided
     * @param reader - the reader that reads the csv
     * @param separater - the separator character used for the csv
     * @param escapeChar - the escape character used for the csv
     */
    public CSVReader(Reader reader, char separator, char quoteChar, char escapeChar) {
        br = new BufferedReader(reader);
        this.separator = separator;
        this.quoteChar = quoteChar;
        this.escapeChar = escapeChar;
    }


    /*
     * Reads the entire file at once and puts the lines into an ArrayList<String[]>.
     *
     * @return an ArrayList<String[]>, each element is a String[] for a line
     *          from the csv file
     * @throws IOException - let user deal with exceptions...
     *
     */
    public ArrayList<String[]> readAll() throws IOException {
        ArrayList<String[]> allLines = new ArrayList<String[]>();
        String[] nextLine;
        do {
            nextLine = readNextLine();
            if (nextLine != null)
                allLines.add(nextLine);
        } while (nextLine != null);
        return allLines;
    }

    /*
     * Reads the next line from the BufferReader and parses the line into a
     * String[].  Returns null if at EOF.
     *
     * @return a String[], each csv as an element in the array
     * @throws IOException - let user deal with exceptions...
     */
    public String[] readNextLine() throws IOException{
        String nextLine = br.readLine();
        if (nextLine == null)
            return null;

        return parseLine(nextLine);

    }

    /*
     * Parses the given line and returns an array of the csv
     *
     * @param line - the line to parse
     * @return a String[], each csv as an element in the array
     * @throws IOException - let user deal with exceptions...
     */
    private String[] parseLine(String line) throws IOException{

        ArrayList<String> tokens = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        boolean insideQuotes = false;

        do {
            //if already inside quotes, then we just reached end of line...
            if (insideQuotes) {
                sb.append("\n");
                line = br.readLine();   //so get the next line and continue...
                if (line == null)
                    break;
            }

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                //first check if we are escaping anything...
                if (c == escapeChar) {

                    if (insideQuotes &&
                    line.length() > (i+1) &&
                    line.charAt(i+1) == quoteChar) {
                        sb.append(line.charAt(++i));
                    } else if (escapeChar == quoteChar) {   //special case...
                        insideQuotes = !(insideQuotes);
                    }

                } else if (c == quoteChar) {    //or are we starting/ending a quoted section
                    insideQuotes = !insideQuotes;
                } else if (c == separator && !(insideQuotes)) {
                    tokens.add(sb.toString());
                    sb = new StringBuffer();    //no remove all...just make new one
                } else {
                    sb.append(c);
                }

            }

        } while (insideQuotes);
        tokens.add(sb.toString());


        //not 100% sure I had to put the new String[] {} in the toArray method..
        //but it didn't work without it...looked at doc and have an idea but...
        return (String[]) tokens.toArray(new String[] {});
    }

    /*
     * Close the BufferedReader
     * @throws IOException - let user deal with exceptions...
     */
    public void close() throws IOException {
        br.close();
    }
}
