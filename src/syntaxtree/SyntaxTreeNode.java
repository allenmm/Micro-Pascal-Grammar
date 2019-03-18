package syntaxtree;

/**
 * The base class for all nodes in a syntax tree for a pascal program.
 *
 * @author Marissa Allen
 */
public abstract class SyntaxTreeNode
{

    /**
     * Creates a String representation of this node and its children.
     *
     * @param level - The tree level that the syntax tree node resides at.
     * @return - A String representing the syntax tree node.
     */
    public abstract String indentedToString(int level);

    /**
     * Creates an indentation String for the indentedToString.
     *
     * @param level - The amount of indentation for the indentedToString.
     * @return A String that displays the given amount of indentation for
     * the indentedToString.
     */
    protected String indentation(int level)
    {
        String answer = "";
        if (level > 0)
        {
            answer = "|-- ";
        }
        for (int indent = 1; indent < level; indent++) answer += "--- ";
        return (answer);
    }

}
