package compiler;

import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception {

		/*
		 * Verify that user has provided input file
		 */
		if (args.length < 1) {
			System.err.println("Provide Input File");
			System.exit(1);
		}

		/*
		 * Input file (source code)
		 */
		String path = args[0];

		/*
		 * Retrieving parsing service
		 */
		parser myParser = parser.getParser(path);

		/*
		 * Retrieving the AST object-tree
		 */
		Node root = myParser.getTree();

		/*
		 * Extracting the input file name without extension
		 */
		int startPos = path.lastIndexOf(File.separator);
		int endPos = path.lastIndexOf(".");
		if (endPos <= startPos) {
			endPos = path.length();
		}
		String nameWithoutExtension = path.substring(0, endPos);

		/*
		 * Output AST contents (pre-order)
		 */
		String ASTFileName = nameWithoutExtension + ".ast.txt";
		PrintWriter ast_pw = new PrintWriter(ASTFileName);
		System.out.println("Generating Abstract Syntax Tree (AST) [" + ASTFileName + "]");
		System.out.println();
		root.print(ast_pw);
		ast_pw.close();
		
		
		/*
		 * Output MIXAL code
		 */
		String outputFileName = nameWithoutExtension + ".mixal";
		System.out.println("Generating MIXAL code [" + outputFileName + "]");
		System.out.println();
		PrintWriter out_pw = new PrintWriter(outputFileName);
		root.code(out_pw);
		out_pw.close();
		
		/*
		 * End of procedure
		 */
		System.out.println("End of procedure!");
	}
}
