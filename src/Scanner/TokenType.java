package scanner;

/**
 * A class that contains the different token types for every keyword and 
 * symbol. The token type is the individual token type descriptor of the
 * keywords, identifiers, and symbols. All of the keywords and symbols are
 * listed after the design section and anything else is an identifier.
 *
 * @author Marissa Allen
 */
public enum TokenType {
    NUMBER, ID, PLUS, MINUS, SEMI, ASSIGN, PROGRAM, VAR, ARRAY,
    OF, FUNCTION, PROCEDURE, BEGIN, END, IF, THEN, ELSE, WHILE, DO, 
    NOT, OR, AND, DIV, MOD, INTEGER, REAL, READ, WRITE, RETURN, COMMA,
    RBRACKET, LBRACKET, COLON, RPAREN, LPAREN, MULTI, EQUIV, NOTEQUAL,
    GTHAN, LTHAN, GTHANEQUAL, LTHANEQUAL, FSLASH, PERIOD
}