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

        // Initializes InputStreamReader object
        InputStreamReader isr = new InputStreamReader(fis);

        /*Creates a Scanner object to read input from the
        InputStreamReader object. */
        Scanner scanner = new Scanner(isr);
        System.out.println("Token test 'program' from file: ");
        //Expected enum Token type.
        TokenType expected = TokenType.PROGRAM;

        //Testing the program Token to see if it passes.
        try {
            testToken = scanner.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("program passed.");
        } catch (Exception e) {

            System.out.println("Unexpected Token found: " + testToken);
        }

        System.out.println("Token test 'foo' from file: ");
        //Expected enum Token type.
        expected = TokenType.ID;

        //Testing the ID Token to see if it passes.
        try {
            testToken = scanner.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("foo passed.");
        } catch (Exception e) {

            System.out.println("Unexpected Token found: " + testToken);
        }

        System.out.println("Token test ';' from file: ");
        //Expected enum Token type.
        expected = TokenType.SEMI;

        //Testing the ; Token to see if it passes.
        try {
            testToken = scanner.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("; passed.");
        } catch (Exception e) {

            System.out.println("Unexpected Token found: " + testToken);
        }

        System.out.println("Token test 'begin' from file: ");
        //Expected enum Token type.
        expected = TokenType.BEGIN;

        //Testing the begin Token to see if it passes.
        try {
            testToken = scanner.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("begin passed.");
        } catch (Exception e) {

            System.out.println("Unexpected Token found: " + testToken);
        }

        System.out.println("Token test 'end' from file: ");
        //Expected enum Token type.
        expected = TokenType.END;

        //Testing the end Token to see if it passes.
        try {
            testToken = scanner.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("end passed.");
        } catch (Exception e) {

            System.out.println("Unexpected Token found: " + testToken);
        }

        System.out.println("Token test '.' from file: ");
        //Expected enum Token type.
        expected = TokenType.PERIOD;

        //Testing the period Token to see if it passes.
        try {
            testToken = scanner.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println(". passed.");
        } catch (Exception e) {

            System.out.println("Unexpected Token found: " + testToken);
        }

        System.out.println("Token test '%' from file: ");
        //Expected enum Token type.
        expected = null;

        //Testing the period Token to see if it passes.
        try {
            testToken = scanner.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("% passed, is a Token.");
        } catch (Exception e) {

            System.out.println("Unexpected Token found, TokenType: " + testToken);
        }

        String testInput = "program";

        //Creates a new string reader.
        Scanner testScan = new Scanner(new StringReader(testInput));

        System.out.println("Token test 'program' from string: ");
        //Expected enum Token type.
        expected = TokenType.PROGRAM;

        //Testing the program Token to see if it passes.
        try
        {
            testToken = testScan.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("program passed.");
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Token found: " + testInput);
        }

        testInput = "foo";

        testScan = new Scanner(new StringReader(testInput));

        System.out.println("Token test 'foo' from string: ");
        //Expected enum Token type.
        expected = TokenType.ID;

        //Testing the ID Token to see if it passes.
        try
        {
            testToken = testScan.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("foo passed.");
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Token found: " + testInput);
        }

        testInput = ";";

        testScan = new Scanner(new StringReader(testInput));

        System.out.println("Token test ';' from string: ");
        //Expected enum Token type.
        expected = TokenType.SEMI;

        //Testing the ; Token to see if it passes.
        try
        {
            testToken = testScan.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("; passed.");
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Token found: " + testInput);
        }

        testInput = "begin";
        testScan = new Scanner(new StringReader(testInput));

        System.out.println("Token test 'begin' from string: ");
        //Expected enum Token type.
        expected = TokenType.BEGIN;

        //Testing the begin Token to see if it passes.
        try
        {
            testToken = testScan.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("begin passed.");
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Token found: " + testInput);
        }

        testInput = "end";
        testScan = new Scanner(new StringReader(testInput));

        System.out.println("Token test 'end' from string: ");
        //Expected enum Token type.
        expected = TokenType.END;

        //Testing the end Token to see if it passes.
        try
        {
            testToken = testScan.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println("end passed.");
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Token found: " + testInput);
        }

        testInput = ".";
        testScan = new Scanner(new StringReader(testInput));

        System.out.println("Token test '.' from string: ");
        //Expected enum Token type.
        expected = TokenType.PERIOD;

        //Testing the period Token to see if it passes.
        try
        {
            testToken = testScan.nextToken();
            //The actual Token type.
            TokenType actual = testToken.getType();
            //Testing to see if the type actually matches the TokenType enum.
            assertEquals(expected, actual);
            System.out.println(". passed.");
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Token found: " + testInput);
        }

        

    }
}