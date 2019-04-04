package parser;

import org.junit.Test;

import static org.junit.Assert.*;

import syntaxtree.*;

/**
 * A JUnit Java Program that illustrates building a part of the syntax
 * tree using the non-terminal symbols in the micro pascal grammar in the
 * Parser class and nodes from the classes in the syntaxtree package.
 * The resulting syntax tree is then printed out as an indentedToString.
 *
 * @author Marissa Allen
 */
public class ParserTest
{

    /**
     * This method uses JUnit to test the factor method from the
     * Parser class by testing to see if the indentedToString from the
     * call to the factor method matches the expected indentedToString.
     * This is a text string test.
     */
    @Test
    public void testFactor()
    {
        System.out.println("\n" + "######################" + "\n" +
                "#     Test factor    #" + "\n" +
                "######################" + "\n");
        String test = "87654321";
        Parser instance = new Parser
                (test, false);
        ExpressionNode expressionNode = instance.factor();
        String expected = "Value: 87654321\n";
        String actual = expressionNode.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed a factor");
        System.out.println("The factor for 87654321 is: ");
        System.out.println(actual);

    }

    /**
     * This method uses JUnit to test the simple_expression method from
     * the Parser class by testing to see if the indentedToString from the
     * call to the simple_expression method matches the expected
     * indentedToString. This is a text string test.
     */
    @Test
    public void testSimple_expression()
    {
        System.out.println("\n" + "##############################" +
                "\n" + "#   Test simple_expression   #" + "\n" +
                "##############################" + "\n");
        String test = "34 + 17 * 7";
        Parser instance = new Parser
                (test, false);
        ExpressionNode expressionNode = instance.simple_expression();
        String expected = "Operation: PLUS\n" +
                "|-- Value: 34\n" +
                "|-- Operation: MULTI\n" +
                "|-- --- Value: 17\n" +
                "|-- --- Value: 7\n";
        String actual = expressionNode.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed a simple_expression");
        System.out.println("The simple_expression for 34 + 17 * 7 is: ");
        System.out.println(actual);
    }


}
