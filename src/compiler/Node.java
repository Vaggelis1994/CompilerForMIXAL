package compiler;

import java.io.PrintWriter;
import java.util.HashMap;


class VarDetails {

	private int address; //used for the current address of the command -provided by the PC

	private int size; //the size of each used variable in bytes

	private int value; //the value of each used variable

	public VarDetails() {} //empty constructor

	/**
	 * Function used for setting the current address of the command
	 * -provided by PC.
	 * @param _address
	 */
	public void setAddress(int _address) {
		address = _address;
	}

	/**
	 * Function used for retrieving the address of a specific command
	 * -provided by PC.
	 * @return address
	 */
	public int getAddress() {
		return address;
	}

	/**
	 * Function used for setting the size of a specific variable
	 * @param _size
	 */
	public void setSize(int _size) {
		size = _size;
	}

	/**
	 * Function used for retrieving the size of a specific variable
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Function used for setting the value of a variable
	 * @param _value
	 */
	public void setValue(int _value) {
		value = _value;
	}
	
	/**
	 * Function used for retrieving the value of a specific variable
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	
	
}



public abstract class Node implements Cloneable{

	public static int spaces = 0;//represents the number of spaces for AST
	
	public static int varCounter = 0; //represents the counter of expressions 
	
	public static int pc = 2000; //represents the location counter -assuming that the origin is at address 2000
	
	//used for setting the flags of while statements
	public static String startW = null;
	public static String endW = null;
	public static boolean whileS = false;
	
	//used for setting the flags of if statements
	public static String startIf = null;
	public static String startElse = null;
	public static String endIf = null;
	public static boolean ifS = false;
	
	
	public static HashMap<String, VarDetails> varTable;//contains ALL the used variables
	//initialized with keywords that must not be used such as MIXAL directives and others
	static
	{
		varTable = new HashMap<String, VarDetails>();
		
		VarDetails vd = new VarDetails();

		//MIXAL instructions
		varTable.put("START", vd);
		varTable.put("END", vd);
		varTable.put("ORIG", vd);
		varTable.put("LDA", vd);
		varTable.put("LDX", vd);
		varTable.put("LD1", vd);
		varTable.put("LD2", vd);
		varTable.put("LD3", vd);
		varTable.put("LD4", vd);
		varTable.put("LD5", vd);
		varTable.put("LD6", vd);
		varTable.put("LDAN", vd);
		varTable.put("LDXN", vd);
		varTable.put("LD1N", vd);
		varTable.put("LD2N", vd);
		varTable.put("LD3N", vd);
		varTable.put("LD4N", vd);
		varTable.put("LD5N", vd);
		varTable.put("LD6N", vd);
		varTable.put("STA", vd);
		varTable.put("STX", vd);
		varTable.put("ST1", vd);
		varTable.put("ST2", vd);
		varTable.put("ST3", vd);
		varTable.put("ST4", vd);
		varTable.put("ST5", vd);
		varTable.put("ST6", vd);
		varTable.put("STJ", vd);
		varTable.put("STZ", vd);
		varTable.put("ADD", vd);
		varTable.put("SUB", vd);
		varTable.put("MUL", vd);
		varTable.put("DIV", vd);
		varTable.put("ENTA", vd);
		varTable.put("ENTX", vd);
		varTable.put("ENT1", vd);
		varTable.put("ENT2", vd);
		varTable.put("ENT3", vd);
		varTable.put("ENT4", vd);
		varTable.put("ENT5", vd);
		varTable.put("ENT6", vd);
		varTable.put("ENNA", vd);
		varTable.put("ENNX", vd);
		varTable.put("ENN1", vd);
		varTable.put("ENN2", vd);
		varTable.put("ENN3", vd);
		varTable.put("ENN4", vd);
		varTable.put("ENN5", vd);
		varTable.put("ENN6", vd);
		varTable.put("INCA", vd);
		varTable.put("INCX", vd);
		varTable.put("INC1", vd);
		varTable.put("INC2", vd);
		varTable.put("INC3", vd);
		varTable.put("INC4", vd);
		varTable.put("INC5", vd);
		varTable.put("INC6", vd);
		varTable.put("DECA", vd);
		varTable.put("DECX", vd);
		varTable.put("DEC1", vd);
		varTable.put("DEC2", vd);
		varTable.put("DEC3", vd);
		varTable.put("DEC4", vd);
		varTable.put("DEC5", vd);
		varTable.put("DEC6", vd);
		varTable.put("CMPA", vd);
		varTable.put("CMPX", vd);
		varTable.put("CMP1", vd);
		varTable.put("CMP2", vd);
		varTable.put("CMP3", vd);
		varTable.put("CMP4", vd);
		varTable.put("CMP5", vd);
		varTable.put("CMP6", vd);
		varTable.put("JMP", vd);
		varTable.put("JSJ", vd);
		varTable.put("JOV", vd);
		varTable.put("JNOV", vd);
		varTable.put("JL", vd);
		varTable.put("JE", vd);
		varTable.put("JG", vd);
		varTable.put("JNL", vd);
		varTable.put("JNE", vd);
		varTable.put("JNG", vd);
		varTable.put("JAN", vd);
		varTable.put("JAZ", vd);
		varTable.put("JAP", vd);
		varTable.put("JANN", vd);
		varTable.put("JANZ", vd);
		varTable.put("JANP", vd);
		varTable.put("JXN", vd);
		varTable.put("JXZ", vd);
		varTable.put("JXP", vd);
		varTable.put("JXNN", vd);
		varTable.put("JXNZ", vd);
		varTable.put("JXNP", vd);
		varTable.put("J1N", vd);
		varTable.put("J2N", vd);
		varTable.put("J3N", vd);
		varTable.put("J4N", vd);
		varTable.put("J5N", vd);
		varTable.put("J6N", vd);
		varTable.put("J1Z", vd);
		varTable.put("J2Z", vd);
		varTable.put("J3Z", vd);
		varTable.put("J4Z", vd);
		varTable.put("J5Z", vd);
		varTable.put("J6Z", vd);
		varTable.put("J1P", vd);
		varTable.put("J2P", vd);
		varTable.put("J3P", vd);
		varTable.put("J4P", vd);
		varTable.put("J5P", vd);
		varTable.put("J6P", vd);
		varTable.put("J1NN", vd);
		varTable.put("J2NN", vd);
		varTable.put("J3NN", vd);
		varTable.put("J4NN", vd);
		varTable.put("J5NN", vd);
		varTable.put("J6NN", vd);	
		varTable.put("J1NZ", vd);
		varTable.put("J2NZ", vd);
		varTable.put("J3NZ", vd);
		varTable.put("J4NZ", vd);
		varTable.put("J5NZ", vd);
		varTable.put("J6NZ", vd);
		varTable.put("J1NP", vd);
		varTable.put("J2NP", vd);
		varTable.put("J3NP", vd);
		varTable.put("J4NP", vd);
		varTable.put("J5NP", vd);
		varTable.put("J6NP", vd);
		varTable.put("MOVE", vd);
		varTable.put("SLA", vd);
		varTable.put("SRA", vd);
		varTable.put("SLAX", vd);
		varTable.put("SRAX", vd);
		varTable.put("SLC", vd);
		varTable.put("SRC", vd);
		varTable.put("NOP", vd);
		varTable.put("HLT", vd);
		varTable.put("IN", vd);
		varTable.put("OUT", vd);
		varTable.put("JRED", vd);
		varTable.put("JBUS", vd);
		varTable.put("NUM", vd);
		varTable.put("CHAR", vd);
		
		//used for assistance
		varTable.put("BUFI", vd);
		varTable.put("BUFO", vd);
		varTable.put("IO", vd);
		varTable.put("LP", vd);
		varTable.put("ENDLP", vd);
		varTable.put("IF", vd);
		varTable.put("EL", vd);
		

	}
	
	
	
	public Node() {} //empty constructor

	public abstract void print(PrintWriter pw);

	public abstract void code(PrintWriter pw);

	
	
}


class Program extends Node {

	private String id = null; //used as the program identifier
	
	private Node declList = null; //the list of declarations	
	
	private Node block = null; //the block for execution
	
	/**
	 * Constructor of Program.
	 * @param _id 
	 * @param _declList
	 * @param _block
	 */
	public Program(String _id, Node _declList,  Node _block) {
		id = _id;
		declList = _declList;
		block = _block;
	}

	@Override
	public void code(PrintWriter pw) {
		
		//setting the buffer for input
		pw.println("BUFI" + "\t" + "EQU" + "\t" + "1000");
		pc+=1;
		
		//setting the buffer for output
		pw.println("BUFO" + "\t" + "EQU" + "\t" + "1500");
		pc+=1;
		
		//setting the I/O device -> Terminal
		pw.println("IO" + "\t" + "EQU" + "\t" + "19");
		pc+=1;
		
		//initializing the new program  
		pw.println(id + "\t" + "EQU" + "\t" + "2000");
		pw.println("\t" + "ORIG" + "\t" + id);
		pc+=2;
		
		declList.code(pw);		
		block.code(pw);
		
		//terminating program
		pw.println("\t" + "END" + "\t" + id);
		pc+=1;
	}


	@Override
	public void print(PrintWriter pw) {
		
		
		pw.print("<program> ");
		pw.print("[PROGRAM: " + id + "]");
		pw.println();
		
		//First child
		spaces += 12;
		pw.print("|---------- ");
		if (declList != null) {
			pw.print("<decl_list>");
			pw.println();
			declList.print(pw);
		}
		spaces -= 12;
		
		//Second child
		spaces += 12;
		pw.print("|---------- ");
		if (block != null) {
			pw.print("<block> ");
			pw.print("[BLOCK]");
			pw.println();
			block.print(pw);
		}
		spaces -= 12;

		pw.print("[END: " + id + "]");
		pw.println();
		
		
	}

}

class DeclList extends Node {

	private Node decl = null; //declaration (one)
	
	private Node declList = null; //list of declarations (more than one)

	/**
	 * Constructor of the declaration list. Initializes both decl and declList to null
	 */
	public DeclList () {
		decl = null;
		declList = null;
	}

	/**
	 * Constructor of the DeclList.
	 * @param _declList
	 * @param _decl
	 */
	public DeclList (Node _declList, Node _decl) {
		decl = _decl;
		declList = _declList;
	}

	@Override
	public void code(PrintWriter pw) {
		
		if (declList != null) {
			declList.code(pw);
		}
		
		if (decl!=null) {
			decl.code(pw);
		}
	}

	
	@Override
	public void print(PrintWriter pw) {
		
		if (declList != null) {
			declList.print(pw);
		}
		
		if (decl!=null) {
			for (int i=0; i<spaces; i++) pw.print(" ");
			pw.print("|---------- ");
			pw.print("<declaration> ");
			decl.print(pw);
		}
		
	}

}

class Declaration extends Node {

	
	private String id; //the identifier of the variable provided to the declaration

	/**
	 * Constructor of Declaration. Initializes a new variable with the given identifier to 0. 
	 * Also, the variable is being saved to the vd HashMap.
	 * @param _id
	 */
	public Declaration(String  _id) {
		id = _id;
		VarDetails vd = new VarDetails();
		vd.setValue(0);
		vd.setAddress(pc);
		varTable.put(id, vd);
		
	}

	@Override
	public void print(PrintWriter pw) {
		pw.println("["+ id +":INTEGER]");
	}

	@Override
	public void code(PrintWriter pw) {
		
		//declaring the variables
		pw.println("\t" + "CON" + "\t" + "0");
		pw.println(id + "\t" + "EQU" + "\t" + "*-1");
		pc+=2;
	}

}

class Block extends Node {

	private Node statSeq = null; //the sequence of statements

	/**
	 * Constructor of Block
	 * @param _statSeq
	 */
	public Block(Node _statSeq) {
		statSeq = _statSeq;
	}

	@Override
	public void code(PrintWriter pw) {
		
		if (statSeq != null) {
			statSeq.code(pw);
		}
	}
	
	@Override
	public void print(PrintWriter pw) {
		
		if (statSeq != null) {
			for (int i=0; i<spaces; i++) pw.print(" ");
			pw.print("|---------- ");
			pw.print("<stat_seq>");
			pw.println();
			spaces += 12;
			statSeq.print(pw);
			spaces -= 12;
		}
	}
}

class StatSeq extends Node {

	private Node stat = null; //statement (one)
	
	private Node statSeq = null; //sequence of statements (more than one)
	
	/**
	 * Constructor of StatSeq. Initializes stat and statSeq to null.
	 */
	public StatSeq() {
		stat = null;
		statSeq = null;
	}

	/**
	 * Constructor of StatSeq
	 * @param _statSeq
	 * @param _stat
	 */
	public StatSeq(Node _statSeq, Node _stat) {
		statSeq = _statSeq;
		stat = _stat;
	}

	@Override
	public void code(PrintWriter pw) {
		
		if (statSeq != null) {
			statSeq.code(pw);
		}
		
		if (stat!=null) {
			stat.code(pw);
		}
	}

	@Override
	public void print(PrintWriter pw) {
		
		if (statSeq != null) {
			statSeq.print(pw);
		}
		
		if (stat!=null) { 
			for (int i=0; i<spaces; i++) pw.print(" ");
			pw.print("|---------- ");
			pw.print("<statement> ");
			stat.print(pw);
			pw.println();
		}
	}



}

class Statement extends Node {

	@Override
	public void print(PrintWriter pw) {}

	@Override
	public void code(PrintWriter pw) {}

}



class Assignment extends Statement {

	private String id = null; //the identifier of the assignment

	private Node exp = null; //the expression of the statement

	/**
	 * Constructor of the Assignment.
	 * If a variable name can not be used, there is an exception thrown. 
	 * @param _id
	 * @param _exp
	 * @throws java.lang.Exception
	 */
	public Assignment(String _id, Node _exp)  throws java.lang.Exception {
		
		id = _id;
		exp =  _exp;
		if (!varTable.containsKey(id)) {
			throw new Exception(id + " is not declared!");
		}
	}

	@Override
	public void print(PrintWriter pw) {
		pw.print("[" + id + " := ");
		exp.print(pw);
		pw.print("]");
	}

	@Override
	public void code(PrintWriter pw) {
		if(exp.getClass().toString().equals("class compiler.IDType") || exp.getClass().toString().equals("class compiler.INTConst")) {
			pw.print("\t" + "LDA" + "\t");
			exp.code(pw);
		}
		else {
			exp.code(pw);
		}
		pw.println("\t" + "STA" + "\t" + id);
		pc+=1;
		
	}

}


class Write extends Statement {

	private Node exp = null; //the expression of the statement

	/**
	 * Constructor of Write.
	 * @param _exp
	 */
	public Write (Node _exp) {
		exp = _exp;
	}

	@Override
	public void print(PrintWriter pw) {
		pw.print("[WRITE ");
		exp.print(pw);
		pw.print("]");
	}

	@Override
	public void code(PrintWriter pw) {
	
		if(exp.getClass().toString().equals("class compiler.IDType") || exp.getClass().toString().equals("class compiler.INTConst")) {
			pw.print("\t" + "LDA" + "\t");
			exp.code(pw);
		}
		else{
			exp.code(pw);
		}
	
		//converting to printable format
		pw.println("\t" + "CHAR");
		pc+=1;
		
		//putting the block to output memory block
		pw.println("\t" + "STX" + "\t" + "BUFO");
		pc+=1;
		
		//printing the block to the device
		pw.println("\t" + "OUT" + "\t" + "BUFO(IO)");
		pc+=1;
		
		//wait until the block is printed
		pw.println("\t" + "JBUS" + "\t" + "*(IO)");
		pc+=1;
		
		
		
	}

}

class Read extends Statement {
	
	private String id = null; //the identifier of the statement

	/**
	 * Constructor of Read
	 * If a variable has not been declared, there is an exception thrown.
	 * @param _id
	 * @throws java.lang.Exception
	 */
	public Read (String _id) throws java.lang.Exception {
		id = _id;
		if (!varTable.containsKey(id)) {
			throw new Exception ("Variable is not declared!");
		}
	}

	@Override
	public void print(PrintWriter pw) {
		pw.print("[READ " + id + "]");
	}

	@Override
	public void code(PrintWriter pw) {
		
		//inserting a new value for the id
		pw.println("\t" + "IN" + "\t" + "BUFI(IO)");
		
		//wait until the block is imported
		pw.println("\t" + "JBUS" + "\t" + "*(IO)");
		
		//setting the number to the register
		pw.println("\t" + "LDX" + "\t" + "BUFI");
		
		//converting to number for manipulation
		pw.println("\t" + "NUM");
		
		//setting the number from the register to the variable
		pw.println("\t" + "STA" + "\t" + id);
		
	}
}

class If extends Statement {

	private Node exp = null; //the expression -check condition- of the statement
	
	private Node statSeq1 = null; //the statement sequence that is executed if the expression is true
	
	private Node statSeq2 = null; //the statement sequence that is executed if the expression is false
	
	//represents the number of if statements
	private int counter = 0;
	
	/**
	 * Constructor of If
	 * @param _exp
	 * @param _statSeq1
	 * @param _statSeq2
	 */
	public If (Node _exp, Node _statSeq1, Node _statSeq2) {
		exp = _exp;
		statSeq1 = _statSeq1;
		statSeq2 = _statSeq2;
		
	}


	@Override
	public void print(PrintWriter pw) {
		
		pw.println();
		for (int i=0; i<spaces; i++) pw.print(" ");
		pw.print("[IF]");
		pw.println();
		for (int i=0; i<spaces; i++) pw.print(" ");
		pw.print("|---------- ");
		exp.print(pw);
		
		pw.println();
		for (int i=0; i<spaces; i++) pw.print(" ");
		pw.print("[THEN]");
		pw.println();
		if (statSeq1 != null) {
			statSeq1.print(pw);
		}
		
		for (int i=0; i<spaces; i++) pw.print(" ");
		pw.print("[ELSE]");
		pw.println();
		if (statSeq2 != null) {
			statSeq2.print(pw);
		}
		for (int i=0; i<spaces; i++) pw.print(" ");
		
		pw.print("[ENDIF]");
	}

	@Override
	public void code(PrintWriter pw) {
		
		ifS = true;
		
		startIf = "IF" + counter; //used as a flag for true outcome of the expression
		
		startElse = "EL" + counter; //used as a flag for the false outcome of the expression
		
		endIf = "EF" + counter; //used as a flag for the end of the if-statement
		
		if(exp.getClass().toString().equals("class compiler.IDType") || exp.getClass().toString().equals("class compiler.INTConst")) {
			pw.print("\t" + "LDA" + "\t");
			exp.code(pw);
		}
		else {
			exp.code(pw);
		}
		
		pw.print(startIf);
		statSeq1.code(pw);
		
		//jump to the end flag
		pw.println("\t" + "JMP" + "\t" + endIf);
		
		
		pw.print(startElse);
		statSeq2.code(pw);
		
		//jump to the end flag
		pw.println("\t" + "JMP" + "\t" + endIf);
		
		pw.print(endIf);
		
		counter+=1;
		
		ifS = false;
	}

}


class While extends Statement {
	
	private Node exp = null; //the expression of the statement -checking condition.
	
	private Node statSeq = null; //the statement sequence of the statement -loop for execution.

	//represents the number of while statements
	private int counter = 0;
	
	public While(Node _exp, Node _statSeq) {
		
		exp = _exp;
		statSeq = _statSeq;
	}

	@Override
	public void print(PrintWriter pw) {
		
		spaces += 12;
		pw.print("[WHILE]");
		pw.println();
		
		for (int i=0; i<spaces; i++) pw.print(" ");
		pw.print("|---------- ");
		pw.print("<expression> ");
		exp.print(pw);
		pw.println();
		
		for (int i=0; i<spaces; i++) pw.print(" ");
		pw.print("[DO]");
		pw.println();
		if (statSeq != null) {
			statSeq.print(pw);
		}
		
		for (int i=0; i<spaces; i++) pw.print(" ");
		pw.print("[ENDDO]");
		spaces -= 12;
	}

	@Override
	public void code(PrintWriter pw) {
		
		whileS = true;
		
		startW = "LOOP" + counter; //used as a flag for the beginning of the loop
		
		endW = "ENDLP" + counter; //used as a flag for the end of the loop

		if(exp.getClass().toString().equals("class compiler.IDType") || exp.getClass().toString().equals("class compiler.INTConst")) {
			pw.print("\t" + "LDA" + "\t");
			exp.code(pw);
		}
		else {
			exp.code(pw);
		}
		
		pw.print(startW);
		statSeq.code(pw);
		
		whileS = true;
		
		if(exp.getClass().toString().equals("class compiler.IDType") || exp.getClass().toString().equals("class compiler.INTConst")) {
			pw.print("\t" + "LDA" + "\t");
			exp.code(pw);
		}
		else {
			exp.code(pw);
		}
		pw.print(endW);

		counter+=1;

		whileS = false;

	}
}


class Expression extends Node {

	private int operator = -1; //the operator used for the expression 
	
	private boolean unary = false; //the condition of an unary expression (otherwise, is a binary)
	
	private Node exp1 = null; //the first expression of the expression
	
	private Node exp2 = null; //the second -optional- expression of the expression
	
	/**
	 * Constructor of the (unary) Expression
	 * @param operator
	 * @param _exp
	 */
	public Expression(int operator, Node _exp) {
		exp1 = _exp;
		unary = true;
	}
	
	
	/**
	 * Constructor of the (binary) Expression
	 * @param _exp1
	 * @param _operator
	 * @param _exp2
	 */
	public Expression(Node _exp1, int _operator, Node _exp2) {
		exp1 =_exp1;
		operator = _operator;
		exp2 = _exp2;	
	}

	
	
	@Override
	public void code(PrintWriter pw) {
		if (unary) {
			if (exp1!=null) {
				if(exp1.getClass().toString().equals("class compiler.IDType") || exp1.getClass().toString().equals("class compiler.INTConst")) {
					
					//if the expression is unary, load the negative value of the number
					pw.print("\t" + "LDAN" + "\t");
					exp1.code(pw);
				}
				else {
					exp1.code(pw);
				}
			}
			unary = false;
		} 
		else {
			if (exp1!=null) {
				if(exp1.getClass().toString().equals("class compiler.IDType") || exp1.getClass().toString().equals("class compiler.INTConst")) {
					
					//if the expression is not unary, load the positive value of the number
					pw.print("\t" + "LDA" + "\t");
					exp1.code(pw);
				}
				else {
					exp1.code(pw);
				}
			}
			
			switch (operator) {
				
				case sym.PLUS:
					
					//used for number addition. Symbol used is: '+'
					pw.print("\t" + "ADD" + "\t");
					break;
				
				case sym.MINUS:
					
					//used for number subtraction. Symbol used is: '-'
					pw.print("\t" + "SUB" + "\t");
					break;
				
				case sym.MULT:
					
					//used for number multiplication. Symbol used is: '*'
					pw.print("\t" + "MUL" + "\t");
					break;
				
				case sym.DIV:
					
					//used for number (integer) division. Symbol used is: '/'
					pw.print("\t" + "DIV" + "\t");
					break;
				
				case sym.GTR:
					
					//used for number (greater) comparison. Symbol used is: '>'
					pw.print("\t" + "CMPA" + "\t" );
					break;
				
				case sym.LESS:
					
					//used for number (less) comparison. Symbol used is: '<'
					pw.print("\t" + "CMPA" + "\t" );
					break;				
				
				case sym.GTR_EQ:
					
					//used for number (greater or equal) comparison. Symbol used is: '>='
					pw.print("\t" + "CMPA" + "\t" );
					break;				
				
				case sym.LESS_EQ:
					
					//used for number (less or equal) comparison. Symbol used is: '<='
					pw.print("\t" + "CMPA" + "\t" );
					break;
					
				case sym.EQ:
					
					//used for number (equal) comparison. Symbol used is: '='
					pw.print("\t" + "CMPA" + "\t" );
					break;
					
				case sym.NOT_EQ:
					
					//used for number (not equal) comparison. Symbol used is: '!='
					pw.print("\t" + "CMPA" + "\t" );
					break;					
				
				default :
					break;
				}
		
			if (exp2!=null) { 
				
				exp2.code(pw);
				
				//If a second expression exists, means that the expression is binary.
				//Also, it is checked whether it is used for a while-statement or an if-statement or neither of them
				//In any of the above cases, there are flags used in addition to jump instructions.
				
				switch (operator) {
				
					case sym.GTR:
						
						if (whileS) {
							pw.println("\t" + "JG" + "\t" + startW);
							pw.println("\t" + "JLE" + "\t" + endW);
							whileS = false;
							pc+=2;
						}
						
						if (ifS) {
							pw.println("\t" + "JG" + "\t" + startIf);
							pw.println("\t" + "JLE" + "\t" + startElse);
							ifS = false;
							pc+=2;
						}
						
						break;
						
					case sym.LESS:
						
						if (whileS) {
							pw.println("\t" + "JL" + "\t" + startW);
							pw.println("\t" + "JGE" + "\t" + endW);
							whileS = false;
							pc+=2;
						}
						
						if (ifS) {
							pw.println("\t" + "JL" + "\t" + startIf);
							pw.println("\t" + "JGE" + "\t" + startElse);
							ifS = false;
							pc+=2;
						}
						
						break;
						
					case sym.GTR_EQ:
						
						if (whileS) {
							pw.println("\t" + "JGE" + "\t" + startW);
							pw.println("\t" + "JL" + "\t" + endW);
							whileS = false;
							pc+=2;
						}
						
						if (ifS) {
							pw.println("\t" + "JGE" + "\t" + startIf);
							pw.println("\t" + "JL" + "\t" + startElse);
							ifS = false;
							pc+=2;
						}
						
						break;
						
					case sym.LESS_EQ:
						
						if (whileS) {
							pw.println("\t" + "JLE" + "\t" + startW);
							pw.println("\t" + "JG" + "\t" + endW);
							whileS = false;
							pc+=2;
						}
						
						if (ifS) {
							pw.println("\t" + "JLE" + "\t" + startIf);
							pw.println("\t" + "JG" + "\t" + startElse);
							ifS = false;
							pc+=2;
						}
						
						break;
						
					case sym.EQ:
						
						if (whileS) {
							pw.println("\t" + "JE" + "\t" + startW);
							pw.println("\t" + "JNE" + "\t" + endW);
							whileS = false;
							pc+=2;
						}
						
						if (ifS) {
							pw.println("\t" + "JE" + "\t" + startIf);
							pw.println("\t" + "JNE" + "\t" + startElse);
							ifS = false;
							pc+=2;
						}
						
						break;
					
					case sym.NOT_EQ:
						
						if (whileS) {
							pw.println("\t" + "JNE" + "\t" + startW);
							pw.println("\t" + "JE" + "\t" + endW);
							whileS = false;
							pc+=2;
						}
						
						if (ifS) {
							pw.println("\t" + "JNE" + "\t" + startIf);
							pw.println("\t" + "JE" + "\t" + startElse);
							ifS = false;
							pc+=2;
						}
						
						break;
					
					default:
						break;
						
				}
				
				
			}
		}

	}


	@Override
	public void print(PrintWriter pw) {
		
		if (unary) {
			pw.print("-");
			if (exp1!=null) exp1.print(pw);
		} 
		else {
			if (exp1!=null) {
				pw.print("[");
				exp1.print(pw);
			}
			switch (operator) {
				case sym.PLUS:
					pw.print("+");
					break;
				case sym.MINUS:
					pw.print("-");
					break;
				case sym.MULT:
					pw.print("*");
					break;
				case sym.DIV:
					pw.print("/");
					break;
				case sym.GTR:
					pw.print(">");
					break;
				case sym.LESS:
					pw.print("<");
					break;
				case sym.GTR_EQ:
					pw.print(">=");
					break;
				case sym.LESS_EQ:
					pw.print("<=");
					break;
				case sym.EQ:
					pw.print("=");
					break;
				case sym.NOT_EQ:
					pw.print("!=");
					break;
				default :
					break;
			}
			if (exp2!=null) { 
				exp2.print(pw);
				pw.print("]");
			}
		}
	}

}

class IDType extends Node {

	private String id; //the identifier of the (variable) expression 
	
	/**
	 * Constructor of IDType
	 * @param _id
	 */
	public IDType(String _id) {
		id = _id;
	}
	
	public void print(PrintWriter pw) {
		pw.print(id);
	}

	@Override
	public void code(PrintWriter pw) {
		pw.print(id);
		pw.println();
		pc+=1;
	}
}

class INTConst extends Node {

	private int number; //the number of the (variable) expression
	
	/**
	 * Constructor of INTConst
	 * @param _number
	 */
	public INTConst (int _number) {
		number = _number;
	}
	
	public void print(PrintWriter pw) {
		pw.print(number);
	}

	@Override
	public void code(PrintWriter pw) {
		
		//For every used number without a previous assignment declaration, there is a temporary variable 
		//created with name VXXXX (where XXXX stands for the number of the assignment)
		//Afterwards, the variable is saved to the vd HashMap and also initialized with the specific value.
		
		VarDetails vd = new VarDetails();
		vd.setAddress(pc+2);
		vd.setValue(number);
		
		varTable.put("V"+varCounter, vd);
		
		pw.print("V" + varCounter);
		pw.println();

		pw.println("\t" + "CON" + "\t" + number);
		pw.println("V" + varCounter + "\t" + "EQU" + "\t" + "*-1");
		varCounter+=1; 
		pc+=3;
	}
}
