package codegen;

import parser.SymbolTable;
import scanner.TokenType;
import syntaxtree.*;

/**
 * This class will create code for an Equation tree.
 * @author Marissa Allen
 */
public class CodeGeneration {

    private int currentTRegister = 0;


    /**
     * Starts the code from the root node by writing the outline of the
     * assembly code, and telling the root node to write its answer into $s0.
     *
     * @param root The root node of the equation to be written
     * @return A String of the assembly code.
     */
    public String writeCodeForRoot( ExpressionNode root)
    {
        StringBuilder code = new StringBuilder();
        code.append( ".data\n");
        code.append( "answer:   .word   0\n\n\n");
        code.append( ".text\n");
        code.append( "main:\n");

        String nodeCode = null;
        int tempTRegValue = this.currentTRegister;
        nodeCode = writeCode( root, "$s0");
        this.currentTRegister = tempTRegValue;
        code.append( nodeCode);
        code.append( "sw     $s0,   answer\n");
        code.append( "addi   $v0,   10\n");
        code.append( "syscall\n");
        return( code.toString());
    }

    /**
     * Writes code for the given node.
     * This generic write code takes any ExpressionNode, and then
     * recasts according to subclass type for dispatching.
     * @param node The node for which to write code.
     * @param reg The register in which to put the result.
     * @return
     */
    public String writeCode( ExpressionNode node, String reg) {
        String nodeCode = null;
        if( node instanceof OperationNode) {
            nodeCode = writeCode( (OperationNode)node, reg);
        }
        else if( node instanceof ValueNode) {
            nodeCode = writeCode( (ValueNode)node, reg);
        }
        else if(node instanceof VariableNode)
        {
            nodeCode = writeCode((VariableNode)node, reg);
        }
        return( nodeCode);
    }

    /**
     * Writes code for an operations node.
     * The code is written by gathering the child nodes' answers into
     * a pair of registers, and then executing the op on those registers,
     * placing the result in the given result register.
     * @param opNode The operation node to perform.
     * @param resultRegister The register in which to put the result.
     * @return The code which executes this operation.
     */
    public String writeCode( OperationNode opNode, String resultRegister)
    {
        String code;
        ExpressionNode left = opNode.getLeft();
        String leftRegister = "$t" + currentTRegister++;
        code = writeCode( left, leftRegister);
        ExpressionNode right = opNode.getRight();
        String rightRegister = "$t" + currentTRegister++;
        code += writeCode( right, rightRegister);
        TokenType kindOfOp = opNode.getOperation();
        if( kindOfOp == TokenType.PLUS)
        {
            // add resultregister, left, right
            code += "add    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if( kindOfOp == TokenType.MINUS)
        {
            // add resultregister, left, right
            code += "sub    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if( kindOfOp == TokenType.OR)
        {
            // add resultregister, left, right
            code += "or    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if( kindOfOp == TokenType.MULTI)
        {
            code += "mult   " + leftRegister + ",   " + rightRegister + "\n";
            code += "mflo   " + resultRegister + "\n";
        }
        if( kindOfOp == TokenType.FSLASH)
        {

        }
        if( kindOfOp == TokenType.DIV)
        {

        }
        if( kindOfOp == TokenType.MOD)
        {

        }
        if( kindOfOp == TokenType.AND)
        {
            // add resultregister, left, right
            code += "and    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if( kindOfOp == TokenType.EQUIV)
        {

        }
        if( kindOfOp == TokenType.NOTEQUAL)
        {

        }
        if( kindOfOp == TokenType.LTHAN)
        {

        }
        if( kindOfOp == TokenType.LTHANEQUAL)
        {

        }
        if( kindOfOp == TokenType.GTHANEQUAL)
        {

        }
        if( kindOfOp == TokenType.GTHAN)
        {

        }
        this.currentTRegister -= 2;
        return( code);
    }

    /**
     * Writes code for a value node.
     * The code is written by executing an add immediate with the value
     * into the destination register.
     * Writes code that looks like  addi $reg, $zero, value
     * @param valNode The node containing the value.
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
