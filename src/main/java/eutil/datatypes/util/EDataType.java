package eutil.datatypes.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import eutil.math.ENumUtil;

/**
 * An enum to keep track of java datatypes. Useful for tracking generic datatypes at runtime.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public enum EDataType {
	
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
	INTERFACE,
	
	// Special types
	RECORD,
	MODULE,
	ANNOTATION,
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
		case NUMBER: case CHAR: case BYTE: case SHORT:
		case INT: case LONG: case FLOAT: case DOUBLE:
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Returns {@code true} if this datatype is able to be created normally through Java code.
	 * More specifically, a boolean can be defined, where a void value cannot.
	 * 
	 * <blockquote>
	 * Note: While 'null' can be assigned to objects as a placeholder, by definition 'null' is the absence of a type and can not be created, only assigned.
	 * Therefore, 'null', at least by this library's definition, is not considered a standard datatype.
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
	public static boolean isNumber(EDataType typeIn) {
		return (typeIn != null) ? typeIn.isNumber() : false;
	}
	
	/** Returns the dataType of the given Number. If the input is null, null is returned instead. */
	public static EDataType getNumberType(Number in) {
		if (in instanceof Byte) return BYTE;
		if (in instanceof Short) return SHORT;
		if (in instanceof Integer) return INT;
		if (in instanceof Long) return LONG;
		if (in instanceof Float) return FLOAT;
		if (in instanceof Double) return DOUBLE;
		return NULL;
	}
	
	/** Returns the datatype of the given object. */
	public static EDataType getDataType(Object in) {
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
		if (in instanceof Constructor) return CONSTRUCTOR;
		if (in instanceof Method) return METHOD;
		if (in instanceof Module) return MODULE;
		if (in instanceof Number) return NUMBER;
		if (in instanceof Class) return CLASS;
		
		Class<?> c = in.getClass();
		if (c.isRecord()) return RECORD;
		if (c.isAnnotation()) return ANNOTATION;
		if (c.isArray()) return ARRAY;
		if (c.isInterface()) return INTERFACE;
		if (c.isEnum()) return ENUM;
		
		return OBJECT;
	}
	
	/**
	 * Returns the datatype that the given class represents.
	 * 
	 * @param in The class of an object
	 * @return The parsed datatype or 'NULL' if null
	 * 
	 * @since 1.8.1
	 */
	public static EDataType getDataType(Class<?> in) {
		if (in == null) return NULL;
		
		if (in.isRecord()) return RECORD;
		if (in.isArray()) return ARRAY;
		if (in.isEnum()) return ENUM;
		if (in.isInterface()) return INTERFACE;
		if (in.isAnnotation()) return ANNOTATION;
		if (in.isAssignableFrom(Module.class)) return MODULE;
		
		if (in.isAssignableFrom(Method.class)) {
			Class<? extends Method> m = (Class<? extends Method>) in;
			if (m.isAssignableFrom(Constructor.class)) return CONSTRUCTOR;
			return METHOD;
		}
		
		if (in.isAssignableFrom(Boolean.class) || in.isAssignableFrom(boolean.class)) return BOOLEAN;
		if (in.isAssignableFrom(Byte.class) || in.isAssignableFrom(byte.class)) return BYTE;
		if (in.isAssignableFrom(Character.class) || in.isAssignableFrom(char.class)) return CHAR;
		if (in.isAssignableFrom(Short.class) || in.isAssignableFrom(short.class)) return SHORT;
		if (in.isAssignableFrom(Integer.class) || in.isAssignableFrom(int.class)) return INT;
		if (in.isAssignableFrom(Long.class) || in.isAssignableFrom(long.class)) return LONG;
		if (in.isAssignableFrom(Float.class) || in.isAssignableFrom(float.class)) return FLOAT;
		if (in.isAssignableFrom(Double.class) || in.isAssignableFrom(double.class)) return DOUBLE;
		
		if (in.isAssignableFrom(String.class)) return STRING;
		if (in.isAssignableFrom(Number.class)) return NUMBER;
		
		return OBJECT;
	}
	
	public static EDataType getStringDataType(String in) {
		if (in == null || in.equals("null")) return NULL;
		if (in.equals("true") || in.equals("false")) return BOOLEAN;
		if (in.isEmpty() || in.trim().isEmpty()) return STRING;
		if (ENumUtil.isNumber(in)) return (ENumUtil.isInteger(in)) ? LONG : DOUBLE;
		if (in.length() == 1) return CHAR;
		return STRING;
	}
	
	/** Returns the dataType of the given String.
	 *  If the input is found to not be a number, null is returned instead.
	 *  If the input is null, null is returned instead. */
	public static EDataType getNumberType(String in) {
		return getNumberType(ENumUtil.parseNumber(in));
	}
	
	public static Object castTo(Number in, EDataType typeOut) {
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
	
	public static Object castTo(Object in, EDataType typeOut) {
		if (in == null) return null;
		if (in instanceof Number n) return castTo(n, typeOut);
		if (in instanceof String s) {
			if (typeOut.isNumber()) return castTo(ENumUtil.parseNumber(s), typeOut);
			if (typeOut == EDataType.CHAR) return (s.isEmpty()) ? '\u0000' : s.charAt(0);
		}
		return in;
	}
	
	//-------------------
	// Static Converters
	//-------------------
	
	/**
	 * Returns the datatype of the given object.
	 * 
	 * @param objectIn The object to parse a datatype from
	 * @return The datatype of the given object
	 * 
	 * @since 1.8.1
	 */
	public static EDataType of(Object objectIn) { return EDataType.getDataType(objectIn); }
	
	/**
	 * Returns the datatype that the given class represents.
	 * 
	 * @param in The class of an object
	 * @return The parsed datatype or 'NULL' if null
	 * 
	 * @since 1.8.1
	 */
	public static EDataType of(Class<?> classIn) { return EDataType.getDataType(classIn); }
	
}
