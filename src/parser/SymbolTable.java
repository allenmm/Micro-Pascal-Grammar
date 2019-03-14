package parser;

import java.util.HashMap;

/**
 * This program creates a symbol table that is implemented as a hash map
 * to store information about every pascal identifier added to the symbol
 * table. The symbol table will store identifier information such as the
 * character string (or lexeme), its datatype, and its kind. The kind of
 * identifier can be a program, variable, array, function, or procedure.
 *
 * @author Marissa Allen
 */
public class SymbolTable
{

    private HashMap<String, SymbolData> symbols = new HashMap<>();

    /**
     * Adds a variable identifier to the symbol table if the variable
     * name doesn't exist in the table.
     *
     * @param name - lexeme containing a variable name.
     */
    public void addVarName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData(name, KindEnum.VAR_NAME));
        }
    }

    /**
     * Adds a program identifier to the symbol table if the program
     * name doesn't exist in the table.
     *
     * @param name - lexeme containing a program name.
     */
    public void addProgramName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData
                    (name, KindEnum.PROGRAM_NAME));
        }
    }

    /**
     * Adds an array identifier to the symbol table if the array name
     * doesn't exist in the table.
     *
     * @param name - lexeme containing an array name.
     */
    public void addArrayName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData(name, KindEnum.ARRAY_NAME));
        }
    }

    /**
     * Adds a procedure identifier to the symbol table if the procedure
     * name doesn't exist in the table.
     *
     * @param name - lexeme containing a procedure name.
     */
    public void addProcedureName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData
                    (name, KindEnum.PROCEDURE_NAME));
        }
    }

    /**
     * Adds a function identifier to the symbol table if the function
     * name doesn't exist in the table.
     *
     * @param name - lexeme containing a function name.
     */
    public void addFunctionName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData
                    (name, KindEnum.FUNCTION_NAME));
        }
    }

    /**
     * Checks to see if the identifier name exists and if it is a
     * variable name.
     *
     * @param name - The symbol name that is being checked.
     * @return - A true will be returned if the name exists in the
     * Symbol Table and if it's a variable. A false will be returned if
     * the name doesn't exist in the Symbol Table or it is not a
     * variable.
     */
    public boolean isVarName(String name)
    {

        SymbolData s = symbols.get(name);
        //If a variable name key exists and the kind is a variable name
        if (s != null && s.kind == KindEnum.VAR_NAME)
        {
            return true;
        }

        return false;
    }

    /**
     * Checks to see if the identifier name exists and if it is a
     * procedure name.
     *
     * @param name - The symbol name that is being checked.
     * @return - A true will be returned if the name exists in the
     * Symbol Table and if it's a procedure. A false will be returned if
     * the name doesn't exist in the Symbol Table or it is not a
     * procedure.
     */
    public boolean isProcedureName(String name)
    {

        SymbolData s = symbols.get(name);
        //If procedure name key exists and the kind is a procedure name
        if (s != null && s.kind == KindEnum.PROCEDURE_NAME)
        {
            return true;
        }

        return false;
    }

    /**
     * Checks to see if the identifier name exists and if it is a
     * program name.
     *
     * @param name - The symbol name that is being checked.
     * @return - A true will be returned if the name exists in the
     * Symbol Table and if it's a program. A false will be returned if
     * the name doesn't exist in the Symbol Table or it is not a
     * program.
     */
    public boolean isProgramName(String name)
    {
        SymbolData s = symbols.get(name);
        //If a program name key exists and the kind is a program name
        if (s != null && s.kind == KindEnum.PROGRAM_NAME)
        {
            return true;
        }

        return false;
    }

    /**
     * Checks to see if the identifier name exists and if it is an
     * array name.
     *
     * @param name - The symbol name that is being checked.
     * @return - A true will be returned if the name exists in the
     * Symbol Table and if it's an array. A false will be returned if
     * the name doesn't exist in the Symbol Table or it is not an
     * array.
     */
    public boolean isArrayName(String name)
    {
        SymbolData s = symbols.get(name);
        //If an array name key exists and the kind is an array name
        if (s != null && s.kind == KindEnum.ARRAY_NAME)
        {
            return true;
        }

        return false;
    }

    /**
     * Checks to see if the identifier name exists and if it is a
     * function name.
     *
     * @param name - The symbol name that is being checked.
     * @return - A true will be returned if the name exists in the
     * Symbol Table and if it's a function. A false will be returned if
     * the name doesn't exist in the Symbol Table or it is not a
     * function.
     */
    public boolean isFunctionName(String name)
    {
        SymbolData s = symbols.get(name);
        //If a function name key exists and the kind is a function name
        if (s != null && s.kind == KindEnum.FUNCTION_NAME)
        {
            return true;
        }
        return false;
    }

    /**
     * The string displayed for the returned SymbolTable.
     *
     * @return - returns the SymbolTable's lexeme and SymbolData
     * toString().
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tSymbol Table\n\n" + "Symbols\t\t\t\t" +
                " Kinds\n" +
                "-------------------------------------\n");
        for(HashMap.Entry<String, SymbolData> entry: symbols.entrySet())
        {
            String output = String.format("%-20s %-20s",
                    entry.getValue().name, entry.getValue().kind);
            sb.append(output);
            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * Contains the datatype and kind information identifier attribute
     * values that are stored in the hash map.
     *
     * @author Marissa Allen
     */
    private class SymbolData
    {
        String name;
        KindEnum kind;

        SymbolData(String name, KindEnum kind)
        {
            this.name = name;
            this.kind = kind;
        }

        /**
         * The string displayed for the returned SymbolData.
         *
         * @return - returns the SymbolData's name and kind.
         */
        @Override
        public String toString()
        {
            return  name + kind;
        }
    }

}

