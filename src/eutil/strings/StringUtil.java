package eutil.strings;

import static eutil.EUtil.*;
import static eutil.lambda.Comparisons.*;

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
 * @author Hunter Bragg
 * @since 1.2.0
 */
public final class StringUtil {
	
	private StringUtil() {}
	
	/**
	 * Returns the string version of the object. This function also
	 * accounts for null values.
	 */
	public static String toString(Object o) {
		return toString(o, "null");
	}
	
	/**
	 * Returns the string version of the object. This function also
	 * accounts for null values. If the value is null, "null" is returned.
	 */
	public static String toString(Object o, String defaultVal) {
		return (o != null) ? o.toString() : defaultVal;
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
	
	public static String toString(boolean[] e, String separator) { var s=sb(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r(); }
	public static String toString(byte[] e, String separator) { var s=sb(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(char[] e, String separator) { var s=sb(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(int[] e, String separator) { var s=sb(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(short[] e, String separator) { var s=sb(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(long[] e, String separator) { var s=sb(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(float[] e, String separator) { var s=sb(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(double[] e, String separator) { var s=sb(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static <E> String toString(E[] e, String separator) { var s=sb(separator);for(int i=0;i<e.length;i++)s.a(e[i],separator);return s.r();}
	public static String toString(List<?> e, String separator) { var s=sb(separator);for(int i=0;i<e.size();i++)s.a(e.get(i),separator);return s.r();}
	public static String toString(Iterator<?> e, String separator) { var s=sb(separator);while(e.hasNext())s.a(e.next(),separator);return s.r(); }
	public static String toString(Enumeration<?> e, String separator) { var s=sb(separator);while(e.hasMoreElements())s.a(e.nextElement(),separator);return s.r(); }
	public static String toString(Map<?, ?> e, String separator) {
		var s = sb(separator);
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
		var out = sb();
		for (int i = 0; i < e.length; i++) {
			out.a(mapper.apply(e[i]));
			out.a(separator);
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
	 * Returns the longest String out of all in the given collection.
	 * 
	 * @param strings a collection of strings
	 * @return String The longest String
	 * @since 1.1.1
	 */
	public static String getLongest(Collection<String> strings) {
		return tryGet(filterNull(strings).max(strlen));
	}
	
	/**
	 * Returns the longest String out of all given.
	 * 
	 * @param strings An array of passed Strings
	 * @return String The longest String
	 * @since 1.0.0
	 */
	public static String getLongest(String... strings) {
		return tryGet(filterNull(strings).max(strlen));
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
			r.append(s, ((i + 1 < in.length) ? spacer : ""));
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
			return toSearch.length() - toFind.length() - s.indexOf(f);
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
		private StringBuilder b;
		private String s; //separator
		
		private ShortSB() { b = new StringBuilder(); }
		private ShortSB(String in) { b = new StringBuilder(in); }
		private static ShortSB sep(String in) { var s=new ShortSB();s.s=in;return s; }
		private static ShortSB sep(String in, String sep) { var s=new ShortSB(in);s.s=sep;return s; }
		
		@Override public String toString() { return b.toString(); }
		
		String r() { return(e())?b.toString():(s!=null)?sub(0,l()-l(s)):b.toString(); }
		String r(String sep) { return(e())?b.toString():sub(0,l()-l(sep)); }
		
		private static int l(String in) { return in.length(); }
		
		ShortSB c() { b = new StringBuilder(); return this; }
		ShortSB clear() { return c(); }
		ShortSB a(Object o) { b.append(o); return this; }
		ShortSB a(Object... a) { for(var o:a)b.append(o);return this; }
		ShortSB append(Object o) { b.append(o); return this; }
		ShortSB append(Object... a) { for(var o:a)b.append(o);return this; }
		ShortSB rev() { b.reverse(); return this; }
		ShortSB reverse() { b.reverse(); return this; }
		int indexOf(String in) { return b.indexOf(in); }
		int l() { return b.length(); }
		int length() { return b.length(); }
		boolean e() { return b.isEmpty(); }
		boolean isEmpty() { return b.isEmpty(); }
		String sub(int index) { return b.substring(index); }
		String sub(int start, int end) { return b.substring(start, end); }
		String ts() { return b.toString(); }
		String trim() { return b.toString().trim(); }
		StringBuilder getSB() { return b; }
	}
	
	private static ShortSB sb() { return new ShortSB(); }
	private static ShortSB sb(String in) { return new ShortSB(in); }
	private static ShortSB sbs(String sep) { return ShortSB.sep(sep); }
	private static ShortSB sbss(String in, String sep) { return ShortSB.sep(in, sep); }
	
}
