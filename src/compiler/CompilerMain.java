package compiler;

import parser.Parser;
import parser.SymbolTable;
import syntaxtree.ProgramNode;
import analysis.SemanticAnalyzer;

import java.io.PrintWriter;


/**
 * This class reads a pascal file from args and writes a formatted symbol
 * table to a file. The file read from args is checked to see if a file
 * was entered or not. If no file is entered then it asks the user to
 * enter a file name, and then exits the file. If a file is present, then
 * it is read, passed into the program method in the Parser class
 * through its constructor, and a toString of the symbol table from the
 * pascal program and a indentedToString of the syntax tree is generated
 * and written to a file. The symbol table and a ProgramNode from the
 * parser are then passed into the SemanticAnalyzer class to check that
 * the declarations and statements of a program are semantically correct
 * assign the type of each ExpressionNode, and make sure that the
 * variable is declared before use.
 *
 * @author Marissa Allen
 */
public class CompilerMain
{
    public static void main(String[] args)
    {
        //Tests to see if a file was entered.
        if (args.length == 0)
        {
            System.out.println("Please enter a file name");
            System.exit(0);
        }
        else
        {
            /*Takes an input file from the command line argument and
            stores it in a String called fileName.*/
            String fileName = args[0];
            Parser instance = new Parser
                    (fileName, true);

            ProgramNode pn = instance.program();
            SymbolTable st = instance.getSymbolTable();
            SemanticAnalyzer sa = new SemanticAnalyzer(pn, st);
            sa.analyze();
            if (sa.goodToGo())
            {
                //placeholder for running code generator
            }
            else
            {
                System.out.println("Semantic Analyzer failed. No " +
                        "assembly code generated.");
            }
            /*Calls Parser Object method program. Constructor
            is automatically called when an object of the class is
            created. */
            String indentedString = pn.indentedToString(0);
            /*The symbol table toString generated by passing the file
            into the program method. */
            String tableString = st.toString();
            int dotIndex = fileName.indexOf(".");
            /*The file name from the first letter of the file name up
            to the dot. */
            String baseFileName = fileName.substring(0, dotIndex);
            /*Changing the file extension by adding the file extension
            .table on to the file name. */
            String fileOutName = baseFileName + ".table";
            try
            {
                PrintWriter printWriter = new PrintWriter(fileOutName);
                //Writing symbol table toString out to the .table file
                printWriter.print(tableString);
                printWriter.close();
            }
            catch (Exception e)
            {
                System.out.println("Failed to write to file");
            }

            fileOutName = baseFileName + ".tree";
            try
            {
                PrintWriter printWriter = new PrintWriter(fileOutName);
                /*Writing a syntax tree indentedToString out to the
                .tree file*/
                printWriter.print(indentedString);
                printWriter.close();
            }
            catch (Exception e)
            {
                System.out.println("Failed to write to file");
            }


        }
    }
}
