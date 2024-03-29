package eutil.sys;

import java.io.PrintStream;

/**
 * PrintStream which redirects it's output to a given logger. Modified by
 * Hunter Bragg.
 *
 * @author Arkan
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class TracingPrintStream extends PrintStream {

	private int BASE_DEPTH = 4;
	private static PrintStream defaultOut = System.out;
	private static boolean tracing = false;
	private static boolean tracePrimitives = true;
	private static boolean traceEmptyLines = false;
	
	public TracingPrintStream(PrintStream original) {
		super(original);
	}

	@Override
	public void println() {
		if (traceEmptyLines) super.println(getPrefix(""));
		else super.println();
	}
	
	@Override public void println(Object x) { super.println(getPrefix(x)); }
	@Override public void println(boolean x) { super.println((tracePrimitives) ? getPrefix(x) : x); }
	@Override public void println(char x) { super.println((tracePrimitives) ? getPrefix(x) : x); }
	@Override public void println(int x) { super.println((tracePrimitives) ? getPrefix(x) : x); }
	@Override public void println(long x) { super.println((tracePrimitives) ? getPrefix(x) : x); }
	@Override public void println(float x) { super.println((tracePrimitives) ? getPrefix(x) : x); }
	@Override public void println(double x) { super.println((tracePrimitives) ? getPrefix(x) : x); }
	//@Override public void println(char[] x) { super.println(getPrefix(x)); }
	@Override public void println(String x) {
		if (x == null || x.isEmpty() && !traceEmptyLines) super.println();
		else super.println(getPrefix(x));
	}
	
	@Override
	public PrintStream printf(String format, Object... args) {
		return format(getPrefix() + format, args);
	}

	private String getPrefix() { return getPrefix(null, false); }
	private String getPrefix(Object x) { return getPrefix(x, true); }
	private String getPrefix(Object x, boolean print) {
		StackTraceElement[] elems = Thread.currentThread().getStackTrace();

		// The caller is always at BASE_DEPTH, including this call.
		StackTraceElement elem = elems[BASE_DEPTH];

		// Kotlins IoPackage masks origins 2 deeper in the stack.
		if (elem.getClassName().startsWith("kotlin.io.")) elem = elems[BASE_DEPTH + 2];
		else if (elem.getClassName().startsWith("java.lang.Throwable")) elem = elems[BASE_DEPTH + 4];
		
		return "[" + elem.getClassName() + ":" + elem.getMethodName() + ":" + elem.getLineNumber() + "]: " + ((print) ? x : "");
	}

	//----------------
	// Static Methods
	//----------------
	
	public static boolean isTracingPrimitives() { return tracePrimitives; }
	public static boolean tracesEmptyLines() { return traceEmptyLines; }
	
	public static void setTracePrimitives(boolean val) { tracePrimitives = val; }
	public static void enableTracingEmptyLines(boolean val) { traceEmptyLines = val; }
	
	public static boolean isTracing() { return tracing; }
	public static void setTracing(boolean in) {
		if (in) enableTrace();
		else disableTrace();
	}
	
	public static void enableTrace() {
		if (!tracing) {
			System.setOut(new TracingPrintStream(defaultOut));
			tracing = true;
		}
	}
	
	public static void disableTrace() {
		if (tracing) {
			System.setOut(defaultOut);
			tracing = false;
		}
	}
	
}