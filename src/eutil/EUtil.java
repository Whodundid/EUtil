package eutil;

import static eutil.lambda.Predicates.fileExists;
import static eutil.lambda.Predicates.notNull;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Stream;

import eutil.datatypes.EArrayList;

/** 
 * A static helper library containing a number of useful functions including, but not limited to:
 * 
 * <ul>
 * 		<li> Versatile storage datatypes
 * 		<li> Math operations and helpers
 * 		<li> Date/Time helpers
 * 		<li> Reflection helpers
 * 		<li> Lambda structures and operations
 * 		<li> Array and List conversions
 * 		<li> String operations
 * 		<li> System Info
 * </ul>
 *  
 *  @author Hunter Bragg
 *  @version 1.4.0
 */
public class EUtil {
	
	//------------------
	// Hide Constructor
	//------------------
	
	/**
	 * Hidden to prevent instantiation as EUtil is intended to be a library class
	 * containing only utility methods.
	 * 
	 * @since 1.3.1
	 */
	private EUtil() {}
	
	//------------------
	
	/** The EUtil library version. */
	public static final String version = "1.4.0";
	/** EUtil static logger. */
	public static final Logger logger = Logger.getLogger("EUtil");
	
	//----------------
	// Static Loggers
	//----------------
	
	public static void log(Level levelIn, String msg) { logger.log(levelIn, msg); }
	public static void info(String msg) { logger.log(Level.INFO, msg); }
	public static void error(String msg) { logger.log(Level.SEVERE, msg); }
	public static void error(String msg, Throwable throwableIn) { logger.log(Level.SEVERE, msg, throwableIn); }
	
	//--------------
	// Object Tests
	//--------------
	
	/**
	 * Returns true if any of the given objects are null.
	 * In the event that nothing is passed to this method, false is returned by default.
	 * 
	 * @param objsIn the series of objects to check through
	 * @return True if any of the given objects are null
	 */
	public static boolean anyNull(Object... objsIn) {
		for (Object o : objsIn)
			if (o == null)
				return true;
		return false;
	}

	/**
	 * Returns true if any of the given objects are not null.
	 * In the event that nothing is passed to this method, false is returned by default.
	 * 
	 * @param objsIn the series of objects to check through
	 * @return True if any of the given objects are null
	 */
	public static boolean notNull(Object... objsIn) {
		if (objsIn.length == 0) return false;
		for (var o : objsIn)
			if (o == null)
				return false;
		return true;
	}

	/**
	 * Returns true if the given objects are equal to each other, this method also
	 * accounts for null objects.
	 */
	public static boolean isEqual(Object a, Object b) {
		return (a != null) ? a.equals(b) : b == null;
	}
	
	/**
	 * Returns true if the given objects are not equal to each other, this
	 * method also accounts for null objects.
	 */
	public static boolean isNotEqual(Object a, Object b) {
		return (a != null) ? !a.equals(b) : b != null;
	}
	
	/**
	 * Compares the given list of grouped elements in a [A, A, B, B, C, C, ...] arrangement.
	 * Returns true iff every element group has equal values.
	 * 
	 * @param objs A list of grouped values to be compared
	 * @return {@code boolean}
	 * 
	 * @since 1.2.1
	 */
	public static boolean compareInOrder(Object... objs) {
		if (objs.length % 2 != 0) return false;
		boolean val = true;
		
		// iterates across the given list comparing the given element vs. the very next element.
		for (int i = 0; i < objs.length; i += 2) {
			if (isNotEqual(objs[i], objs[i + 1])) {
				val = false;
				break;
			}
		}
		
		return val;
	}
	
	/**
	 * Returns true if any of the given values are true.
	 * In the event that nothing is passed to this method, false is returned by default.
	 * 
	 * @param args Values to check for true within
	 * @return True if any of the given values are true
	 */
	public static boolean anyTrue(boolean... args) {
		for (boolean b : args) if (b) return true;
		return false;
	}
	
	/**
	 * Returns true if any of the given values are false.
	 * In the event that nothing is passed to this method, false is returned by default.
	 * 
	 * @param args Values to check for false within
	 * @return True if any of the given values are false
	 */
	public static boolean anyFalse(boolean... args) {
		for (boolean b : args) if (!b) return true;
		return false;
	}
	
	//-------------
	// List Checks
	//-------------
	
	/**
	 * A statement that returns the specified 'ifTrue' value if any member within
	 * the given iterable matches the given predicate. If none of the members in the
	 * given iterable match the predicate then the 'ifFalse' value is returned
	 * instead.
	 * 
	 * @param <A>       The type of objects being compared
	 * @param <R>       The type of value being returned in either case
	 * @param list      The elements being compared
	 * @param predicate The comparison statement to be performed on each value
	 * @param ifTrue    Returned if any element matches the given comparison
	 * @param ifFalse   Returned if no element matches the given comparison
	 * 
	 */
	public static <A, R> R anyMatch(Iterable<A> list, Predicate<? super A> predicate, R ifTrue, R ifFalse) {
		Objects.requireNonNull(list);
		Objects.requireNonNull(predicate);
		
		for (A a : list)
			if (predicate.test(a))
				return ifTrue;
		
		return ifFalse;
	}
	
	/**
	 * Returns the first member in an iterable that matches the given predicate. If
	 * no object matches, null is returned instead.
	 */
	public static <E> E getFirst(Iterable<E> list, Predicate<? super E> predicate) {
		Objects.requireNonNull(predicate);
		
		for (E e : list)
			if (predicate.test(e))
				return e;
		
		return null;
	}
	
	/**
	 * Returns the first member in the list that matches the given predicate. If no
	 * object matches, the default value is returned instead.
	 */
	public static <E> E getFirst(Iterable<E> list, Predicate<? super E> predicate, E defaultVal) {
		E val = getFirst(list, predicate);
		return (val != null) ? val : defaultVal;
	}
	
	/**
	 * Returns the last member in a list that matches the given predicate. If no
	 * object matches, null is returned.
	 */
	public static <E> E getLast(List<E> list, Predicate<? super E> predicate) {
		Objects.requireNonNull(predicate);
		
		for (int i = list.size(); i >= 0; i--) {
			E e = list.get(i);
			if (predicate.test(e)) return e;
		}
		
		return null;
	}
	
	/**
	 * Returns the last member in the list that matches the given predicate. If not
	 * object matches, the default value is returned instead.
	 */
	public static <E> E getLast(List<E> list, Predicate<? super E> predicate, E defaultVal) {
		E val = getLast(list, predicate);
		return (val != null) ? val : defaultVal;
	}
	
	//------------
	// Stack Util
	//------------
	
	/**
	 * Takes in a Stack of some type and returns a new Stack whose elements are in a
	 * reversed order to the original Stack.
	 * 
	 * @param <E> The type of objects within the given Stack
	 * @param in The given Stack
	 * @return A new Stack with a reversed order of the original elements
	 */
	public static <E> Stack<E> reverseStack(Stack<E> in) {
		if (in == null) return null;
		
		var reversed = new Stack<E>();
		while (!in.isEmpty()) reversed.push(in.pop());
		
		return reversed;
	}
	
	/**
	 * Converts a Collection to a Stack of the same type.
	 * <p>
	 * Iterates over the given Collection pushing each element onto a newly created
	 * Stack which will be returned.
	 * 
	 * @param <E> The type of objects within the given collection
	 * @param in  The given Collection
	 * @return A Stack containing the items in the given Collection
	 */
	public static <E> Stack<E> toStack(Collection<E> in) {
		var stack = new Stack<E>();
		if (in != null) {
			var it = in.iterator();
			while (it.hasNext()) stack.add(it.next());
		}
		return stack;
	}
	
	/**
	 * Converts a Stack to a List of the same type.
	 * <p>
	 * Simply put, this method iterates over the contents of the given Stack, using
	 * Stack::iterator, and adds them to a new list to be returned.
	 * 
	 * @param <E> The type of objects within the given Stack
	 * @param in  The given Stack
	 * @return A list containing the items in the given Stack
	 */
	public static <E> EArrayList<E> toList(Stack<E> in) {
		var list = new EArrayList<E>();
		if (in != null) {
			var it = in.iterator();
			while (it.hasNext()) list.add(it.next());
		}
		return list;
	}
	
	//-------------
	// File Checks
	//-------------
	
	/**
	 * Returns true if the given file is not null and actually exists on the system.
	 */
	public static boolean fileExists(File f) {
		return fileExists.test(f);
	}
	
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
	
	public static void printArray(Object[] arr) { forEach(arr, System.out::println); }
	public static void printList(Iterable arr) { forEach(arr, System.out::println); }
	
	public static <E, T> void printArray(E[] arr, Function<? super E, ? super T> type) {
		nullDo(arr, a -> asList(a).map(type).forEach(System.out::println));
	}
	
	public static <E> E[] add(E obj, E[] array) {
		var list = new EArrayList<E>(obj).addA(array);
		return list.toArray(array);
	}
	
	public static <E> E[] addEnd(E obj, E[] array) {
		var list = new EArrayList<E>().addA(array);
		list.add(obj);
		return list.toArray(array);
	}
	
	public static <E> E[] addAt(E obj, int pos, E[] array) {
		var list = new EArrayList<E>().addA(array);
		if (pos >= 0 && pos < array.length + 1) {
			list.add(pos, obj);
		}
		return list.toArray(array);
	}
	
	/** Checks if the values in one array match the values from another. */
	public static boolean compareLists(List<?> list1, List<?> list2) {
		if (list1.size() != list2.size()) return false; //if the sizes differ, they're not the same.
		for (int i = 0; i < list1.size(); i++) {
			var a = list1.get(i);
			var b = list2.get(i);
			if (!isEqual(a, b)) return false;
		}
		return true;
	}
	
	/**
	 * Reverses the incoming list.
	 * If the list is null, then null is returned instead.
	 * 
	 * @since 1.2.0
	 * @param <E> Generic Type
	 * @param in The incoming list to be reversed.
	 * @return List<E> The reversed list.
	 */
	public static <E> List<E> reverseList(List<E> in) {
		if (in == null) return null;
		Collections.reverse(in);
		return in;
	}
	
	//----------------
	// Lambda Helpers
	//----------------
	
	public static long length(Iterable itr) { return itr.spliterator().getExactSizeIfKnown(); }
	
	// array conversions
	
	/** Boxes a generic varargs of typed-objects into a typed-array. */
	public static <E> E[] asArray(E... vals) { return asList(vals).toArray(vals); }
	
	/** Converts a generic varargs of typed-objects into a typed-EArrayList. */
	public static <E> EArrayList<E> asList(E... vals) { return new EArrayList<E>(vals); }
	
	public static <E> E[] toArray(Collection<? extends E> list) {
		E[] arr = (E[]) new Object[list.size()];
		for (int i = 0; i < list.size(); i++) arr[i] = (E) list.toArray()[i];
		return arr;
	}
	
	public static <E> EArrayList<E> toList(E[] arr) { return new EArrayList<E>(arr); }
	
	/** Converts a typed-array to a Stream. */
	public static <E> Stream<E> stream(E... vals) { return Arrays.stream(vals); }
	/** Converts a typed-array to a Stream that filters out null objects. */
	public static <E> Stream<E> filterNull(E... vals) { return stream(vals).filter(notNull); }
	
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
	public static <E> Stream<E> filterNull(Collection<E> list) { return list.stream().filter(notNull); }
	/** Converts a typed-Collection to a Stream that filters out null objects then performs the given filter. */
	public static <E> Stream<E> filterNull(Collection<E> list, Predicate<? super E> filter) { return filterNull(list).filter(filter); }
	/** Converts a typed-Collection to a Stream that maps each object to the specified type. */
	public static <E, T> Stream<T> map(Collection<E> list, Function<? super E, ? extends T> mapper) { return list.stream().map(mapper); }
	/** Performs a forEach loop on each element. Directly mapped from 'Java.util.List' */
	public static <E> void forEach(Iterable<E> list, Consumer<? super E> action) { list.forEach(action); }
	/** Performs a forEach loop on each element. Then returns the specified 'returnVal' argument. */
	public static <E, R> R forEachR(Iterable<E> list, Consumer<? super E> action, R returnVal) { forEach(list, action); return returnVal; }
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
	public static <E> Stream<E> filterNull(E[] arr, Predicate<? super E> filter) { return stream(arr).filter(filter.and(notNull)); }
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
	
	public static void forEach(boolean[] arr, Consumer<? super Boolean> action) { for (boolean e : arr) action.accept(e); }
	public static void forEach(byte[] arr, Consumer<? super Byte> action) { for (byte e : arr) action.accept(e); }
	public static void forEach(char[] arr, Consumer<? super Character> action) { for (char e : arr) action.accept(e); }
	public static void forEach(short[] arr, Consumer<? super Short> action) { for (short e : arr) action.accept(e); }
	public static void forEach(int[] arr, Consumer<? super Integer> action) { for (int e : arr) action.accept(e); }
	public static void forEach(long[] arr, Consumer<? super Long> action) { for (long e : arr) action.accept(e); }
	public static void forEach(float[] arr, Consumer<? super Float> action) { for (float e : arr) action.accept(e); }
	public static void forEach(double[] arr, Consumer<? super Double> action) { for (double e : arr) action.accept(e); }
	
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
		if (check && ifTrue != null) ifTrue.run();
		else if (ifFalse != null) ifFalse.run();
		return returnVal;
	}
	
	/** One line if-else statement. Returns true if input equates to true. */
	public static <E, R> boolean ifElse(boolean check, Runnable ifTrue, Runnable ifFalse) {
		if (check && ifTrue != null) {
			ifTrue.run();
			return true;
		}
		else if (ifFalse != null) ifFalse.run();
		return false;
	}
	
	//lambda operations
	
	public static <E> E lambdaDo(E obj, Consumer<? super E> action) { action.accept(obj); return obj; }
	
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
	/** A statement that returns the result of a given bifunction if the given object is not null. If either input is null, the default value is returned instead. */
	public static <E, A, R> R nullApplyR(E obj1, A obj2, BiFunction<? super E, ? super A, R> function, R defaultVal) { return (notNull(obj1, obj2)) ? function.apply(obj1, obj2) : defaultVal; }
	
	//repeaters
	
	public static <E> void repeat(Runnable func, int times) { for (int i = 0; i < times; i++) { func.run(); } }
	public static <E> void repeat(E object, Consumer<? super E> action, int times) { for (int i = 0; i < times; i++) { action.accept(object); } }
	public static <E, R> R repeatR(Runnable func, int times, R returnVal) { repeat(func, times); return returnVal; }

	//contains
	
	public static <E> boolean contains(E[] arr, E value) { for (E e : arr) { if (isEqual(e, value)) { return true; } } return false; }
	public static <E> boolean contains(Collection<E> arr, E value) { for (E e : arr) { if (isEqual(e, value)) { return true; } } return false; }
	public static <E> E containsR(E[] arr, E value) { for (E e : arr) if (isEqual(e, value)) { return e; } return null; }
	public static <E> E containsR(Collection<E> arr, E value) { for (E e : arr) if (isEqual(e, value)) { return e; } return null; }
	
	public static <E> boolean contains(Iterable<E> data, E val) {
		Iterator<E> dataI = data.iterator();
		while (dataI.hasNext()) {
			if (isEqual(val, dataI.next())) return true;
		}
		return false;
	}
	
	public static <E> boolean contains(boolean[] arr, boolean x) { for (boolean i : arr) if (i == x) return true; return false; }
	public static <E> boolean contains(char[] arr, char x) { for (char i : arr) if (i == x) return true; return false; }
	public static <E> boolean contains(byte[] arr, byte x) { for (byte i : arr) if (i == x) return true; return false; }
	public static <E> boolean contains(short[] arr, short x) { for (short i : arr) if (i == x) return true; return false; }
	public static <E> boolean contains(int[] arr, int x) { for (int i : arr) if (i == x) return true; return false; }
	public static <E> boolean contains(long[] arr, long x) { for (long i : arr) if (i == x) return true; return false; }
	public static <E> boolean contains(float[] arr, float x) { for (float i : arr) if (i == x) return true; return false; }
	public static <E> boolean contains(double[] arr, double x) { for (double i : arr) if (i == x) return true; return false; }
	
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
		catch(Throwable e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static <R> R tryIfCodeR(boolean check, Runnable func, R returnVal) {
		try {
			if (check) func.run();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
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
			catch (Throwable e) {
				e.printStackTrace();
			}
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
			catch (Throwable e) {
				e.printStackTrace();
			}
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
			catch (Throwable e) {
				e.printStackTrace();
			}
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
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return val;
	}
	
	public static <R> R tryCodeR(Runnable func, R ifPass, R ifFail) { return (tryCode(func)) ? ifPass : ifFail; }
	public static <R> R tryIfCodeR(boolean check, Runnable func, R ifPass, R ifFail) { return (tryIfCode(check, func)) ? ifPass : ifFail; }
	
	public static <E> void tryDo(E obj, Consumer<? super E> action) {
		try { action.accept(obj); }
		catch (Throwable e) { e.printStackTrace(); }
	}
	
	public static <E, A> void tryDo(E obj1, A obj2, BiConsumer<? super E, ? super A> action) {
		try { action.accept(obj1, obj2); }
		catch (Throwable e) { e.printStackTrace(); }
	}
	
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
	
	public static <E, R> R tryDoR(E obj, Consumer<? super E> action, R returnVal) {
		tryDo(obj, action);
		return returnVal;
	}
	
	public static <E, R> R tryDoR(E obj, Consumer<? super E> action, R ifPass, R ifFail) {
		boolean val = false;
		try {
			action.accept(obj);
			val = true;
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		return (val) ? ifPass : ifFail;
	}
	
	public static <E, A, R> R tryDoR(E obj1, A obj2, BiConsumer<? super E, ? super A> action, R returnVal) {
		tryDo(obj1, obj2, action);
		return returnVal;
	}
	
	public static <E, A, R> R tryDoR(E obj1, A obj2, BiConsumer<? super E, ? super A> action, R ifPass, R ifFail) {
		boolean val = false;
		try {
			action.accept(obj1, obj2);
			val = true;
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
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
