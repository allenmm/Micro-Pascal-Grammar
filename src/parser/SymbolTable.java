package parser;

import java.util.HashMap;


public class SymbolTable
{

    private HashMap<String, SymbolData> symbols = new HashMap<>();

    /**
     * Adds a variable identifier to the Symbol Table.
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

    public void addProgramName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData(name, KindEnum.PROGRAM_NAME));
        }
    }

    public void addArrayName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData(name, KindEnum.ARRAY_NAME));
        }
    }

    /**
     * Adds a procedure identifier to the Symbol Table.
     *
     * @param name
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
     * Adds a function identifier to the Symbol Table.
     *
     * @param name
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
     *
     */
    private class SymbolData
    {
        private String name;
        private KindEnum kind;

        SymbolData(String name, KindEnum kind)
        {
            this.name = name;
            this.kind = kind;
        }

    }

}

