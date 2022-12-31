package eutil.debug;

import eutil.strings.EStringBuilder;
import eutil.strings.EStringUtil;

/**
 * A slew of functions that are intended to aid in overall development
 * or debug efforts.
 * 
 * @author Hunter Bragg
 * @since 1.5
 */
public class DebugToolKit {
	
	private DebugToolKit() {}
	
	public static final String SPACER_NONE = "";
	public static final String SPACER_SPACE = " ";
	public static final String SPACER_COLON = ":";
	public static final String SPACER_SPACED_COLON = " : ";
	public static final String SPACER_COMMA = ", ";
	public static final String SPACER_PERIOD = ".";
	
	public static String printSpacer = SPACER_SPACED_COLON;
	
	/**
	 * Introduces support for {x} argument formatting in Strings.
	 * <p>
	 * EX: format("Hello {0}!", "World"); -> "Hello World!"
	 * 
	 * @param format The format string with argument nested insertions
	 * @param args 	 The list of object arguments to be used for
	 *             	 insertions
	 *             
	 * @return A string with nested arguments included
	 * @since 1.6.0
	 */
	public static String format(String format, Object... args) {
		return performFormat(format, args);
	}
	
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
	
	public static String debugPrint(Object... toPrint) {
		return debugPrintWithTitle(null, true, toPrint);
	}
	
	public static String debugPrint(boolean printToConsole, Object... toPrint) {
		return debugPrintWithTitle(null, printToConsole, toPrint);
	}
	
	public static String debugPrintWithTitle(String title, Object... toPrint) {
		return debugPrintWithTitle(title, true, toPrint);
	}
	
	public static String debugPrintWithTitle(String title, boolean printToConsole, Object... toPrint) {
		String converted = EStringUtil.combineAll(toPrint);
		boolean hasTitle = title != null && !title.isBlank();
		
		converted = converted.replace("\t", "    ");
		if (converted.endsWith("\n")) converted = converted.substring(0, converted.length() - 1);
		
		int longestConvertedLength = converted.length();
		if (converted.contains("\n")) {
			longestConvertedLength = EStringUtil.getLongestLength(converted.split("\n"));
		}
		
		int len = longestConvertedLength;
		if (hasTitle) {
			title = "| " + title + " |";
			len = Math.max(len, title.length());
		}
		
		String titleDashes = EStringUtil.repeatString("-", title.length());
		String dashes = EStringUtil.repeatString("-", len);
		
		var sb = new EStringBuilder();
		if (hasTitle) {
			sb.println(titleDashes);
			sb.println(title);
		}
		sb.println(dashes);
		sb.println();
		sb.println(converted);
		sb.println();
		sb.println(dashes);
		
		if (printToConsole) System.out.println(sb);
		
		return sb.toString();
	}
	
	//------------------------------------------------------------------------------
	
	private static String performFormat(String format, Object[] args) {
		StringBuilder out = new StringBuilder();
		StringBuilder argNumString = null;
		boolean inNum = false;
		
		for (int i = 0; i < format.length(); i++) {
			char c = format.charAt(i);
			
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
					out.append(EStringUtil.toString(args[argNum]));
					argNumString = null;
					inNum = false;
				}
				//if the character wasn't a digit or a '}' then cancel argument insertion
				else {
					out.append('{');
					out.append(argNumString.toString());
					
					if (c == '{') {
						//check if at end
						if (i == format.length() - 1) {
							out.append(c);
							break;
						}
						//check if empty argument
						if ((format.length() - 1 == i) || format.length() > i && format.charAt(i + 1) == '}') {
							out.append("{}");
							i++;
							continue;
						}
						
						argNumString = new StringBuilder();
						inNum = true;
					}
					else {
						out.append(c);
						argNumString = null;
						inNum = false;
					}
				}
			}
			//if '{' start trying to read an argument index number for insertion
			else if (c == '{') {
				//check if at end
				if (i == format.length() - 1) {
					out.append(c);
					break;
				}
				//check if empty argument
				if ((format.length() - 1 == i) || format.length() > i && format.charAt(i + 1) == '}') {
					out.append("{}");
					i++;
					continue;
				}
				
				argNumString = new StringBuilder();
				inNum = true;
			}
			//otherwise, just append the character onto the output
			else {
				out.append(c);
			}
		}
		
		return out.toString();
	}
	
	private static void performPrintln(boolean nl, String toPrint, Object[] args) {
		if (args.length == 0) {
			if (nl) System.out.println(toPrint);
			else System.out.print(toPrint);
			return;
		}
		
		String out = performFormat(toPrint, args);
		
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
			out.append(EStringUtil.toString(toPrint[i]));
			if (i < toPrint.length - 1) out.append(printSpacer);
		}
		
		if (nl) System.out.println(out);
		else System.out.print(out);
	}
	
}
