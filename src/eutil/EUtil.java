package eutil;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;
import storageUtil.EArrayList;

/** A static helper library containing a number of useful functions including but not limited to:
 * 
 *  <blockquote><pre>
 *  Object relations
 *  List checks
 *  String operations
 *  File checks
 *  Array and List conversions
 *  Lambda operations
 *  Try-catch operataions
 *  </pre></blockquote><p>
 *  
 *  @author Hunter Bragg
 */
public class EUtil {
	
	//---------
	// Objects
	//---------
	
	/** Returns true if any of the given objects are null. */
	public static boolean isNull(Object obj, Object... objsIn) {
		boolean val = false;
		for (Object o : add(obj, objsIn)) { if (o == null) { val = true; } }
		return val;
	}
	
	/** Returns false if any of the given objects are null. */
	public static boolean notNull(Object obj, Object... objsIn) { return !isNull(obj, objsIn); }
	
	/** Returns true if the given objects are equal to each other, this method also accounts for null objects. */
	public static boolean isEqual(Object a, Object b) {
		return (a != null) ? a.equals(b) : b == null;
	}
	
	//-------------
	// List Checks
	//-------------
	
	/** Returns true if any values within either list match. */
	public static <e> boolean findMatch(List<e> objsIn, List<e> list) {
		if (list != null && objsIn.size() > 0) {
			for (e element : list) {
				if (element == null) {
					for (e check : objsIn) {
						if (check == null) { return true; }
					}
				}
				else {
					for (e check : objsIn) {
						if (check.equals(element)) { return true; }
					}
				}
			}
		}
		return false;
	}
	
	/** A statement that returns the specified ifTrue value if any member within the given list matches the given predicate.
	 *  If no member of the list matches the predicate then the ifFalse value is returned instead. */
	public static <A, R> R forEachTest(List<A> list, Predicate<? super A> predicate, R ifTrue, R ifFalse) {
		Objects.requireNonNull(list);
		Objects.requireNonNull(predicate);
		
		for (A a : list) {
			if (predicate.test(a)) { return ifTrue; }
		}
		
		return ifFalse;
	}
	
	/** Returns the first member in a list that matches the given predicate. If no object matches, null is returned. */
	public static <E> E getFirst(List<E> list, Predicate<? super E> predicate) {
		Objects.requireNonNull(predicate);
		
		if (list != null) {
			for (E e : list) {
				if (predicate.test(e)) { return e; }
			}
		}
		
		return null;
	}
	
	/** Returns the first member in the list that matches the given predicate. If no object matches, the default value is returned instead. */
	public static <E> E getFirst(List<E> list, Predicate<? super E> predicate, E defaultVal) {
		E val = getFirst(list, predicate);
		return (val != null) ? val : defaultVal;
	}
	
	/** Returns the last member in a list that matches the given predicate. If no object matches, null is returned. */
	public static <E> E getLast(List<E> list, Predicate<? super E> predicate) {
		Objects.requireNonNull(predicate);
		
		if (list != null) {
			for (int i = list.size(); i >= 0; i--) {
				E e = list.get(i);
				if (predicate.test(e)) { return e; }
			}
		}
		
		return null;
	}
	
	/** Returns the last member in the list that matches the given predicate. If not object matches, the default value is returned instead. */
	public static <E> E getLast(List<E> list, Predicate<? super E> predicate, E defaultVal) {
		E val = getLast(list, predicate);
		return (val != null) ? val : defaultVal;
	}
	
	//----------------
	// String Helpers
	//----------------
	
	public static String toString(Object o) { return toString(o, "null"); }
	public static String toString(Object o, String defaultVal) { return (o != null) ? o.toString() : defaultVal; }
	
	/** Returns the longest String out of all given. */
	public static String getLongest(String... strings) {
		int longest = 0;
		String theString = null;
		
		for (String s : strings) {
			if (s != null) {
				String temp = s.replace("\t", "        ");
				if (temp.length() > longest) {
					longest = temp.length();
					theString = temp;
				}
			}
		}
		
		return theString;
	}
	
	/** Captializes the first letter in the given string. */
	public static String capitalFirst(String in) {
		if (in != null) {
			String val = "";
			if (in.length() > 0) {
				val += Character.toUpperCase(in.charAt(0));
				val += in.substring(1, in.length());
				return val;
			}
		}
		return in;
	}
	
	/** Returns a string made from the given char repeated num times. */
	public static String repeatString(String in, int num) {
		return new String(new char[num]).replace("\0", in);
	}
	
	/** Returns the number of spaces within the string. */
	public static int countSpaces(String in) { return countChar(in, ' '); }
	/** Returns the number of times the given string contains the given test char. */
	public static int countChar(String in, char test) {
		if (in != null) {
			int num = 0;
			for (int i = 0; i < in.length(); i++) {
				if (in.charAt(i) == test) { num++; }
			}
			return num;
		}
		return -1;
	}
	
	/** Returns a String consisting of the toString version of each object. */
	public static String combineAll(Object[] in) { return combineAll(in, ""); }
	/** Returns a String consisting of the toString version of each object with an optional spacer String in between each object. */
	public static String combineAll(Object[] in, String spacer) {
		String r = "";
		if (in != null) {
			if (in.length > 1) {
				for (int i = 0; i < in.length; i++) {
					Object s = in[i];
					r += s + ((i + 1 < in.length) ? String.valueOf(spacer) : "");
				}
			}
			else if (in.length == 1) { r += in[0]; }
		}
		return r;
	}
	
	/** Returns a string made from the combination of each string in a list concatenated together. */
	public static String combineAll(List in) { return combineAll(in, ""); }
	/** Returns a string made from the combination of each string in a list concatenated together with an optional spacer String
	 *  in between each object. */
	public static String combineAll(List in, String spacer) {
		String r = "";
		if (in != null) {
			if (in.size() > 1) {
				for (int i = 0; i < in.size(); i++) {
					Object s = in.get(i);
					r += s + ((i + 1 < in.size()) ? String.valueOf(spacer) : "");
				}
			}
			else if (in.size() == 1) { r += in.get(0); }
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
		if (in != null) {
			if (startPos >= 0 && startPos <= in.length()) {
				String from = startFromEnd ? in.substring(startPos, in.length()) : in.substring(startPos);
				int index = startFromEnd ? findStartingIndex(from, toFind, true) : findStartingIndex(from, toFind);
				return (index >= 0) ? (startFromEnd ? from.substring(index + 1, in.length()) : from.substring(0, index)) : from;
			}
		}
		return in;
	}
	
	public static String subStringAfter(String in, String toFind) { return subStringAfterString(in, 0, toFind); }
	public static String subStringAfterString(String in, int startPos, String toFind) {
		if (in != null) {
			if (startPos >= 0 && startPos <= in.length()) {
				int index = findIndexAfter(in, toFind);
				return (index >= 0) ? in.substring(index) : in;
			}
		}
		return in;
	}
	
	/** Creates a substring from a given string ending at the first space found from the given starting position. */
	public static String subStringToSpace(String in, int startPos) { return subStringToSpace(in, startPos, false); }
	/** Creates a substring from a given string ending at the first space found from the given starting position. */
	public static String subStringToSpace(String in, int startPos, boolean startFromEnd) {
		if (in != null && !in.isEmpty()) {
			int pos = (startFromEnd) ? in.length() - 1 : startPos;
			
			if (startFromEnd) {
				while ((pos > startPos || pos >= 0) && in.charAt(pos) != ' ') {
					pos--;
				}
				return in.substring(pos + 1, in.length());
			}
			else {
				while (pos < in.length() && in.charAt(pos) != ' ') {
					pos++;
				}
				return in.substring(startPos, pos);
			}
		}
		return in;
	}
	
	/** Returns the index for the position in a string where another string is located within it. */
	public static int findStartingIndex(String toSearch, String toFind) { return findStartingIndex(toSearch, toFind, false); }
	
	/** Returns the index for the position in a string where another string is located within it. Can specifiy whether the search starts from the front or back. */
	public static int findStartingIndex(String toSearch, String toFind, boolean fromBack) {
		if (toSearch != null && !toSearch.isEmpty() && toFind != null && toFind.length() <= toSearch.length()) {
			if (!fromBack) {
				String cur = "";
				int index = 0;
				int j = 0;
				
				for (int i = 0; i <= toSearch.length() - 1; i++) {
					if (cur.equals(toFind)) { return index; }
					if (toSearch.charAt(i) == toFind.charAt(j)) {
						cur += toSearch.charAt(i);
						j++;
					}
					else if (toSearch.charAt(i) == toFind.charAt(0)) {
						cur = "" + toSearch.charAt(i);
						index = i + 1;
						j = 1;
					}
					else {
						cur = "";
						index = i + 1;
						j = 0;
					}
				}
			}
			else {
				try {
					String cur = "";
					int index = toSearch.length() - 1;
					int j = toFind.length() - 1;
					
					for (int i = toSearch.length() - 1; i >= 0; i--) {
						if (cur.equals(toFind)) { return index; }
						if (j < 0) {
							cur = "";
							index = i - 1;
							j = toFind.length() - 1;
						}
						else if (toSearch.charAt(i) == toFind.charAt(j)) {
							cur += toSearch.charAt(i);
							j--;
						}
						else {
							cur = "";
							index = i - 1;
							j = toFind.length() - 1;
						}
					}
				}
				catch (Exception e) { e.printStackTrace(); }
			}
		}
		return -1;
	}
	
	/** Returns the index in the toSearch string that is exactly 1 index after the toFind String's last index.
	 *  If the toSearch String does not actually contain the toFind String, -1 is returned instead. */
	public static int findIndexAfter(String toSearch, String toFind) {
		if (toSearch != null && toFind != null && !toSearch.isEmpty() && !toFind.isEmpty() && toSearch.contains(toFind)) {
			int index = findStartingIndex(toSearch, toFind);
			return index + toFind.length();
		}
		return -1;
	}
	
	/** Returns the index in the toSearch string that is exactly 1 index after the toFind String's last index.
	 *  If the toSearch String does not actually contain the toFind String, -1 is returned instead. */
	public static int findIndexAfter(String toSearch, String toFind, boolean fromBack) {
		if (toSearch != null && toFind != null && !toSearch.isEmpty() && !toFind.isEmpty() && toSearch.contains(toFind)) {
			int index = findStartingIndex(toSearch, toFind, fromBack);
			return index + toFind.length();
		}
		return -1;
	}
	
	/** Returns true if the given char matches any of the test chars. If no test chars were given, false is returned by default. */
	public static boolean testChar(char charIn, char... tests) {
		for (char c : tests) {
			if (charIn == c) { return true; }
		}
		return false;
	}
	
	/** Returns the given char which matches the corresponding test char. If no test chars were given, null is returned by default. */
	public static Character testCharR(char charIn, char... tests) {
		for (char c : tests) {
			if (charIn == c) { return c; }
		}
		return null;
	}
	
	//-------------
	// File Checks
	//-------------
	
	/** Returns true if the given file is not null and actually exists on the system. */
	public static boolean fileExists(File f) { return (f != null && f.exists()); }
	
	//---------------
	// Array Helpers
	//---------------
	
	public static String toString(boolean[] e) { return toString(e, " "); }
	public static String toString(byte[] e) { return toString(e, " "); }
	public static String toString(char[] e) { return toString(e, " "); }
	public static String toString(int[] e) { return toString(e, " "); }
	public static String toString(short[] e) { return toString(e, " "); }
	public static String toString(long[] e) { return toString(e, " "); }
	public static String toString(float[] e) { return toString(e, " "); }
	public static String toString(double[] e) { return toString(e, " "); }
	public static <E> String toString(E[] e) { return toString(e, " "); }
	public static String toString(List<?> e) { return toString(e, " "); }
	
	public static String toString(boolean[] e, String separator) { String s = ""; for (int i = 0; i < e.length; i++) { s += e[i] + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	public static String toString(byte[] e, String separator) { String s = ""; for (int i = 0; i < e.length; i++) { s += e[i] + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	public static String toString(char[] e, String separator) { String s = ""; for (int i = 0; i < e.length; i++) { s += e[i] + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	public static String toString(int[] e, String separator) { String s = ""; for (int i = 0; i < e.length; i++) { s += e[i] + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	public static String toString(short[] e, String separator) { String s = ""; for (int i = 0; i < e.length; i++) { s += e[i] + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	public static String toString(long[] e, String separator) { String s = ""; for (int i = 0; i < e.length; i++) { s += e[i] + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	public static String toString(float[] e, String separator) { String s = ""; for (int i = 0; i < e.length; i++) { s += e[i] + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	public static String toString(double[] e, String separator) { String s = ""; for (int i = 0; i < e.length; i++) { s += e[i] + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	public static <E> String toString(E[] e, String separator) { String s = ""; for (int i = 0; i < e.length; i++) { s += e[i] + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	public static String toString(List<?> e, String separator) { String s = ""; for (int i = 0; i < e.size(); i++) { s += e.get(i) + separator; } return (s.isEmpty()) ? s : s.substring(0, s.length() - separator.length()); }
	
	public static Object[] toObjArr(boolean[] e) { Object[] a = new Object[e.length]; for (int i = 0; i < e.length; i++) { a[i] = e[i]; } return a; }
	public static Object[] toObjArr(byte[] e) { Object[] a = new Object[e.length]; for (int i = 0; i < e.length; i++) { a[i] = e[i]; } return a; }
	public static Object[] toObjArr(char[] e) { Object[] a = new Object[e.length]; for (int i = 0; i < e.length; i++) { a[i] = e[i]; } return a; }
	public static Object[] toObjArr(int[] e) { Object[] a = new Object[e.length]; for (int i = 0; i < e.length; i++) { a[i] = e[i]; } return a; }
	public static Object[] toObjArr(short[] e) { Object[] a = new Object[e.length]; for (int i = 0; i < e.length; i++) { a[i] = e[i]; } return a; }
	public static Object[] toObjArr(long[] e) { Object[] a = new Object[e.length]; for (int i = 0; i < e.length; i++) { a[i] = e[i]; } return a; }
	public static Object[] toObjArr(float[] e) { Object[] a = new Object[e.length]; for (int i = 0; i < e.length; i++) { a[i] = e[i]; } return a; }
	public static Object[] toObjArr(double[] e) { Object[] a = new Object[e.length]; for (int i = 0; i < e.length; i++) { a[i] = e[i]; } return a; }
	public static <E> Object[] toObjArr(E[] e) { Object[] a = new Object[e.length]; for (int i = 0; i < e.length; i++) { a[i] = e[i]; } return a; }
	
	public static void printArray(boolean[] arr) { printArray(toObjArr(arr)); }
	public static void printArray(byte[] arr) { printArray(toObjArr(arr)); }
	public static void printArray(char[] arr) { printArray(toObjArr(arr)); }
	public static void printArray(int[] arr) { printArray(toObjArr(arr)); }
	public static void printArray(short[] arr) { printArray(toObjArr(arr)); }
	public static void printArray(long[] arr) { printArray(toObjArr(arr)); }
	public static void printArray(float[] arr) { printArray(toObjArr(arr)); }
	public static void printArray(double[] arr) { printArray(toObjArr(arr)); }
	
	public static void printArray(Object[] arr) {
		if (arr != null) {
			for (Object o : arr) {
				System.out.println(o);
			}
		}
	}
	
	public static void printList(List arr) {
		if (arr != null) {
			for (Object o : arr) {
				System.out.println(o);
			}
		}
	}
	
	public static <E, T> void printArray(E[] arr, Function<? super E, ? super T> type) {
		nullDo(arr, a -> asList(a).map(type).forEach(s -> System.out.println(s)));
	}
	
	public static <E> E[] add(E obj, E[] array) {
		EArrayList<E> list = new EArrayList(obj).addA(array);
		return list.toArray(array);
	}
	
	public static <E> E[] addEnd(E obj, E[] array) {
		EArrayList<E> list = new EArrayList().addA(array);
		list.add(obj);
		return list.toArray(array);
	}
	
	public static <E> E[] addAt(E obj, int pos, E[] array) {
		EArrayList<E> list = new EArrayList().addA(array);
		if (pos >= 0 && pos < array.length + 1) {
			list.add(pos, obj);
		}
		return list.toArray(array);
	}
	
	/** Utility function to check if the values in one array match the values from another. */
	public static boolean validateArrayContents(List list1, List list2) {
		if (list1.size() != list2.size()) { return false; } //if the sizes differ, they're not the same.
		for (int i = 0; i < list1.size(); i++) {
			Object a = list1.get(i);
			Object b = list2.get(i);
			if (a != null && b != null) {
				Class c1 = list1.get(i).getClass();
				Class c2 = list2.get(i).getClass();
				if (!c1.equals(c2)) { return false; }
			}
			else if (a == null && b != null) { return false; }
			else if (a != null && b == null) { return false; }
			else { return false; }
		}
		return true;
	}
	
	//----------------
	// Lambda Helpers
	//----------------
	
	public static long length(Iterable itr) { return itr.spliterator().getExactSizeIfKnown(); }
	
	// array conversions
	
	/** Boxes a generic varags of typed-objects into a typed-array. */
	public static <E> E[] asArray(E... vals) { return asList(vals).toArray(vals); }
	
	/** Converts a generic varags of typed-objects into a typed-EArrayList. */
	public static <E> EArrayList<E> asList(E... vals) { return new EArrayList<E>(vals); }
	
	public static <E> E[] toArray(Collection<? extends E> list) {
		E[] arr = (E[]) new Object[list.size()];
		for (int i = 0; i < list.size(); i++) { arr[i] = (E) list.toArray()[i]; }
		return arr;
	}
	
	public static <E> EArrayList<E> toList(E[] arr) { return new EArrayList<E>(arr); }
	
	/** Converts a typed-array to a Stream. */
	public static <E> Stream<E> stream(E... vals) { return ((EArrayList<E>) new EArrayList<E>().add(vals)).stream(); }
	/** Converts a typed-array to a Stream that filters out null objects. */
	public static <E> Stream<E> filterNull(E... vals) { return stream(vals).filter(o -> o != null); }
	
	/** Converts a typed-array to a Stream then performs the given filter. */
	public static <E> Stream<E> filterA(Predicate<? super E> filter, E... vals) { return stream(vals).filter(filter); }
	/** Converts a typed-array to a Stream that filters out null objects then performs the given filter. */
	public static <E> Stream<E> filterNullA(Predicate<? super E> filter, E... vals) { return filterNull(vals).filter(filter); }
	/** Converts a typed-array to a Stream that maps each object to the specified type. */
	public static <E, T> Stream<T> mapA(Function<? super E, ? extends T> mapper, E... vals) { return stream(vals).map(mapper); }
	/** Performs a forEach loop across each element of a given array. */
	public static <E> void forEachA(Consumer<? super E> action, E... vals) { forEach(vals, action); }
	/** Converts a typed-array to a Stream then performs the given filter and finally performs a forEach loop on each remaining element. */
	public static <E> void filterForEachA(Predicate<? super E> filter, Consumer<? super E> action, E... vals) { filterA(filter, vals).forEach(action); }
	/** Converts a typed-array to a Stream that filters out null objects then performs a forEach loop on each remaining element. */
	public static <E> void filterNullForEachA(Consumer<? super E> action, E... vals) { filterNull(vals).forEach(action); }
	/** Converts a typed-array to a Stream that filters out null objects then performs the given filter and finally performs a forEach loop on each remaining element. */
	public static <E> void filterNullForEachA(Predicate<? super E> filter, Consumer<? super E> action, E... vals) { filterNull(vals).filter(filter).forEach(action); }
	
	public static <E, T> EArrayList<T> mapListA(Function<? super E, ? extends T> mapper, E... vals) { return stream(vals).map(mapper).collect(EArrayList.toEArrayList()); }
	
	//list conversions
	
	/** Converts a typed-Collection to a Stream then performs the given filter. */
	public static <E> Stream<E> filter(Collection<E> list, Predicate<? super E> filter) { return list.stream().filter(filter); }
	/** Converts a typed-Collection to a Stream that filters out null objects. */
	public static <E> Stream<E> filterNull(Collection<E> list) { return list.stream().filter(o -> o != null); }
	/** Converts a typed-Collection to a Stream that filters out null objects then performs the given filter. */
	public static <E> Stream<E> filterNull(Collection<E> list, Predicate<? super E> filter) { return filterNull(list).filter(filter); }
	/** Converts a typed-Collection to a Stream that maps each object to the specified type. */
	public static <E, T> Stream<T> map(Collection<E> list, Function<? super E, ? extends T> mapper) { return list.stream().map(mapper); }
	/** Performs a forEach loop on each element. Directly mapped from 'Java.util.List' */
	public static <E> void forEach(Collection<E> list, Consumer<? super E> action) { list.forEach(action); }
	/** Performs a forEach loop on each element. Then returns the specified 'returnVal' argument. */
	public static <E, R> R forEachR(Collection<E> list, Consumer<? super E> action, R returnVal) { forEach(list, action); return returnVal; }
	/** Converts a typed-Collection to a Stream then performs the given filter then finally performs a forEach loop on each remaining element. */
	public static <E> void filterForEach(Collection<E> list, Predicate<? super E> filter, Consumer<? super E> action) { filter(list, filter).forEach(action); }
	/** Converts a typed-Collection to a Stream that filters out null objects then finally performs a forEach loop on each remaining element. */
	public static <E> void filterNullForEach(Collection<E> list, Consumer<? super E> action) { filterNull(list).forEach(action); }
	/** Converts a typed-Collection to a Stream that filters out null objects then performs the given filter then finally performs a forEach loop on each remaining element. */
	public static <E> void filterNullForEach(Collection<E> list, Predicate<? super E> filter, Consumer<? super E> action) { filterNull(list).filter(filter).forEach(action); }
	/** Converts a typed-Collection to a Stream that filters out null objects then finally performs a forEach loop on each remaining element. Then returns the specified value. */
	public static <E, R> R filterNullForEachR(Collection<E> list, Consumer<? super E> action, R retVal) { filterNull(list).forEach(action); return retVal; }
	/** Converts a typed-Collection to a Stream that filters out null objects then performs the given filter then finally performs a forEach loop on each remaining element. Then returns the specified value. */
	public static <E, R> R filterNullForEachR(Collection<E> list, Predicate<? super E> filter, Consumer<? super E> action, R retVal) { filterNull(list).filter(filter).forEach(action); return retVal; }
	
	public static <E, R> EArrayList<R> mapList(Collection<E> list, Function<? super E, ? extends R> mapper) { return list.stream().map(mapper).collect(EArrayList.toEArrayList()); }
	
	/** Converts a typed-Array to a Stream then performs the given filter. */
	public static <E> Stream<E> filter(E[] arr, Predicate<? super E> filter) { return stream(arr).filter(filter); }
	/** Converts a typed-Array to a Stream that filters out null objects then performs the given filter. */
	public static <E> Stream<E> filterNull(E[] arr, Predicate<? super E> filter) { return stream(arr).filter(filter.and(o -> o != null)); }
	/** Converts a typed-Array to a Stream that maps each object to the specified type. */
	public static <E, T> Stream<T> map(E[] arr, Function<? super E, ? extends T> mapper) { return stream(arr).map(mapper); }
	/** Performs a forEach loop on each element. */
	public static <E> void forEach(E[] arr, Consumer<? super E> action) { for (E e : arr) { action.accept(e); } }
	/** Performs a forEach loop across each element of a given array if the given input is true and the list is not null. Returns provided value regardless of input. */
	public static <E, R> R forEachR(E[] arr, Consumer<? super E> action, R returnVal) { forEach(arr, action); return returnVal; }
	/** Converts a typed-Array to a Stream then performs the given filter then finally performs a forEach loop on each remaining element. */
	public static <E> void filterForEach(E[] arr, Predicate<? super E> filter, Consumer<? super E> action) { filter(arr, filter).forEach(action); }
	/** Converts a typed-Array to a Stream that filters out null objects then finally performs a forEach loop on each remaining element. */
	public static <E> void filterNullForEach(E[] arr, Consumer<? super E> action) { filterNull(arr).forEach(action); }
	/** Converts a typed-Array to a Stream that filters out null objects then performs the given filter then finally performs a forEach loop on each remaining element. */
	public static <E> void filterNullForEach(E[] arr, Predicate<? super E> filter, Consumer<? super E> action) { filterNull(arr).filter(filter).forEach(action); }
	/** Converts a typed-Array to a Stream that filters out null objects then finally performs a forEach loop on each remaining element. Then returns the specified value. */
	public static <E, R> R filterNullForEachR(E[] arr, Consumer<? super E> action, R retVal) { filterNull(arr).forEach(action); return retVal; }
	/** Converts a typed-Array to a Stream that filters out null objects then performs the given filter then finally performs a forEach loop on each remaining element. Then returns the specified value. */
	public static <E, R> R filterNullForEachR(E[] arr, Predicate<? super E> filter, Consumer<? super E> action, R retVal) { filterNull(arr).filter(filter).forEach(action); return retVal; }
	
	public static <E, R> EArrayList<R> mapList(E[] arr, Function<? super E, ? extends R> mapper) { return map(arr, mapper).collect(EArrayList.toEArrayList()); }
	
	/** Converts an array of a specified type into a Stream and performs the given filter across each element then 
	 *  finally collects the filtered data into the specified Collector. */
	public static <E, R, A> R filterInto(E[] arrIn, Predicate<? super E> filter, Collector<? super E, A, R> collector) { return filterA(filter, arrIn).collect(collector); }
	/** Converts a List of a specified type into a Stream and performs the given filter across each element then 
	 *  finally collects the filtered data into the specified Collector. */
	public static <E, R, A> R filterInto(Collection<E> listIn, Predicate<? super E> filter, Collector<? super E, A, R> collector) { return filter(listIn, filter).collect(collector); }
	
	public static <E, A> EArrayList<E> filterAsList(E[] arrIn, Predicate<? super E> filter) { return filterA(filter, arrIn).collect(EArrayList.toEArrayList()); }
	public static <E, A> EArrayList<E> filterAsList(Collection<E> listIn, Predicate<? super E> filter) { return filter(listIn, filter).collect(EArrayList.toEArrayList()); }
	
	//array forEach
	
	public static void forEach(boolean[] arr, Consumer<? super Boolean> action) { for (boolean e : arr) { action.accept(e); } }
	public static void forEach(byte[] arr, Consumer<? super Byte> action) { for (byte e : arr) { action.accept(e); } }
	public static void forEach(char[] arr, Consumer<? super Character> action) { for (char e : arr) { action.accept(e); } }
	public static void forEach(short[] arr, Consumer<? super Short> action) { for (short e : arr) { action.accept(e); } }
	public static void forEach(int[] arr, Consumer<? super Integer> action) { for (int e : arr) { action.accept(e); } }
	public static void forEach(long[] arr, Consumer<? super Long> action) { for (long e : arr) { action.accept(e); } }
	public static void forEach(float[] arr, Consumer<? super Float> action) { for (float e : arr) { action.accept(e); } }
	public static void forEach(double[] arr, Consumer<? super Double> action) { for (double e : arr) { action.accept(e); } }
	
	//array forEach Returns
	
	public static <R> R forEachR(boolean[] arr, Consumer<? super Boolean> action, R returnVal) { for (boolean e : arr) { action.accept(e); } return returnVal; }
	public static <R> R forEachR(byte[] arr, Consumer<? super Byte> action, R returnVal) { for (byte e : arr) { action.accept(e); } return returnVal; }
	public static <R> R forEachR(char[] arr, Consumer<? super Character> action, R returnVal) { for (char e : arr) { action.accept(e); } return returnVal; }
	public static <R> R forEachR(short[] arr, Consumer<? super Short> action, R returnVal) { for (short e : arr) { action.accept(e); } return returnVal; }
	public static <R> R forEachR(int[] arr, Consumer<? super Integer> action, R returnVal) { for (int e : arr) { action.accept(e); } return returnVal; }
	public static <R> R forEachR(long[] arr, Consumer<? super Long> action, R returnVal) { for (long e : arr) { action.accept(e); } return returnVal; }
	public static <R> R forEachR(float[] arr, Consumer<? super Float> action, R returnVal) { for (float e : arr) { action.accept(e); } return returnVal; }
	public static <R> R forEachR(double[] arr, Consumer<? super Double> action, R returnVal) { for (double e : arr) { action.accept(e); } return returnVal; }
	
	//if checks
	
	/** Performs a forEach loop across each element of a given list if the given input is true and the list is not null. Returns true if the given input is true. */
	public static <E> boolean ifForEach(boolean check, Collection<E> list, Consumer<? super E> action) { if (check && notNull(list)) { list.forEach(action); return true; } return false; }
	public static <E> boolean ifForEach(boolean check, E[] arr, Consumer<? super E> action) { if (check && notNull(arr)) { forEach(arr, action); return true; } return false; }
	public static boolean ifForEach(boolean check, boolean[] arr, Consumer<? super Boolean> action) { if (check && notNull(arr)) { forEach(arr, action); return true; } return false; }
	public static boolean ifForEach(boolean check, byte[] arr, Consumer<? super Byte> action) { if (check && notNull(arr)) { forEach(arr, action); return true; } return false; }
	public static boolean ifForEach(boolean check, char[] arr, Consumer<? super Character> action) { if (check && notNull(arr)) { forEach(arr, action); return true; } return false; }
	public static boolean ifForEach(boolean check, short[] arr, Consumer<? super Short> action) { if (check && notNull(arr)) { forEach(arr, action); return true; } return false; }
	public static boolean ifForEach(boolean check, int[] arr, Consumer<? super Integer> action) { if (check && notNull(arr)) { forEach(arr, action); return true; } return false; }
	public static boolean ifForEach(boolean check, long[] arr, Consumer<? super Long> action) { if (check && notNull(arr)) { forEach(arr, action); return true; } return false; }
	public static boolean ifForEach(boolean check, float[] arr, Consumer<? super Float> action) { if (check && notNull(arr)) { forEach(arr, action); return true; } return false; }
	public static boolean ifForEach(boolean check, double[] arr, Consumer<? super Double> action) { if (check && notNull(arr)) { forEach(arr, action); return true; } return false; }
	
	/** One line if-else statement. Returns provided return value regardless of input. */
	public static <E, R> R ifElseR(boolean check, Runnable ifTrue, Runnable ifFalse, R returnVal) {
		if (check && ifTrue != null) { ifTrue.run(); }
		else if (ifFalse != null) { ifFalse.run(); }
		return returnVal;
	}
	
	/** One line if-else statement. Returns true if input equates to true. */
	public static <E, R> boolean ifElse(boolean check, Runnable ifTrue, Runnable ifFalse) {
		if (check && ifTrue != null) { ifTrue.run(); return true; }
		else if (ifFalse != null) { ifFalse.run(); }
		return false;
	}
	
	//null checks
	
	/** A statement that performs the following action on the given object if the object is not null. */
	public static <E> boolean nullDo(E obj, Consumer<? super E> action) { if (notNull(obj)) { action.accept(obj); return true; } return false; }
	/** Returns false if either object was null. If either is null, no action is performed. */
	public static <E, A> boolean nullDo(E obj1, A obj2, BiConsumer<? super E, ? super A> action) { if (notNull(obj1, obj2)) { action.accept(obj1, obj2); return true; } return false; }
	/** Returns the provided value regardless of inputs. If object is null, no action is performed. */
	public static <E, R> E nullDoR(E obj, Consumer<? super E> action) { if (notNull(obj)) { action.accept(obj); } return obj; }
	/** Returns the provided value regardless of inputs. If object is null, no action is performed. */
	public static <E, R> R nullDoR(E obj, Consumer<? super E> action, R returnVal) { if (notNull(obj)) { action.accept(obj); } return returnVal; }
	/** Returns the provided value regardless of inputs. If either object is null, no action is performed. */
	public static <E, A, R> E nullDoR(E obj1, A obj2, BiConsumer<? super E, ? super A> action) { if (notNull(obj1, obj2)) { action.accept(obj1, obj2); } return obj1; }
	/** Returns the provided value regardless of inputs. If either object is null, no action is performed. */
	public static <E, A, R> R nullDoR(E obj1, A obj2, BiConsumer<? super E, ? super A> action, R returnVal) { if (notNull(obj1, obj2)) { action.accept(obj1, obj2); } return returnVal; }
	/** A statement that returns the result of a given function if the given object is not null. */
	public static <E, R> R nullApplyR(E obj, Function<? super E, R> function) { return (notNull(obj)) ? function.apply(obj) : null; }
	/** A statement that returns the result of a given function if the given object is not null. */
	public static <E, R> R nullApplyR(E obj, Function<? super E, R> function, R defaultVal) { return (notNull(obj)) ? function.apply(obj) : defaultVal; }
	/** A statement that returns the result of a given bifunction if the given object is not null. */
	public static <E, A, R> R nullApplyR(E obj1, A obj2, BiFunction<? super E, ? super A, R> function, R defaultVal) { return (notNull(obj1, obj2)) ? function.apply(obj1, obj2) : defaultVal; }
	
	//repeaters
	
	public static <E> void repeat(Runnable func, int times) { for (int i = 0; i < times; i++) { func.run(); } }
	public static <E, R> R repeatR(Runnable func, int times, R returnVal) { repeat(func, times); return returnVal; }
	public static <E> void repeat(E object, Consumer<? super E> action, int times) { for (int i = 0; i < times; i++) { action.accept(object); } }

	//contains
	
	public static <E> boolean contains(E[] arr, E value) { for (E e : arr) { if (isEqual(e, value)) { return true; } } return false; }
	public static <E> boolean contains(Collection<E> arr, E value) { for (E e : arr) { if (isEqual(e, value)) { return true; } } return false; }
	public static <E> E containsR(E[] arr, E value) { for (E e : arr) if (isEqual(e, value)) { return e; } return null; }
	public static <E> E containsR(Collection<E> arr, E value) { for (E e : arr) if (isEqual(e, value)) { return e; } return null; }
	
	//----------------
	// Try Statements
	//----------------
	
	public static boolean tryCode(Runnable func) { try { func.run(); return true; } catch (Throwable e) { e.printStackTrace(); } return false; }
	public static <R> R tryCodeR(Runnable func, R returnVal) { try { func.run(); } catch (Throwable e) { e.printStackTrace(); } return returnVal; }
	
	public static boolean tryIfCode(boolean check, Runnable func) {
		try {
			if (check) {
				func.run();
				return true;
			}
		}
		catch(Throwable e) { e.printStackTrace(); }
		return false;
	}
	
	public static <R> R tryIfCodeR(boolean check, Runnable func, R returnVal) {
		try {
			if (check) { func.run(); }
		}
		catch(Throwable e) { e.printStackTrace(); }
		return returnVal;
	}
	
	/** Attempts to perform the given function and returns its result.
	 *  If either the check if false, the function call throws an error, or if the function returns null, null will be returned instead. */
	public static <R> R tryIfCodeR(boolean check, Function<Object, R> func) {
		R val = null;
		if (check) { 
			try {
				val = func.apply(null);
			}
			catch (Throwable e) { e.printStackTrace(); }
		}
		return val;
	}
	
	/** Attempts to perform the given function and returns its result.
	 *  If either the check if false, the function call throws an error, or if the function returns null, the defaultVal will be returned instead. */
	public static <R> R tryIfCodeR(boolean check, Function<Object, R> func, R defaultVal) {
		R val = defaultVal;
		if (check) { 
			try {
				val = func.apply(null);
			}
			catch (Throwable e) { e.printStackTrace(); }
		}
		return val;
	}
	
	/** Attempts to perform the given function on a specified object and returns its result.
	 *  If either the check if false, the function call throws an error, or if the function returns null, null will be returned instead. */
	public static <E, R> R tryIfCodeR(boolean check, E object, Function<E, R> func) {
		R val = null;
		if (check) { 
			try {
				val = func.apply(object);
			}
			catch (Throwable e) { e.printStackTrace(); }
		}
		return val;
	}
	
	/** Attempts to perform the given function on a specified object and returns its result.
	 *  If either the check if false, the function call throws an error, or if the function returns null, the defaultVal will be returned instead. */
	public static <E, R> R tryIfCodeR(boolean check, E object, Function<E, R> func, R defaultVal) {
		R val = defaultVal;
		if (check) { 
			try {
				val = func.apply(object);
			}
			catch (Throwable e) { e.printStackTrace(); }
		}
		return val;
	}
	
	public static <R> R tryCodeR(Runnable func, R ifPass, R ifFail) { return (tryCode(func)) ? ifPass : ifFail; }
	public static <R> R tryIfCodeR(boolean check, Runnable func, R ifPass, R ifFail) { return (tryIfCode(check, func)) ? ifPass : ifFail; }
	
	public static <E> void tryDo(E obj, Consumer<? super E> action) { try { action.accept(obj); } catch (Throwable e) { e.printStackTrace(); } }
	public static <E, A> void tryDo(E obj1, A obj2, BiConsumer<? super E, ? super A> action) { try { action.accept(obj1, obj2); } catch (Throwable e) { e.printStackTrace(); } }
	
	public static <E> boolean tryNullDo(E obj, Consumer<? super E> action) {
		boolean val = false;
		try { val = nullDo(obj, action); }
		catch (Throwable e) { e.printStackTrace(); }
		return val;
	}
	public static <E, A> boolean tryNullDo(E obj1, A obj2, BiConsumer<? super E, ? super A> action) {
		boolean val = false;
		try { val = nullDo(obj1, obj2, action); }
		catch (Throwable e) { e.printStackTrace(); }
		return val;
	}
	
	public static <E, R> R tryDoR(E obj, Consumer<? super E> action, R returnVal) { tryDo(obj, action); return returnVal; }
	public static <E, R> R tryDoR(E obj, Consumer<? super E> action, R ifPass, R ifFail) {
		boolean val = false;
		try {
			action.accept(obj);
			val = true;
		}
		catch (Throwable e) { e.printStackTrace(); }
		return (val) ? ifPass : ifFail;
	}
	public static <E, A, R> R tryDoR(E obj1, A obj2, BiConsumer<? super E, ? super A> action, R returnVal) { tryDo(obj1, obj2, action); return returnVal; }
	public static <E, A, R> R tryDoR(E obj1, A obj2, BiConsumer<? super E, ? super A> action, R ifPass, R ifFail) {
		boolean val = false;
		try {
			action.accept(obj1, obj2);
			val = true;
		}
		catch (Throwable e) { e.printStackTrace(); }
		return (val) ? ifPass : ifFail;
	}
	
	public static <E, R> R tryNullDoR(E obj, Consumer<? super E> action, R returnVal) { tryNullDo(obj, action); return returnVal; }
	public static <E, R> R tryNullDoR(E obj, Consumer<? super E> action, R ifPass, R ifFail) { return (tryDoR(obj, action, true, false)) ? ifPass : ifFail; }
	public static <E, A, R> R tryNullDoR(E obj1, A obj2, BiConsumer<? super E, ? super A> action, R returnVal) { tryNullDo(obj1, obj2, action); return returnVal; }
	public static <E, A, R> R tryNullDoR(E obj1, A obj2, BiConsumer<? super E, ? super A> action, R ifPass, R ifFail) { return (tryDoR(obj1, obj2, action, true, false)) ? ifPass : ifFail; }
	
	public static <E> Stream<E> tryFilterA(Predicate<? super E> filter, E... vals) {
		Stream<E> stream = null;
		try { stream = filterA(filter, vals); }
		catch (Throwable e) { e.printStackTrace(); }
		return stream;
	}
	
	public static <E> E tryGet(Optional<E> optional) {
		try { return optional.get(); }
		catch (Throwable e) { return null; }
	}
	
	public static boolean tryFileCode(File fileIn, Runnable func) { return (fileExists(fileIn)) ? tryCode(func) : false; }
	public static <R> R tryFileCodeR(File fileIn, Runnable func, R returnVal) { tryFileCode(fileIn, func); return returnVal; }
	public static <R> R tryFileCodeR(File fileIn, Runnable func, R ifPass, R ifFail) { return (tryFileCode(fileIn, func)) ? ifPass : ifFail; }
	public static <R> boolean tryFileCodeR(File fileIn, Function<Object, Boolean> func) { return (fileExists(fileIn)) ? func.apply(null) : false; }
	
}