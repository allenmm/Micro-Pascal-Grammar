package syntaxtree;

/**
 * The assignment statement node represents a single assignment
 * statement. This node assigns a variable node value, as an
 * assignment statement child node, to the left side of the assignment
 * statement node. Then it assigns an expression node value, as a
 * assignment statement child node, to the right of the assignment
 * statement node.
 *
 * @author Marissa Allen
 */
public class AssignmentStatementNode extends StatementNode
{

    private VariableNode lvalue;
    private ExpressionNode expression;

    public VariableNode getLvalue()
    {
        return lvalue;
    }

    public void setLvalue(VariableNode lvalue)
    {
        this.lvalue = lvalue;
    }

    public ExpressionNode getExpression()
    {
        return expression;
    }

    public void setExpression(ExpressionNode expression)
    {
        this.expression = expression;
    }


    /**
     * Creates a String representation of this assignment statement node
     * and its children.
     *
     * @param level - The tree level that the assignment statement node
     *              resides at.
     * @return - A String representing the assignment statement node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "Assignment\n";
        answer += this.lvalue.indentedToString(level + 1);
        answer += this.expression.indentedToString(level + 1);
        return answer;
    }
}
