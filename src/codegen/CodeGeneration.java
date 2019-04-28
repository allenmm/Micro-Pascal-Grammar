package codegen;

import parser.SymbolTable;
import scanner.TokenType;
import syntaxtree.*;

import java.util.ArrayList;

/**
 * This class takes in a syntax tree and a symbol table as its input and
 * returns a string of MIPS assembly language code as its output.
 * It does this by outlining a blueprint of the assembly code for the
 * overall program and generating code to fill in the missing parts of
 * the blueprint using nodes from the syntax tree.
 *
 * @author Marissa Allen
 */
public class CodeGeneration
{
    private int currentTRegister = -1;
    private int whileCounter = 0;
    private ProgramNode program;
    private SymbolTable symbols;
    private String nodeCode ="";
    private int ifCounter = 0;

    /**
     * CodeGeneration constructor that takes and stores values from a
     * ProgramNode and a SymbolTable to use throughout the program.
     *
     * @param program - A ProgramNode generated by a pascal program.
     * @param symbols - A SymbolTable generated by a pascal program.
     */
    public CodeGeneration(ProgramNode program, SymbolTable symbols)
    {
        this.program = program;
        this.symbols = symbols;
    }

    /**
     * A method that outlines the blueprint of the assembly code.
     * A part of the code has already been outlined here, all that's
     * missing are the declarations and statements. This method uses the
     * ProgramNode generated by a pascal program to pass VariableNodes
     * and CompoundStatementNodes to other methods and generate
     * assembly code for expressions and statements to fill in the
     * missing pieces of the blueprint.
     *
     * @return - A String of the assembly code.
     */
    public String genCode()
    {
        //Program overall section. Writes .data section and a .ascizz.
        String answer = "# The .data section of the MIPS assembly\n";
        answer += ".data\n";
        answer += "newline:    .asciiz     \"\\" + "n" + "\" \n";

        //Declarations section
        DeclarationsNode dn = program.getVariables();
        ArrayList<VariableNode> vn = dn.getVar();
        for (VariableNode varNodes : vn)
        {
            /*Mips assigns variables as varname: .word 0
            * and*/
            answer += varNodes.getName() + " :   .word   0\n";
        }

        //Program overall section. Writes beginning of main.
        answer += "\n.text\n";
        answer += "\nmain:\n";
        answer += "addi    $sp, $sp, -4   # Decrements 4 off the " +
                "stack pointer register.\n";
        answer += "sw    $ra 0($sp)       # Saves register $ra for use " +
                "as a return register.\n";

        //Statement section
        answer += writeCode(program.getMain());

        //Program overall section. Writes end of main.
        answer += "lw    $ra 0($sp)       # Restores the original " +
                "value $ra had in main. \n";
        answer += "addi    $sp, $sp, 4    # Increments 4 onto the " +
                "stack pointer register. \n";
        answer += "jr $ra                 # Jumps back to the line after "
                +"jal main in \n                       # the code. " +
                "End.\n";

        return answer;
    }

    /**
     * Writes code for the given node. This generic write code takes any
     * ExpressionNode, and then recasts according to subclass type for
     * dispatching where the method checks to see if an ExpressionNode
     * is an instance of either an OperationNode, ValueNode, or
     * VariableNode. Passes the instance of an ExpressionNode to other
     * expression methods to generate assembly code to fill in the
     * missing pieces of the blueprint and complete the program. The
     * generated expression portion of the assembly code for all of the
     * expressions is then returned and added to the blueprint to help
     * write the full pascal program.
     *
     * @param node - The node for which to write code.
     * @param reg - The register in which to put the result.
     * @return - A String of the assembly code for all of the expressions.
     */
    public String writeCode(ExpressionNode node, String reg)
    {
        String nodeCode = null;

        if (node instanceof OperationNode)
        {
            //Pass in node and register to generate assembly code.
            nodeCode = writeCode((OperationNode) node, reg);
        }
        else if (node instanceof ValueNode)
        {
            //Pass in node and register to generate assembly code.
            nodeCode = writeCode((ValueNode) node, reg);
        }
        else if (node instanceof VariableNode)
        {
            //Pass in node and register to generate assembly code.
            nodeCode = writeCode((VariableNode) node, reg);
        }
        return nodeCode;
    }

    /**
     * Writes code for an operations node.
     * The code is written by gathering the child nodes' answers into
     * a pair of registers, and then executing the op on those registers,
     * placing the result in the given result register.
     *
     * @param opNode - The operation node to perform.
     * @param resultRegister - The register in which to put the result.
     * @return - A String of the assembly code that executes
     * this operation.
     */
    public String writeCode(OperationNode opNode, String resultRegister)
    {
        int whileNumber = ++this.whileCounter;
        String code;
        ExpressionNode left = opNode.getLeft();
        String leftRegister = "$t" + ++currentTRegister;
        code = writeCode(left, leftRegister);
        ExpressionNode right = opNode.getRight();
        String rightRegister = "$t" + ++currentTRegister;
        code += writeCode(right, rightRegister);
        TokenType kindOfOp = opNode.getOperation();
        if (kindOfOp == TokenType.PLUS)
        {
            // add resultregister, left, right
            code += "add    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if (kindOfOp == TokenType.MINUS)
        {
            // add resultregister, left, right
            code += "sub    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if (kindOfOp == TokenType.OR)
        {
            // add resultregister, left, right
            code += "or    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if (kindOfOp == TokenType.MULTI)
        {
            code += "mult   " + leftRegister + ",   " + rightRegister + "\n";
            code += "mflo   " + resultRegister + "\n";
        }
        if (kindOfOp == TokenType.FSLASH)
        {
            //floating point division

        }
        /*
        psuedocode for divide: div $d, $s, $t

        expansion:
        div $s, $t
        mflo $d
        */
        if (kindOfOp == TokenType.DIV)
        {
            code += "div    " + leftRegister + ",   " + rightRegister + "\n";
            code += "mflo   " + resultRegister + "\n";
        }
        if (kindOfOp == TokenType.MOD)
        {
            code += "div    " + leftRegister + ",   " + rightRegister + "\n";
            code += "mfhi   " + resultRegister + "\n";
        }
        if (kindOfOp == TokenType.AND)
        {
            // add resultregister, left, right
            code += "and    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if (kindOfOp == TokenType.EQUIV)
        {
            code += "beq    " + leftRegister + ",   " + rightRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        if (kindOfOp == TokenType.NOTEQUAL)
        {
            code += "bne    " + leftRegister + ",   " + rightRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        if (kindOfOp == TokenType.LTHAN)
        {
            code += "blt    "  + leftRegister + ",   " + rightRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        if (kindOfOp == TokenType.LTHANEQUAL)
        {
            code += "ble    " + leftRegister + ",   " + rightRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        //$s = left reg, $t = right reg. pseudocode: bge $s, $t, C
        if (kindOfOp == TokenType.GTHANEQUAL)
        {
            code += "bge    " + leftRegister + ",   " + rightRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        if (kindOfOp == TokenType.GTHAN)
        {
            code += "bgt    " + leftRegister + ",   " + rightRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        this.currentTRegister -= 2;
        return code;
    }

    /**
     * Generates the assembly code for a value node. The code is
     * written by executing an add immediate with the value into the
     * destination register.
     * Writes code that looks like  addi $reg, $zero, value
     *
     * @param valNode - The node containing the numeric value to load
     * into the register.
     * @param resultRegister - The register the numeric value is placed
     * in.
     * @return - A String of the assembly code that executes the value
     * node for the expression.
     */
    public String writeCode(ValueNode valNode, String resultRegister)
    {
        String value = valNode.getAttribute();
        String code = "li     " + resultRegister + ",  " + value + "     "
                +
                "    # Loads a register with a specific numeric value. " +
                "\n";
        return code;
    }

    /**
     * Generates the assembly code for a variable node.
     * The code is written by executing a load word with the variable
     * into the destination register.
     * Writes code that looks like: lw $reg, variable
     *
     * @param varNode - The node containing the variable to load
     * into the register.
     * @param resultRegister - The register the variable is placed in.
     * @return - A String of the assembly code that executes the variable
     * node for the expression.
     */
    public String writeCode(VariableNode varNode, String resultRegister)
    {
        String code = "lw     " + resultRegister + ", " +
                varNode.getName() +
                "         # Loads the variable labels." + "\n";
        return code;
    }

    /**
     * Writes code for the given node. This generic write code takes any
     * StatementNode and checks to see if it is an instance of either a
     * CompoundStatementNode, AssignmentStatementNode, IfStatementNode,
     * WriteStatementNode, ReadStatementNode, or ReturnStatementNode.
     * Passes the instance of a StatementNode to other statement methods
     * to generate assembly code to fill in the missing pieces of the
     * blueprint and complete the program. The generated statement
     * portion of the assembly code for all of the statements is then
     * returned and added to the blueprint to help write the full
     * pascal program.
     *
     * @param node - The node containing the statement to check the
     * instance of.
     * @return - A String of the assembly code for all of the statements.
     */
    public String writeCode(StatementNode node)
    {
        if (node instanceof CompoundStatementNode)
        {
            for (StatementNode sNode :
                    ((CompoundStatementNode) node).getStatements())
            {
                nodeCode = writeCode(sNode);
            }
        }
        else if (node instanceof AssignmentStatementNode)
        {
            //Pass in node to generate assembly code.
            nodeCode += writeCode((AssignmentStatementNode) node);
        }
        else if (node instanceof IfStatementNode)
        {
            //Pass in node to generate assembly code.
            nodeCode += writeCode((IfStatementNode) node);
        }
        else if (node instanceof WriteStatementNode)
        {
            //Pass in node to generate assembly code.
            nodeCode += writeCode((WriteStatementNode) node);
        }
        else if (node instanceof WhileStatementNode)
        {
            //Pass in node to generate assembly code.
            nodeCode += writeCode((WhileStatementNode) node);
        }
        else if (node instanceof ReadStatementNode)
        {
            //Pass in node to generate assembly code.
            nodeCode += writeCode((ReadStatementNode) node);
        }
        else if (node instanceof ReturnStatementNode)
        {
            //Pass in node to generate assembly code.
            nodeCode += writeCode((ReturnStatementNode) node);
        }
        return nodeCode;
    }

    /**
     * Generates the assembly code for an assignment statement node.
     * Writing to the data memory. The basic instruction to write an
     * integer to memory is called store word. A integer value is moved
     * to memory using the syntax sw $reg, label. This assigns to the
     * label the integer held in the register.
     *
     * @param asn - The name of the label of the value to be stored in
     * memory.
     * @return - A String of the assembly code that executes the
     * assignment statement node for the statement.
     */
    public String writeCode(AssignmentStatementNode asn)
    {
        String reg = "$t" + ++currentTRegister;
        String code = writeCode(asn.getExpression(), reg);
        code = code + "sw     " + reg + ", " + asn.getLvalue().getName()
                + "         # Memory[label] = $reg\n";
        currentTRegister--;
        return code;
    }

    /**
     * Generates the assembly code for the read statement node.
     * Writes code for the given node. The code reads data from the
     * console. $v0 is set to 5, which tells syscall to read an integer
     * from the console. Syscall reads the integer. And sw moves the
     * read integer into memory using the syntax sw $reg, label. This
     * assigns the label the integer held in $v0.
     * This code generated by this method looks like:
     * li    $v0, 5
     * syscall
     * sw $v0, label
     *
     * @param readNode - The node containing the variable name to be
     * read and stored in memory.
     * @return - A String of the assembly code that executes the
     * read statement node for the statement.
     */
    public String writeCode(ReadStatementNode readNode)
    {
        String code = "li    $v0, 5\nsyscall\nsw     $v0, " +
                readNode.getVarTest().getName() + "\n";
        return code;
    }

    /**
     * Generates the assembly code for the write statement node.
     * Writes code for the given node. Register $v0 is set to 1,
     * this tells syscall to print the integer specified in the line
     * directly before line li, $v0 1. And addi adds the two registers
     * together and stores them in $a0 to be printed.
     * This code generated by this method looks like:
     * lw     $t0, label
     * li    $v0, 1
     * addi   $a0, $t0, 0
     * syscall
     *
     * @param writeNode - The node containing the ExpressionNode value
     * to be printed.
     * @return - A String of the assembly code that executes the
     * write statement node for the statement.
     */
    public String writeCode(WriteStatementNode writeNode)
    {
        String reg = "$t" + ++currentTRegister;
        String code = writeCode(writeNode.getWriteTest(), reg);
        code = code + "li    $v0, 1\n" + "addi   $a0, " + reg + ", 0   " +
                "  # Adds the two registers together and \n" +
                "                       # stores them in " +
                "$a0 to be printed.\nsyscall     " +
                "           # Prints the stored register value.\n";
        currentTRegister--;
        return code;
    }

    /**
     * Generates the assembly code for the return statement node.
     * Writes code for the given node. Register $v0 is set to the value
     * of the ExpressionNode that will be returned.
     * This code generated by this method looks like:
     * li     $v0,  nodeValue
     *
     * @param returnNode - The node containing the ExpressionNode value
     * to be returned.
     * @return - A String of the assembly code that executes the
     * return statement node for the statement.
     */
    public String writeCode(ReturnStatementNode returnNode)
    {
        String reg = "$v0";
        String code = writeCode(returnNode.getReturnTest(), reg) +
                "     " +
                "                  # Returns the function statements";
        return code;
    }

    /**
     * Generates the assembly code for the while statement node.
     * Writes code for the given node. A loop has a label at the top,
     * an expression section, a statement section, a jump label, and an
     * end label. A counter number for the while loop and branch loop
     * are appended onto every label to generate unique labels for
     * multiple loops and their exit labels.
     * This code generated by this method looks like:
     * WhileLabel1:
     * li $t0, numberValue
     * li $t1, numberValue
     * bgt    $t1,   $t2, endLoop
     * else statement section
     * j WhileLabel1:
     * endLoop:
     *
     * @param whileStatementNode - The node containing the expression and
     * statements for the while loop.
     * to be returned.
     * @return - A String of the assembly code that executes the
     * while statement node for the statement.
     */
    public String writeCode(WhileStatementNode whileStatementNode)
    {
        String code;
        int whileNumber = this.whileCounter + 1;
        String reg = "$t" + ++currentTRegister;
        ExpressionNode en = (whileStatementNode.getWhileTest());
        String whileExp = writeCode(en, reg);
        String doStatement = writeCode(whileStatementNode.getStatement());
        code = "TopWhile" + whileNumber + ":\n" +  whileExp +"\n"+
                doStatement + "\n" + "j TopWhile" + whileNumber + "\n"
                + "endLoop" + whileNumber + ":\n";
        currentTRegister--;
        return code;
    }

    /**
     * Generates the assembly code for the if statement node.
     * Writes code for the given node. An if statement has a branch
     * condition expression, a statements section, a jump section, a
     * label for the branch condition expression, and another statements
     * section. A counter number for the if statement and branch condition
     * are appended onto every label to generate unique labels for
     * multiple if statements and their exit labels.
     *
     * Example code:
     * beq s3, s4, True     # branch if true
     * sub s0, s1, s2       # f=g-h(false) In code comes before true.
     * j endLoop            # go to endLoop
     * True:
     * add s0,s1,s2         # f=g+h (true). In code comes after true.
     * endLoop:
     *
     * @param statement
     * @return
     */
    public String writeCode(IfStatementNode statement)
    {
        String code;
        /*Used to grab the last two lines of the Strings appended to
        the then and else statements. Otherwise every
        assignmentStatementNode is appended onto the then and else
        statements.*/
        ifCounter = ++ifCounter * 2;
        String thenStatementResult = "";
        String elseStatementResult = "";
        int whileNumber = this.whileCounter + 1;
        String reg = "$t" + ++currentTRegister;
        ExpressionNode en = statement.getTest();
        String ifExp = writeCode(en, reg);
        String thenStatement = writeCode(statement.getThenStatement());
        String[] arr = thenStatement.split("\n");
        int arrlength = arr.length;
        int arrStart = arrlength - ifCounter;
        /* Iterates over the stored array of appended statements to
        remove appends before the arrStart index. This is done to
        stop every single AssignmentStatementNode from being appended in
        an if statement, and give the MIPS assembly a assembly code a
        cleaner apperance. */
        while(arrStart < arrlength)
        {
            thenStatementResult += arr[arrStart] + "\n";
            arrStart++;
        }
        String elseStatement = writeCode(statement.getElseStatement());
        arr = elseStatement.split("\n");
        arrlength = arr.length;
        arrStart = arrlength - ifCounter;
        //Gives else section of if statement a cleaner appearance.
        while(arrStart < arrlength)
        {
            elseStatementResult += arr[arrStart] + "\n";
            arrStart++;
        }
        code = ifExp +"\n"+ elseStatementResult + "\n" + "j Next \n\n"+
                "endLoop" + whileNumber + ":\n"+ thenStatementResult+"\n"
                +"Next: \n";
        currentTRegister--;
        return code;
    }
}
