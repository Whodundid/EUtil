package eutil.datatypes;

//Author: Hunter Bragg

/**
 * An enum which specifies java number datatypes: [byte, short, integer, long, float, double, null]
 * 
 * @author Hunter
 * @since 1.0.0
 */
public enum NumType {
	
	// Standard Java number datatypes
	BYTE,
	SHORT,
	INTEGER,
	LONG,
	FLOAT,
	DOUBLE,
	
	/** Exclusively used to represent a non-number value. */
	NULL;
	
}
