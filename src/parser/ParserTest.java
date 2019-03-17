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
     */
    @Test
    public void programTest() {

        ProgramNode pn = new ProgramNode("sample");
        DeclarationsNode dn = new DeclarationsNode();
        pn.setVariables(dn);
        VariableNode vn = new VariableNode("dollars");
        VariableNode vn2 = new VariableNode("yen");
        VariableNode vn3 = new VariableNode("bitcoins");
        dn.addVariable(vn);
        dn.addVariable(vn2);
        dn.addVariable(vn3);
        SubProgramDeclarationsNode sdn = new SubProgramDeclarationsNode();
        pn.setFunctions(sdn);
        CompoundStatementNode csn = new CompoundStatementNode();
        pn.setMain(csn);
        AssignmentStatementNode asn = new AssignmentStatementNode();
        asn.setLvalue(vn);
        ValueNode valueNode = new ValueNode("1000000");
        asn.setExpression(valueNode);
        csn.addStatement(asn);

        AssignmentStatementNode asn2 = new AssignmentStatementNode();
        asn2.setLvalue(vn2);
        OperationNode opn = new OperationNode(TokenType.MULTI);
        opn.setLeft(vn);
        ValueNode valueNode2 = new ValueNode("102");
        opn.setRight(valueNode2);
        asn2.setExpression(opn);
        csn.addStatement(asn2);

        AssignmentStatementNode asn3 = new AssignmentStatementNode();
        asn3.setLvalue(vn3);
        OperationNode opn2 = new OperationNode(TokenType.DIV);
        opn.setLeft(vn);
        ValueNode valueNode3 = new ValueNode("400");
        opn.setRight(valueNode3);
        asn3.setExpression(opn);
        csn.addStatement(asn3);

        String actual = pn.indentedToString(0);

//        String test = "program sample;\n" +
//                "var dollars, yen, bitcoins: integer;\n" +
//                "\n" +
//                "begin\n" +
//                "  dollars := 1000000;\n" +
//                "  yen := dollars * 110;\n" +
//                "  bitcoins := dollars / 3900\n" +
//                "end\n" +
//                ".";
//        Parser parser = new Parser( test, false);
//        ProgramNode actual = parser.program();
//        String actualString = actual.indentedToString( 0);
        String expectedString =
                "Program: sample\n" +
                        "|-- Declarations\n" +
                        "|-- --- Name: dollars\n" +
                        "|-- --- Name: yen\n" +
                        "|-- --- Name: bitcoins\n" +
                        "|-- SubProgramDeclarations\n" +
                        "|-- Compound Statement\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Name: dollars\n" +
                        "|-- --- --- Value: 1000000\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Name: yen\n" +
                        "|-- --- --- Operation: MULTIPLY\n" +
                        "|-- --- --- --- Name: dollars\n" +
                        "|-- --- --- --- Value: 110\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Name: bitcoins\n" +
                        "|-- --- --- Operation: DIVIDE\n" +
                        "|-- --- --- --- Name: dollars\n" +
                        "|-- --- --- --- Value: 3900\n";
        assertEquals( expectedString, actual);
    }
}
