
package parser;

import org.junit.Test;
import static org.junit.Assert.*;
import scanner.TokenType;

/**
 * A Java Program that illustrates reading from a text file or strings
 * using a parser class using JUnit testing. Either a string or a file
 * path name will be passed into the parser constructor from the parser
 * class and grab the . If the string file path is present and the file name is true,
 * then the JUnit test will pass the file path,  then the
 *
 * @author Marissa Allen
 */
public class RecognizerTest
{

    /**
     * This method uses JUnit to test the program method from the
     * Recognizer class by testing to see if the current token matches
     * the expected token type in the program method. This is a
     * text string test and the file string test.
     */
    @Test
    public void testProgram()
    {
        System.out.println("\n" + "######################" + "\n" +
                "#    Test program      #" + "\n" +
                "######################" + "\n");

        //Pascal file test
        Recognizer instance = new Recognizer
                ( "src/parser/simplest.pas", true);
        try
        {
            instance.program();
            //If it's good pascal, it should print this out.
            System.out.println("Passed, parsed the happy path.");
        }
        catch (Exception actual)
        {
            fail("Didn't want to throw exception");
        }



        //Happy path, with good pascal.
        String test = "program foo ; begin end .";
        instance = new Recognizer( test, false);
        try
        {
            instance.program();
            //If it's good pascal, it should print this out.
            System.out.println("Passed, parsed the happy path.");
        }
        catch (Exception actual)
        {
            fail("Didn't want to throw exception");
        }
        test = "program foo begin end .";
        instance = new Recognizer( test, false);
        try
        {
            instance.program();
            //If it's bad pascal, it should throw an exception.
            fail("Didn't throw exception");
        }
        catch (Exception actual)
        {
            String expected = "Match of SEMI found BEGIN instead.";
            assertEquals(expected, actual.getMessage());
        }

    }

    /**
     * This method uses JUnit to test the exp method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the exp method. This is a text file path
     * test.
     */
    @Test
    public void testSimple_expression()
    {
        System.out.println("######################" + "\n" +
                "#      Test exp      #" + "\n" +
                "######################" + "\n");
        Recognizer instance = new Recognizer
                ( "src/parses", true);
        //Calls Recognizer Object method exp. Constructor is automatically
        //called when an object of the class is created.
        instance.simple_expression();
        System.out.println("It Parsed!");
    }

    /**
     * This method uses JUnit to test the simple_part method from the
     * Recognizer class by testing to see if the current token matches
     * the expected token type in the simple_part method.
     * This is a text string test.
     */
    @Test
    public void testSimple_part() {
        System.out.println("\n" + "######################" + "\n" +
                "#   Test simple part   #" + "\n" +
                "######################" + "\n");
        Recognizer instance = new Recognizer( "+ 34", false);
        //Calls Recognizer Object method simple_part. Constructor is automatically
        //called when an object of the class is created.
        instance.simple_part();
        System.out.println("It Parsed!");
    }

    /**
     * This method uses JUnit to test the addop method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the addop method. This is a text string
     * test.
     */
    @Test
    public void testAddop() {
        System.out.println("\n" + "######################" + "\n" +
                    "#   Test addop  #" + "\n" +
                    "######################" + "\n");
        TokenType plus = TokenType.PLUS;
        Recognizer instance = new Recognizer( "+", false);
        //Calls Recognizer Object method match. Constructor is
        // automatically called when an object of the class is created.
        instance.match(plus);
        System.out.println("Recognized the single addop.");
    }

    /**
     * This method uses JUnit to test the term method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the term method. This is a text string
     * test.
     */
    @Test
    public void testTerm() {
        System.out.println("\n" + "######################" + "\n" +
                "#     Test term      #" + "\n" +
                "######################" + "\n");
        Recognizer instance = new Recognizer("23 / 17", false);
        //Calls Recognizer Object method term. Constructor is automatically
        //called when an object of the class is created.
        instance.term();
        System.out.println("Parsed a term.");
    }

    /**
     *This method uses JUnit to test the term_part method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the term_part method. This is a text
     * string test.
     */
    @Test
    public void testTerm_part()
    {
        System.out.println("\n" + "######################" + "\n" +
                "#   Test term_part   #" + "\n" +
                "######################" + "\n");
        Recognizer instance = new Recognizer( "* foo / foo2", false);
        //Calls Recognizer Object method term_part. Constructor is automatically
        //called when an object of the class is created.
        instance.term_part();
        System.out.println("Parsed a term.");
    }

    /**
     * This method uses JUnit to test the mulop method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the mulop method. This is a text string
     * test.
     */
    @Test
    public void testMulop() {
        System.out.println("\n" + "######################" + "\n" +
                "#     Test mulop     #" + "\n" +
                "######################" + "\n");
        Recognizer instance = new Recognizer( "*", false);
        //Calls Recognizer Object method mulop. Constructor is automatically
        //called when an object of the class is created.
        instance.mulop();
        System.out.println("Recognized the single mulop.");
    }

    /**
     *This method uses JUnit to test the factor method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the factor method. This is a text string
     * test.
     */
    @Test
    public void testFactor() {
        System.out.println("\n" + "######################" + "\n" +
                "#   Test factor   #" + "\n" +
                "######################" + "\n");
        Recognizer instance = new Recognizer( "87654321", false);
        //Calls Recognizer Object method factor. Constructor is automatically
        //called when an object of the class is created.
        instance.factor();
        System.out.println("Recognized the factor token.");
    }

    /**
     *This method uses JUnit to test the match method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the match method. This is a text string
     * test that tests the period token type as the expected token to
     * match against the string token passed into the parser. This is a
     * text string test.
     */
    @Test
    public void testMatch()
    {
        System.out.println("\n" + "######################" + "\n" +
                "#     Test match     #" + "\n" +
                "######################" + "\n");
        TokenType ett = TokenType.PERIOD;
        Recognizer instance = new Recognizer( ".", false);
        //Calls Recognizer Object method match. Constructor is
        //automatically called when an object of the class is created.
        instance.match(ett);
        System.out.println("It matches!");
    }

    /**
     * This method uses JUnit to test the error method from the Recognizer
     * class. This is a text string test.
     */
    @Test
    public void testError() {
        System.out.println("\n" + "######################" + "\n" +
                "#     Test error     #" + "\n" +
                "######################" + "\n");
        String message = "error test";
        Recognizer instance = new Recognizer( "", false);
        //Calls Recognizer Object method error and passes the message
        //directly into it. Constructor is automatically called when an
        //object of the class is created.
        instance.error(message);
        System.out.println("Successfully tested the error.");
    }

}