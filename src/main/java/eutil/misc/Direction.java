package eutil.misc;

import java.util.List;

import eutil.datatypes.EArrayList;

/**
 * An enum representing standard compass directions and their various properties.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public enum Direction {
	
	N("N", "North", true, 0.0),
	NE("NE", "North East", false, 45.0),
	E("E", "East", true, 90.0),
	SE("SE", "South East", false, 135.0),
	S("S", "South", true, 180.0),
	SW("SW", "South West", false, 225.0),
	W("W", "West", true, 270.0),
	NW("NW", "North West", false, 315.0),
	
	/**
	 * Directions that pertain to the lines of longitude (N/S).
	 * @since 1.5.4
	 */
	LONGITUDE("LONGITUDE", "N/S", false, Double.NaN),
	
	/**
	 * Directions that pertain to the lines of latitude (E/W).
	 * @since 1.5.4
	 */
	LATITUDE("LATITUDE", "E/W", false, Double.NaN),
	
	/** Used to represent an invalid or non-existent direction. */
	OUT("Unknown", "Unknown", false, Double.NaN);
	
	//------------------------------------------------
	
	/** The shorthand string identifier of the direction. */
	public final String identifier;
	/** The formal name of the compass direction. */
	public final String name;
	/** True if this direction is a cardinal direction. */
	public final boolean cardinal;
	/** The compass degree value of this direction. */
	public final double degree;
	
	//--------------
	// Constructors
	//--------------
	
	private Direction(String shortIn, String nameIn, boolean cardinalIn, double degreeIn) {
		identifier = shortIn;
		name = nameIn;
		cardinal = cardinalIn;
		degree = degreeIn;
	}
	
	//------------------------------------------------
	
	//---------
	// Methods
	//---------
	
    /**
     * Returns the closest compass direction to the given degree value.
     * 
     * @param dirIn The direction to convert
     * @return A direction wrapping the given degree
     * 
     * @since 2.6.6
     */
	public static Direction getDirection(double dirIn) {
		if (dirIn == Double.NaN) return null;
		dirIn %= 360;
		
		if (dirIn > 315 || dirIn <= 45 || dirIn == 0.0 || dirIn == 360.0) return E;
		else if (dirIn > 225 && dirIn <= 315) return S;
		else if (dirIn > 135 && dirIn <= 225) return W;
		return N;
	}
	
	/** Returns the shorthand version of this compass degree. <blockquote> Example: North = 'N' </blockquote> */
	public String getIdentifier() { return identifier; }
	/** Returns the formal name of this compass direction. */
	public String getName() { return name; }
	/** Returns true if this direction is a cardinal direction. <blockquote> Example: N, E, S, W </blockquote> */
	public boolean isCardinal() { return cardinal; }
	/** Returns the compass degree of the given direction. */
	public double getDegree() { return degree; }
	/** Returns the compass degree of the given direction as an int. */
	public int getDegreeInt() { return (int) degree; }
	
    public Direction asCardinalDirection() {
        if (degree > 315 || degree <= 45 || degree == 0.0 || degree == 360.0) return N;
        else if (degree > 225 || degree <= 315) return S;
        else if (degree > 135 || degree <= 225) return W;
        return E;
    }
	
	//----------------
	// Static Methods
	//----------------
	
	/** Returns the compass direction related to given enum ordinal value. */
	public static Direction get(int ordinal) { return values()[ordinal]; }
	
	/** Returns a list containing only cardinal directions. */
	public static List<Direction> cardinals() { return new EArrayList<>(N, E, S, W); }
	/** Returns a list containing only ordinal directions. */
	public static List<Direction> ordinals() { return new EArrayList<>(NE, NW, SE, SW); }
	/** Returns a list containing all real directions. */
	public static List<Direction> directions() { return new EArrayList<>(N, NE, E, NW, S, SE, W, SW); }
	
	/**
	 * Returns a list containing the directions that correspond to the lines of longitude (N/S).
	 * @since 1.5.4
	 */
	public static List<Direction> longitudes() { return new EArrayList<>(N, S); }
	
	/**
	 * Returns a list containing the directions that correspond to the lines of latitude (E/W).
	 * @since 1.5.4
	 */
	public static List<Direction> latitudes() { return new EArrayList<>(E, W); }
	
}
