package eutil.lambda;

import java.util.Comparator;

import eutil.strings.EStringUtil;

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
	public static final Comparator<Byte> compareBytes = Byte::compare;
	
	/**
	 * Compares Shorts.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Short> compareShorts = Short::compare;
	
	/**
	 * Compares Integers.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Integer> compareInts = Integer::compare;
	
	/**
	 * Compares Longs.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Long> compareLongs = Long::compare;
	
	/**
	 * Compares Floats.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Float> compareFloats = Float::compare;
	
	/**
	 * Compares Doubles.
	 * 
	 * @since 1.5.2
	 */
	public static final Comparator<Double> compareDoubles = Double::compare;
	
	/**
	 * Compares Strings.
	 * 
	 * @since 2.6.0
	 */
	public static final Comparator<String> compareStrings = Comparable::compareTo;
	
	/**
	 * Compares the 'toString' representation of two objects.
	 * 
	 * @since 2.6.0
	 */
	public static final Comparator<Object> compareToStrings = (a, b) -> {
	    final String aStr = EStringUtil.toString(a);
	    final String bStr = EStringUtil.toString(b);
	    
	    return aStr.compareTo(bStr);
	};
	
	/**
	 * Compares Strings ignoring case.
	 * 
	 * @since 2.6.0
	 */
	public static final Comparator<String> compareStringsIgnoreCase = String::compareToIgnoreCase;
	
	   /**
     * Compares the 'toString' representation of two objects.
     * 
     * @since 2.6.0
     */
    public static final Comparator<Object> compareToStringsIgnoreCase = (a, b) -> {
        final String aStr = EStringUtil.toString(a);
        final String bStr = EStringUtil.toString(b);
        
        return aStr.compareToIgnoreCase(bStr);
    };
	
}
