package eutil.datatypes.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import eutil.math.ENumUtil;

/**
 * An enum to keep track of java datatypes. Useful for tracking generic datatypes at runtime.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public enum JavaDatatype {
	
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
	LIST,
	
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
	    return this == NUMBER ||
	           this == BYTE ||
	           this == SHORT ||
	           this == INT ||
	           this == LONG ||
	           this == FLOAT ||
	           this == DOUBLE;
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
		
		return switch (this) {
		case STRING, OBJECT, ARRAY, CONSTRUCTOR, METHOD, CLASS, ENUM -> true;
		default -> false;
		};
	}
	
	//----------------
	// Static Methods
	//----------------
	
	/** Returns true if the given dataType is a number. */
	public static boolean isNumber(JavaDatatype typeIn) {
		return typeIn != null && typeIn.isNumber();
	}
	
	/** Returns the dataType of the given Number. If the input is null, null is returned instead. */
	public static JavaDatatype getNumberType(Number in) {
		if (in instanceof Byte) return BYTE;
		if (in instanceof Short) return SHORT;
		if (in instanceof Integer) return INT;
		if (in instanceof Long) return LONG;
		if (in instanceof Float) return FLOAT;
		if (in instanceof Double) return DOUBLE;
		return NULL;
	}
	
	/** Returns the datatype of the given object. */
	public static JavaDatatype getDataType(Object in) {
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
		if (in instanceof List) return LIST;
		
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
    public static JavaDatatype getDataType(Class<?> in) {
        if (in == null) return NULL;
        
        if (in == Boolean.class || in == boolean.class) return BOOLEAN;
        if (in == Byte.class || in == byte.class) return BYTE;
        if (in == Character.class || in == char.class) return CHAR;
        if (in == Short.class || in == short.class) return SHORT;
        if (in == Integer.class || in == int.class) return INT;
        if (in == Long.class || in == long.class) return LONG;
        if (in == Float.class || in == float.class) return FLOAT;
        if (in == Double.class || in == double.class) return DOUBLE;
        
        if (in == String.class) return STRING;
        if (in == Number.class) return NUMBER;
        
        if (in.isRecord()) return RECORD;
        if (in.isArray()) return ARRAY;
        if (in.isEnum()) return ENUM;
        if (in.isInterface()) return INTERFACE;
        if (in.isAnnotation()) return ANNOTATION;
        if (in.isAssignableFrom(Module.class)) return MODULE;
        
        if (in.isAssignableFrom(Method.class)) {
            Class<Method> m = (Class<Method>) in;
            if (m.isAssignableFrom(Constructor.class)) return CONSTRUCTOR;
            return METHOD;
        }
        
        if (List.class.isAssignableFrom(in)) return LIST;
        
        return OBJECT;
    }
	
	public static JavaDatatype getStringDataType(String in) {
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
	public static JavaDatatype getNumberType(String in) {
		return getNumberType(ENumUtil.parseNumber(in));
	}
	
	public static Object castTo(Number in, JavaDatatype typeOut) {
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
	
	public static Object castTo(Object in, JavaDatatype typeOut) {
		if (in == null) return null;
		if (in instanceof Number n) return castTo(n, typeOut);
		if (in instanceof String s) {
			if (typeOut.isNumber()) return castTo(ENumUtil.parseNumber(s), typeOut);
			if (typeOut == JavaDatatype.CHAR) return (s.isEmpty()) ? '\u0000' : s.charAt(0);
		}
		return in;
	}
	
    /**
     * Returns true if the given object is a primitive datatype.
     * 
     * @param in The object to check
     * @return True if the given object is a primitive Java datatype
     * @since 2.5.4
     */
	public static boolean isPrimitiveType(Object in) {
	    if (in == null) return false;
	    return isPrimitiveClass(in.getClass());
	}
	
	/**
	 * Returns true if the given Java class is a primitive datatype class.
	 * 
	 * @param in The class to check
	 * @return True if the given class is a primitive Java datatype class
	 * @since 2.5.4
	 */
    public static boolean isPrimitiveClass(Class<?> in) {
        if (in == null) return false;
        
        return in == boolean.class ||
               in == char.class ||
               in == byte.class ||
               in == short.class ||
               in == int.class ||
               in == long.class ||
               in == float.class ||
               in == double.class;
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
	public static JavaDatatype of(Object objectIn) { return JavaDatatype.getDataType(objectIn); }
	
	/**
	 * Returns the datatype that the given class represents.
	 * 
	 * @param in The class of an object
	 * @return The parsed datatype or 'NULL' if null
	 * 
	 * @since 1.8.1
	 */
	public static JavaDatatype of(Class<?> classIn) { return JavaDatatype.getDataType(classIn); }
	
}
