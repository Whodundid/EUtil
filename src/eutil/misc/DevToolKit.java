package eutil.misc;

import eutil.strings.StringUtil;

/**
 * A slew of functions that are intended to aid in overall development
 * or debug efforts.
 * 
 * @author Hunter Bragg
 * @since 1.5
 */
public class DevToolKit {
	
	public static String printSpacer = " : ";
	
	/**
	 * Introduces support for {x} argument insertion in print statements.
	 * <p>
	 * EX: printf("Hello {0}!", "World");
	 * 
	 * @param toPrint The base string with argument insertions nested
	 * @param args    The list of object arguments to be used for
	 *                insertions
	 */
	public static void printf(String toPrint, Object... args) {
		performPrintln(false, toPrint, args);
	}
	
	/**
	 * Introduces support for {x} argument insertion in println
	 * statements.
	 * <p>
	 * EX: printlnf("Hello {0}!", "World");
	 * 
	 * @param toPrint The base string with argument insertions nested
	 * @param args    The list of object arguments to be used for
	 *                insertions
	 */
	public static void printlnf(String toPrint, Object... args) {
		performPrintln(true, toPrint, args);
	}
	
	/**
	 * Prints each argument onto the same line with a space in between.
	 * 
	 * @param toPrint The objects to be printed
	 */
	public static void print(Object... toPrint) {
		performPrintln(false, toPrint);
	}
	
	/**
	 * Prints each argument onto the same line with a space in between and
	 * then adds a new line afterwards.
	 * 
	 * @param toPrint The objects to be printed
	 */
	public static void println(Object... toPrint) {
		performPrintln(true, toPrint);
	}
	
	//------------------------------------------------------------------------------
	
	private static void performPrintln(boolean nl, String toPrint, Object[] args) {
		if (args.length == 0) {
			if (nl) System.out.println(toPrint);
			else System.out.print(toPrint);
			return;
		}
		
		StringBuilder out = new StringBuilder();
		StringBuilder argNumString = null;
		boolean inNum = false;
		
		for (int i = 0; i < toPrint.length(); i++) {
			char c = toPrint.charAt(i);
			
			if (inNum) {
				//if digit, continue to add to argNumString
				if (Character.isDigit(c)) argNumString.append(c);
				//if '}' is found convert argNum to int and insert arg value
				else if (c == '}') {
					int argNum = Integer.parseInt(argNumString.toString());
					//if argNum is out of arg length -- error out
					if (argNum >= args.length) {
						throw new IndexOutOfBoundsException("Bad arg index! {"+argNum+"} is out of range: [0,"+(args.length-1)+"]");
					}
					out.append(StringUtil.toString(args[argNum]));
					argNumString = null;
					inNum = false;
				}
				//if the character wasn't a digit or a '}' then cancel argument insertion
				else {
					out.append(argNumString.toString());
					argNumString = null;
					inNum = false;
				}
			}
			//if '{' start trying to read an argument index number for insertion
			else if (c == '{') {
				//check if at end
				if (i == toPrint.length() - 1) {
					out.append(c);
					break;
				}
				//check if empty argument
				if ((toPrint.length() - 1 == i) || toPrint.length() > i && toPrint.charAt(i + 1) == '}') {
					out.append("{}");
					i++;
					continue;
				}
				
				argNumString = new StringBuilder();
				inNum = true;
			}
		}
		
		if (nl) System.out.println(out);
		else System.out.print(out);
	}
	
	private static void performPrintln(boolean nl, Object[] toPrint) {
		if (toPrint.length == 0) {
			if (nl) System.out.println();
			return;
		}
		
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < toPrint.length; i++) {
			out.append(StringUtil.toString(toPrint[i]));
			if (i < toPrint.length - 1) out.append(printSpacer);
		}
		
		if (nl) System.out.println(out);
		else System.out.print(out);
	}
	
}
