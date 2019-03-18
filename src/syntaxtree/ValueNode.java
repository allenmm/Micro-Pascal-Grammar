package syntaxtree;

/**
 * The value node represents a value or number in an expression.
 *
 * @author Marissa Allen
 */
public class ValueNode extends ExpressionNode
{

    /**
     * The attribute associated with this node.
     */
    String attribute;

    /**
     * Creates a ValueNode with the given attribute.
     *
     * @param attr - The attribute for the value node.
     */
    public ValueNode(String attr)
    {
        this.attribute = attr;
    }

    /**
     * Returns the attribute of the value node.
     *
     * @return The attribute of the value node.
     */
    public String getAttribute()
    {
        return (this.attribute);
    }

    /**
     * Returns the attribute as the description of this node.
     *
     * @return The attribute String of this node.
     */
    @Override
    public String toString()
    {
        return (attribute);
    }

    /**
     * Creates a String representation of this value node.
     *
     * @param level - The tree level that the value node resides at.
     * @return - A String representing thevalue node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = this.indentation(level);
        answer += "Value: " + this.attribute + "\n";
        return answer;
    }

    /**
     * Checks to see if the two ValueNodes are equal.
     *
     * @param o - A ValueNode.
     * @return -  Will return true if the ValueNodes objects are equal
     * and will return false if they are not equal.
     */
    @Override
    public boolean equals(Object o)
    {
        boolean answer = false;
        if (o instanceof ValueNode)
        {
            ValueNode other = (ValueNode) o;
            if (this.attribute.equals(other.attribute)) answer = true;
        }
        return answer;
    }
}
