package codegen;

import org.junit.Test;
import static org.junit.Assert.*;
import parser.Parser;
import syntaxtree.*;
import parser.TypeEnum;

/**
 * A JUnit Java class that illustrates passing in a ProgramNode,
 * StatementNode, or ExpressionNode into the CodeGeneration methods to
 * generate assembly code and making sure that code matches the expected
 * assembly code string.
 *
 * @author Marissa
 */
public class CodeGenerationTest {

    /**
     * This method tests both the overall program and the declarations
     * in one test, because the declarations only occur in a program.
     * It tests the genCode method from the CodeGeneration class by
     * testing to see if the generated string of assembly code from the
     * call to the genCode method matches the expected assembly code
     * string. This is a text string test.
     */
    @Test
    public void testGenCode()
    {
        System.out.println("\n" + "#################################" + "\n" +
                "# Test Program and Declarations #" + "\n" +
                "#################################" + "\n");

        String test = "program foo;\n" +
                "var fee, fi, fo: integer;\n" +
                "begin\n" +
                "fee := 4;\n" +
                "fi := 5;\n" +
                "fo := 3 * fee + fi;\n" +
                "if fo < 13\n" +
                "then\n" +
                "fo := 13\n" +
                "else\n" +
                "fo := 26\n" +
                ";\n" +
                "write( fo)\n" +
                "end\n" +
                ".\n";
        Parser parser = new Parser(test, false);
        ProgramNode program = parser.program();
        //Symbol table isn't used yet in this iteration so it is null.
        CodeGeneration gen = new CodeGeneration(program, null);
        String expected = "# The .data section of the MIPS assembly\n" +
                ".data\n" +
                "newline:    .asciiz     \"\\n\" \n" +
                "fee :   .word   0\n" +
                "fi :   .word   0\n" +
                "fo :   .word   0\n" +
                "\n" +
                ".text\n" +
                "\n" +
                "main:\n" +
                "addi    $sp, $sp, -4   " +
                "# Decrements 4 off the stack pointer register.\n" +
                "sw    $ra 0($sp)       " +
                "# Saves register $ra for use as a return register.\n" +
                "li     $t0,  4         " +
                "# Loads a register with a specific numeric value. \n" +
                "sw     $t0, fee        " +
                " # Memory[label] = $reg\n" +
                "li     $t0,  5        " +
                " # Loads a register with a specific numeric value. \n" +
                "sw     $t0, fi         " +
                "# Memory[label] = $reg\n" +
                "li     $t2,  3        " +
                " # Loads a register with a specific numeric value. \n" +
                "lw     $t3, fee        " +
                " # Loads the variable labels.\n" +
                "mult   $t2,   $t3\n" +
                "mflo   $t1\n" +
                "lw     $t2, fi         # Loads the variable labels.\n" +
                "add    $t0,   $t1,   $t2\n" +
                "sw     $t0, fo         # Memory[label] = $reg\n" +
                "lw     $t1, fo         # Loads the variable labels.\n" +
                "li     $t2,  13        " +
                " # Loads a register with a specific numeric value. \n" +
                "blt    $t1,   $t2, endLoop3\n" +
                "\n" +
                "li     $t1,  26         " +
                "# Loads a register with a specific numeric value. \n" +
                "sw     $t1, fo         # Memory[label] = $reg\n" +
                "\n" +
                "j Next \n" +
                "\n" +
                "endLoop3:\n" +
                "li     $t1,  13        " +
                " # Loads a register with a specific numeric value. \n" +
                "sw     $t1, fo         # Memory[label] = $reg\n" +
                "\n" +
                "Next: \n" +
                "lw     $t0, fo         # Loads the variable labels.\n" +
                "li    $v0, 1\n" +
                "addi   $a0, $t0, 0     " +
                "# Adds the two registers together and \n" +
                "                       " +
                "# stores them in $a0 to be printed.\n" +
                "syscall               " +
                " # Prints the stored register value.\n" +
                "lw    $ra 0($sp)      " +
                " # Restores the original value $ra had in main. \n" +
                "addi    $sp, $sp, 4   " +
                " # Increments 4 onto the stack pointer register. \n" +
                "jr $ra                " +
                " # Jumps back to the line after jal main in \n" +
                "                       # the code. End.\n";
        String actual = gen.genCode(); //toString of with manual asm code
        assertEquals(expected, actual);
        System.out.println("Passed! Generated assembly code for the " +
                "program, including its declarations.");
    }

    /**
     * This method tests the writeCode method that takes in statements
     */
    @Test
    public void testWriteCodeStatement()
    {
        System.out.println("\n" + "#################################" +
                "\n" +
                "# Test statement assembly code #" + "\n" +
                "#################################" + "\n");

        String test = "if 3=3 then foo := 3 else return 0";
        Parser parser = new Parser(test, false);
        parser.getSymbolTable().addVarName("foo",
                TypeEnum.INTEGER_TYPE);
        parser.getSymbolTable().addVarName("fo",
                TypeEnum.INTEGER_TYPE);
        StatementNode stateNode = parser.statement();
        CodeGeneration gen =
                new CodeGeneration(null, null);
        String expected = "li     $t1,  3        " +
                " # Loads a register with a specific numeric value. \n" +
                "li     $t2,  3         " +
                "# Loads a register with a specific numeric value. \n" +
                "beq    $t1,   $t2, endLoop1\n" +
                "\n" +
                "li     $v0,  0         " +
                "# Loads a register with a specific numeric value. \n" +
                "\t\t# Returns the function statements\n" +
                "\n" +
                "j Next \n" +
                "\n" +
                "endLoop1:\n" +
                "li     $t1,  3         " +
                "# Loads a register with a specific numeric value. \n" +
                "sw     $t1, foo         " +
                "# Memory[label] = $reg\n" +
                "\n" +
                "Next: \n";
        String actual = gen.writeCode(stateNode);
        assertEquals(expected, actual);
        System.out.println("Passed! Generated assembly code for" +
                " the statement.");
    }

    /**
     * This method tests the writeCode method that takes in expressions
     */
    @Test
    public void testWriteCodeExpression()
    {
        //one test that writes code JUST for expressions

        System.out.println("\n" + "#################################" +
                "\n" +
                "# Test expression assembly code #" + "\n" +
                "#################################" + "\n");

        String test = "3+4+5";
        Parser parser = new Parser(test, false);
        ExpressionNode expNode = parser.expression();
        CodeGeneration gen =
                new CodeGeneration(null, null);
        String expected = "li     $t1,  3         " +
                "# Loads a register with a specific numeric value. \n" +
                "li     $t2,  4         " +
                "# Loads a register with a specific numeric value. \n" +
                "add    $t0,   $t1,   $t2\n" +
                "li     $t1,  5         " +
                "# Loads a register with a specific numeric value. \n" +
                "add    $t0,   $t0,   $t1\n";
        String actual = gen.writeCode(expNode, "$t0");
        assertEquals(expected, actual);
        System.out.println("Passed!");
    }
    
}
