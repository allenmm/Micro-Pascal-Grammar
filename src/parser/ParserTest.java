package parser;

import org.junit.Test;

import static org.junit.Assert.*;

import scanner.TokenType;
import syntaxtree.*;

import java.util.function.Function;

/**
 *
 * @author Marissa Allen
 */
public class ParserTest
{

    /**
     * JUnit test for the indentedToString of a pascal program.
     *
     * Based on the pascal program percentage below:
     *
     * program percentage;
     * var percentinput, giveninteger, output: integer;
     *
     * begin
     * percentinput := 10;
     * giveninteger := 50;
     * output := percentinput/100 * givennumber
     * end
     * .
     */
    @Test
    public void programNodeTest()
    {

        ProgramNode pn = new ProgramNode("percentageformula");
        DeclarationsNode dn = new DeclarationsNode();
        pn.setVariables(dn);
        VariableNode vn = new VariableNode("percentinput");
        VariableNode vn2 = new VariableNode("giveninteger");
        VariableNode vn3 = new VariableNode("output");
        dn.addVariable(vn);
        dn.addVariable(vn2);
        dn.addVariable(vn3);
        SubProgramDeclarationsNode sdn = new SubProgramDeclarationsNode();
        pn.setFunctions(sdn);
        CompoundStatementNode csn = new CompoundStatementNode();
        pn.setMain(csn);

        AssignmentStatementNode asn = new AssignmentStatementNode();
        asn.setLvalue(vn);
        ValueNode valueNode = new ValueNode("10");
        asn.setExpression(valueNode);
        csn.addStatement(asn);

        AssignmentStatementNode asn2 = new AssignmentStatementNode();
        asn2.setLvalue(vn2);
        ValueNode valueNode2 = new ValueNode("50");
        asn2.setExpression(valueNode2);
        csn.addStatement(asn2);

        AssignmentStatementNode asn3 = new AssignmentStatementNode();
        asn3.setLvalue(vn3);
        OperationNode opn = new OperationNode(TokenType.DIV);
        opn.setLeft(vn);
        ValueNode valueNode3 = new ValueNode("100");
        OperationNode opn2 = new OperationNode(TokenType.MULTI);
        opn.setRight(opn2);
        opn2.setLeft(valueNode3);
        opn2.setRight(vn2);
        asn3.setExpression(opn);
        csn.addStatement(asn3);

        String actual = pn.indentedToString(0);

        String expected =
                "Program: percentageformula\n" +
                        "|-- Declarations\n" +
                        "|-- --- Name: percentinput\n" +
                        "|-- --- Name: giveninteger\n" +
                        "|-- --- Name: output\n" +
                        "|-- SubProgramDeclarations\n" +
                        "|-- Compound Statement\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Name: percentinput\n" +
                        "|-- --- --- Value: 10\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Name: giveninteger\n" +
                        "|-- --- --- Value: 50\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Name: output\n" +
                        "|-- --- --- Operation: DIV\n" +
                        "|-- --- --- --- Name: percentinput\n" +
                        "|-- --- --- --- Operation: MULTI\n" +
                        "|-- --- --- --- --- Value: 100\n" +
                        "|-- --- --- --- --- Name: giveninteger\n";
        assertEquals(expected, actual);
    }
}
