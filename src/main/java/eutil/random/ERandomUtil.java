package eutil.random;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import eutil.math.ENumUtil;
import eutil.misc.Direction;

/**
 * A collection of helper methods which aid with generating random numbers, chars, strings, and names.
 * 
 * @apiNote Previous name: 'RandomUtil'
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public final class ERandomUtil {
	
	// prevent instantiation
	private ERandomUtil() {}
	
	/** Returns true if the check value matches the produced random number within inclusive range boundaries. */
	public static boolean roll(int check, int min, int max) {
		return check == ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	/** Returns the number from a roll within a specified inclusive range boundaries. (ints) */
	public static int getRoll(int min, int max) {
	    if (min == max) return min;
		return ThreadLocalRandom.current().nextInt(min, ENumUtil.clamp(max + 1, max, Integer.MAX_VALUE));
	}
	
	/** Returns the number from a roll within a specified inclusive range boundaries. (longs) */
	public static long getRoll(long min, long max) {
	    if (min == max) return min;
		return ThreadLocalRandom.current().nextLong(min, ENumUtil.clamp(max + 1, max, Long.MAX_VALUE));
	}
	
	/** Returns the number from a roll within a specified inclusive range boundaries. (floats) */
    public static float getRoll(float min, float max) {
        if (min == max) return min;
    	return ThreadLocalRandom.current().nextFloat(min, max);
    }
	
	/** Returns the number from a roll within a specified inclusive range boundaries. (doubles) */
	public static double getRoll(double min, double max) {
	    if (min == max) return min;
		return ThreadLocalRandom.current().nextDouble(min, max);
	}
	
	/**
	 * Returns 'n' number of randomly generated numbers between the given
	 * inclusive range boundaries.
	 * 
	 * @param nTimes The number of random values to produce
	 * @param min The lower bound
	 * @param max The upper bound
	 * @return An array consisting of an amount of 'n' randomly generated values
	 * @since 1.5.3
	 */
	public static int[] getNRolls(int nTimes, int min, int max) {
		int[] arr = new int[nTimes];
		for (int i = 0; i < nTimes; i++) {
			arr[i] = getRoll(min, max);
		}
		return arr;
	}
	
	/**
	 * Returns 'n' number of randomly generated numbers between the given
	 * inclusive range boundaries.
	 * 
	 * @param nTimes The number of random values to produce
	 * @param min The lower bound
	 * @param max The upper bound
	 * @return An array consisting of an amount of 'n' randomly generated values
	 * @since 1.5.3
	 */
	public static long[] getNRolls(int nTimes, long min, long max) {
		long[] arr = new long[nTimes];
		for (int i = 0; i < nTimes; i++) {
			arr[i] = getRoll(min, max);
		}
		return arr;
	}
	
	/**
	 * Returns 'n' number of randomly generated numbers between the given
	 * inclusive range boundaries.
	 * 
	 * @param nTimes The number of random values to produce
	 * @param min The lower bound
	 * @param max The upper bound
	 * @return An array consisting of an amount of 'n' randomly generated values
	 * @since 1.5.3
	 */
	public static double[] getNRolls(int nTimes, double min, double max) {
		double[] arr = new double[nTimes];
		for (int i = 0; i < nTimes; i++) {
			arr[i] = getRoll(min, max);
		}
		return arr;
	}
	
	/** Returns a random element from the given array. */
	public static <E> E getRandVal(E... in) {
		int len = in.length;
		return in[getRoll(0, ENumUtil.clamp(len - 1, 0, len - 1))];
	}
	
	/** Returns a random element from the given iterable object. */
	public static <E> E getRandVal(Iterable<E> in) {
		Iterator<E> it = in.iterator();
		ArrayList<E> a = new ArrayList<>();
		while (it.hasNext()) a.add(it.next());
		return a.get(getRoll(0, ENumUtil.clamp(a.size() - 1, 0, a.size() - 1)));
	}
	
	/** Returns a random boolean value. */
	public static boolean randomBool() {
		return ThreadLocalRandom.current().nextBoolean();
	}
	
	public static Direction randomDir() { return randomDir(false); }
	public static Direction randomDir(boolean cardinal) {
		return (cardinal) ? getRandVal(Direction.cardinals()) : getRandVal(Direction.directions());
	}
	
	/** Returns a random char that can be made from any type-able ascii character. */
	public static char randomChar() { return randomChar(RandStringTypes.ANY); }
	/** Returns a random char from a specified set of RandStringTypes. */
	public static char randomChar(RandStringTypes typeIn) {
		switch (typeIn) {
		case LETTERS_ALL: return (char) ((roll(1, 0, 1)) ? getRoll(65, 90) : getRoll(97, 122)); //A-Z | a-z
		case LETTERS_UPPER: return (char) getRoll(65, 90); //A-Z
		case LETTERS_LOWER: return (char) getRoll(97, 122); //a-z
		case NUMBERS: return (char) getRoll(48, 57); //0-9
		case LETALL_NUM:
			switch (getRoll(0, 2)) {
			case 0: return (char) getRoll(48, 57); //numbers
			case 1: return (char) getRoll(65, 90); //A-Z
			case 2: return (char) getRoll(97, 122); //a-z
			}
		case LETUP_NUM:
			switch (getRoll(0, 1)) {
			case 0: return (char) getRoll(48, 57); //numbers
			case 1: return (char) getRoll(65, 90); //A-Z
			}
		case LETLOW_NUM:
			switch (getRoll(0, 1)) {
			case 0: return (char) getRoll(48, 57); //numbers
			case 1: return (char) getRoll(97, 122); //a-z
			}
		case SYMBOLS:
			switch (getRoll(0, 3)) {
			case 0: return (char) getRoll(33, 47); //!-/
			case 1: return (char) getRoll(58, 64); //:-@
			case 2: return (char) getRoll(91, 96); //[-`
			case 3: return (char) getRoll(123, 126); //{-~
			}
		case ANY:
		default: return (char) getRoll(32, 126);
		}
	}
	
	/** Returns a String that is comprised of randomized, type-able ascii characters. */
	public static String randomString() { return randomString(getRoll(5, 30), RandStringTypes.ANY); }
	/** Returns a String that is comprised of randomized, type-able ascii characters from a specified set. */
	public static String randomString(RandStringTypes typeIn) { return randomString(getRoll(5, 30), typeIn); }
	/** Returns a String of a specified length that is comprised of randomized, type-able ascii characters. */
	public static String randomString(int length) { return randomString(length, RandStringTypes.ANY); }
	/** Returns a String of a specified length that is comprised of randomized, type-able ascii characters from a specified set. */
	public static String randomString(int length, RandStringTypes typeIn) {
		String str = "";
		for (int i = 0; i < length; i++) str += randomChar(typeIn);
		return str;
	}
	
	/** Returns a String that holds a randomized human-esque name. @see RandomNames */
	public static String randomName() { return RandomNames.get(); }
	/** Returns a String that holds a randomized word. @see RandomWords */
	public static String randomWord() { return RandomWords.get(); }
	
	/** An enum that specifies types of character sets. */
	public enum RandStringTypes {
		LETTERS_ALL,
		LETTERS_UPPER,
		LETTERS_LOWER,
		NUMBERS,
		LETALL_NUM,
		LETUP_NUM,
		LETLOW_NUM,
		SYMBOLS,
		ANY;
	}

}
