package analysis;

import org.junit.Test;

import static org.junit.Assert.*;

import parser.Parser;
import parser.SymbolTable;
import parser.TypeEnum;
import scanner.TokenType;
import syntaxtree.*;

/**
 * A JUnit Java Program that illustrates checking to see if all variables
 * are declared before they are used, testing to see if a type is
 * assigned to every StatementNode, and adding a type onto the
 * ExpressionNodes of the syntax tree.
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
        System.out.println("\n" + "###############################" + "\n" +
                "# Test assign expression type #" + "\n" +
                "###############################" + "\n");

        //Negative null type test.
        Parser parser = new Parser("fooz + 5", false);
        ExpressionNode test = parser.expression();
        SymbolTable st = new SymbolTable();
        st.addVarName("fooz", null);
        SemanticAnalyzer analyze = new SemanticAnalyzer(null, st);
        analyze.assignExpressionType(test);
        String expected = "Operation: PLUS INTEGER_TYPE\n" +
                "|-- Variable Name: fooz null\n" +
                "|-- Value: 5 INTEGER_TYPE\n";
        String actual = test.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed an expression");
        System.out.println("The expression for 20 + 5: ");
        System.out.println(actual);

        //Positive, numbers only test.
        parser = new Parser("20 + 5", false);
        test = parser.expression();
        analyze =
                new SemanticAnalyzer(null, null);
        analyze.assignExpressionType(test);
        expected = "Operation: PLUS INTEGER_TYPE\n" +
                "|-- Value: 20 INTEGER_TYPE\n" +
                "|-- Value: 5 INTEGER_TYPE\n";
        actual = test.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed an expression");
        System.out.println("The expression for 20 + 5: ");
        System.out.println(actual);

        //Positive test, variable declared as real.
        parser = new Parser("fee + 5", false);
        test = parser.expression();
        st = new SymbolTable();
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

        //Positive test, variable declared as integer.
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
        System.out.println("\n" + "###########################" + "\n" +
                "#     Test good to go     #" + "\n" +
                "###########################" + "\n");
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

    /* This method uses JUnit to test the assignStatementTypes method
     * from the SemanticAnalyzer class by testing to see if a type is
     * assigned to every StatementNode by finding all of the
     * ExpressionNodes within a StatementNode.
     */
    @Test
    public void testAssignStatementTypes()
    {
        System.out.println("\n" + "##############################" + "\n" +
                "# Test assign statement type #" + "\n" +
                "##############################" + "\n");
        //Negative test.
        AssignmentStatementNode asn = new AssignmentStatementNode();
        VariableNode vn = new VariableNode("foo");
        ValueNode value = new ValueNode("4");
        asn.setLvalue(vn);
        asn.setExpression(value);
        SymbolTable st = new SymbolTable();
        st.addVarName("foo", TypeEnum.REAL_TYPE);
        SemanticAnalyzer analyze = new SemanticAnalyzer(null, st);
        analyze.assignStatementTypes(asn);
        System.out.println("Passed! Message printed that types don't " +
                "match across assignment.\n");

        //Positive test.
        asn = new AssignmentStatementNode();
        vn = new VariableNode("fee");
        value = new ValueNode("7");
        asn.setLvalue(vn);
        asn.setExpression(value);
        st = new SymbolTable();
        st.addVarName("fee", TypeEnum.INTEGER_TYPE);
        analyze = new SemanticAnalyzer(null, st);
        analyze.assignStatementTypes(asn);
        System.out.println("Success! Types match across assignment.");
    }
}