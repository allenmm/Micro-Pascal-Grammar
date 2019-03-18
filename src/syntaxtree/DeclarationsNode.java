package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a set of declarations in a Pascal program.
 *
 * @author Marissa Allen
 */
public class DeclarationsNode extends SyntaxTreeNode
{

    private ArrayList<VariableNode> vars = new ArrayList<VariableNode>();

    /**
     * Adds a variable to this declaration.
     *
     * @param aVariable - The variable node to add to this declaration.
     */
    public void addVariable(VariableNode aVariable)
    {
        vars.add(aVariable);
    }

    /**
     * Creates a String representation of the declarations node and
     * its children.
     *
     * @param level - The tree level at which the declarations node
     *              resides.
     * @return - A String representing the declarations node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "Declarations\n";
        for (VariableNode variable : vars)
        {
            answer += variable.indentedToString(level + 1);
        }
        return answer;
    }
}
