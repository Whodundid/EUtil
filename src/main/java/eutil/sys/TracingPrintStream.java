package eutil.sys;

import java.io.PrintStream;

/**
 * A print stream which when enabled, will prepend the cooresponding stack
 * trace location of the calling method in code.
 *
 * @author Arkan
 * @author Hunter Bragg
 * 
 * @since 1.0.0
 */
public class TracingPrintStream extends PrintStream {

    //========
    // Fields
    //========
    
    /** The base stack trace array depth to extract relevant call data from. */
	private static final int BASE_DEPTH = 4;
	/** The original Java System.out print stream that existed at the time this class was loaded. */
	private static final PrintStream DEFAULT_STREAM_INSTANCE = System.out;
	/** A static singleton tracer that can be applied and removed as desired. */
	private static final TracingPrintStream TRACER_STREAM_INSTANCE = new TracingPrintStream(DEFAULT_STREAM_INSTANCE);
	
	/** True if actively tracing outputs. */
	private static boolean tracing = false;
	/** True if primitive value outputs will also be traced. */
	private static boolean tracePrimitives = true;
	/** True if empty line outputs will also be traced. */
	private static boolean traceEmptyLines = false;
	
	//==============
    // Constructors
    //==============
	
	/**
	 * Wraps the given print stream with line tracing functionality.
	 * 
	 * @param stream The print stream to wrap
	 */
	private TracingPrintStream(PrintStream stream) {
		super(stream);
	}

	//===========
    // Overrides
    //===========
	
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

	//=========================
    // Internal Helper Methods
    //=========================
	
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

	//================
	// Static Methods
	//================
	
	public static boolean isTracing() { return tracing; }
	public static boolean isTracingPrimitives() { return tracePrimitives; }
	public static boolean tracesEmptyLines() { return traceEmptyLines; }
	
	/** Enables line tracing for printed values that are only primitives. */
	public static void setTracePrimitives(boolean val) { tracePrimitives = val; }
	/** Enables line tracing for all empty lines that are printed. */
	public static void enableTracingEmptyLines(boolean val) { traceEmptyLines = val; }
	
	/**
	 * Enables or disables tracing based on given boolean input.
	 * 
	 * @param val True to enable, false to disable
	 */
	public static void enableTracing(boolean val) {
		if (val) enableTrace();
		else disableTrace();
	}
	
	/**
	 * Enables output tracing by hijacking the std::out print stream with a tracing one.
	 */
	public static void enableTrace() {
	    if (tracing) return;
	    System.setOut(TRACER_STREAM_INSTANCE);
        tracing = true;
	}
	
	/**
	 * Restores the original, non-tracing std::out print stream.
	 */
	public static void disableTrace() {
	    if (!tracing) return;
	    System.setOut(DEFAULT_STREAM_INSTANCE);
        tracing = false;
	}
	
}