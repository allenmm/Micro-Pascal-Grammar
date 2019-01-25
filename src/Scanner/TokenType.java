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
    NUMBER, ID, PLUS, MINUS, SEMI, ASSIGN, WHILE, PROGRAM, VAR, ARRAY,
    OF, FUNCTION, PROCEDURE, BEGIN, END, IF, THEN, ELSE, DO, NOT, OR, 
    AND, DIV, MOD, INTEGER, REAL, COMMA, RBRACKET, LBRACKET, COLON,
    RPAREN, LPAREN, LCURLY, RCURLY, ASTERISK, EQUAL, NOTEQUAL, GTHAN,
    LTHAN, GTHANEQUAL, LTHANEQUAL, FSLASH, PERIOD
}
