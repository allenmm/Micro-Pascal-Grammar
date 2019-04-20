package syntaxtree;

import scanner.TokenType;

/**
 * The operation node represents any operation in an expression.
 *
 * @author Marissa Allen
 */
public class OperationNode extends ExpressionNode
{

    /**
     * The left operator of the operation node.
     */
    private ExpressionNode left;

    /**
     * The right operator of the operation node.
     */
    private ExpressionNode right;

    /**
     * The kind of operation.
     */
    private TokenType operation;

    /**
     * Creates an operation node given an operation token.
     *
     * @param op - The token representing the operation node's
     *           math operation.
     */
    public OperationNode(TokenType op)
    {
        this.operation = op;
    }


    // Getters
    public ExpressionNode getLeft()
    {
        return (this.left);
    }

    public ExpressionNode getRight()
    {
        return (this.right);
    }

    public TokenType getOperation()
    {
        return (this.operation);
    }

    // Setters
    public void setLeft(ExpressionNode node)
    {
        // If we already have a left, remove it from our child list.
        this.left = node;
    }

    public void setRight(ExpressionNode node)
    {
        // If we already have a right, remove it from our child list.
        this.right = node;
    }

    public void setOperation(TokenType op)
    {
        this.operation = op;
    }

    /**
     * Returns the operation token as a String.
     *
     * @return - The String version of the operation token.
     */
    @Override
    public String toString()
    {
        return operation.toString();
    }

    /**
     * Creates a String representation of the operation node and its
     * children.
     *
     * @param level - The tree level that the operation node resides at.
     * @return - A String representing the operation node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "Operation: " + this.operation + " " +
                OperationNode.super.getType() + "\n";
        answer += left.indentedToString(level + 1);
        answer += right.indentedToString(level + 1);
        return (answer);
    }

    /**
     * Checks to see if the two OperationNodes are equal.
     *
     * @param o - An OperationNode.
     * @return - Will return true if the OperationNodes are equal and
     * will return false if they are not equal.
     */
    @Override
    public boolean equals(Object o)
    {
        boolean answer = false;
        if (o instanceof OperationNode)
        {
            OperationNode other = (OperationNode) o;
            if ((this.operation == other.operation) &&
                    this.left.equals(other.left) &&
                    this.right.equals(other.right)) answer = true;
        }
        return answer;
    }
}
