This folder contains a scanner project for a micro pascal grammar compiler. The scanner converts the input program into a sequence of tokens, reads the input characters, and produces a sequence of tokens for the recognizer. The recognizer is a recursive descent parser that checks to see if the input for a non-terminal method is a pascal program described by the micro pascal grammer. If it is, the grammar rule is executed. The symbol table is a hash map that will store information about the variable, program, array, function, or procedure identifiers found in the pascal program. The parser obtains a string of tokens from the scanner, checks to see if the string of token names can be generated by the micro pascal grammer, and constructs a syntax tree if it is a pascal program.

This project contains a README.md, twenty five java class files, two versions of the SDD, a jflex file, three pascal test files, and it will contain two input text files for testing.
