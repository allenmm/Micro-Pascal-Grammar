package parser;

import org.junit.Test;

import static org.junit.Assert.*;

import syntaxtree.*;

/**
 *
 * @author Marissa Allen
 */
public class ParserTest
{

    /**
     * JUnit test for a pascal program. 
     */
    @Test
    public void programTest() {

        String test = "program sample;\n" +
                "var dollars, yen, bitcoins: integer;\n" +
                "\n" +
                "begin\n" +
                "  dollars := 1000000;\n" +
                "  yen := dollars * 110;\n" +
                "  bitcoins := dollars / 3900\n" +
                "end\n" +
                ".";
        Parser parser = new Parser( test, false);
        ProgramNode actual = parser.program();
        String actualString = actual.indentedToString( 0);
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
        assertEquals( expectedString, actualString);
    }
}
