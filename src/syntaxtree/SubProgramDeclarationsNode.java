package syntaxtree;

import java.util.ArrayList;

/**
 * The subprogram declarations node represents a collection of
 * subprogram declarations.
 *
 * @author Marissa Allen
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode
{

    private ArrayList<SubProgramNode> procs = new ArrayList<SubProgramNode>();

    /**
     * Adds a subprogram node to the ArrayList.
     *
     * @param aSubProgram - A subprogram node.
     */
    public void addSubProgramDeclaration(SubProgramNode aSubProgram)
    {
        procs.add(aSubProgram);
    }

    /**
     * Creates a String representation of the subprogram declarations
     * node and its children.
     *
     * @param level - The tree level that the subprogram declarations
     *              node resides at.
     * @return - A String representing the subprogram declarations node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "SubProgramDeclarations\n";
        for (SubProgramNode subProg : procs)
        {
            answer += subProg.indentedToString(level + 1);
        }
        return answer;
    }

}
