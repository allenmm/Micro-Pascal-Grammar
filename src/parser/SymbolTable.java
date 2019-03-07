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


    private class SymbolData
    {
        String name;
        KindEnum kind;

        SymbolData(String name, KindEnum kind)
        {
            this.name = name;
            this.kind = kind;
        }

    }

}

