package codegen;

import parser.SymbolTable;
import scanner.TokenType;
import syntaxtree.*;

import java.util.ArrayList;

/**
 * This class will create code for an Equation tree.
 *
 * @author Marissa Allen
 */
public class CodeGeneration
{

    private int currentTRegister = 0;


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

    public String genCode()
    {
        String answer = "# The .data section of the MIPS assembly\n";
        answer += ".data\n";
        answer += "newline:    .asciiz     \"\\" + "n" + "\" \n";
        DeclarationsNode dn = program.getVariables();
        ArrayList<VariableNode> vn = dn.getVar();
        for (VariableNode varNodes : vn)
        {
            //Mips assigns variables as varname: .word 0
            answer += varNodes.getName() + " :   .word   0\n\n\n";
        }

        answer += "\n.text\n";
        answer += "\nmain:\n";
        answer += "addi    $sp, $sp, -4\n";
        answer += "sw    $ra 0($sp)\n";

        answer += writeCode(program.getMain());

        answer += "lw    $ra 0($sp)\n";
        answer += "addi    $sp, $sp, 4\n";
        answer += "jr $ra\n";

        return answer;
    }

    /**
     * Writes code for the given node.
     * This generic write code takes any ExpressionNode, and then
     * recasts according to subclass type for dispatching.
     *
     * @param node The node for which to write code.
     * @param reg  The register in which to put the result.
     * @return
     */
    public String writeCode(ExpressionNode node, String reg)
    {
        String nodeCode = null;
        if (node instanceof OperationNode)
        {
            nodeCode = writeCode((OperationNode) node, reg);
        }
        else if (node instanceof ValueNode)
        {
            nodeCode = writeCode((ValueNode) node, reg);
        }
        else if (node instanceof VariableNode)
        {
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
     * @param opNode         The operation node to perform.
     * @param resultRegister The register in which to put the result.
     * @return The code which executes this operation.
     */
    public String writeCode(OperationNode opNode, String resultRegister)
    {
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
            /*Use the label reglabelnum as a 'global' for the current generic lable to use outside for label placement?*/

        }
        if (kindOfOp == TokenType.NOTEQUAL)
        {
            code += "bne    " + leftRegister + ",   " + rightRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        if (kindOfOp == TokenType.LTHAN)
        {
            code += "blt    " + rightRegister + ",   " + leftRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        if (kindOfOp == TokenType.LTHANEQUAL)
        {
            code += "ble    " + rightRegister + ",   " + leftRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        //$s = right reg, $t = left reg. Psuedocode: bge $s, $t, C
        if (kindOfOp == TokenType.GTHANEQUAL)
        {
            code += "bge    " + rightRegister + ",   " + leftRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        if (kindOfOp == TokenType.GTHAN)
        {
            code += "bgt    " + rightRegister + ",   " + leftRegister
                    + ", endLoop" + whileNumber + "\n";
        }
        this.currentTRegister -= 2;
        return code;
    }

    /**
     * Writes code for a value node.
     * The code is written by executing an add immediate with the value
     * into the destination register.
     * Writes code that looks like  addi $reg, $zero, value
     *
     * @param valNode        The node containing the value.
     * @param resultRegister The register in which to put the value.
     * @return The code which executes this value node.
     */
    public String writeCode( ValueNode valNode, String resultRegister)
    {
        String value = valNode.getAttribute();
        String code = "addi   " + resultRegister + ",   $zero, " + value + "\n";
        return( code);
    }

}
