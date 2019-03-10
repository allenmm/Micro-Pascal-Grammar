package parser;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A JUnit Java Program that illustrates adding a symbol/identifier to the
 * Symbol Table and checking to see if it is either a program, variable,
 * function, or procedure identifier.
 *
 * @author Marissa Allen
 */
public class SymbolTableTest
{
    /**
     * This method uses JUnit to test the isProcedureName method from the
     * SymbolTable class by testing to see if the identifier is a
     * procedure name and was properly added to the Symbol Table.
     */
    @Test
    public void testIsProcedureName()
    {
        System.out.println("\n" + "#########################" + "\n" +
                "#  Test IsProcedureName #" + "\n" +
                "#########################" + "\n");
        SymbolTable symbol = new SymbolTable();
        symbol.addProcedureName("foo");
        /* Positive test to see if the identifier was added to the
         Symbol Table */
        assertTrue(symbol.isProcedureName("foo"));
        System.out.println("Passed, it is a procedure name.");
        //Testing to make sure hash map added the correct identifier.
        assertFalse(symbol.isProcedureName("fooz"));
        System.out.println("Passed, variable name not recognized " +
                "because it wasn't added to the SymbolTable.");
        /* Negative test to see if a procedure name is accidentally
         added as a variable name identifier.*/
        assertFalse(symbol.isVarName("foo"));
        System.out.println("Passed, variable name not recognized " +
                "because it wasn't added to the SymbolTable.");
    }

    /**
     * This method uses JUnit to test the isVarName method from the
     * SymbolTable class by testing to see if the identifier is a
     * variable name and was properly added to the Symbol Table.
     */
    @Test
    public void testIsVarName()
    {
        System.out.println("\n" + "####################" + "\n" +
                "#  Test IsVarName  #" + "\n" +
                "####################" + "\n");
        SymbolTable symbol = new SymbolTable();
        symbol.addVarName("foo");
        /* Positive test to see if the identifier was added to the
         Symbol Table */
        assertTrue(symbol.isVarName("foo"));
        System.out.println("Passed, it is a variable name.");
        //Testing to make sure hash map added the correct identifier.
        assertFalse(symbol.isVarName("fooz"));
        System.out.println("Passed, procedure name not recognized " +
                "because it wasn't added to the SymbolTable.");
        /* Negative test to see if a variable name is accidentally
         added as a procedure name identifier.*/
        assertFalse(symbol.isProcedureName("foo"));
        System.out.println("Passed, procedure name not recognized " +
                "because it wasn't added to the SymbolTable.");
    }

    /**
     * This method uses JUnit to test the isFunctionName method from the
     * SymbolTable class by testing to see if the identifier is a
     * function name and was properly added to the Symbol Table.
     */
    @Test
    public void testIsFunctionName()
    {
        System.out.println("\n" + "#########################" + "\n" +
                "#  Test IsFunctionName  #" + "\n" +
                "#########################" + "\n");
        SymbolTable symbol = new SymbolTable();
        symbol.addFunctionName("foo");
        /* Positive test to see if the identifier was added to the
         Symbol Table */
        assertTrue(symbol.isFunctionName("foo"));
        System.out.println("Passed, it is a function name.");
        //Testing to make sure hash map added the correct identifier.
        assertFalse(symbol.isFunctionName("fooz"));
        System.out.println("Passed, function name not recognized because " +
                "it wasn't added to the SymbolTable.");
        /* Negative test to see if a function name is accidentally
         added as a procedure name identifier.*/
        assertFalse(symbol.isProcedureName("foo"));
        System.out.println("Passed, procedure name not recognized because" +
                " it wasn't added to the SymbolTable.");
    }
    

}
