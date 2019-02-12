package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;

/**
 * A Java Program that illustrates reading from a text file or strings.
 * This program creates a
 * @author Marissa Allen
 */
public class Parser {

    ///////////////////////////////
    //    Instance Variables
    ///////////////////////////////

    private Token lookahead;

    private Scanner scanner;

    ///////////////////////////////
    //       Constructors
    ///////////////////////////////

    /**
     * The parser constructor that takes in a file path or text
     * containing tokens.
     *
     * If isFilename is true, it signifies that the parser is looking
     * at a file. If false, it signifies that the parser is looking at a
     * lexeme and not a file.
     *
     * @param text - file path or text containing tokens to be
     * recognized.
     * @param isFilename - boolean expression to  help recognize if it
     * is a file path or text.
     */
    public Parser( String text, boolean isFilename)
    {
        if( isFilename)
        {
            FileInputStream fis = null;
            try
            {
                fis = new FileInputStream("src/parser/simplest.pas");
            }
            catch (FileNotFoundException ex)
            {
                error( "No file");
            }
            InputStreamReader isr = new InputStreamReader( fis);
            scanner = new Scanner( isr);
        }
        else
            {
            scanner = new Scanner( new StringReader( text));
            }
        try
        {
            //Initializing lookahead by reading a token.
            lookahead = scanner.nextToken();
        } catch (IOException ex) {
            error( "Scan error");
        }

    }

    ///////////////////////////////
    //       Methods
    ///////////////////////////////

    /**
     * This method is used to parse an expression. It is the
     * simple expression in the micro pascal grammar.
     */
    public void exp()
    {
       if(lookahead.getType() == TokenType.ID ||
               lookahead.getType() == TokenType.NUMBER ||
               lookahead.getType() == TokenType.LPAREN ||
               lookahead.getType() == TokenType.NOT)
       {
           term();
           simple_part();
       }
       else if(lookahead.getType() == TokenType.PLUS ||
               lookahead.getType() == TokenType.MINUS)
       {
           sign();
           term();
           simple_part();
       }
       else
       {
           error("Simple Expression");
       }
    }

    /**
     * This is 
     */
    public void simple_part()
    {
        if( lookahead.getType() == TokenType.PLUS ||
                lookahead.getType() == TokenType.MINUS ||
                lookahead.getType() == TokenType.OR) {
            addop();
            term();
            simple_part();
        }
        else{
            // Do nothing. The empty lambda option.
        }
    }

    /**
     *
     */
    public void addop() {
        if( lookahead.getType() == TokenType.PLUS)
        {
            match( TokenType.PLUS);
        }
        else if( lookahead.getType() == TokenType.MINUS)
        {
            match( TokenType.MINUS);
        }
        else if ( lookahead.getType() == TokenType.OR)
        {
            match( TokenType.OR); //need to add OR here?
        }
        else {
            error( "Addop");
        }
    }

    /**
     *
     */
    public void term() {
        factor();
        term_part();
    }

    /**
     *
     */
    public void term_part()
    {
        if( isMulop( lookahead) )
        {
            mulop();
            factor();
            term_part();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     * Checks to see if the token from the input string is a mulop token
     * or not and returns a boolean expression if it's true or false.
     * @param token - The token to check against from the input string.
     * @return - If the token is a mulop it will be returned, otherwise
     * it's false and nothing will be returned.
     */
    private boolean isMulop( Token token) {
        boolean answer = false;
        if( token.getType() == TokenType.MULTI ||
                token.getType() == TokenType.FSLASH ||
                token.getType() == TokenType.DIV ||
                token.getType() == TokenType.MOD ||
                token.getType() == TokenType.AND) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     */
    public void mulop() {
        if( lookahead.getType() == TokenType.MULTI)
        {
            match( TokenType.MULTI);
        }
        else if( lookahead.getType() == TokenType.FSLASH)
        {
            match( TokenType.FSLASH);
        }
        else if( lookahead.getType() == TokenType.DIV)
        {
            match( TokenType.DIV);
        }
        else if( lookahead.getType() == TokenType.MOD)
        {
            match( TokenType.MOD);
        }
        else if( lookahead.getType() == TokenType.AND)
        {
            match( TokenType.AND);
        }
        else {
            error( "Mulop");
        }
    }

    /**
     *
     *
     * @param expected - This is the expected token type.
     */
    public void match( TokenType expected) {
        System.out.println("match( " + expected + ")");
        if( this.lookahead.getType() == expected) {
            try {
                this.lookahead = scanner.nextToken();
                if( this.lookahead == null) {
                    this.lookahead = new Token( "End of File", null);
                }
            } catch (IOException ex) {
                error( "Scanner exception");
            }
        }
        else {
            error("Match of " + expected + " found " + this.lookahead.getType()
                    + " instead.");
        }
    }

    /**
     * A custom error method for the parser. When the parser errors, an
     * error message is printed out and the program is exited. The error
     * message contains the error message string from the specific
     * function, the line number the error was on, and the column number.
     * @param message - The error message that is printed by using the
     * error strings from different functions.
     */
    public void error( String message) {
        System.out.println( "Error " + message + " at line " +
                this.scanner.getLine() + " column " +
                this.scanner.getColumn());
        //System.exit( 1);
    }

    /**
     * Recognizes a pascal program.
     */
    public void program()
    {
        match(TokenType.PROGRAM);
        match(TokenType.ID);
        match(TokenType.SEMI);
        declarations();
        subprogram_declarations();
        compound_statement();
        match(TokenType.PERIOD);
    }

    /**
     *
     */
    public void identifier_list()
    {
        match(TokenType.ID);
        /*Comparing the current lookahead with what you would
        expect to see if there were any declarations there.*/
        if(this.lookahead.getType() == TokenType.COMMA)
        {
            match(TokenType.COMMA);
            identifier_list();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     *
     */
    public void declarations()
    {
        /*Comparing the current lookahead with what you would
        expect to see if there were any declarations there.*/
        if(this.lookahead.getType() == TokenType.VAR)
        {
            match(TokenType.VAR);
            identifier_list();
            match(TokenType.COLON);
            type();
            match(TokenType.SEMI);
            declarations();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     *
     */
    public void type()
    {
        /*Comparing the current lookahead with what you would
        expect to see if there were any declarations there.*/
        if(this.lookahead.getType() == TokenType.INTEGER ||
                this.lookahead.getType() == TokenType.REAL)
        {
            standard_type();
        }
        else if(this.lookahead.getType() == TokenType.ARRAY)
        {
            match(TokenType.ARRAY);
            match(TokenType.LBRACKET);
            match(TokenType.NUMBER);
            match(TokenType.COLON);
            match(TokenType.NUMBER);
            match(TokenType.RBRACKET);
            match(TokenType.OF);
            standard_type();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     *
     */
    public void standard_type()
    {
        /*Comparing the current lookahead with what you would
        expect to see if there were any declarations there.*/
        if(this.lookahead.getType() == TokenType.INTEGER)
        {
            match(TokenType.INTEGER);
        }
        /* Otherwise comparing the current lookahead with a different
        declaration.*/
        else if(this.lookahead.getType() == TokenType.REAL)
        {
            match(TokenType.REAL);
        }
        else
        {
            error("Standard Type");
        }
    }

    /**
     *
     */
    public void subprogram_declarations()
    {
        /*Comparing the current lookahead with what you would
        expect to see if there were any declarations there.*/
        if(lookahead.getType() == TokenType.FUNCTION ||
                lookahead.getType() == TokenType.PROCEDURE)
        {
            subprogram_declaration();
            match(TokenType.SEMI);
            subprogram_declarations();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     *
     */
    public void subprogram_declaration()
    {
        subprogram_head();
        declarations();
        compound_statement();
    }

    /**
     *
     */
    public void subprogram_head()
    {
        /*Comparing the current lookahead with what you would
        expect to see if there were any declarations there.*/
        if(lookahead.getType() == TokenType.FUNCTION)
        {
            match(TokenType.FUNCTION);
            match(TokenType.ID);
            arguments();
            match(TokenType.COLON);
            standard_type();
            match(TokenType.SEMI);
        }
        else if(lookahead.getType() == TokenType.PROCEDURE)
        {
            match(TokenType.PROCEDURE);
            match(TokenType.ID);
            arguments();
            match(TokenType.SEMI);
        }
        else{
            error("Subprogram Head");
        }
    }

    /**
     *
     */
    public void arguments()
    {
        /*Comparing the current lookahead with what you would
        expect to see if there were any declarations there.*/
        if(this.lookahead.getType() == TokenType.LPAREN)
        {
            match(TokenType.LPAREN);
            parameter_list();
            match(TokenType.RPAREN);
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     *
     */
    public void parameter_list()
    {
        identifier_list();
        match(TokenType.COLON);
        type();
        if(lookahead.getType() == TokenType.SEMI)
        {
            match(TokenType.SEMI);
            parameter_list();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     *
     */
    public void compound_statement()
    {
        match(TokenType.BEGIN);
        optional_statements();
        match(TokenType.END);
    }

    /**
     *
     */
    public void optional_statements()
    {
        if(lookahead.getType() == TokenType.ID)
        {
            statement_list();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     *
     */
    public void statement_list()
    {
        statement();
        if(lookahead.getType() == TokenType.SEMI)
        {
            match(TokenType.SEMI);
            statement_list();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }


    /**
     * Creating the assignop method stated in the grammar.
     * According to the micro pascal grammar, the ASSIGN token type
     * stands for the assignop := token.
     */
    public void assignop()
    {
        if(lookahead.getType() == TokenType.ID)
        {
            match(TokenType.ASSIGN);
        }
        else
        {
            error("Assignop");
        }
    }

    /**
     *
     */
    public void statement()
    {
        if(lookahead.getType() == TokenType.ID)
        {
            if(lookahead.getType() == TokenType.LBRACKET)
            {
                variable();
                assignop();
                expression();
            }
            else if(lookahead.getType() == TokenType.LPAREN)
            {
                procedure_statement();
            }
        }
        else if(lookahead.getType() == TokenType.BEGIN)
        {
            compound_statement();
        }
        else if(lookahead.getType() == TokenType.IF)
        {
            match(TokenType.IF);
            expression();
            match(TokenType.THEN);
            statement();
            match(TokenType.ELSE);
            statement();
        }
        else if(lookahead.getType() == TokenType.WHILE)
        {
            match(TokenType.WHILE);
            expression();
            match(TokenType.DO);
            statement();
        }
        else if(lookahead.getType() == TokenType.READ)
        {
            match(TokenType.READ);
            match(TokenType.LPAREN);
            match(TokenType.ID);
            match(TokenType.RPAREN);
        }
        else if(lookahead.getType() == TokenType.WRITE)
        {
            match(TokenType.WRITE);
            match(TokenType.LPAREN);
            expression();
            match(TokenType.RPAREN);
            match(TokenType.RETURN);
            expression();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     *
     */
    public void variable()
    {
        match(TokenType.ID);
        /*Comparing the current lookahead with what you would
        expect to see if there were any declarations there.*/
        if(this.lookahead.getType() == TokenType.LBRACKET)
        {
            match(TokenType.LBRACKET);
            expression();
            match(TokenType.RBRACKET);
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

}
