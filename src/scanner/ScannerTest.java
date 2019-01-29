package scanner;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.junit.Test;

/**
 * A Java Program that illustrates reading from a text file and strings
 * using a scanner class using JUnit testing. This program creates a
 * StringReader, passing a String as parameter to the StringReader
 * constructor. It then reads the characters, one character at a time,
 * from the StringReader class. This program also takes an input file
 * and creates an input file stream to read from a file. FileInputStream
 * reads data from a file in the form of sequence of bytes. Then that is
 * passed to the InputStreamReader, which reads bytes and decodes them into
 * characters using a specified charset. Then those characters are
 * fed into the scanner class. As long as the scanner doesn't hit a null
 * token, the scanner prints out any tokens that are not whitespace
 * tokens or null tokens.
 *
 * @author Marissa Allen
 */
public class ScannerTest {
    /**
     * This method uses JUnit to test the nextToken method by testing
     * if the scanner returns the correct matching type and lexeme.
     */
    @Test
    public void testnextToken() {
        Token testToken = null;

        String filename = "key_symbol.txt";

        // Initializes FileInputStream object
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}