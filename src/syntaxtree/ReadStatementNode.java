package syntaxtree;

/**
 * The read statement node represents a read statement in Pascal.
 *
 * @author Marissa Allen
 */
public class ReadStatementNode extends StatementNode
{
    private VariableNode varTest;

    public VariableNode getVarTest()
    {
        return varTest;
    }

    public void setVarTest(VariableNode varTest)
    {
        this.varTest = varTest;
    }

    /**
     * Creates a String representation of the read statement node and its
     * children.
     *
     * @param level - The tree level that the read statement node resides
     *              at.
     * @return - A String representing the read statement node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "Read\n";
        answer += this.varTest.indentedToString(level + 1);
        return answer;
    }
}
