package eutil.lambda;

import java.util.Comparator;

/**
 * A collection of comparison logic structures.
 * 
 * @author Hunter Bragg
 * @since 1.1.1
 */
public final class Comparisons {
	
	//--------------
	// Constructors
	//--------------
	
	// Hide constructor
	private Comparisons() {}
	
	//------------------
	// Comparison Types
	//------------------
	
	/**
	 * A general string length comparison.
	 * 
	 * @since 1.1.1
	 */
	public static final Comparator<String> strlen = Comparator.comparingInt(String::length);
	
	/**
	 * Compares Bytes.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Byte> compareBytes = (a, b) -> Byte.compare(a, b);
	
	/**
	 * Compares Shorts.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Short> compareShorts = (a, b) -> Short.compare(a, b);
	
	/**
	 * Compares Integers.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Integer> compareInts = (a, b) -> Integer.compare(a, b);
	
	/**
	 * Compares Longs.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Long> compareLongs = (a, b) -> Long.compare(a, b);
	
	/**
	 * Compares Floats.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Float> compareFloats = (a, b) -> Float.compare(a, b);
	
	/**
	 * Compares Doubles.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Double> compareDoubles = (a, b) -> Double.compare(a, b);
	
}
