package parser;

import java.util.HashMap;


public class SymbolTable
{

    private HashMap<String, SymbolData> symbols = new HashMap<>();


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

    public void addProcedureName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData(name, KindEnum.PROCEDURE_NAME));
        }
    }

    public void addFunctionName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData(name, KindEnum.FUNCTION_NAME));
        }
    }

    public boolean isVarName(String name)
    {

        SymbolData s = symbols.get(name);
        if (s != null && s.kind == KindEnum.VAR_NAME)
        {
            return true;
        }

        return false;
    }

    public boolean isProcedureName(String name)
    {

        SymbolData s = symbols.get(name);
        if (s != null && s.kind == KindEnum.PROCEDURE_NAME)
        {
            return true;
        }

        return false;
    }
    public boolean isProgramName(String name)
    {
        SymbolData s = symbols.get(name);
        if (s != null && s.kind == KindEnum.PROGRAM_NAME)
        {
            return true;
        }

        return false;
    }

    public boolean isArrayName(String name)
    {
        SymbolData s = symbols.get(name);
        if (s != null && s.kind == KindEnum.ARRAY_NAME)
        {
            return true;
        }

        return false;
    }

    /**
     * Checks to see if the Symbol Table name exists and if it is a
     * function name.
     *
     * @param name - The Symbol Table name that is being checked.
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

