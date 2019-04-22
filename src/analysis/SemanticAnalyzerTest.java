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

    /* This method uses JUnit to test the goodToGo method from the
     * SemanticAnalyzer class by using a negative test to make sure
     * that all variables are declared before they are used. */
    @Test
    public void testGoodToGo()
    {
        Parser parser = new Parser("fi", false);
        SymbolTable st = new SymbolTable();
        st.addVarName("fi", null);
        ExpressionNode test = parser.expression();
        SemanticAnalyzer analyze = new SemanticAnalyzer(null, st);
        analyze.assignExpressionType(test);
        assertFalse(analyze.goodToGo());
        System.out.println("Passed, variable name not declared " +
                "because it has no type.");
    }

}