/**
 * The AllenScanner is scanner program that is going to use JFlex 
 * to generate a Java scanner that will scan through the input text
 * file and return a lexeme, yytext returns the matched lexeme. 
 * The lexeme is going to be the string containing the actual characters
 * that were read in that make up one particular token in the language. 
 *
 * @author Marissa Allen
 */

/* Declarations */

package scanner;
import java.util.HashMap;

%%

%public				/* Makes the class public. */
%class  Scanner   /* Names the produced java file. */
/*Makes all of the generated methods and fields of the class private, except for the constructor and the next_token method.*/

%function nextToken /* Renames the yylex() function. */
%type   String     /* Defines the return type of the scanning function.*/
/* The code to be executed at the end of the file. 
Tells Jflex what to return, null. */
%eofval{
  return null;
%eofval}

%{
	/*Copies the HashMap code inside the brackets and puts it 
	inside the class itself. */
	private HashMap<String, TokenType> lookupTable;
%}

%init{
	lookupTable = new HashMap<String, TokenType>();
	lookupTable.put("plus", TokenType.PLUS);
	lookupTable.put("minus", TokenType.MINUS);
	
%init}

/* Patterns */

other         = .		   /*Matches any character other than newline*/
letter        = [A-Za-z]   /*Matches any characters between a-z or A-Z*/
word          = {letter}+  /*The preceding element is matched
							 the letter will be matched one 
							 or more times.*/
whitespace    = [ \n\t]    /*Matches any characters between  \n\t*/
digit	=	[0-9]          /*Matches any characters between 0-9*/
digits = {digit}+     	   /*Encapsulates the digit id and matches it 
							 one or more times. Same as {digit}{digit} */
float = {digits}\.{digits}	/*Encapsulates the digits id and matches the
							  string character . literally. Matches a 
							  number pattern like 10.10*/
id 		= {letter}({letter}|{digit})* /*Encapsulates the letter and 
										digits ids. Matches a letter 
										pattern followed by a number or
										letter zero or more times. */
optional_exponent = (((E|e)[\+|\-]?){digits})?  /*Matches optional
											exponents.
											The entire exponent is
											optional because of the ?
											where it will match the empty
											input.*/
scientific_notation = {digit}\.{digits}{optional_exponent}



%%
/* Lexical Rules */

		 /*Print out the digit that was found. */
{digit} { System.out.println("Found a digit: " + yytext());
		  return( yytext());}
			
		  /*Print out the digits that were found. */
{digits}  { System.out.println("Found some digits: " + yytext());
		   return( yytext());}

			 /*Print out a letter that was found and returns it.*/
{letter}     {
             System.out.println("Found a word: " + yytext());
             return( yytext());
            }
			
			/*Print out the word that was found and returns it.*/
{word}     {
             System.out.println("Found a word: " + yytext());
             return( yytext());
            }
            
{whitespace}  {  /* Ignore Whitespace */ 
                 return "";
              }
              
             /*Print out the id that was found and returns it.*/ 
{id}  		{
             System.out.println("Found an identifier: "
             + yytext());
             return( yytext());
            }           
		
		
             /*Print out the id that was found and returns it.*/ 
{float}  		{
             System.out.println("Found a float: "
             + yytext());
             return( yytext());
            } 
            
             /*Print out the id that was found and returns it.*/ 
{scientific_notation}  		
			{
             System.out.println("Found a scientific notation: "
             + yytext());
             return( yytext());
            }
            			
			/*Print out an illegal character that was found 
			and returns it.*/
{other}    { 
             System.out.println("Illegal char: '" + yytext() 
             + "' found.");
             return "";
           }