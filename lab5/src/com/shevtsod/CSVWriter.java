/**
* CSVWriter.java
*
* @author Trevor Douglas
*   <A HREF="mailto:douglatr@uregina.ca"> (douglatr@uregina.ca) </A>
*
* Original code copyright © Mar 15, 2010 Trevor Douglas.  Modifications can be made
* with Author's consent.
* @version Mar 15, 2010
*
**/

package com.shevtsod;


import java.io.*;
import java.util.*;


/**
 * @author tdouglas, Daniel Shevtsov (SID: 200351253)
 *
 */


public class CSVWriter {


    private BufferedWriter bw;

    private char separator;
    private static final char DEFAULT_SEPARATOR = ',';
    private char quoteChar;
    private static final char DEFAULT_QUOTE_CHAR = '"';
    private char escapeChar;
    private static final char DEFAULT_ESCAPE_CHAR = '"';

    /*
     * Constructor method that provides the bare minimum as far as arguments go
     * @param writer - the writer that writes out the csv
     */
    public CSVWriter(Writer writer) {
        this(writer, DEFAULT_SEPARATOR);

    }

    /*
     * Constructor method with a separator character provided
     * @param writer - the writer that writes out the csv
     * @param separater - the separator character used for the csv
     */
    public CSVWriter(Writer writer, char separator) {
        this(writer, separator, DEFAULT_QUOTE_CHAR);

    }
    /*
     * Constructor method with a separator and quote character provided
     * @param writer - the writer that writes out the csv
     * @param separater - the separator character used for the csv
     */
    public CSVWriter(Writer writer, char separator, char quoteChar) {
        this(writer, separator, quoteChar, DEFAULT_ESCAPE_CHAR);
    }

    /*
     * Constructor method with a separator, quote and escape character provided
     * @param writer - the writer that writes out the csv
     * @param separater - the separator character used for the csv
     * @param escapeChar - the escape character used for the csv
     */
    public CSVWriter(Writer writer, char separator, char quoteChar, char escapeChar) {
        bw = new BufferedWriter(writer);
        this.separator = separator;
        this.quoteChar = quoteChar;
        this.escapeChar = escapeChar;
    }

    /*
     * Writes the entire ArrayList to the BufferedWritier.
     * @param allLines - the ArrayList<String[}> to write out to the file
     * @throws IOException - let user deal with exceptions...
     */
    public void writeAll(ArrayList<String[]> allLines) throws IOException {
        //Let's try that iterator thing you showed in class...
        for (Iterator<String[]> it = allLines.iterator(); it.hasNext();) {
            String[] nextLine = (String[]) it.next();
            writeNextLine(nextLine);
        }

    }

    /*
     * Writes the next line to the BufferedWriter
     * @param line - the String[] with each element being a value that needs
     *              to be written out
     * @throws IOException - let user deal with exceptions...
     */
    public void writeNextLine(String[] line) throws IOException {
        if (line == null)
            return;


        //basically the opposite of reading it...pretty straightforward
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < line.length; i++) {
            if (i != 0) {
                sb.append(separator);
            }

            String nextField = line[i];
            //I never liked putting "" for an empty field...so I won't do it
            if (nextField == null || nextField.equals(""))
                continue;

            sb.append(quoteChar);
            for (int j = 0; j < nextField.length(); j++) {
                char nextChar = nextField.charAt(j);
                if (nextChar == quoteChar) {
                    sb.append(escapeChar);
                    sb.append(nextChar);
                } else if (nextChar == escapeChar) {
                    sb.append(escapeChar);
                    sb.append(nextChar);
                } else {
                    sb.append(nextChar);
                }
            }
            sb.append(quoteChar);
        }
        sb.append("\n");
        bw.write(sb.toString());
        bw.flush();
    }

    /*
     * Close the BufferedWriter
     * @throws IOException - let user deal with exceptions...
     */
    public void close() throws IOException {
        bw.close();
    }
}

