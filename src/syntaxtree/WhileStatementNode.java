package syntaxtree;

public class WhileStatementNode extends StatementNode
{
    private ExpressionNode test;
    private StatementNode statement;

    public ExpressionNode getTest()
    {
        return test;
    }

    public void setTest(ExpressionNode test)
    {
        this.test = test;
    }

    public StatementNode getStatement()
    {
        return statement;
    }

    public void setStatement(StatementNode statement)
    {
        this.statement = statement;
    }

    /**
     * Creates a String representation of the while statement node and its
     * children.
     *
     * @param level - The tree level that the while statement node resides
     *              at.
     * @return - A String representing the while statement node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "While\n";
        answer += this.test.indentedToString(level + 1);
        answer += this.statement.indentedToString(level + 1);
        return answer;
    }
}
