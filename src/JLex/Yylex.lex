package compiler;

import java_cup.runtime.*;
import java.io.FileInputStream;
import java.io.InputStream;
      
%%
%line
%cup

   
%{   

	public static void main(String args[]) throws Exception {
		InputStream is = new FileInputStream(args[0]);
		Yylex lexer = new Yylex(is);

		Symbol token = null;
		do {
			token = lexer.next_token();
			System.out.println(token == null ? "EOF" : token.toString());
		} while (token != null);
	}
	
    	
%}
   
identifier 	= [a-zA-Z]([a-zA-Z_]|[0-9])*
integer         = [0]|("-"?[1-9][0-9]*)
whitespace      = [ \n\t\f\r]*

   
%%
   

   

"PROGRAM"      	{ return new Symbol(sym.PROGRAM); }
"BEGIN"         { return new Symbol(sym.BEGIN); }
"END"           { return new Symbol(sym.END); }
"WRITE" 		{ return new Symbol(sym.WRITE); }
"READ"			{ return new Symbol(sym.READ); }
"IF"            { return new Symbol(sym.IF); }
"THEN"			{ return new Symbol(sym.THEN); }
"ELSE"          { return new Symbol(sym.ELSE); }
"ENDIF" 		{ return new Symbol(sym.ENDIF); }
"WHILE"			{ return new Symbol(sym.WHILE); }
"DO"			{ return new Symbol(sym.DO); }
"ENDDO"			{ return new Symbol(sym.ENDDO); }
"VAR"           { return new Symbol(sym.VAR); }
"INTEGER" 		{ return new Symbol(sym.INTEGER); }
"+"             { return new Symbol(sym.PLUS); }
"-"             { return new Symbol(sym.MINUS); }
"*"             { return new Symbol(sym.MULT); }
"/"             { return new Symbol(sym.DIV); }
">"             { return new Symbol(sym.GTR); }
"<="            { return new Symbol(sym.LESS_EQ); }
"="             { return new Symbol(sym.EQ); }
">="            { return new Symbol(sym.GTR_EQ); }
"!="            { return new Symbol(sym.NOT_EQ); }
"<"             { return new Symbol(sym.LESS); }
";"             { return new Symbol(sym.SEMI); }
"."             { return new Symbol(sym.DOT); }
":"             { return new Symbol(sym.COLON); }
":="            { return new Symbol(sym.ASSIGNMENT); }
"("             { return new Symbol(sym.LEFT_P); }
")"             { return new Symbol(sym.RIGHT_P); }
{identifier}    { return new Symbol(sym.ID, yytext()); }
{integer}       { return new Symbol(sym.INT, new Integer(Integer.parseInt(yytext()))); }
{whitespace}    { /* Ignore whitespace. */ }
.               { System.out.println("Illegal char: " + yytext() + "@ line: " + yyline); System.exit(1); }

