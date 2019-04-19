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

    /**
     * This method uses JUnit to test the statement method from
     * the Parser class by testing to see if the indentedToString from the
     * call to the statement method matches the expected
     * indentedToString. This is a text string test.
     */
    @Test
    public void testStatement()
    {
        System.out.println("\n" + "########################" + "\n" +
                "#    Test statement    #" + "\n" +
                "########################" + "\n");

        String test = "foo := 3";
        Parser instance = new Parser(test, false);
        SymbolTable st = instance.getSymbolTable();
        st.addVarName("foo", null);
        StatementNode statementNode = instance.statement();
        String expected = "Assignment\n" +
                "|-- Variable Name: foo\n" +
                "|-- Value: 3\n";
        String actual = statementNode.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed a statement");
        System.out.println("The statement for foo := 3 is: ");
        System.out.println(actual);
    }

    /**
     * This method uses JUnit to test the subprogram_declaration method
     * from the Parser class by testing to see if the indentedToString
     * from the call to the subprogram_declaration method matches the
     * expected indentedToString. This is a text string test.
     */
    @Test
    public void testSubprogram_declaration()
    {
        System.out.println("\n" + "#################################" +
                "\n" + "#  Test subprogram_declaration  #" + "\n" +
                "#################################" + "\n");

        //Pascal string test.
        String test = "procedure fooz (identifier : integer) ;\n" +
                "var declarations2 : integer ;\n" +
                "begin read ( foo2 ) end";
        Parser instance = new Parser(test, false);
        SubProgramNode subProgramNode = instance.subprogram_declaration();
        String expected = "SubProgramDeclarations\n" +
                "|-- SubProgramDeclarations\n";
        String actual = subProgramNode.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed a subprogram declaration");
        System.out.println("The for the subprogram declaration is: ");
        System.out.println(actual);
    }

    /**
     * This method uses JUnit to test the declarations method
     * from the Parser class by testing to see if the indentedToString
     * from the call to the declarations method matches the
     * expected indentedToString. This is a text string test.
     */
    @Test
    public void testDeclarations()
    {
        System.out.println("\n" + "######################" + "\n" +
                "#  Test declarations #" + "\n" +
                "######################" + "\n");

        //Pascal string test. Happy path, with good pascal.
        String test = "var declarations : integer ;";
        Parser instance = new Parser(test, false);
        DeclarationsNode declarationsNode = instance.declarations();
        String expected = "Declarations\n" +
                "|-- Variable Name: declarations\n";
        String actual = declarationsNode.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed a declaration");
        System.out.println("The declaration for var declarations : " +
                "integer ; is: ");
        System.out.println(actual);
    }

    /**
     * This method uses JUnit to test the program method from
     * the Parser class by testing to see if the indentedToString from the
     * call to the program method matches the expected
     * indentedToString. This is a text string test.
     */
    @Test
    public void testProgram()
    {
        System.out.println("\n" + "######################" + "\n" +
                "#    Test program    #" + "\n" +
                "######################" + "\n");

        //Pascal string test.
        String test = "program foo ; begin end .";
        Parser instance = new Parser(test, false);
        ProgramNode programNode = instance.program();
        String expected = "Program: foo\n" +
                "|-- Declarations\n" +
                "|-- SubProgramDeclarations\n" +
                "|-- Compound Statement\n";
        String actual = programNode.indentedToString(0);
        assertEquals(expected, actual);
        System.out.println("Parsed a program");
        System.out.println("The program for program foo ; begin end . " +
                "is: ");
        System.out.println(actual);
    }

}
