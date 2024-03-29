package syntaxtree;

/**
 * The program node represents a Pascal Program.
 *
 * @author Marissa Allen
 */
public class ProgramNode extends SyntaxTreeNode
{

    private String name;
    private DeclarationsNode variables;
    private SubProgramDeclarationsNode functions;
    private CompoundStatementNode main;

    /**
     * ProgramNode constructor that takes in a string for the name of the
     * program.
     *
     * @param aName - the name of the pascal program.
     */
    public ProgramNode(String aName)
    {
        this.name = aName;
    }

    public DeclarationsNode getVariables()
    {
        return variables;
    }

    public void setVariables(DeclarationsNode variables)
    {
        this.variables = variables;
    }

    public SubProgramDeclarationsNode getFunctions()
    {
        return functions;
    }

    public void setFunctions(SubProgramDeclarationsNode functions)
    {
        this.functions = functions;
    }

    public CompoundStatementNode getMain()
    {
        return main;
    }

    public void setMain(CompoundStatementNode main)
    {
        this.main = main;
    }

    /**
     * This method creates a String representation of the program node
     * and its children.
     *
     * @param level - The tree level that the program node resides at.
     * @return - A String representing the program node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "Program: " + name + "\n";
        answer += variables.indentedToString(level + 1);
        answer += functions.indentedToString(level + 1);
        answer += main.indentedToString(level + 1);
        return answer;
    }
}
