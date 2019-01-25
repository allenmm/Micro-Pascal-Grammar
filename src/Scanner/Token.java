/**
 * Constructor class for a token object. Every token will consist of the
 * lexeme, or characters in the input stream, and the token type.
 * 
 * @author Marissa Allen
 */
package scanner;

public class Token {
    public String lexeme;
    public TokenType type;
    public Token( String l, TokenType t) {
        this.lexeme = l;
        this.type = t;
    }
}
