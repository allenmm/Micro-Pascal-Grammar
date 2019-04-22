package analysis;

import org.junit.Test;
import static org.junit.Assert.*;
import parser.Parser;
import parser.SymbolTable;
import parser.TypeEnum;
import scanner.TokenType;
import syntaxtree.*;

/**
 *
 * @author Marissa
 */
public class SemanticAnalyzerTest
{
    /* This method uses JUnit to test the assignStatementTypes method
     * from the SemanticAnalyzer class by testing to see if a datatype
     * is assigned to each ExpressionNode. The type of each ExpressionNode
     * should be printed in the tree's indentedToString.
     */
    @Test
    public void testAssignExpressionType()
    {
        Parser parser = new Parser("20 + 5", false);
        ExpressionNode test = parser.expression();
        SemanticAnalyzer analyze = new SemanticAnalyzer(null, null);
        analyze.assignExpressionType(test);
        String expected = "Operation: PLUS INTEGER_TYPE\n" +
                "|-- Value: 20 INTEGER_TYPE\n" +
                "|-- Value: 5 INTEGER_TYPE\n";
        String actual = test.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed an expression");
        System.out.println("The expression for 20 + 5: ");
        System.out.println(actual);

        parser = new Parser("fee + 5", false);
        test = parser.expression();
        SymbolTable st = new SymbolTable();
        st.addVarName("fee", TypeEnum.REAL_TYPE);
        analyze = new SemanticAnalyzer(null, st);
        analyze.assignExpressionType(test);
        expected = "Operation: PLUS REAL_TYPE\n" +
                "|-- Variable Name: fee REAL_TYPE\n" +
                "|-- Value: 5 INTEGER_TYPE\n";
        actual = test.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed an expression");
        System.out.println("The expression for fee + 5: ");
        System.out.println(actual);

        parser = new Parser("foo + 5", false);
        test = parser.expression();
        st = new SymbolTable();
        st.addVarName("foo", TypeEnum.INTEGER_TYPE);
        analyze = new SemanticAnalyzer(null, st);
        analyze.assignExpressionType(test);
        expected = "Operation: PLUS INTEGER_TYPE\n" +
                "|-- Variable Name: foo INTEGER_TYPE\n" +
                "|-- Value: 5 INTEGER_TYPE\n";
        actual = test.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed an expression");
        System.out.println("The expression for 20 + 5: ");
        System.out.println(actual);
    }

    /* This method uses JUnit to test the goodToGo method from the
     * SemanticAnalyzer class by using a negative test to make sure
     * that all variables are declared before they are used.
     */
    @Test
    public void testGoodToGo()
    {
        System.out.println("\n" + "#########################" + "\n" +
                "#     Test goodToGo     #" + "\n" +
                "#########################" + "\n");
        Parser parser = new Parser("fi", false);
        SymbolTable st = new SymbolTable();
        st.addVarName("fi", null);
        ExpressionNode test = parser.expression();
        SemanticAnalyzer analyze = new SemanticAnalyzer(null, st);
        analyze.assignExpressionType(test);
        assertFalse(analyze.goodToGo());
        System.out.println("Passed, variable name not declared " +
                "because it has no type.");

        parser = new Parser("fi - 5", false);
        test = parser.expression();
        st = new SymbolTable();
        st.addVarName("fi", null);
        analyze = new SemanticAnalyzer(null, st);
        analyze.assignExpressionType(test);
        assertFalse(analyze.goodToGo());
        System.out.println("Passed, variable name not declared " +
                "because it has no type.");

    }

}