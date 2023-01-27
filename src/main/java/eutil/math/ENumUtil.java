package eutil.math;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.math3.util.FastMath;

import eutil.EUtil;
import eutil.datatypes.boxes.Box2;
import eutil.datatypes.util.ENumType;
import eutil.strings.EStringUtil;

/**
 * A static library containing various helper functions related to special
 * math operations or number manipulations.
 * 
 * @apiNote Previous Name: 'NumberUtil'
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class ENumUtil {
	
	//=================
	// Clamp Functions
	//=================
	
	/** Restricts a value between the given minimum and maximum values. */
	public static byte clamp(byte num, byte min, byte max) { return (num < min) ? min : ((num > max) ? max : num); }
	/** Restricts a value between the given minimum and maximum values. */
	public static short clamp(short num, short min, short max) { return (num < min) ? min : ((num > max) ? max : num); }
	/** Restricts a value between the given minimum and maximum values. */
	public static int clamp(int num, int min, int max) { return (num < min) ? min : ((num > max) ? max : num); }
	/** Restricts a value between the given minimum and maximum values. */
	public static long clamp(long num, long min, long max) { return (num < min) ? min : ((num > max) ? max : num); }
	/** Restricts a value between the given minimum and maximum values. */
	public static float clamp(float num, float min, float max) { return (num < min) ? min : ((num > max) ? max : num); }
	/** Restricts a value between the given minimum and maximum values. */
	public static double clamp(double num, double min, double max) { return (num < min) ? min : ((num > max) ? max : num); }
	/** Restricts a value between the given minimum and maximum values. */
	public static Number clamp(Number num, Number min, Number max) {
		double n = num.doubleValue();
		double l = min.doubleValue();
		double u = max.doubleValue();
		return (n < l) ? l : ((n > u) ? u : n);
	}
	
	//=========
	// Min/Max
	//=========
	
	/** Returns the higher of the given two numbers. */
	public static Number max(Number a, Number b) { return (a.doubleValue() > b.doubleValue()) ? a : b; }
	/** Returns the lower of the given two numbers. */
	public static Number min(Number a, Number b) { return (a.doubleValue() < b.doubleValue()) ? a : b; }
	
	//==================================================================
	// Fast Math : Apache Commons Math 3.6.1 : Directly Mapped : et al.
	//==================================================================
	
    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x)
     */
	public static int abs(final int x) {
		return FastMath.abs(x);
	}
	
    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x)
     */
    public static long abs(final long x) {
       return FastMath.abs(x);
    }

    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x)
     */
    public static float abs(final float x) {
        return FastMath.abs(x);
    }

    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x)
     */
    public static double abs(double x) {
        return FastMath.abs(x);
    }
	
	//==================
	// Decimal Rounding
	//==================
	
	public static double round(double value, int places) {
		if (places < 0) return Double.NaN;
		value *= 100;
		value = Math.round(value);
		value = (double) ((int) value);
		return value / 100;
	}
	
	/** Double to String with fixed number of decimal places. (Round Decimal to String with number of Places). */
	public static String roundDSP(double value, int places) {
		DecimalFormat f = new DecimalFormat("#." + EStringUtil.repeatString("#", places));
		return f.format(value);
	}
	
	/** Round a Decimal to a String with 2 places. */
	public static String roundD2(double value) { return roundDSP(value, 2); }
	
	//===============
	// Number Checks
	//===============
	
	/** Returns the datatype of a given number. */
	public static ENumType getNumType(Class<? extends Number> c) {
		if (c == Byte.class || c == byte.class) 	return ENumType.BYTE;
		if (c == Short.class || c == short.class) 	return ENumType.SHORT;
		if (c == Integer.class || c == int.class) 	return ENumType.INTEGER;
		if (c == Long.class || c == long.class) 	return ENumType.LONG;
		if (c == Float.class || c == float.class) 	return ENumType.FLOAT;
		if (c == Double.class || c == double.class) return ENumType.DOUBLE;
		return ENumType.NULL;
	}
	
	/** Returns true if the given String can be cast as a number. */
	public static boolean isNumber(String in) { return parseNumber(in) != null; }
	
	/** Attempts to parse a number from a given string.
	 *  Returns either a long or a double depending on which type it reads. */
	public static Number parseNumber(String in) {
		if (in != null && !in.trim().isEmpty() && !in.isEmpty()) {
			if (in.length() == 1 && (in.charAt(0) == '.' || in.charAt(0) == '-')) return null;
			boolean decimal = false;
			boolean negative = false;
			
			for (int i = 0; i < in.length(); i++) {
				char c = in.charAt(i);
				
				//check if negative
				if (i == 0 && c == '-') negative = true;
				else if (c == '.') {
					//if there is more than one decimal, it's not a number
					if (decimal) return null;
					decimal = true;
				}
				else if (!Character.isDigit(c)) return null;
			}
			
			try {
				Number val;
				
				if (decimal) {
					val = (negative) ? -Double.valueOf(in) : Double.valueOf(in);
				}
				else {
					val = (negative) ? -Long.valueOf(in) : Long.valueOf(in);
				}
				
				return val;
			}
			catch (NumberFormatException e) { e.printStackTrace(); }
		}
		return null;
	}
	
	/** Returns true if the given string is an integer. */
	public static boolean isInteger(String s) { return s.matches("-?\\d+"); }
	/** Returns true if the given string is an integer when using the specified base. */
	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty()) return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1) return false;
				continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0) return false;
		}
		return true;
	}
	
	/** Returns true if the given object is not a decimal value. (byte, short int, long) */
	public static boolean isInteger(Object in) {
		if (in instanceof Number n) return isInteger(n);
		if (in instanceof String s) return isInteger(s);
		return false;
	}
	
	/** Returns true if the given number is not a decimal value. (byte, short, int, long) */
	public static boolean isInteger(Number in) {
		if (in instanceof Byte) 	return true;
		if (in instanceof Short) 	return true;
		if (in instanceof Integer) 	return true;
		if (in instanceof Long) 	return true;
		return false;
	}
	
	/** Returns true if the given object is a decimal value. (float, double) */
	public static boolean isDecimal(Object in) {
		if (in instanceof Number n) return isDecimal(n);
		return false;
	}
	
	/** Returns true if the given number is a decimal value. (float, double) */
	public static boolean isDecimal(Number in) {
		if (in instanceof Float) 	return true;
		if (in instanceof Double) 	return true;
		return false;
	}
	
	//==========
	// Distance
	//==========
	
	/** Returns the Cartesian distance between two points. (the distance formula) */
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	/** Returns the Cartesian distance between two points within storage boxes. (the distance formula) */
	public static double getDistance(Box2<Double, Double> point1, Box2<Double, Double> point2) {
		if (EUtil.anyNull(point1, point2)) return Double.NaN;
		if (EUtil.anyNull(point1.getA(), point1.getB(), point2.getA(), point2.getB())) return Double.NaN;
		
		double x1 = point1.getA();
		double y1 = point1.getB();
		double x2 = point2.getA();
		double y2 = point2.getB();
		
		return (int) distance(x1, y1, x2, y2);
	}
	
	//============================
	// Specialized Math Functions
	//============================
	
	/** Returns the sum of all numbers within the given list. (does not check for null values!) */
	public static double sumValues(List<Number> valsIn) {
		if (valsIn == null) return Double.NaN;
		
		double total = 0;
		for (Number n : valsIn) {
			total += n.doubleValue();
		}
		return total;
	}
	
	/** Returns the sum of each number squared by the given expIn value. */
	public static double squareAndPowValues(List<Number> valsIn, double expIn) {
		if (valsIn == null) return Double.NaN;
		
		double total = 0;
		for (Number n : valsIn) {
			total += Math.pow(n.doubleValue(), expIn);
		}
		return total;
	}
	
	/** Returns the sum of each element multiplied by the value in the same index of the other list. */
	public static double sumOfProducts(List<Number> vals1, List<Number> vals2) {
		if (EUtil.anyNull(vals1, vals2)) return Double.NaN;
		if (vals1.size() != vals2.size()) return Double.NaN;
		
		double total = 0;
		for (int i = 0; i < vals1.size(); i++) {
			total += (vals1.get(i).doubleValue() * vals2.get(i).doubleValue());
		}
		return total;
	}
	
	/** Returns the sum of each element squared by the value in the same index of the other list. */
	public static double sumSquareFirstProdSecond(List<Number> vals1, int expIn, List<Number> vals2) {
		if (EUtil.anyNull(vals1, vals2)) return Double.NaN;
		if (vals1.size() != vals2.size()) return Double.NaN;
		
		double total = 0;
		for (int i = 0; i < vals1.size(); i++) {
			total += (Math.pow(vals1.get(i).doubleValue(), expIn) * vals2.get(i).doubleValue());
		}
		return total;
	}
	
	//==========================
	// Number Comparison Checks
	//==========================
	
	//-----------
	// IsBetween
	//-----------
	
	/**
	 * Returns true if the given 'byte' is between the lower and upper bounds.
	 * @since 1.6.0
	 */
	public static boolean isBetween(byte num, byte low, byte high) {
		return num >= low && num <= high;
	}
	/**
	 * Returns true if the given 'short' is between the lower and upper bounds.
	 * @since 1.6.0
	 */
	public static boolean isBetween(short num, short low, short high) {
		return num >= low && num <= high;
	}
	/**
	 * Returns true if the given 'int' is between the lower and upper bounds.
	 * @since 1.6.0
	 */
	public static boolean isBetween(int num, int low, int high) {
		return num >= low && num <= high;
	}
	/**
	 * Returns true if the given 'long' is between the lower and upper bounds.
	 * @since 1.6.0
	 */
	public static boolean isBetween(long num, long low, long high) {
		return num >= low && num <= high;
	}
	/**
	 * Returns true if the given 'float' is between the lower and upper bounds.
	 * @since 1.6.0
	 */
	public static boolean isBetween(float num, float low, float high) {
		return num >= low && num <= high;
	}
	/**
	 * Returns true if the given 'double' is between the lower and upper bounds.
	 * @since 1.6.0
	 */
	public static boolean isBetween(double num, double low, double high) {
		return num >= low && num <= high;
	}
	
	//---------------
	// IsGreaterThan
	//---------------
	
	/** Shorthand of 'isGreaterThan'. */
	public static boolean GT(byte num, byte... toCheck) { return isGreaterThan(num, toCheck); }
	/** Shorthand of 'isGreaterThan'. */
	public static boolean GT(short num, short... toCheck) { return isGreaterThan(num, toCheck); }
	/** Shorthand of 'isGreaterThan'. */
	public static boolean GT(int num, int... toCheck) { return isGreaterThan(num, toCheck); }
	/** Shorthand of 'isGreaterThan'. */
	public static boolean GT(long num, long... toCheck) { return isGreaterThan(num, toCheck); }
	/** Shorthand of 'isGreaterThan'. */
	public static boolean GT(float num, float... toCheck) { return isGreaterThan(num, toCheck); }
	/** Shorthand of 'isGreaterThan'. */
	public static boolean GT(double num, double... toCheck) { return isGreaterThan(num, toCheck); }
	/** Shorthand of 'isGreaterThan'. */
	public static boolean GT(Number num, Number... toCheck) { return isGreaterThan(num, toCheck); }
	
	/**
	 * Returns true if the given 'byte' is greater than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThan(byte num, byte... toCheck) {
		for (byte n : toCheck)
			if (num > n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'short' is greater than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThan(short num, short... toCheck) {
		for (short n : toCheck)
			if (num > n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'int' is greater than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThan(int num, int... toCheck) {
		for (int n : toCheck)
			if (num > n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'float' is greater than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThan(long num, long... toCheck) {
		for (long n : toCheck)
			if (num > n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'float' is greater than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThan(float num, float... toCheck) {
		for (float n : toCheck)
			if (num > n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'double' is greater than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThan(double num, double... toCheck) {
		for (double n : toCheck)
			if (num > n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'Number' is greater than any of the given 'toCheck' values.
	 * Note: Uses the internal number's 'doubleValue' for the comparison check.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThan(Number num, Number... toCheck) {
		for (Number n : toCheck) {
			if (num.doubleValue() > n.doubleValue()) return true;
		}
		return false;
	}
	
	//---------------------
	// IsGreaterThanEquals
	//---------------------
	
	/** Shorthand of 'isGreaterThanEquals'. */
	public static boolean GTE(byte num, byte... toCheck) { return isGreaterThanEquals(num, toCheck); }
	/** Shorthand of 'isGreaterThanEquals'. */
	public static boolean GTE(short num, short... toCheck) { return isGreaterThanEquals(num, toCheck); }
	/** Shorthand of 'isGreaterThanEquals'. */
	public static boolean GTE(int num, int... toCheck) { return isGreaterThanEquals(num, toCheck); }
	/** Shorthand of 'isGreaterThanEquals'. */
	public static boolean GTE(long num, long... toCheck) { return isGreaterThanEquals(num, toCheck); }
	/** Shorthand of 'isGreaterThanEquals'. */
	public static boolean GTE(float num, float... toCheck) { return isGreaterThanEquals(num, toCheck); }
	/** Shorthand of 'isGreaterThanEquals'. */
	public static boolean GTE(double num, double... toCheck) { return isGreaterThanEquals(num, toCheck); }
	/** Shorthand of 'isGreaterThanEquals'. */
	public static boolean GTE(Number num, Number... toCheck) { return isGreaterThanEquals(num, toCheck); }
	
	/**
	 * Returns true if the given 'byte' is greater than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThanEquals(byte num, byte... toCheck) {
		for (byte n : toCheck)
			if (num >= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'short' is greater than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThanEquals(short num, short... toCheck) {
		for (short n : toCheck)
			if (num >= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'int' is greater than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThanEquals(int num, int... toCheck) {
		for (int n : toCheck)
			if (num >= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'float' is greater than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThanEquals(long num, long... toCheck) {
		for (long n : toCheck)
			if (num >= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'float' is greater than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThanEquals(float num, float... toCheck) {
		for (float n : toCheck)
			if (num >= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'double' is greater than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThanEquals(double num, double... toCheck) {
		for (double n : toCheck)
			if (num >= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'Number' is greater than or equal to any of the given 'toCheck' values.
	 * Note: Uses the internal number's 'doubleValue' for the comparison check.
	 * @since 1.6.0
	 */
	public static boolean isGreaterThanEquals(Number num, Number... toCheck) {
		for (Number n : toCheck) {
			if (num.doubleValue() >= n.doubleValue()) return true;
		}
		return false;
	}
	
	//------------
	// IsLessThan
	//------------
	
	/** Shorthand of 'isLessThan'. */
	public static boolean LT(byte num, byte... toCheck) { return isLessThan(num, toCheck); }
	/** Shorthand of 'isLessThan'. */
	public static boolean LT(short num, short... toCheck) { return isLessThan(num, toCheck); }
	/** Shorthand of 'isLessThan'. */
	public static boolean LT(int num, int... toCheck) { return isLessThan(num, toCheck); }
	/** Shorthand of 'isLessThan'. */
	public static boolean LT(long num, long... toCheck) { return isLessThan(num, toCheck); }
	/** Shorthand of 'isLessThan'. */
	public static boolean LT(float num, float... toCheck) { return isLessThan(num, toCheck); }
	/** Shorthand of 'isLessThan'. */
	public static boolean LT(double num, double... toCheck) { return isLessThan(num, toCheck); }
	/** Shorthand of 'isLessThan'. */
	public static boolean LT(Number num, Number... toCheck) { return isLessThan(num, toCheck); }
	
	/**
	 * Returns true if the given 'byte' is less than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThan(byte num, byte... toCheck) {
		for (byte n : toCheck)
			if (num < n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'short' is less than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThan(short num, short... toCheck) {
		for (short n : toCheck)
			if (num < n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'int' is less than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThan(int num, int... toCheck) {
		for (int n : toCheck)
			if (num < n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'long' is less than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThan(long num, long... toCheck) {
		for (long n : toCheck)
			if (num < n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'float' is less than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThan(float num, float... toCheck) {
		for (float n : toCheck)
			if (num < n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'double' is less than any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThan(double num, double... toCheck) {
		for (double n : toCheck)
			if (num < n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'Number' is less than any of the given 'toCheck' values.
	 * Note: Uses the internal number's 'doubleValue' for the comparison check.
	 * @since 1.6.0
	 */
	public static boolean isLessThan(Number num, Number... toCheck) {
		for (Number n : toCheck) {
			if (num.doubleValue() < n.doubleValue()) return true;
		}
		return false;
	}
	
	//------------------
	// IsLessThanEquals
	//------------------
	
	/** Shorthand of 'isLessThanEquals'. */
	public static boolean LTE(byte num, byte... toCheck) { return isLessThanEquals(num, toCheck); }
	/** Shorthand of 'isLessThanEquals'. */
	public static boolean LTE(short num, short... toCheck) { return isLessThanEquals(num, toCheck); }
	/** Shorthand of 'isLessThanEquals'. */
	public static boolean LTE(int num, int... toCheck) { return isLessThanEquals(num, toCheck); }
	/** Shorthand of 'isLessThanEquals'. */
	public static boolean LTE(long num, long... toCheck) { return isLessThanEquals(num, toCheck); }
	/** Shorthand of 'isLessThanEquals'. */
	public static boolean LTE(float num, float... toCheck) { return isLessThanEquals(num, toCheck); }
	/** Shorthand of 'isLessThanEquals'. */
	public static boolean LTE(double num, double... toCheck) { return isLessThanEquals(num, toCheck); }
	/** Shorthand of 'isLessThanEquals'. */
	public static boolean LTE(Number num, Number... toCheck) { return isLessThanEquals(num, toCheck); }
	
	/**
	 * Returns true if the given 'byte' is less than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThanEquals(byte num, byte... toCheck) {
		for (byte n : toCheck)
			if (num <= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'short' is less than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThanEquals(short num, short... toCheck) {
		for (short n : toCheck)
			if (num <= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'int' is less than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThanEquals(int num, int... toCheck) {
		for (int n : toCheck)
			if (num <= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'long' is less than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThanEquals(long num, long... toCheck) {
		for (long n : toCheck)
			if (num <= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'float' is less than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThanEquals(float num, float... toCheck) {
		for (float n : toCheck)
			if (num <= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'double' is less than or equal to any of the given 'toCheck' values.
	 * @since 1.6.0
	 */
	public static boolean isLessThanEquals(double num, double... toCheck) {
		for (double n : toCheck)
			if (num <= n) return true;
		return false;
	}
	/**
	 * Returns true if the given 'Number' is less than or equal to any of the given 'toCheck' values.
	 * Note: Uses the internal number's 'doubleValue' for the comparison check.
	 * @since 1.6.0
	 */
	public static boolean isLessThanEquals(Number num, Number... toCheck) {
		for (Number n : toCheck) {
			if (num.doubleValue() <= n.doubleValue()) return true;
		}
		return false;
	}
	
	//================
	// Number Parsing
	//================
	
	/**
	 * Attempts to parse a 'byte' from the given string.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param in The string to parse
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed byte
	 * @since 1.6.0
	 */
	public static byte parseByte(String in, byte defaultValue) {
		try { return Byte.parseByte(in); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'short' from the given string.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param in The string to parse
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed short
	 * @since 1.6.0
	 */
	public static short parseShort(String in, short defaultValue) {
		try { return Short.parseShort(in); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse an 'int' from the given string.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param in The string to parse
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed int
	 * @since 1.6.0
	 */
	public static int parseInt(String in, int defaultValue) {
		try { return Integer.parseInt(in); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'long' from the given string.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param in The string to parse
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed long
	 * @since 1.6.0
	 */
	public static long parseLong(String in, long defaultValue) {
		try { return Long.parseLong(in); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'float' from the given string.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param in The string to parse
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed float
	 * @since 1.6.0
	 */
	public static float parseFloat(String in, float defaultValue) {
		try { return Float.parseFloat(in); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'double' from the given string.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param in The string to parse
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed double
	 * @since 1.6.0
	 */
	public static double parseDouble(String in, double defaultValue) {
		try { return Double.parseDouble(in); }
		catch (Throwable t) { return defaultValue; }
	}
	
	//-----------------------------
	// Number Parsing - List Index
	//-----------------------------
	
	/**
	 * Attempts to parse a 'byte' from the string at the given index of the given list.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param list The list of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed byte
	 * @since 1.6.0
	 */
	public static byte parseByte(List<String> list, int index, byte defaultValue) {
		try { return Byte.parseByte(list.get(index)); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'short' from the string at the given index of the given list.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param list The list of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed short
	 * @since 1.6.0
	 */
	public static short parseShort(List<String> list, int index, short defaultValue) {
		try { return Short.parseShort(list.get(index)); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse an 'int' from the string at the given index of the given list.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param list The list of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed int
	 * @since 1.6.0
	 */
	public static int parseInt(List<String> list, int index, int defaultValue) {
		try { return Integer.parseInt(list.get(index)); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'long' from the string at the given index of the given list.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param list The list of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed long
	 * @since 1.6.0
	 */
	public static long parseLong(List<String> list, int index, long defaultValue) {
		try { return Long.parseLong(list.get(index)); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'float' from the string at the given index of the given list.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param list The list of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed float
	 * @since 1.6.0
	 */
	public static float parseFloat(List<String> list, int index, float defaultValue) {
		try { return Float.parseFloat(list.get(index)); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'double' from the string at the given index of the given list.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param list The list of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed double
	 * @since 1.6.0
	 */
	public static double parseDouble(List<String> list, int index, double defaultValue) {
		try { return Double.parseDouble(list.get(index)); }
		catch (Throwable t) { return defaultValue; }
	}
	
	//------------------------------
	// Number Parsing - Array Index
	//------------------------------
	
	/**
	 * Attempts to parse a 'byte' from the string at the given index of the given array.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param arr The array of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed byte
	 * @since 1.6.0
	 */
	public static byte parseByte(String[] arr, int index, byte defaultValue) {
		try { return Byte.parseByte(arr[index]); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'short' from the string at the given index of the given array.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param arr The array of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed short
	 * @since 1.6.0
	 */
	public static short parseShort(String[] arr, int index, short defaultValue) {
		try { return Short.parseShort(arr[index]); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse an 'int' from the string at the given index of the given array.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param arr The array of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed int
	 * @since 1.6.0
	 */
	public static int parseInt(String[] arr, int index, int defaultValue) {
		try { return Integer.parseInt(arr[index]); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'long' from the string at the given index of the given array.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param arr The array of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed long
	 * @since 1.6.0
	 */
	public static long parseLong(String[] arr, int index, long defaultValue) {
		try { return Long.parseLong(arr[index]); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'float' from the string at the given index of the given array.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param arr The array of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed float
	 * @since 1.6.0
	 */
	public static float parseFloat(String[] arr, int index, float defaultValue) {
		try { return Float.parseFloat(arr[index]); }
		catch (Throwable t) { return defaultValue; }
	}
	/**
	 * Attempts to parse a 'double' from the string at the given index of the given array.
	 * If the operation fails, the 'defaultValue' is returned instead.
	 * 
	 * @param arr The array of strings to get from
	 * @param index the index of the string to parse from
	 * @param defaultValue Returned if the parse operation fails
	 * @return A parsed double
	 * @since 1.6.0
	 */
	public static double parseDouble(String[] arr, int index, double defaultValue) {
		try { return Double.parseDouble(arr[index]); }
		catch (Throwable t) { return defaultValue; }
	}
	
}
