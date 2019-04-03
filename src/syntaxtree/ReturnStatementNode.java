package syntaxtree;

/**
 * The return statement node represents a return statement in Pascal.
 *
 * @author Marissa Allen
 */
public class ReturnStatementNode extends StatementNode
{
    private ExpressionNode returnTest;

    public ExpressionNode getReturnTest()
    {
        return returnTest;
    }

    public void setReturnTest(ExpressionNode returnTest)
    {
        this.returnTest = returnTest;
    }

    /**
     * Creates a String representation of the return statement node and
     * its children.
     *
     * @param level - The tree level that the return statement node
     *              resides at.
     * @return - A String representing the return statement node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "Return\n";
        answer += this.returnTest.indentedToString(level + 1);
        return answer;
    }
}
