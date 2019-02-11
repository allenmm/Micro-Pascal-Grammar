
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
 * class. If the string file path is present and the file name is true,
 * then the test will pass the file path
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
     * Test of exp method, of class Parser.
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
     * Test of exp_prime method, of class Parser.
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

//    /**
//     * Test of addop method, of class Parser.
//     */
//    @Test
//    public void testAddop() {
//        System.out.println("\n" + "######################" + "\n" +
//                    "#   Test addop  #" + "\n" +
//                    "######################" + "\n");
//        Parser instance = null;
//        instance.addop();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of term method, of class Parser.
     */
    @Test
    public void testTerm() {
        System.out.println("\n" + "######################" + "\n" +
                "#   Test term   #" + "\n" +
                "######################" + "\n");
        Parser instance = new Parser("23 / 17", false);
        instance.term();
        System.out.println("Parsed a term");
    }

//    /**
//     * Test of term_prime method, of class Parser.
//     */
//    @Test
//    public void testTerm_prime() {
//       System.out.println("\n" + "######################" + "\n" +
//       "#   Test term_prime   #" + "\n" +
//       "######################" + "\n");
//        Parser instance = null;
//        instance.term_prime();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of mulop method, of class Parser.
//     */
//    @Test
//    public void testMulop() {
//       System.out.println("\n" + "######################" + "\n" +
//       "#   Test mulop   #" + "\n" +
//       "######################" + "\n");
//        Parser instance = new Parser( "expressions/muloponly.pas");
//        //instance.mulop();
//        System.out.println("Saw the single mulop");
//    }
//

    /**
     * Test of mulop method, of class Parser.
     */
    @Test
    public void testMulop() {
       System.out.println("\n" + "######################" + "\n" +
       "#   Test mulop   #" + "\n" +
       "######################" + "\n");
        Parser instance = new Parser( "*", false);
        instance.mulop();
        System.out.println("Recognized the single mulop");
    }

    /**
     * This method uses JUnit to test the factor method from the Parser
     * class by testing to see if the current token matches the
     * expected token type.
     */
    @Test
    public void testFactor() {
        System.out.println("\n" + "######################" + "\n" +
                "#   Test factor   #" + "\n" +
                "######################" + "\n");
        Parser instance = new Parser( "87654321", false);
        instance.factor();
    }
//
//    /**
//     * This method uses JUnit to test the match method from the Parser
//     * class.
//     */
//    @Test
//    public void testMatch() {
//        System.out.println("\n" + "match");
//        TokenType ett = null;
//        Parser instance = null;
//        instance.match(ett);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * This method uses JUnit to test the error method from the Parser
//     * class.
//     */
//    @Test
//    public void testError() {
//        System.out.println("\n" + "error");
//        String message = "";
//        Parser instance = null;
//        instance.error(message);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
