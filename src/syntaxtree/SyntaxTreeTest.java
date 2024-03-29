package syntaxtree;

import org.junit.Test;
import scanner.TokenType;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit Java class that illustrates building a syntax tree using the
 * nodes from the classes in the syntaxtree package. The resulting
 * syntax tree is then printed out as an indentedToString.
 *
 * @author Marissa Allen
 */
public class SyntaxTreeTest
{

    /**
     * A JUnit syntax tree test for the program method of a
     * pascal program. This test manually builds a syntax tree by passing
     * a pascal program into the non-terminal methods in the Parser
     * class and the nodes from the classes in the syntaxtree package.
     * The output is an indentedToString() of the pascal program's
     * syntax tree.
     *
     * The indentedToString for the syntax tree is based on the pascal
     * program percentage below:
     *
     * program percentage;
     * var percentinput, giveninteger, output: integer;
     *
     * begin
     * percentinput := 10;
     * giveninteger := 50;
     * output := percentinput/100 * giveninteger
     * end
     * .
     */
    @Test
    public void testProgram()
    {
        ProgramNode pn = new ProgramNode("percentageformula");
        DeclarationsNode dn = new DeclarationsNode();
        pn.setVariables(dn);
        VariableNode vn = new VariableNode("percentinput");
        VariableNode vn2 = new VariableNode("giveninteger");
        VariableNode vn3 = new VariableNode("output");
        //Adding the name of the variable associated with the node.
        dn.addVariable(vn);
        dn.addVariable(vn2);
        dn.addVariable(vn3);
        SubProgramDeclarationsNode sdn = new SubProgramDeclarationsNode();
        pn.setFunctions(sdn);
        CompoundStatementNode csn = new CompoundStatementNode();
        pn.setMain(csn);

        AssignmentStatementNode asn = new AssignmentStatementNode();
        /* Assigns a variable node value to the left side of the
        assignment statement node. */
        asn.setLvalue(vn);
        ValueNode valueNode = new ValueNode("10");
        /* Assigns an expression node value to the right of the
        assignment statement node. */
        asn.setExpression(valueNode);
        csn.addStatement(asn);

        AssignmentStatementNode asn2 = new AssignmentStatementNode();
        /* Assigns a variable node value to the left side of the
        assignment statement node. */
        asn2.setLvalue(vn2);
        ValueNode valueNode2 = new ValueNode("50");
        /* Assigns an expression node value to the right of the
        assignment statement node. */
        asn2.setExpression(valueNode2);
        csn.addStatement(asn2);

        AssignmentStatementNode asn3 = new AssignmentStatementNode();
        /* Assigns a variable node value to the left side of the
        assignment statement node. */
        asn3.setLvalue(vn3);
        OperationNode opn = new OperationNode(TokenType.MULTI);
        /* Assigns an expression node value to the left side of the
        operation node. */
        OperationNode opn2 = new OperationNode(TokenType.FSLASH);
        opn.setLeft(opn2);
        ValueNode valueNode3 = new ValueNode("100");

        /* Assigns an expression node value to the right side of the
        operation node. */
        opn.setRight(vn2);
        opn2.setLeft(vn);
        opn2.setRight(valueNode3);
        /* Assigns an expression node value to the right of the
        assignment statement node. */
        asn3.setExpression(opn);
        csn.addStatement(asn3);

        String actual = pn.indentedToString(0);

        String expected =
                "Program: percentageformula\n" +
                        "|-- Declarations\n" +
                        "|-- --- Variable Name: percentinput\n" +
                        "|-- --- Variable Name: giveninteger\n" +
                        "|-- --- Variable Name: output\n" +
                        "|-- SubProgramDeclarations\n" +
                        "|-- Compound Statement\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Variable Name: percentinput\n" +
                        "|-- --- --- Value: 10\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Variable Name: giveninteger\n" +
                        "|-- --- --- Value: 50\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Variable Name: output\n" +
                        "|-- --- --- Operation: MULTI\n" +
                        "|-- --- --- --- Operation: FSLASH\n" +
                        "|-- --- --- --- --- Variable Name: " +
                        "percentinput\n" +
                        "|-- --- --- --- --- Value: 100\n" +
                        "|-- --- --- --- Variable Name: giveninteger\n";
        assertEquals(expected, actual);
        System.out.println(expected);
        System.out.println("It passed!");
    }
}
