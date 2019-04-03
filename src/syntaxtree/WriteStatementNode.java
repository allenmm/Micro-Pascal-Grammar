package syntaxtree;

/**
 * The write statement node represents a write statement in Pascal.
 *
 * @author Marissa Allen
 */
public class WriteStatementNode extends StatementNode
{
    private ExpressionNode writeTest;

    public ExpressionNode getWriteTest()
    {
        return writeTest;
    }

    public void setWriteTest(ExpressionNode writeTest)
    {
        this.writeTest = writeTest;
    }

    /**
     * Creates a String representation of the write statement node and its
     * children.
     *
     * @param level - The tree level that the write statement node resides
     *              at.
     * @return - A String representing the write statement node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "Write\n";
        answer += this.writeTest.indentedToString(level + 1);
        return answer;
    }
}
