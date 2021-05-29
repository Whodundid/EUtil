package eutil.misc;

import eutil.math.NumberUtil;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

/** An enum to keep track of java datatypes. Mainly used for generics at runtime. */
public enum EDataType {
	VOID,
	OBJECT,
	BOOLEAN,
	CHAR,
	BYTE,
	SHORT,
	INT,
	LONG,
	FLOAT,
	DOUBLE,
	STRING,
	NUMBER,
	VAR,
	CONSTRUCTOR,
	METHOD,
	ARRAY,
	CLASS,
	ENUM,
	NULL,
	THIS;
	
	/** Returns true if the given dataType is a number. */
	public boolean isNumber() {
		switch (this) {
		case CHAR: case BYTE: case SHORT: case INT: case LONG: case FLOAT: case DOUBLE: return true;
		default: return false;
		}
	}
	
	/** Returns true if the given dataType is a number. */
	public static boolean isNumber(EDataType typeIn) { return (typeIn != null) ? typeIn.isNumber() : false; }
	
	/** Returns the dataType of the given Number. If the input is null, null is returned instead. */
	public static EDataType getNumberType(Number in) {
		if (in instanceof Byte) { return BYTE; }
		if (in instanceof Short) { return SHORT; }
		if (in instanceof Integer) { return INT; }
		if (in instanceof Long) { return LONG; }
		if (in instanceof Float) { return FLOAT; }
		if (in instanceof Double) { return DOUBLE; }
		return NULL;
	}
	
	public static EDataType getDataType(Object in) {
		if (in == null) { return NULL; }
		if (in instanceof Boolean) { return BOOLEAN; }
		if (in instanceof Character) { return CHAR; }
		if (in instanceof Byte) { return BYTE; }
		if (in instanceof Short) { return SHORT; }
		if (in instanceof Integer) { return INT; }
		if (in instanceof Long) { return LONG; }
		if (in instanceof Float) { return FLOAT; }
		if (in instanceof Double) { return DOUBLE; }
		if (in instanceof String) { return STRING; }
		if (in instanceof Method) { return METHOD; }
		if (in instanceof Array) { return ARRAY; }
		if (in instanceof Enum) { return ENUM; }
		return OBJECT;
	}
	
	public static EDataType getStringDataType(String in) {
		if (in == null || in.equals("null")) { return NULL; }
		if (in.equals("true") || in.equals("false")) { return BOOLEAN; }
		if (in.isEmpty() || in.isBlank()) { return STRING; }
		if (NumberUtil.isNumber(in)) { return (NumberUtil.isInteger(in)) ? LONG : DOUBLE; }
		if (in.length() == 1) { return CHAR; }
		return STRING;
	}
	
	/** Returns the dataType of the given String.
	 *  If the input is found to not be a number, null is returned instead.
	 *  If the input is null, null is returned instead. */
	public static EDataType getNumberType(String in) {
		return getNumberType(NumberUtil.parseNumber(in));
	}
	
	public static Object castTo(Number in, EDataType typeOut) {
		if (in != null && typeOut != null) {
			if (typeOut.isNumber()) {
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
		}
		return null;
	}
	
	public static Object castTo(Object in, EDataType typeOut) {
		if (in != null) {
			if (in instanceof String) { return castTo(NumberUtil.parseNumber((String) in), typeOut); }
			if (in instanceof Number) { return castTo((Number) in, typeOut); }
		}
		return null;
	}
	
}
