
package parser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
public class ParserTest {

    public ParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * This method uses JUnit to test the exp method from the Parser
     * class by testing to see if the current token matches the
     * expected token type in the exp method. This is a text file path
     * test.
     */
    @Test
    public void testExp() {
        System.out.println("######################" + "\n" +
                "#      Test exp      #" + "\n" +
                "######################" + "\n");
        Parser instance = new Parser( "src/parser/simplest.pas", true);
        instance.exp();
        System.out.println("It Parsed!");
    }

    /**
     * This method uses JUnit to test the exp_prime method from the Parser
     * class by testing to see if the current token matches the
     * expected token type in the exp_prime method. This is a text string
     * test.
     */
    @Test
    public void testExp_prime() {
        System.out.println("\n" + "######################" + "\n" +
                "#   Test exp_prime   #" + "\n" +
                "######################" + "\n");
        Parser instance = new Parser( "+ 34", false);
        instance.exp_prime();
        System.out.println("It Parsed!");
    }

    /**
     * This method uses JUnit to test the addop method from the Parser
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
        Parser instance = new Parser( "+", false);
        instance.match(plus);
        System.out.println("Recognized the single addop.");
    }

    /**
     * This method uses JUnit to test the term method from the Parser
     * class by testing to see if the current token matches the
     * expected token type in the term method. This is a text string
     * test.
     */
    @Test
    public void testTerm() {
        System.out.println("\n" + "######################" + "\n" +
                "#     Test term      #" + "\n" +
                "######################" + "\n");
        Parser instance = new Parser("23 / 17", false);
        instance.term();
        System.out.println("Parsed a term.");
    }

    /**
     *This method uses JUnit to test the term_prime method from the Parser
     * class by testing to see if the current token matches the
     * expected token type in the term_prime method. This is a text
     * string test.
     */
    @Test
    public void testTerm_prime() {
        System.out.println("\n" + "######################" + "\n" +
                "#   Test term_prime   #" + "\n" +
                "######################" + "\n");
        Parser instance = new Parser( "* foo /", false);
        instance.term_prime();
        System.out.println("Parsed a term.");
    }


    /**
     * This method uses JUnit to test the mulop method from the Parser
     * class by testing to see if the current token matches the
     * expected token type in the mulop method. This is a text string
     * test.
     */
    @Test
    public void testMulop() {
        System.out.println("\n" + "######################" + "\n" +
                "#     Test mulop     #" + "\n" +
                "######################" + "\n");
        Parser instance = new Parser( "*", false);
        instance.mulop();
        System.out.println("Recognized the single mulop.");
    }

    /**
     *This method uses JUnit to test the factor method from the Parser
     * class by testing to see if the current token matches the
     * expected token type in the factor method. This is a text string
     * test.
     */
    @Test
    public void testFactor() {
        System.out.println("\n" + "######################" + "\n" +
                "#   Test factor   #" + "\n" +
                "######################" + "\n");
        Parser instance = new Parser( "87654321", false);
        instance.factor();
        System.out.println("Recognized the factor token.");
    }

    /**
     *This method uses JUnit to test the match method from the Parser
     * class by testing to see if the current token matches the
     * expected token type in the match method. This is a text string
     * test that tests the period token type as the expected token to
     * match against the string token passed into the parser.
     */
    @Test
    public void testMatch()
    {
        System.out.println("\n" + "######################" + "\n" +
                "#     Test match     #" + "\n" +
                "######################" + "\n");
        TokenType ett = TokenType.PERIOD;
        Parser instance = new Parser( ".", false);
        instance.match(ett);
        System.out.println("It matches!");
    }

    /**
     * This method uses JUnit to test the error method from the Parser
     * class.
     */
    @Test
    public void testError() {
        System.out.println("\n" + "######################" + "\n" +
                "#     Test error     #" + "\n" +
                "######################" + "\n");
        String message = ", error test";
        Parser instance = new Parser( "", false);
        instance.error(message);
        System.out.println("Successfully tested the error.");
    }
}
