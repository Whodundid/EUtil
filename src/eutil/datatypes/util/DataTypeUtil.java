package eutil.datatypes.util;

import eutil.math.NumberUtil;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * An enum to keep track of java datatypes. Useful for tracking generic datatypes at runtime.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public enum DataTypeUtil {
	
	// Standard Java datatypes
	BOOLEAN,
	CHAR,
	BYTE,
	SHORT,
	INT,
	LONG,
	FLOAT,
	DOUBLE,
	STRING,
	
	// Abstract datatypes
	OBJECT,
	NUMBER,
	ARRAY,
	
	// Special types
	CONSTRUCTOR,
	METHOD,
	CLASS,
	ENUM,
	VOID,
	NULL,
	
	;
	
	//---------
	// Methods
	//---------
	
	/** Returns true if the given dataType is a number. */
	public boolean isNumber() {
		switch (this) {
		case NUMBER: case CHAR: case BYTE: case SHORT: case INT: case LONG: case FLOAT: case DOUBLE: return true;
		default: return false;
		}
	}
	
	/**
	 * Returns {@code true} if this datatype is able to be created normally thorugh Java code.
	 * More specifically, a boolean can be defined, where a void value cannot.
	 * 
	 * <blockquote>
	 * Note: While 'null' can be assigned to objects as a placeholder, by definition 'null' is the absence of a type and can not be created, only assigned.
	 * Therefore, 'null', at least by this library's defintion, is not considered a standard datatype.
	 * </blockquote>
	 * 
	 * @return {@code boolean}
	 * @since 1.1
	 */
	public boolean isStandard() {
		// If it's a number, it's standard by default
		if (isNumber()) return true;
		
		switch (this) {
		case STRING: case OBJECT: case ARRAY: case CONSTRUCTOR: case METHOD: case CLASS: case ENUM: return true;
		default: return false;
		}
	}
	
	//----------------
	// Static Methods
	//----------------
	
	/** Returns true if the given dataType is a number. */
	public static boolean isNumber(DataTypeUtil typeIn) {
		return (typeIn != null) ? typeIn.isNumber() : false;
	}
	
	/** Returns the dataType of the given Number. If the input is null, null is returned instead. */
	public static DataTypeUtil getNumberType(Number in) {
		if (in instanceof Byte) return BYTE;
		if (in instanceof Short) return SHORT;
		if (in instanceof Integer) return INT;
		if (in instanceof Long) return LONG;
		if (in instanceof Float) return FLOAT;
		if (in instanceof Double) return DOUBLE;
		return NULL;
	}
	
	public static DataTypeUtil getDataType(Object in) {
		if (in == null) return NULL;
		if (in instanceof Boolean) return BOOLEAN;
		if (in instanceof Character) return CHAR;
		if (in instanceof Byte) return BYTE;
		if (in instanceof Short) return SHORT;
		if (in instanceof Integer) return INT;
		if (in instanceof Long) return LONG;
		if (in instanceof Float) return FLOAT;
		if (in instanceof Double) return DOUBLE;
		if (in instanceof String) return STRING;
		if (in instanceof Method) return METHOD;
		if (in instanceof Array) return ARRAY;
		if (in instanceof Enum) return ENUM;
		return OBJECT;
	}
	
	public static DataTypeUtil getStringDataType(String in) {
		if (in == null || in.equals("null")) return NULL;
		if (in.equals("true") || in.equals("false")) return BOOLEAN;
		if (in.isEmpty() || in.trim().isEmpty()) return STRING;
		if (NumberUtil.isNumber(in)) return (NumberUtil.isInteger(in)) ? LONG : DOUBLE;
		if (in.length() == 1) return CHAR;
		return STRING;
	}
	
	/** Returns the dataType of the given String.
	 *  If the input is found to not be a number, null is returned instead.
	 *  If the input is null, null is returned instead. */
	public static DataTypeUtil getNumberType(String in) {
		return getNumberType(NumberUtil.parseNumber(in));
	}
	
	public static Object castTo(Number in, DataTypeUtil typeOut) {
		if (in != null && typeOut != null && typeOut.isNumber()) {
			switch (typeOut) {
			case BYTE: return in.byteValue();
			case SHORT: return in.shortValue();
			case INT: return in.intValue();
			case LONG: return in.longValue();
			case FLOAT: return in.floatValue();
			case DOUBLE: return in.doubleValue();
			default: break;
			}
		}
		return null;
	}
	
	public static Object castTo(Object in, DataTypeUtil typeOut) {
		if (in != null) {
			if (in instanceof String) {
				if (typeOut.isNumber()) return castTo(NumberUtil.parseNumber((String) in), typeOut);
				else if (typeOut == DataTypeUtil.CHAR) {
					String s = (String) in;
					if (s.isEmpty()) return '\u0000';
					else return s.charAt(0);
				}
				else return in;
			}
			if (in instanceof Number) return castTo((Number) in, typeOut);
		}
		return null;
	}
	
}
