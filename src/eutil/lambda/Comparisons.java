package eutil.lambda;

import java.util.Comparator;

/**
 * A collection of comparison logic structures.
 * 
 * @author Hunter Bragg
 * @since 1.1.1
 */
public final class Comparisons {
	
	private Comparisons() {}
	
	/** A general string length comparison. */
	public static final Comparator<String> strlen = Comparator.comparingInt(String::length);
	
}
