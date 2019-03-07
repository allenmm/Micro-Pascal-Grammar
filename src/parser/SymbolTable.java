package parser;

import java.util.HashMap;


public class SymbolTable
{

    private HashMap<String, SymbolData> symbols = new HashMap<>();
    

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

