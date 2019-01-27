/**
 * Constructor class for a token object. Every token will consist of the
 * lexeme, or characters in the input stream, and the token type.
 * 
 * @author Marissa Allen
 */
package scanner;

public class Token
{
    public String lexeme;
    public TokenType type;
    
	/**
	 * Constructor that creates a Token that has a lexeme as the name and
	 * a type from the TokenType enum class.
	 *
	 * @param l - The Token name defined by the characters 
	 * in the input stream.
	 * @param t - The Token type.
	 */
    public Token( String l, TokenType t)
    {
        this.lexeme = l;
        this.type = t;
    }
    
	public String getLexeme() {
		return lexeme;
	}

	public TokenType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Token [lexeme=" + lexeme + ", type=" + type + ", toString()=" + super.toString() + "]";
	}
	
}