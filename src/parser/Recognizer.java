package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;

/**
 * This class creates a recognizer, implemented as a top-down
 * recursive descent parser. The recognizer doesn’t perform any
 * syntax-directed translation. It will only say whether the input
 * string is from the language described by the micro pascal grammar.
 * The Recognizer class is a Java Program that illustrates reading from
 * a text file or strings. Either a string or a file path name will be
 * passed into the constructor in the Recognizer class when a method of
 * the class is called. If the string file path in the constructor is
 * present and the file name is true, then a test in the RecognizerTest
 * JUnit class will pass the file path. If the text string in the
 * constructor is present and the file name is false, then the JUnit
 * test will pass the string.
 *
 * @author Marissa Allen
 */
public class Recognizer
{

    ///////////////////////////////
    //    Instance Variables
    ///////////////////////////////

    private Token lookahead;

    private Scanner scanner;

    private SymbolTable symbols = new SymbolTable();

    ///////////////////////////////
    //       Constructors
    ///////////////////////////////

    /**
     * The parser constructor that takes in a file path or text
     * containing tokens.
     * <p>
     * If isFilename is true, it signifies that the parser is looking
     * at a file. If false, it signifies that the parser is looking at a
     * lexeme and not a file.
     *
     * @param text       - file path or text containing tokens to be
     *                   recognized.
     * @param isFilename - boolean expression to  help recognize if it
     *                   is a file path or text.
     */
    public Recognizer(String text, boolean isFilename)
    {
        if (isFilename)
        {
            FileInputStream fis = null;
            try
            {
                fis = new FileInputStream(text);
            }
            catch (FileNotFoundException ex)
            {
                //not a file
                error("No file");
            }
            InputStreamReader isr = new InputStreamReader(fis);
            scanner = new Scanner(isr);
        }
        else
        {
            scanner = new Scanner(new StringReader(text));
        }
        try
        {
            //Initializing lookahead by reading a token.
            lookahead = scanner.nextToken();
        }
        catch (IOException ex)
        {
            //not a string.
            error("Scan error");
        }

    }

    ///////////////////////////////
    //       Methods
    ///////////////////////////////

    /**
     * Executes the rule for the program non-terminal symbol in
     * the micro pascal grammar if it is recognized as a pascal program.
     */
    public void program()
    {
        match(TokenType.PROGRAM);
        String programName = lookahead.lexeme;
        match(TokenType.ID);
        /* Allows the current identifier to be added as a
        program in the symbol table. */
        symbols.addProgramName(programName);
        match(TokenType.SEMI);
        declarations();
        subprogram_declarations();
        compound_statement();
        match(TokenType.PERIOD);
    }

    /**
     * Executes the rule for the identifier_list non-terminal symbol in
     * the micro pascal grammar.
     */
    public void identifier_list()
    {
        String varName = lookahead.lexeme;
        match(TokenType.ID);
        /* Allows the current identifier to be added as a
        variable in the symbol table. */
        symbols.addVarName(varName, null);
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.COMMA)
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
     * Executes the rule for the declarations non-terminal symbol in
     * the micro pascal grammar.
     */
    public void declarations()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.VAR)
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
     * Executes the rule for the type non-terminal symbol in
     * the micro pascal grammar.
     */
    public void type()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.INTEGER ||
                this.lookahead.getType() == TokenType.REAL)
        {
            standard_type();
        }
        /* Otherwise comparing the current lookahead with a different
        declaration.*/
        else if (this.lookahead.getType() == TokenType.ARRAY)
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
            //if not a type
            error("Type");
        }
    }

    /**
     * Executes the rule for the standard_type non-terminal symbol in
     * the micro pascal grammar.
     */
    public void standard_type()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.INTEGER)
        {
            match(TokenType.INTEGER);
        }
        /* Otherwise comparing the current lookahead with a different
        declaration.*/
        else if (this.lookahead.getType() == TokenType.REAL)
        {
            match(TokenType.REAL);
        }
        else
        {
            //if not a standard type
            error("Standard Type");
        }
    }

    /**
     * Executes the rule for the subprogram_declarations non-terminal
     * symbol in the micro pascal grammar.
     */
    public void subprogram_declarations()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (lookahead.getType() == TokenType.FUNCTION ||
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
     * Executes the rule for the subprogram_declaration non-terminal
     * symbol in the micro pascal grammar.
     */
    public void subprogram_declaration()
    {
        subprogram_head();
        declarations();
        compound_statement();
    }

    /**
     * Executes the rule for the subprogram_head non-terminal symbol in
     * the micro pascal grammar.
     */
    public void subprogram_head()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (lookahead.getType() == TokenType.FUNCTION)
        {
            match(TokenType.FUNCTION);
            String functionName = lookahead.lexeme;
            match(TokenType.ID);
            /* Allows the current identifier to be added as a
            function in the symbol table. */
            symbols.addFunctionName(functionName);
            arguments();
            match(TokenType.COLON);
            standard_type();
            match(TokenType.SEMI);
        }
        /* Otherwise comparing the current lookahead with a different
        declaration.*/
        else if (lookahead.getType() == TokenType.PROCEDURE)
        {
            match(TokenType.PROCEDURE);
            String procedureName = lookahead.lexeme;
            match(TokenType.ID);
            /* Allows the current identifier to be added as a
            procedure in the symbol table. */
            symbols.addProcedureName(procedureName);
            arguments();
            match(TokenType.SEMI);
        }
        else
        {
            //if not a subprogram head
            error("Subprogram Head");
        }
    }

    /**
     * Executes the rule for the arguments non-terminal symbol in
     * the micro pascal grammar.
     */
    public void arguments()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.LPAREN)
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
     * Executes the rule for the parameter_list non-terminal symbol in
     * the micro pascal grammar.
     */
    public void parameter_list()
    {
        identifier_list();
        match(TokenType.COLON);
        type();
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (lookahead.getType() == TokenType.SEMI)
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
     * Executes the rule for the compound_statement non-terminal symbol
     * in the micro pascal grammar.
     */
    public void compound_statement()
    {
        match(TokenType.BEGIN);
        optional_statements();
        match(TokenType.END);
    }

    /**
     * Executes the rule for the optional_statements non-terminal
     * symbol in the micro pascal grammar.
     */
    public void optional_statements()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (isStatement(lookahead))
        {
            statement_list();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     * Executes the rule for the statement_list non-terminal symbol in
     * the micro pascal grammar.
     */
    public void statement_list()
    {
        statement();
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (lookahead.getType() == TokenType.SEMI)
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
     * Executes the rule for the statement non-terminal symbol in
     * the micro pascal grammar.
     */
    public void statement()
    {
        /*All if/else if statements compare the lookahead token with a
        token type to see if it matches the same type. */
        if (lookahead.getType() == TokenType.ID)
        {
            /*Checks to see if the current identifier is a variable
            or a procedure name. */
            if (symbols.isVarName(lookahead.lexeme))
            {
                variable();
                assignop();
                expression();
            }
            else
            {
                procedure_statement();
            }
        }
        else if (lookahead.getType() == TokenType.BEGIN)
        {
            compound_statement();
        }
        else if (lookahead.getType() == TokenType.IF)
        {
            match(TokenType.IF);
            expression();
            match(TokenType.THEN);
            statement();
            match(TokenType.ELSE);
            statement();
        }
        else if (lookahead.getType() == TokenType.WHILE)
        {
            match(TokenType.WHILE);
            expression();
            match(TokenType.DO);
            statement();
        }
        else if (lookahead.getType() == TokenType.READ)
        {
            match(TokenType.READ);
            match(TokenType.LPAREN);
            match(TokenType.ID);
            match(TokenType.RPAREN);
        }
        else if (lookahead.getType() == TokenType.WRITE)
        {
            match(TokenType.WRITE);
            match(TokenType.LPAREN);
            expression();
            match(TokenType.RPAREN);
        }
        else if (lookahead.getType() == TokenType.RETURN)
        {
            match(TokenType.RETURN);
            expression();
        }
        else
        {
            //if not a statement
            error("Statement");
        }
    }

    /**
     * Executes the rule for the variable non-terminal symbol in
     * the micro pascal grammar.
     */
    public void variable()
    {
        match(TokenType.ID);
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.LBRACKET)
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

    /**
     * Executes the rule for the procedure_statement non-terminal symbol
     * in the micro pascal grammar.
     */
    public void procedure_statement()
    {
        match(TokenType.ID);
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.LPAREN)
        {
            match(TokenType.LPAREN);
            expression_list();
            match(TokenType.RPAREN);
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     * Executes the rule for the expression_list non-terminal symbol in
     * the micro pascal grammar.
     */
    public void expression_list()
    {
        expression();
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.COMMA)
        {
            match(TokenType.COMMA);
            expression_list();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     * Executes the rule for the expression non-terminal symbol in
     * the micro pascal grammar.
     */
    public void expression()
    {
        simple_expression();
        if (isRelop(lookahead))
        {
            relop();
            simple_expression();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     * This method is used to parse an expression. Executes the rule
     * for the simple_expression non-terminal symbol in the micro pascal
     * grammar.
     */
    public void simple_expression()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.ID ||
                this.lookahead.getType() == TokenType.NUMBER ||
                this.lookahead.getType() == TokenType.LPAREN ||
                this.lookahead.getType() == TokenType.NOT)
        {
            term();
            simple_part();
        }
        else if (this.lookahead.getType() == TokenType.PLUS ||
                this.lookahead.getType() == TokenType.MINUS)
        {
            sign();
            term();
            simple_part();
        }
        else
        {
            //if not a simple expression
            error("Simple Expression");
        }
    }

    /**
     * Executes the rule for the simple_part non-terminal symbol in
     * the micro pascal grammar.
     */
    public void simple_part()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (lookahead.getType() == TokenType.PLUS ||
                lookahead.getType() == TokenType.MINUS ||
                lookahead.getType() == TokenType.OR)
        {
            addop();
            term();
            simple_part();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
    }

    /**
     * Executes the rule for the term non-terminal symbol in
     * the micro pascal grammar.
     */
    public void term()
    {
        factor();
        term_part();
    }

    /**
     * Executes the rule for the term_part non-terminal symbol in
     * the micro pascal grammar.
     */
    public void term_part()
    {
        if (isMulop(lookahead))
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
     * Executes the rule for the factor non-terminal symbol in
     * the micro pascal grammar.
     */
    public void factor()
    {
        /*All if/else if statements compare the lookahead token with a
        token type to see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.ID)
        {
            match(TokenType.ID);
            if (this.lookahead.getType() == TokenType.LBRACKET)
            {
                match(TokenType.LBRACKET);
                expression();
                match(TokenType.RBRACKET);
            }
            else if (this.lookahead.getType() == TokenType.LPAREN)
            {
                match(TokenType.LPAREN);
                expression_list();
                match(TokenType.RPAREN);
            }
            else
            {
                //Do nothing. The empty lambda option.
            }
        }
        else if (this.lookahead.getType() == TokenType.NUMBER)
        {
            match(TokenType.NUMBER);
        }
        else if (this.lookahead.getType() == TokenType.LPAREN)
        {
            match(TokenType.LPAREN);
            expression();
            match(TokenType.RPAREN);
        }
        else if (this.lookahead.getType() == TokenType.NOT)
        {
            match(TokenType.NOT);
            factor();
        }
        else
        {
            //if not a factor
            error("Factor");
        }
    }

    /**
     * Executes the rule for the sign non-terminal symbol in
     * the micro pascal grammar.
     */
    public void sign()
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.PLUS)
        {
            match(TokenType.PLUS);
        }
        else if (this.lookahead.getType() == TokenType.MINUS)
        {
            match(TokenType.MINUS);
        }
        else
        {
            //if not a sign
            error("Sign");
        }
    }

    /**
     * This method is based on the relop rules in the micro pascal
     * grammar. It is used to compare the lookahead token with
     * a token type to see if it matches the same type.
     */
    private void relop()
    {
        /*All if/else if statements compare the lookahead token with a
        token type to see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.EQUIV)
        {
            match(TokenType.EQUIV);
        }
        else if (this.lookahead.getType() == TokenType.NOTEQUAL)
        {
            match(TokenType.NOTEQUAL);
        }
        else if (this.lookahead.getType() == TokenType.LTHAN)
        {
            match(TokenType.LTHAN);
        }
        else if (this.lookahead.getType() == TokenType.GTHAN)
        {
            match(TokenType.GTHAN);
        }
        else if (this.lookahead.getType() == TokenType.LTHANEQUAL)
        {
            match(TokenType.LTHANEQUAL);
        }
        else if (this.lookahead.getType() == TokenType.GTHANEQUAL)
        {
            match(TokenType.GTHANEQUAL);
        }
        else
        {
            //if not a relop
            error("Relop");
        }
    }

    /**
     * This method is used to compare the lookahead token with
     * a token type to see if it matches the same type.
     */
    private void addop()
    {
        /*All if/else if statements compare the lookahead token with a
        token type to see if it matches the same type. */
        if (lookahead.getType() == TokenType.PLUS)
        {
            match(TokenType.PLUS);
        }
        else if (lookahead.getType() == TokenType.MINUS)
        {
            match(TokenType.MINUS);
        }
        else if (lookahead.getType() == TokenType.OR)
        {
            match(TokenType.OR); //need to add OR here?
        }
        else
        {
            //if not an addop
            error("Addop");
        }
    }

    /**
     * Creating the mulop method. According to the micro pascal grammar,
     * the ASSIGN token type
     * stands for the assignop := token.
     */
    protected void mulop()
    {
        /*All if/else if statements compare the lookahead token with a
        token type to see if it matches the same type. */
        if (lookahead.getType() == TokenType.MULTI)
        {
            match(TokenType.MULTI);
        }
        else if (lookahead.getType() == TokenType.FSLASH)
        {
            match(TokenType.FSLASH);
        }
        else if (lookahead.getType() == TokenType.DIV)
        {
            match(TokenType.DIV);
        }
        else if (lookahead.getType() == TokenType.MOD)
        {
            match(TokenType.MOD);
        }
        else if (lookahead.getType() == TokenType.AND)
        {
            match(TokenType.AND);
        }
        else
        {
            //if not a mulop
            error("Mulop");
        }
    }

    /**
     * Creating the assignop method. According to the micro pascal
     * grammar, the ASSIGN token type stands for the assignop := token.
     */
    private void assignop()
    {
       /*Compares the lookahead token with a token type to see if it
         matches the same type. */
        if (lookahead.getType() == TokenType.ASSIGN)
        {
            match(TokenType.ASSIGN);
        }
        else
        {
            //if not an assignop
            error("Assignop");
        }
    }

    /**
     * Compares the token type that's expected with the actual token
     * type. If the token type passed in is the same as the actual
     * token type, then it is the current token or the end of the file.
     *
     * @param expected - This is the expected token type.
     */
    protected void match(TokenType expected)
    {
        System.out.println("match( " + expected + ")");
        /*Compares the lookahead token with a token type to see if it
         matches the same expected type. */
        if (this.lookahead.getType() == expected)
        {
            try
            {
                this.lookahead = scanner.nextToken();
                if (this.lookahead == null)
                {
                    this.lookahead = new
                            Token("End of File", null);
                }
            }
            catch (IOException ex)
            {
                error("Scanner exception");
            }
        }
        else
        {
            error("Match of " + expected + " found " +
                    this.lookahead.getType()
                    + " instead.");
        }
    }

    /**
     * A custom error method for the parser. When the parser errors, an
     * error message is printed out and the program is exited. The error
     * message contains the error message string from the specific
     * function, the line number the error was on, and the column number.
     *
     * @param message - The error message that is printed by using the
     *                error strings from different functions.
     */
    protected void error(String message)
    {
        throw new RuntimeException(message);
    }

    /**
     * Checks to see if the token from the input string is a mulop token
     * or not and returns a boolean expression if it's true or false.
     *
     * @param token - The lookahead token to check against from
     *              the input string.
     * @return - If the token is a mulop it will be returned, otherwise
     * it's false and nothing will be returned.
     */
    private boolean isMulop(Token token)
    {
        boolean answer = false;
        /*Compares the lookahead token with a token type to see if it
         matches the same type. */
        if (token.getType() == TokenType.MULTI ||
                token.getType() == TokenType.FSLASH ||
                token.getType() == TokenType.DIV ||
                token.getType() == TokenType.MOD ||
                token.getType() == TokenType.AND)
        {
            answer = true;
        }
        return answer;
    }


    /**
     * Checks to see if the token from the input string is a relop token
     * or not and returns a boolean expression if it's true or false.
     *
     * @param token - The lookahead token to check against from the
     *              input string.
     * @return - If the token is a relop it will be returned, otherwise
     * it's false and nothing will be returned.
     */
    private boolean isRelop(Token token)
    {
        boolean answer = false;
        /*Compares the lookahead token with a token type to see if it
         matches the same type. */
        if (token.getType() == TokenType.EQUIV ||
                token.getType() == TokenType.NOTEQUAL ||
                token.getType() == TokenType.LTHAN ||
                token.getType() == TokenType.GTHAN ||
                token.getType() == TokenType.LTHANEQUAL ||
                token.getType() == TokenType.GTHANEQUAL)
        {
            answer = true;
        }
        return answer;
    }


    /**
     * Checks to see if the token from the input string is a statement
     * token or not and returns a boolean expression if it's true or
     * false.
     *
     * @param token - The lookahead token to check against from the
     *              input string.
     * @return - If the token is a statement it will be returned,
     * otherwise it's false and nothing will be returned.
     */
    private boolean isStatement(Token token)
    {
        boolean answer = false;
        /*Compares the lookahead token with a token type to see if it
         matches the same type. */
        if (token.getType() == TokenType.ID ||
                token.getType() == TokenType.BEGIN ||
                token.getType() == TokenType.IF ||
                token.getType() == TokenType.WHILE ||
                token.getType() == TokenType.READ ||
                token.getType() == TokenType.WRITE ||
                token.getType() == TokenType.RETURN)
        {
            answer = true;
        }
        return answer;
    }

    public SymbolTable getSymbolTable()
    {
        return symbols;
    }

}

