
package syntaxtree;

import java.util.ArrayList;

/**
 * The compound statement node represents a compound statement in Pascal.
 * A compound statement is a block of zero or more statements that are
 * run sequentially.
 * @author Marissa Allen
 */
public class CompoundStatementNode extends StatementNode {
    
    private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();
    
    /**
     * Adds a statement to this compound statement.
     * @param state - The statement to add to this compound statement.
     */
    public void addStatement( StatementNode state) {
        this.statements.add( state);
    }

    /**
     * This method creates a String representation of the compound
     * statement node and its children.
     * @param level - The tree level that the compound statement node
     * resides at.
     * @return - A String representing the compound statement node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Compound Statement\n";
        for( StatementNode state : statements) {
            answer += state.indentedToString( level + 1);
        }
        return answer;
    }
}
