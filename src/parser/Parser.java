package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import syntaxtree.*;

/**
 * This program is a Java program that illustrates reading from a text
 * file or strings. Either a string or a file path name will be passed
 * into the constructor in the Parser class when a method of the class
 * is called. This class creates a parser, implemented as a top-down
 * recursive descent parser. In the middle of the recursive decent the
 * tree is built for the program. The parser builds a syntax tree using
 * the non-terminal symbols from the grammar and the nodes from
 * the classes in the syntaxtree package. Every time a node returns, it
 * returns a part that is added to the syntax tree. The parser also tells
 * whether an input string is from the language that is described by
 * the micro pascal grammar or not.
 *
 * @author Marissa Allen
 */
public class Parser
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
     *
     * If isFilename is true, it signifies that the parser is looking
     * at a file. If false, it signifies that the parser is looking at a
     * lexeme and not a file.
     *
     * @param text       - file path or text containing tokens to be
     *                   recognized.
     * @param isFilename - boolean expression to  help recognize if it
     *                   is a file path or text.
     */
    public Parser(String text, boolean isFilename)
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
     *
     * @return - The program node representing the pascal program entered.
     */
    public ProgramNode program()
    {
        match(TokenType.PROGRAM);
        String programName = lookahead.lexeme;
        match(TokenType.ID);
        /* Allows the current identifier to be added as a
        program in the symbol table. */
        symbols.addProgramName(programName);
        match(TokenType.SEMI);
        ProgramNode pn = new ProgramNode(programName);
        DeclarationsNode dn = declarations();
        pn.setVariables(dn);
        SubProgramDeclarationsNode sdn = subprogram_declarations();
        pn.setFunctions(sdn);
        CompoundStatementNode csn = compound_statement();
        pn.setMain(csn);
        match(TokenType.PERIOD);
        return pn;
    }

    /**
     * Executes the rule for the identifier_list non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - A ArrayList of zero or more strings in the pascal
     * program.
     */
    public ArrayList<String> identifier_list()
    {
        ArrayList answer = new ArrayList();

        String varName = lookahead.lexeme;
        /* Must add before match because match confirms token type then
        moves onto the next token */
        answer.add(varName);
        match(TokenType.ID);
        /* Allows the current identifier to be added as a
        variable in the symbol table. */
        symbols.addVarName(varName);
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        while (this.lookahead.getType() == TokenType.COMMA)
        {
            match(TokenType.COMMA);
            varName = lookahead.lexeme;
            answer.add(varName);
            match(TokenType.ID);
            symbols.addVarName(varName);
        }

        return answer;
    }

    /**
     * Executes the rule for the declarations non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - The set of declarations representing the pascal program.
     */
    public DeclarationsNode declarations()
    {
        DeclarationsNode answer = new DeclarationsNode();

        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.VAR)
        {

            match(TokenType.VAR);
            ArrayList<String> answers = identifier_list();
            for (String s : answers)
            {
                answer.addVariable(new VariableNode(s));
            }
            match(TokenType.COLON);
            type();
            match(TokenType.SEMI);
            declarations();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }

        return answer;
    }


    /**
     * Executes the rule for the type non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - A ArrayList of zero or more strings in the pascal
     * program.
     */
    public ArrayList<String> type()
    {
        ArrayList answer = new ArrayList();

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
        return answer;
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
     *
     * @return - The collection of subprogram declarations in the
     * pascal program.
     */
    public SubProgramDeclarationsNode subprogram_declarations()
    {
        SubProgramDeclarationsNode answer =
                new SubProgramDeclarationsNode();

        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (lookahead.getType() == TokenType.FUNCTION ||
                lookahead.getType() == TokenType.PROCEDURE)
        {
            answer.addSubProgramDeclaration(subprogram_declaration());
            match(TokenType.SEMI);
            answer = subprogram_declarations();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }

        return answer;
    }

    /**
     * Executes the rule for the subprogram_declaration non-terminal
     * symbol in the micro pascal grammar.
     *
     * @return - A subprogram node in the syntax tree in the
     * pascal program.
     */
    public SubProgramNode subprogram_declaration()
    {
        SubProgramNode answer = new SubProgramNode();

        answer.addSubProgramDeclaration(subprogram_head());
        declarations();
        compound_statement();

        return answer;
    }

    /**
     * Executes the rule for the subprogram_head non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - A subprogram node in the syntax tree in the
     * pascal program.
     */
    public SubProgramNode subprogram_head()
    {
        SubProgramNode answer = new SubProgramNode();
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
            ArrayList<VariableNode> argVar = arguments();
            argVar.add(new VariableNode(functionName));
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
            ArrayList<VariableNode> argVar = arguments();
            argVar.add(new VariableNode(procedureName));
            match(TokenType.SEMI);
        }
        else
        {
            //if not a subprogram head
            error("Subprogram Head");
        }
        return answer;
    }

    /**
     * Executes the rule for the arguments non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - An ArrayList of VariableNodes.
     */
    public ArrayList<VariableNode> arguments()
    {
        ArrayList<VariableNode> answer = new ArrayList<>();
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.LPAREN)
        {
            match(TokenType.LPAREN);
            answer = parameter_list();
            match(TokenType.RPAREN);
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
        return answer;
    }

    /**
     * Executes the rule for the parameter_list non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - An ArrayList of VariableNodes.
     */
    public ArrayList<VariableNode> parameter_list()
    {
        ArrayList<VariableNode> answer = new ArrayList<>();
        ArrayList<String> idList = identifier_list();
        for (String s : idList)
        {
            answer.add(new VariableNode(s));
        }
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
        return answer;
    }

    /**
     * Executes the rule for the compound_statement non-terminal symbol
     * in the micro pascal grammar.
     *
     * @return - A a block of zero or more statements in the pascal
     * program.
     */
    public CompoundStatementNode compound_statement()
    {
        CompoundStatementNode answer = new CompoundStatementNode();

        match(TokenType.BEGIN);
        ArrayList<StatementNode> answers = optional_statements();
        if(answers != null)
        {
            for (StatementNode state : answers)
            {
                answer.addStatement(state);
            }
        }
        match(TokenType.END);

        return answer;
    }

    /**
     * Executes the rule for the optional_statements non-terminal
     * symbol in the micro pascal grammar.
     *
     * @return - A ArrayList of statements in the pascal program.
     */
    public ArrayList<StatementNode> optional_statements()
    {
        ArrayList<StatementNode> answers = null;
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (isStatement(lookahead))
        {
            answers = statement_list();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
        return answers;
    }

    /**
     * Executes the rule for the statement_list non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - A ArrayList of statements in the pascal program.
     */
    public ArrayList<StatementNode> statement_list()
    {
        ArrayList<StatementNode> answer = new ArrayList<>();
        answer.add(statement());
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        while (lookahead.getType() == TokenType.SEMI)
        {
            match(TokenType.SEMI);
            //Alternatively could have done .addAll(statement_list())
            answer.add(statement());
        }

        return answer;
    }

    /**
     * Executes the rule for the statement non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - A statement node representing a single statement in
     * Pascal.
     */
    public StatementNode statement()
    {
        AssignmentStatementNode answer = new AssignmentStatementNode();

        IfStatementNode ifAnswer = new IfStatementNode();

        WhileStatementNode whileAnswer = new WhileStatementNode();

        ReturnStatementNode returnAnswer = new ReturnStatementNode();

        WriteStatementNode writeAnswer = new WriteStatementNode();
        /*All if/else if statements compare the lookahead token with a
        token type to see if it matches the same type. */
        if (lookahead.getType() == TokenType.ID)
        {
            /*Checks to see if the current identifier is a variable
            or a procedure name. */
            if (symbols.isVarName(lookahead.lexeme))
            {
                answer.setLvalue(variable());
                assignop();
                answer.setExpression(expression());
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
            ifAnswer.setTest(expression());
            match(TokenType.THEN);
            statement();
            match(TokenType.ELSE);
            statement();
        }
        else if (lookahead.getType() == TokenType.WHILE)
        {
            match(TokenType.WHILE);
            whileAnswer.setWhileTest(expression());
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
            writeAnswer.setWriteTest(expression());
            match(TokenType.RPAREN);
        }
        else if (lookahead.getType() == TokenType.RETURN)
        {
            match(TokenType.RETURN);
            returnAnswer.setReturnTest(expression());
        }
        else
        {
            //if not a statement
            error("Statement");
        }
        return answer;
    }

    /**
     * Executes the rule for the variable non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - a variable node in the syntax tree in the
     * pascal program.
     */
    public VariableNode variable()
    {
        String varName = lookahead.lexeme;
        VariableNode answer = new VariableNode(varName);

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
        return answer;

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
     *
     * @return - Returns an ExpressionNode. The general representation of
     * any expression in the pascal program.
     */
    public ExpressionNode expression_list()
    {
        ExpressionNode answer = null;

        answer = expression();
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.COMMA)
        {
            match(TokenType.COMMA);
            answer = expression_list();
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
        return answer;
    }

    /**
     * Executes the rule for the expression non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - Returns an ExpressionNode. The general representation of
     * any expression in the pascal program.
     */
    public ExpressionNode expression()
    {
        ExpressionNode answer = null;
        answer = simple_expression();
        if (isRelop(lookahead))
        {
            OperationNode opAnswer = new OperationNode(lookahead.getType());
            opAnswer.setLeft(answer);
            opAnswer = relop();
            opAnswer.setRight(simple_expression());
        }
        else
        {
            //Do nothing. The empty lambda option.
        }
        return answer;
    }

    /**
     * This method is used to parse an expression. Executes the rule
     * for the simple_expression non-terminal symbol in the micro pascal
     * grammar.
     *
     * @return - Returns an ExpressionNode. The general representation of
     * any expression in the pascal program.
     */
    public ExpressionNode simple_expression()
    {
        ExpressionNode answer = null;
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.ID ||
                this.lookahead.getType() == TokenType.NUMBER ||
                this.lookahead.getType() == TokenType.LPAREN ||
                this.lookahead.getType() == TokenType.NOT)
        {
            answer = term();
            answer = simple_part(answer);
        }
        else if (this.lookahead.getType() == TokenType.PLUS ||
                this.lookahead.getType() == TokenType.MINUS)
        {
            sign();
            answer = term();
            answer = simple_part(answer);
        }
        else
        {
            //if not a simple expression
            error("Simple Expression");
        }
        return answer;
    }

    /**
     * Executes the rule for the simple_part non-terminal symbol in
     * the micro pascal grammar.
     *
     * @param - Takes an ExpressionNode as its possible left child.
     * @return - Returns an ExpressionNode. The general representation of
     * any expression in the pascal program.
     */
    public ExpressionNode simple_part(ExpressionNode possibleLeft)
    {
        /*Comparing the current lookahead token with a token type to
        see if it matches the same type. */
        if (lookahead.getType() == TokenType.PLUS ||
                lookahead.getType() == TokenType.MINUS ||
                lookahead.getType() == TokenType.OR)
        {
            OperationNode on = addop();
            ExpressionNode right = term();
            on.setLeft(possibleLeft);
            on.setRight(right);
            return simple_part(on);
        }
        else
        {
            //Do nothing. The empty lambda option.
            return possibleLeft;
        }
    }

    /**
     * Executes the rule for the term non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - Returns an ExpressionNode. The general representation of
     * any expression in the pascal program.
     */
    public ExpressionNode term()
    {
        ExpressionNode left = factor();
        return term_part(left);
    }

    /**
     * Executes the rule for the term_part non-terminal symbol in
     * the micro pascal grammar.
     *
     * @param - Takes an ExpressionNode as its possible left child.
     * @return - Returns an ExpressionNode. The general representation of
     * any expression in the pascal program.
     */
    public ExpressionNode term_part(ExpressionNode possibleLeft)
    {
        if (isMulop(lookahead))
        {
            OperationNode on = mulop();
            ExpressionNode right = factor();
            on.setLeft(possibleLeft);
            on.setRight(right);
            return term_part(on);
        }
        else
        {
            //Do nothing. The empty lambda option.
            return possibleLeft;
        }
    }

    /**
     * Executes the rule for the factor non-terminal symbol in
     * the micro pascal grammar.
     *
     * @return - Returns an ExpressionNode. The general representation of
     * any expression in the pascal program.
     */
    public ExpressionNode factor()
    {
        ExpressionNode answer = null;
        /*All if/else if statements compare the lookahead token with a
        token type to see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.ID)
        {
            String varName = lookahead.getLexeme();
            match(TokenType.ID);
            VariableNode var = new VariableNode(varName);
            answer = var;

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
            String numName = lookahead.getLexeme();
            ValueNode val = new ValueNode(numName);
            match(TokenType.NUMBER);
            answer = val;
        }
        else if (this.lookahead.getType() == TokenType.LPAREN)
        {
            match(TokenType.LPAREN);
            answer = expression();
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
        return answer;
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
     *
     * @return - Returns an OperationNode representing any operation in
     * an expression.
     */
    private OperationNode relop()
    {
        OperationNode answer = null;
        /*All if/else if statements compare the lookahead token with a
        token type to see if it matches the same type. */
        if (this.lookahead.getType() == TokenType.EQUIV)
        {
            match(TokenType.EQUIV);
            answer = new OperationNode(TokenType.EQUIV);
        }
        else if (this.lookahead.getType() == TokenType.NOTEQUAL)
        {
            match(TokenType.NOTEQUAL);
            answer = new OperationNode(TokenType.NOTEQUAL);
        }
        else if (this.lookahead.getType() == TokenType.LTHAN)
        {
            match(TokenType.LTHAN);
            answer = new OperationNode(TokenType.LTHAN);
        }
        else if (this.lookahead.getType() == TokenType.GTHAN)
        {
            match(TokenType.GTHAN);
            answer = new OperationNode(TokenType.GTHAN);
        }
        else if (this.lookahead.getType() == TokenType.LTHANEQUAL)
        {
            match(TokenType.LTHANEQUAL);
            answer = new OperationNode(TokenType.LTHANEQUAL);
        }
        else if (this.lookahead.getType() == TokenType.GTHANEQUAL)
        {
            match(TokenType.GTHANEQUAL);
            answer = new OperationNode(TokenType.GTHANEQUAL);
        }
        else
        {
            //if not a relop
            error("Relop");
        }
        return answer;
    }

    /**
     * This method is used to compare the lookahead token with
     * a token type to see if it matches the same type.
     *
     * @return - Returns an OperationNode representing any operation in
     * an expression.
     */
    private OperationNode addop()
    {
        OperationNode answer = null;
        /*All if/else if statements compare the lookahead token with a
        token type to see if it matches the same type. */
        if (lookahead.getType() == TokenType.PLUS)
        {
            match(TokenType.PLUS);
            answer = new OperationNode(TokenType.PLUS);
        }
        else if (lookahead.getType() == TokenType.MINUS)
        {
            match(TokenType.MINUS);
            answer = new OperationNode(TokenType.MINUS);
        }
        else if (lookahead.getType() == TokenType.OR)
        {
            match(TokenType.OR);
            answer = new OperationNode(TokenType.OR);
        }
        else
        {
            //if not an addop
            error("Addop");
        }
        return answer;
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