package syntaxtree;

import parser.TypeEnum;

/**
 * The expression node is the general representation of any expression.
 * Assigns and gets the type of an ExpressionNode.
 *
 * @author Marissa Allen
 */
public abstract class ExpressionNode extends SyntaxTreeNode
{
    private TypeEnum type = null;

    public TypeEnum getType()
    {
        return type;
    }

    public void setType(TypeEnum type)
    {
        this.type = type;
    }
}
