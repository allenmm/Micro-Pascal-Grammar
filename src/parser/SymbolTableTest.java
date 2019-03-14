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
     * This method uses JUnit to test the toString method from the
     * SymbolTable class by testing to see if the file passed into
     * the program method returns a symbol table toString.
     */
    @Test
    public void testToString()
    {
        System.out.println("\n" + "#########################" + "\n" +
                "#  Test ToString #" + "\n" +
                "#########################" + "\n");
        Recognizer instance = new Recognizer
                ("src/parser/midterm.pas", true);
        /*Calls Recognizer Object method program. Constructor
        is automatically called when an object of the class is
        created. */
        try
        {
            instance.program();
            String actual = instance.getSymbolTable().toString();
            String expected = "\t\tSymbol Table\n" +
                    "\n" +
                    "Symbols\t\t\t\t Kinds\n" +
                    "-------------------------------------\n" +
                    "identifier           VAR_NAME            \n" +
                    "fooz                 PROCEDURE_NAME      \n" +
                    "foo                  PROGRAM_NAME        \n" +
                    "declarations2        VAR_NAME            \n" +
                    "declarations         VAR_NAME            \n";

            assertEquals(expected, actual);
            //If it's good pascal, it should print this out.
            System.out.println("Passed, parsed the happy path" + "\n");
        }
        catch (Exception e)
        {
            System.out.println("Failed, did not parse");
        }
    }

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
        System.out.println("Passed, function name not recognized " +
                "because it wasn't added to the SymbolTable.");
        /* Negative test to see if a function name is accidentally
         added as a procedure name identifier.*/
        assertFalse(symbol.isProcedureName("foo"));
        System.out.println("Passed, procedure name not recognized " +
                "because it wasn't added to the SymbolTable.");
    }

    /**
     * This method uses JUnit to test the isProgramName method from the
     * SymbolTable class by testing to see if the identifier is a
     * program name and was properly added to the Symbol Table.
     */
    @Test
    public void testIsProgramName()
    {
        System.out.println("\n" + "##########################" + "\n" +
                "#   Test IsProgramName   #" + "\n" +
                "##########################" + "\n");
        SymbolTable symbol = new SymbolTable();
        symbol.addProgramName("foo");
        /* Positive test to see if the identifier was added to the
         Symbol Table */
        assertTrue(symbol.isProgramName("foo"));
        System.out.println("Passed, it is a program name.");
        //Testing to make sure hash map added the correct identifier.
        assertFalse(symbol.isProgramName("fooz"));
        System.out.println("Passed, program name not recognized " +
                "because it wasn't added to the SymbolTable.");
        /* Negative test to see if a program name is accidentally
         added as a procedure name identifier.*/
        assertFalse(symbol.isProcedureName("foo"));
        System.out.println("Passed, procedure name not recognized " +
                "because it wasn't added to the SymbolTable.");
    }

    /**
     * This method uses JUnit to test the isArrayName method from the
     * SymbolTable class by testing to see if the identifier is an
     * array name and was properly added to the Symbol Table.
     */
    @Test
    public void testIsArrayName()
    {
        System.out.println("\n" + "######################" + "\n" +
                "#  Test IsArrayName  #" + "\n" +
                "######################" + "\n");
        SymbolTable symbol = new SymbolTable();
        symbol.addArrayName("foo");
        /* Positive test to see if the identifier was added to the
         Symbol Table */
        assertTrue(symbol.isArrayName("foo"));
        System.out.println("Passed, it is an array name.");
        //Testing to make sure hash map added the correct identifier.
        assertFalse(symbol.isArrayName("fooz"));
        System.out.println("Passed, array name not recognized " +
                "because it wasn't added to the SymbolTable.");
        /* Negative test to see if an array name is accidentally
         added as a variable name identifier.*/
        assertFalse(symbol.isVarName("foo"));
        System.out.println("Passed, variable name not recognized " +
                "because it wasn't added to the SymbolTable.");
    }

}
