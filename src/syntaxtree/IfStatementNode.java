package syntaxtree;

/**
 * The if statement node represents an if statement in Pascal.
 * An if statement includes a boolean expression, and two statements.
 *
 * @author Marissa Allen
 */
public class IfStatementNode extends StatementNode
{
    private ExpressionNode test;
    private StatementNode thenStatement;
    private StatementNode elseStatement;

    public ExpressionNode getTest()
    {
        return test;
    }

    public void setTest(ExpressionNode test)
    {
        this.test = test;
    }

    public StatementNode getThenStatement()
    {
        return thenStatement;
    }

    public void setThenStatement(StatementNode thenStatement)
    {
        this.thenStatement = thenStatement;
    }

    public StatementNode getElseStatement()
    {
        return elseStatement;
    }

    public void setElseStatement(StatementNode elseStatement)
    {
        this.elseStatement = elseStatement;
    }

    /**
     * Creates a String representation of if statement node and its
     * children.
     *
     * @param level - The tree level that the if statement node resides
     *              at.
     * @return - A String representing the if statement node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "If\n";
        answer += this.test.indentedToString(level + 1);
        answer += this.thenStatement.indentedToString(level + 1);
        answer += this.elseStatement.indentedToString(level + 1);
        return answer;
    }

}
