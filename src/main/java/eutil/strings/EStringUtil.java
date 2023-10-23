package eutil.strings;

import static eutil.EUtil.*;
import static eutil.lambda.Comparisons.*;
import static eutil.lambda.Functions.*;
import static eutil.lambda.Functions.toString;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import eutil.EUtil;
import eutil.debug.Experimental;

/**
 * A collection of utility functions for string manipulation and
 * toString helpers.
 * 
 * @apiNote Previous name: 'StringUtil'
 * 
 * @author Hunter Bragg
 * @since 1.2.0
 */
public final class EStringUtil {
	
	//-----------------------
	
	/** Hide Constructor */
	private EStringUtil() {}
	
	//-----------------------
	
	/**
	 * Returns the string version of the object. This function also
	 * accounts for null values.
	 * 
	 * @apiNote 1.6.4: Now uses 'String.valueOf' for the defaultVal
	 */
	public static String toString(Object o) {
		return toString(o, String.valueOf(o));
	}
	
	/**
	 * Returns the string version of the object. This function also
	 * accounts for null values. If the value is null, "null" is returned.
	 * 
	 * @apiNote 1.6.4: Now returns the 'String.valueOf' as the result
	 */
	public static String toString(Object o, String defaultVal) {
		return (o != null) ? String.valueOf(o) : defaultVal;
	}
	
	//-----------------------------
	// Array-To-String conversions
	//-----------------------------
	
	public static String toString(boolean[] e) { return toString(e, ", "); }
	public static String toString(byte[] e) { return toString(e, ", "); }
	public static String toString(char[] e) { return toString(e, ", "); }
	public static String toString(int[] e) { return toString(e, ", "); }
	public static String toString(short[] e) { return toString(e, ", "); }
	public static String toString(long[] e) { return toString(e, ", "); }
	public static String toString(float[] e) { return toString(e, ", "); }
	public static String toString(double[] e) { return toString(e, ", "); }
	public static <E> String toString(E[] e) { return toString(e, ", "); }
	public static String toString(List<?> e) { return toString(e, ", "); }
	public static String toString(Iterator<?> e) { return toString(e, ", "); }
	public static String toString(Enumeration<?> e) { return toString(e, ", "); }
	public static String toString(Map<?, ?> e) { return toString(e, ", "); }
	
	public static String toString(boolean[] e, String separator) { var s=sbs(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r(); }
	public static String toString(byte[] e, String separator) { var s=sbs(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(char[] e, String separator) { var s=sbs(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(int[] e, String separator) { var s=sbs(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(short[] e, String separator) { var s=sbs(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(long[] e, String separator) { var s=sbs(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(float[] e, String separator) { var s=sbs(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(double[] e, String separator) { var s=sbs(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static <E> String toString(E[] e, String separator) { var s=sbs(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(List<?> e, String separator) { var s=sbs(separator);for(int i=0;i<e.size();i++)s.a(e.get(i),separator);return s.r();}
	public static String toString(Iterator<?> e, String separator) { var s=sbs(separator);while(e.hasNext())s.a(e.next(),separator);return s.r(); }
	public static String toString(Enumeration<?> e, String separator) { var s=sbs(separator);while(e.hasMoreElements())s.a(e.nextElement(),separator);return s.r(); }
	public static String toString(Map<?, ?> e, String separator) {
		var s = sbs(separator);
		var it = e.entrySet().iterator();
		while (it.hasNext()) {
			var o = it.next();
			//handle self-contains condition
			if (o == e) s.a("Itself!", separator);
			else s.a(toString(o), separator);
		}
		return s.r();
	}
	
	public static <E, T> String toString(E[] e, Function<? super E, ? extends T> mapper) {
		return toString(e, mapper, " ");
	}
	
	public static <E, T> String toString(E[] e, Function<? super E, ? extends T> mapper, String separator) {
		var out = sbs(separator);
		for (int i = 0; i < e.length; i++) {
			out.a(mapper.apply(e[i]), separator);
		}
		return (out.toString().isEmpty()) ? out.toString() : out.sub(0, out.l() - separator.length());
	}
	
	public static <E, T> String toString(Collection<E> e, Function<? super E, ? extends T> mapper) { return toString(EUtil.mapList(e, mapper), " "); }
	public static <E, T> String toString(Collection<E> e, Function<? super E, ? extends T> mapper, String separator) {
		var s = sb();
		for (var val : e) {
			s.a(mapper.apply(val));
			s.a(separator);
		}
		return (s.toString().isEmpty()) ? s.toString() : s.sub(0, s.l() - separator.length());
	}
	
    /**
     * Checks whether or not the given string contains any non-whitespace
     * characters.
     * 
     * @param value The string to check
     * 
     * @return True if the given string is not null and actually contains
     *         non-whitespace characters
     * 
     * @since 2.6.0
     */
	public static boolean isPopulated(String value) {
	    return value != null && !value.isBlank() && !value.isEmpty();
	}
	
    /**
     * Checks whether or not the given string does not contain any
     * non-whitespace characters.
     * 
     * @param value The string to check
     * 
     * @return True if the given string is not null and does not contain any
     *         non-whitespace characters
     * 
     * @since 2.6.0
     */
    public static boolean isNotPopulated(String value) {
        return value == null || value.isBlank() || value.isEmpty();
    }
	
	/**
	 * Performs 'String.compare' from the given 'a' String against the given
	 * 'b' String.
	 * <p>
	 * Note: Both a and b must not be null.
	 * 
	 * @param a First String
	 * @param b Second String
	 * @return The 'String.compare' value from 'a' against 'b'
	 * @since 1.6.4
	 */
	public static int compare(String a, String b) {
		EUtil.requireNonNull(a, b);
		return a.compareTo(b);
	}
	
	/**
	 * Converts to objects to their 'toString' equivalents and then performs
	 * 'String.compare' from the a's toString value against the b's toString
	 * value.
	 * <p>
	 * Note: Both a and b must not be null.
	 * 
	 * @param a First String
	 * @param b Second String
	 * @return The 'String.compare' value from 'a' against 'b'
	 * @since 1.6.4
	 */
	public static int compare(Object a, Object b) {
		EUtil.requireNonNull(a, b);
		String aStr = toString(a);
		String bStr = toString(b);
		return aStr.compareTo(bStr);
	}
	
	   /**
     * Performs 'String.compare' from the given 'a' String against the given
     * 'b' String ignoring case differences.
     * <p>
     * Note: Both a and b must not be null.
     * 
     * @param a First String
     * @param b Second String
     * @return The 'String.compare' value from 'a' against 'b'
     * @since 2.6.0
     */
    public static int compareIgnoreCase(String a, String b) {
        EUtil.requireNonNull(a, b);
        return a.compareToIgnoreCase(b);
    }
    
    /**
     * Converts to objects to their 'toString' equivalents and then performs
     * 'String.compare' from the a's toString value against the b's toString
     * value ignoring case differences.
     * <p>
     * Note: Both a and b must not be null.
     * 
     * @param a First String
     * @param b Second String
     * @return The 'String.compare' value from 'a' against 'b'
     * @since 2.6.0
     */
    public static int compareIgnoreCase(Object a, Object b) {
        EUtil.requireNonNull(a, b);
        String aStr = toString(a);
        String bStr = toString(b);
        return aStr.compareToIgnoreCase(bStr);
    }
	
	//============
	// Equals Any
	//============
	
	/**
	 * Returns true if the given string matches any of the 'toCheck' strings.
	 * 
	 * @param in The string to compare against
	 * @param toCheck The string(s) to check against
	 * @return True if any match
	 * @since 1.5.5
	 */
	public static boolean equalsAny(String in, String... toCheck) {
		for (var s : toCheck)
			if (isEqual(in, s))
				return true;
		return false;
	}
	
    /**
     * Returns true if the given string matches any of the 'toCheck' strings.
     * 
     * @param in The string to compare against
     * @param toCheck The string(s) to check against
     * @return True if any match
     * @since 2.5.1
     */
    public static boolean equalsAny(String in, Collection<String> toCheck) {
        for (var s : toCheck)
            if (isEqual(in, s))
                return true;
        return false;
    }
	
	/**
	 * Returns the 'toCheck' string that matches the given string.
	 * 
	 * @param in The string to compare against
	 * @param toCheck The string(s) to check against
	 * @return The 'toCheck' string string that matches
	 * @since 1.5.5
	 */
	public static String equalsAnyR(String in, String... toCheck) {
		for (var s : toCheck)
			if (isEqual(in, s))
				return s;
		return null;
	}
	
	   /**
     * Returns the 'toCheck' string that matches the given string.
     * 
     * @param in The string to compare against
     * @param toCheck The string(s) to check against
     * @return The 'toCheck' string string that matches
     * @since 1.5.5
     */
    public static <E> E equalsAnyR(String in, E... toCheck) {
        for (var s : toCheck)
            if (isEqual(in, String.valueOf(s)))
                return s;
        return null;
    }
	
    /**
     * Returns the 'toCheck' object's 'toString' that matches the given string.
     * 
     * @param in The string to compare against
     * @param toCheck The objects(s) to check against
     * @return The 'toCheck' object that matches
     * @since 2.5.1
     */
    public static String equalsAnyR(String in, Collection<String> toCheck) {
        for (var s : toCheck)
            if (isEqual(in, toString(s)))
                return s;
        return null;
    }
	
	/**
	 * Returns the 'toCheck' object's 'toString' that matches the given string.
	 * 
	 * @param in The string to compare against
	 * @param toCheck The objects(s) to check against
	 * @return True if any 'toCheck' object's 'toString' matches
	 * @since 1.5.5
	 */
	public static boolean equalsAny(String in, Object... toCheck) {
		for (var s : toCheck)
			if (isEqual(in, toString(s)))
				return true;
		return false;
	}
	
    //=============
    // Starts With
    //=============

    /**
     * Returns true if the given string starts with any of the 'toCheck' strings.
     * 
     * @param in The string to compare against
     * @param toCheck The string(s) to check against
     * @return True if the given string starts with any 'toCheck' string
     * @since 2.5.1
     */
    public static boolean startsWithAny(String in, String... toCheck) {
        if (in == null) return false;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.startsWith(s)) return true;
        return false;
    }
    
    /**
     * Returns true if the given string starts with any of the 'toCheck'
     * strings.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return True if the given string starts with any of the 'toCheck'
     *         object's 'toString' value
     *         
     * @since 2.5.1
     */
    public static boolean startsWithAny(String in, Object... toCheck) {
        if (in == null) return false;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.startsWith(String.valueOf(s))) return true;
        return false;
    }
    
    /**
     * Returns true if the given string starts with any of the 'toCheck'
     * object's 'toString' values.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return True if the given string starts with any of the 'toCheck'
     *         object's 'toString' value
     *         
     * @since 2.5.1
     */
    public static boolean startsWithAny(String in, Collection<?> toCheck) {
        if (in == null) return false;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.startsWith(String.valueOf(s))) return true;
        return false;
    }
    
    /**
     * Returns the 'toCheck' string that the given input string starts with.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return the 'toCheck' string that the given input string starts with
     * 
     * @since 2.5.1
     */
    public static String startsWithAnyR(String in, String... toCheck) {
        if (in == null) return null;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.startsWith(s)) return s;
        return null;
    }
    
    /**
     * Returns the 'toCheck' object's 'toString' value that the given input
     * string starts with.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return the 'toCheck' object's 'toString' value that the given input
     *         string starts with
     *         
     * @since 2.5.1
     */
    public static <E> E startsWithAnyR(String in, E... toCheck) {
        if (in == null) return null;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.startsWith(String.valueOf(s))) return s;
        return null;
    }
    
    /**
     * Returns the 'toCheck' object's 'toString' value that the given input
     * string starts with.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return the 'toCheck' object's 'toString' value that the given input
     *         string starts with
     *         
     * @since 2.5.1
     */
    public static <E> E startsWithAnyR(String in, Collection<E> toCheck) {
        if (in == null) return null;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.startsWith(String.valueOf(s))) return s;
        return null;
    }
    
	//===========
	// Ends With
	//===========
	
    /**
     * Returns true if the given string ends with any of the 'toCheck' strings.
     * 
     * @param in      The string to compare against
     * @param toCheck The objects(s) to check against
     * 
     * @return True if the given string ends with any of the 'toCheck' strings
     * 
     * @since 2.5.1
     */
    public static boolean endsWithAny(String in, String... toCheck) {
        if (in == null) return false;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.endsWith(s)) return true;
        return false;
    }
    
    /**
     * Returns true if the given string ends with any of the 'toCheck' object's
     * 'toString' values.
     * 
     * @param in      The string to compare against
     * @param toCheck The objects(s) to check against
     * 
     * @return True if the given string ends with any of the 'toCheck' object's
     *         'toString' value
     * 
     * @since 2.5.1
     */
    public static boolean endsWithAny(String in, Object... toCheck) {
        if (in == null) return false;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.endsWith(String.valueOf(s))) return true;
        return false;
    }
    
    /**
     * Returns true if the given string ends with any of the 'toCheck' object's
     * 'toString' values.
     * 
     * @param in      The string to compare against
     * @param toCheck The objects(s) to check against
     * 
     * @return True if the given string ends with any of the 'toCheck' object's
     *         'toString' value
     * 
     * @since 2.5.1
     */
    public static boolean endsWithAny(String in, Collection<?> toCheck) {
        if (in == null) return false;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.endsWith(String.valueOf(s))) return true;
        return false;
    }
    
    /**
     * Returns the 'toCheck' string that the given input ends ends with.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return the 'toCheck' string that the given input ends ends with
     * 
     * @since 2.5.1
     */
    public static String endsWithAnyR(String in, String... toCheck) {
        if (in == null) return null;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.endsWith(s)) return s;
        return null;
    }
    
    /**
     * Returns the 'toCheck' object's 'toString' value that the given input
     * string ends with.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return the 'toCheck' object's 'toString' value that the given input
     *         string ends with
     *         
     * @since 2.5.1
     */
    public static <E> E endsWithAnyR(String in, E... toCheck) {
        if (in == null) return null;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.endsWith(String.valueOf(s))) return s;
        return null;
    }
    
    /**
     * Returns the 'toCheck' object's 'toString' value that the given input
     * string ends with.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return the 'toCheck' object's 'toString' value that the given input
     *         string ends with
     *         
     * @since 2.5.1
     */
    public static <E> E endsWithAnyR(String in, Collection<E> toCheck) {
        if (in == null) return null;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.endsWith(String.valueOf(s))) return s;
        return null;
    }
    
	//==============
	// Contains Any
	//==============
	
    /**
     * Returns true if the given string contains any of the 'toCheck' strings.
     * 
     * @param in      The string to compare against
     * @param toCheck The objects(s) to check against
     * 
     * @return True if the given string contains any of the 'toCheck' strings
     * 
     * @since 2.5.1
     */
    public static boolean containsAny(String in, String... toCheck) {
        if (in == null) return false;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.contains(s)) return true;
        return false;
    }
    
    /**
     * Returns true if the given string contains any of the 'toCheck' object's
     * 'toString' values.
     * 
     * @param in      The string to compare against
     * @param toCheck The objects(s) to check against
     * 
     * @return True if the given string contains any of the 'toCheck' object's
     *         'toString' value
     * 
     * @since 2.5.1
     */
    public static boolean containsAny(String in, Object... toCheck) {
        if (in == null) return false;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.contains(String.valueOf(s))) return true;
        return false;
    }
    
    /**
     * Returns true if the given string contains any of the 'toCheck' object's
     * 'toString' values.
     * 
     * @param in      The string to compare against
     * @param toCheck The objects(s) to check against
     * 
     * @return True if the given string contains any of the 'toCheck' object's
     *         'toString' value
     * 
     * @since 2.5.1
     */
    public static boolean containsAny(String in, Collection<?> toCheck) {
        if (in == null) return false;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.contains(String.valueOf(s))) return true;
        return false;
    }
    
    /**
     * Returns the 'toCheck' string that the given input contains.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return the 'toCheck' string that the given input contains
     * 
     * @since 2.5.1
     */
    public static String containsAnyR(String in, String... toCheck) {
        if (in == null) return null;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.contains(s)) return s;
        return null;
    }
    
    /**
     * Returns the 'toCheck' object's 'toString' value that the given input
     * string contains.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return the 'toCheck' object's 'toString' value that the given input
     *         string contains
     *         
     * @since 2.5.1
     */
    public static <E> E containsAnyR(String in, E... toCheck) {
        if (in == null) return null;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.contains(String.valueOf(s))) return s;
        return null;
    }
    
    /**
     * Returns the 'toCheck' object's 'toString' value that the given input
     * string contains.
     * 
     * @param in      The string to compare against
     * @param toCheck The string(s) to check against
     * 
     * @return the 'toCheck' object's 'toString' value that the given input
     *         string contains
     *         
     * @since 2.5.1
     */
    public static <E> E containsAnyR(String in, Collection<E> toCheck) {
        if (in == null) return null;
        for (var s : toCheck)
            if (s == null) continue;
            else if (in.contains(String.valueOf(s))) return s;
        return null;
    }
    
    //======================
    // Starts and Ends With
    //======================
    
    /**
     * Returns true if the given input starts and ends with the given 'toCheck'
     * string.
     * 
     * @param input   The input string
     * @param toCheck The value to check for
     * 
     * @return true if the 'toCheck' value is at the beginning and end of the
     *         input string
     * 
     * @since 2.5.1
     */
    public static boolean startsAndEndsWith(String input, String toCheck) {
        if (input == null || toCheck == null) return false;
        return input.startsWith(toCheck) && input.endsWith(toCheck);
    }
    
    /**
     * Returns true if the given input starts with the given 'beginning' string
     * and ends with the given 'end' string.
     * 
     * @param input     The input string
     * @param beginning The value to check for at the beginning of the string
     * @param end       The value to check for at the end of the string
     * 
     * @return true if the given string starts with and ends with the
     *         respective 'beginning' and 'end' strings
     *         
     * @since 2.6.0
     */
    public static boolean startsAndEndsWith(String input, String beginning, String end) {
        if (input == null || beginning == null || end == null) return false;
        return input.startsWith(beginning) && input.endsWith(end);
    }
    
    /**
     * Returns true if the given input starts and ends with the given 'toCheck'
     * object's 'toString' value.
     * 
     * @param input   The input string
     * @param toCheck An object's 'toString' value to check for
     * 
     * @return true if the 'toCheck' object's 'toString' value is at the
     *         beginning and end of the input string
     *         
     * @since 2.5.1
     */
    public static boolean startsAndEndsWith(String input, Object toCheck) {
        if (input == null || toCheck == null) return false;
        String s = String.valueOf(toCheck);
        return input.startsWith(s) && input.endsWith(s);
    }
    
    /**
     * Returns true if the given input starts and ends with any of the given
     * 'toCheck' strings.
     * 
     * @param input   The input string
     * @param toCheck The value to check for
     * 
     * @return true if any of the 'toCheck' values are present at the beginning
     *         and the end of the input string
     *         
     * @since 2.5.1
     */
    public static boolean startsAndEndsWithAny(String input, String... toCheck) {
        if (input == null || toCheck == null) return false;
        for (String s : toCheck) {
            if (input.startsWith(s) && input.endsWith(s))
                return true;
        }
        return false;
    }
    
    /**
     * Returns true if the given input starts and ends with any of the given
     * 'toCheck' strings.
     * 
     * @param input   The input string
     * @param toCheck The value to check for
     * 
     * @return true if any of the 'toCheck' values are present at the beginning
     *         and the end of the input string
     *         
     * @since 2.5.1
     */
    public static boolean startsAndEndsWithAny(String input, Object... toCheck) {
        if (input == null || toCheck == null) return false;
        for (Object s : toCheck) {
            final String toString = String.valueOf(s);
            if (input.startsWith(toString) && input.endsWith(toString))
                return true;
        }
        return false;
    }
    
	//================
	// Longest Length
	//================
	
	/**
	 * Returns the length of the 'toString' String for each given object.
	 * 
	 * @param objects The length of the longest 'toString' String
	 * @return The length of the string with the longest toString value
	 * @since 1.5.2
	 */
	public static int getLongestLength(Collection<?> objects) {
		return tryGet(map(objects, toStringLen).max(compareInts), -1);
	}
	
	/**
	 * Returns the length of the 'toString' String for each given object.
	 * 
	 * @param objects The length of the longest 'toString' String
	 * @return The length of the string with the longest toString value
	 * @since 1.5.2
	 */
	public static int getLongestLength(String... strings) {
		return tryGet(map(strings, toStringLen).max(compareInts), -1);
	}
	
	/**
	 * Returns the longest 'toString' length across each of the given objects.
	 * 
	 * @param objects The length of the longest 'toString' String
	 * @return The length of the string with the longest toString value
	 * @since 1.5.2
	 */
	public static int getLongestLength(Object... objects) {
		return tryGet(map(objects, toStringLen).max(compareInts), -1);
	}
	
	/**
	 * Returns the longest 'toString' length from the given two objects.
	 * 
	 * @param objects The length of the longest 'toString' String
	 * @return The length of the string with the longest toString value
	 * @since 2.2.0
	 */
	public static int getLongestLength(Object a, Object b) {
		var aLen = String.valueOf(a).length();
		var bLen = String.valueOf(b).length();
		return Math.max(aLen, bLen);
	}
	
	/**
	 * Returns the shortest 'toString' length from the given two objects.
	 * 
	 * @param objects The length of the shortest 'toString' String
	 * @return The length of the string with the shortest toString value
	 * @since 2.2.0
	 */
	public static int getShortestLength(Object a, Object b) {
		var aLen = String.valueOf(a).length();
		var bLen = String.valueOf(b).length();
		return Math.min(aLen, bLen);
	}
	
	/**
	 * Returns the longest toString String value out of all Objects in the given collection.
	 * 
	 * @param objects a collection of objects
	 * @return String The longest 'toString' String
	 * @since 1.1.1
	 */
	public static String getLongest(Collection<?> objects) {
		return tryGet(map(objects, toString).max(strlen), null);
	}
	
	/**
	 * Returns the longest String out of all given.
	 * 
	 * @param strings An array of passed Strings
	 * @return String The longest String
	 * @since 1.0.0
	 */
	public static String getLongest(String... strings) {
		return tryGet(map(strings, toString).max(strlen), null);
	}
	
	/** Capitalizes the first letter in the given string. */
	public static String capitalFirst(String in) {
		if (in == null || in.isEmpty()) return in;
		String val = String.join(String.valueOf(Character.toUpperCase(in.charAt(0))), in.substring(1, in.length()));
		return val;
	}
	
	/** Returns a string made from the given char repeated num times. */
	public static String repeatString(String in, int num) {
		return new String(new char[num]).replace("\0", in);
	}
	
	/** Returns the number of spaces within the string. */
	public static int countSpaces(String in) {
		return countChar(in, ' ');
	}
	
	/**
	 * Returns the number of times the given string contains the given
	 * test char.
	 */
	public static int countChar(String in, char test) {
		if (in == null) return -1;
		int num = 0;
		for (int i = 0; i < in.length(); i++)
			if (in.charAt(i) == test)
				num++;
		return num;
	}
	
	/**
	 * Returns a String consisting of the toString version of each object.
	 */
	public static String combineAll(Object[] in) {
		return combineAll(in, "");
	}
	
	/**
	 * Returns a String consisting of the toString version of each object
	 * with an optional spacer String in between each object.
	 */
	public static String combineAll(Object[] in, String spacer) {
		var r = sb();
		if (in == null || in.length == 0) return r.toString();
		if (in.length == 1) return r.append(in[0]).toString();
		for (int i = 0; i < in.length; i++) {
			Object s = in[i];
			r.append(s, (((i + 1) < in.length) ? spacer : ""));
		}
		return r.ts();
	}
	
	/**
	 * Returns a string made from the combination of each string in a list
	 * concatenated together.
	 */
	public static String combineAll(List<?> in) {
		return combineAll(in, "");
	}
	
	/**
	 * Returns a string made from the combination of each string in a list
	 * concatenated together with an optional spacer String in between
	 * each object.
	 */
	public static String combineAll(List<?> in, String spacer) {
		var r = sb();
		if (in == null || in.isEmpty()) return r.trim();
		if (in.size() == 1) return r.append(in.get(0)).toString();
		for (int i = 0; i < in.size(); i++) {
			Object s = in.get(i);
			r.append(s, ((i + 1 < in.size()) ? spacer : ""));
		}
		return r.trim();
	}
	
	/** Creates a substring from the given string from a given starting position until a specified substring within the original. */
	public static String subStringToString(String in, String toFind) { return subStringToString(in, 0, toFind, false); }
	/** Creates a substring from the given string from a given starting position until a specified substring within the original.
	 *  If starting from the end, substrings will attempted to be formed from the right hand side of the string going left. */
	public static String subStringToString(String in, String toFind, boolean startFromEnd) { return subStringToString(in, 0, toFind, startFromEnd); }
	/** Creates a substring from the given string from a given starting position until a specified substring within the original. */
	public static String subStringToString(String in, int startPos, String toFind) { return subStringToString(in, startPos, toFind, false); }
	/** Creates a substring from the given string from a given starting position until a specified substring within the original.
	 *  If starting from the end, substrings will attempted to be formed from the right hand side of the string going left. */
	public static String subStringToString(String in, int startPos, String toFind, boolean startFromEnd) {
		if (in == null) return in;
		if (startPos < 0 || startPos > in.length()) return in;
		String from = startFromEnd ? in.substring(startPos, in.length()) : in.substring(startPos);
		int index = startFromEnd ? findStartingIndex(from, toFind, true) : findStartingIndex(from, toFind);
		return (index >= 0) ? (startFromEnd ? from.substring(index + 1, in.length()) : from.substring(0, index)) : from;
	}
	
	/** Creates a substring from the given string from a given starting position until a specified substring within the original.
	 *  If starting from the end, substrings will attempted to be formed from the right hand side of the string going left. */
	public static String subStringToStringNull(String in, int startPos, String toFind, boolean startFromEnd) {
		if (in == null) return null;
		if (startPos < 0 || startPos > in.length()) return null;
		String from = startFromEnd ? in.substring(startPos, in.length()) : in.substring(startPos);
		int index = startFromEnd ? findStartingIndex(from, toFind, true) : findStartingIndex(from, toFind);
		return (index >= 0) ? (startFromEnd ? from.substring(index + 1, in.length()) : from.substring(0, index)) : null;
	}
	
	@Experimental(since = "1.1.1")
	/** Takes in an array of Strings for which to find substring matches within the given 'in' string. */
	public static String subStringToString(String in, String... toFind) {
		if (toFind.length == 0) return in;
		for (String s : toFind) {
			String found = subStringToStringNull(in, 0, s, false);
			if (found != null) return found;
		}
		return in;
	}
	
	public static String subStringAfter(String in, String toFind) { return subStringAfterString(in, 0, toFind); }
	public static String subStringAfterString(String in, int startPos, String toFind) {
		if (in == null) return in;
		if (startPos < 0 || startPos > in.length()) return in;
		int index = findIndexAfter(in, toFind);
		return (index >= 0) ? in.substring(index) : in;
	}
	
	/**
	 * Creates a substring from a given string ending at the first space
	 * found from the given starting position.
	 */
	public static String subStringToSpace(String in, int startPos) {
		return subStringToSpace(in, startPos, false);
	}
	
	/**
	 * Creates a substring from a given string ending at the first space
	 * found from the given starting position.
	 */
	public static String subStringToSpace(String in, int startPos, boolean startFromEnd) {
		if (in == null || in.isEmpty()) return in;
		int pos = (startFromEnd) ? in.length() - 1 : startPos;
		
		if (startFromEnd) {
			while ((pos > startPos || pos >= 0) && in.charAt(pos) != ' ') pos--;
			return in.substring(pos + 1, in.length());
		}
		
		while (pos < in.length() && in.charAt(pos) != ' ') pos++;
		return in.substring(startPos, pos);
	}
	
	/**
	 * Returns the index for the position in a string where another string
	 * is located within it.
	 */
	public static int findStartingIndex(String toSearch, String toFind) {
		return findStartingIndex(toSearch, toFind, false);
	}
	
	/**
	 * Returns the index for the position in a string where another string
	 * is located within it. Can specify whether the search starts from
	 * the front or back.
	 */
	public static int findStartingIndex(String toSearch, String toFind, boolean fromBack) {
		if (fromBack) {
			var s = sb(toSearch).reverse();
			var f = sb(toFind).reverse().toString();
			var index = s.indexOf(f);
			return (index > 0) ? toSearch.length() - toFind.length() - s.indexOf(f) : -1;
		}
		return toSearch.indexOf(toFind);
	}
	
	/**
	 * Returns the index in the toSearch string that is exactly 1 index
	 * after the toFind String's last index. If the toSearch String does
	 * not actually contain the toFind String, -1 is returned instead.
	 */
	public static int findIndexAfter(String toSearch, String toFind) {
		if (toSearch != null && toFind != null && !toSearch.isEmpty() && !toFind.isEmpty() && toSearch.contains(toFind)) {
			int index = findStartingIndex(toSearch, toFind);
			return index + toFind.length();
		}
		return -1;
	}
	
	/**
	 * Returns the index in the toSearch string that is exactly 1 index
	 * after the toFind String's last index. If the toSearch String does
	 * not actually contain the toFind String, -1 is returned instead.
	 */
	public static int findIndexAfter(String toSearch, String toFind, boolean fromBack) {
		if (toSearch != null && toFind != null && !toSearch.isEmpty() && !toFind.isEmpty() && toSearch.contains(toFind)) {
			int index = findStartingIndex(toSearch, toFind, fromBack);
			return index + toFind.length();
		}
		return -1;
	}
	
	/**
	 * Returns true if the given char matches any of the test chars. If no
	 * test chars were given, false is returned by default.
	 */
	public static boolean testChar(char charIn, char... tests) {
		for (char c : tests) {
			if (charIn == c) return true;
		}
		return false;
	}
	
	/**
	 * Returns the given char which matches the corresponding test char.
	 * If no test chars were given, null is returned by default.
	 */
	public static Character testCharR(char charIn, char... tests) {
		for (char c : tests) {
			if (charIn == c) return c;
		}
		return null;
	}
	
	//--------------------------------------------------
	// StringUtil Helper Class - CondensedStringBuilder
	//--------------------------------------------------
	
	/**
	 * As so many operations within StringUtil make use of the
	 * StringBuilder class for optimizations, ShortSB is intended to
	 * provide an internal shorthand interface to wrap the functionality
	 * of a StringBuilder using extremely short method names in order to
	 * minimize code footprint.
	 * <p>
	 * This class is not intended to used outside of the StringUtil
	 * context. Furthermore, even within StringUtil this class is written
	 * to maximize functionality, not readability!
	 * 
	 * @author Hunter Bragg
	 */
	private static final class ShortSB {
		private StringBuilder sb;
		private String s; //separator
		
		private ShortSB() { sb = new StringBuilder(); }
		private ShortSB(String in) { sb = new StringBuilder(in); }
		private static ShortSB sep(String in) { var s=new ShortSB();s.s=in;return s; }
		private static ShortSB sep(String in, String sep) { var s=new ShortSB(in);s.s=sep;return s; }
		
		@Override public String toString() { return sb.toString(); }
		
		String r() { return(e())?sb.toString():(s!=null)?sub(0,l()-l(s)):sb.toString(); }
		String r(String sep) { return(e())?sb.toString():sub(0,l()-l(sep)); }
		
		private static int l(String in) { return in.length(); }
		
		ShortSB c() { sb = new StringBuilder(); return this; }
		ShortSB clear() { return c(); }
		ShortSB a(Object o) { sb.append(o); return this; }
		ShortSB a(Object a, Object b) { sb.append(a);sb.append(b);return this; }
		ShortSB a(Object a, Object b, Object c) { sb.append(a);sb.append(b);sb.append(c);return this; }
		ShortSB a(Object... a) { for(var o:a)sb.append(o);return this; }
		ShortSB append(Object o) { sb.append(o); return this; }
		ShortSB append(Object a, Object b) { sb.append(a);sb.append(b);return this; }
		ShortSB append(Object a, Object b, Object c) { sb.append(a);sb.append(b);sb.append(c);return this; }
		ShortSB append(Object... a) { for(var o:a)sb.append(o);return this; }
		ShortSB rev() { sb.reverse(); return this; }
		ShortSB reverse() { sb.reverse(); return this; }
		int indexOf(String in) { return sb.indexOf(in); }
		int l() { return sb.length(); }
		int length() { return sb.length(); }
		boolean e() { return sb.isEmpty(); }
		boolean isEmpty() { return sb.isEmpty(); }
		String sub(int index) { return sb.substring(index); }
		String sub(int start, int end) { return sb.substring(start, end); }
		String ts() { return sb.toString(); }
		String trim() { return sb.toString().trim(); }
		StringBuilder getSB() { return sb; }
	}
	
	private static ShortSB sb() { return new ShortSB(); }
	private static ShortSB sb(String in) { return new ShortSB(in); }
	private static ShortSB sbs(String sep) { return ShortSB.sep(sep); }
	private static ShortSB sbss(String in, String sep) { return ShortSB.sep(in, sep); }
	
}
