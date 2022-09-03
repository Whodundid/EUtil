package eutil.math;

import java.text.DecimalFormat;
import java.util.List;

import eutil.EUtil;
import eutil.datatypes.Box2;
import eutil.datatypes.util.ENumType;
import eutil.strings.StringUtil;

/**
 * A static library containing various helper functions related to special math operations or number manipulations.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class NumberUtil {
	
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
	
	/** Returns the higher of the given two numbers. */
	public static Number max(Number a, Number b) { return (a.doubleValue() > b.doubleValue()) ? a : b; }
	/** Returns the lower of the given two numbers. */
	public static Number min(Number a, Number b) { return (a.doubleValue() < b.doubleValue()) ? a : b; }
	
	public static double round(double value, int places) {
		if (places < 0) return Double.NaN;
		value *= 100;
		value = Math.round(value);
		value = (double) ((int) value);
		return value / 100;
	}
	
	/** Double to String with fixed number of decimal places. (Round Decimal to String with number of Places). */
	public static String roundDSP(double value, int places) {
		DecimalFormat f = new DecimalFormat("#." + StringUtil.repeatString("#", places));
		return f.format(value);
	}
	
	/** Round a Decimal to a String with 2 places. */
	public static String roundD2(double value) { return roundDSP(value, 2); }
	
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
	
	/** Returns the Cartesian distance between two points. (the distance formula) */
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
	
	/** Returns the Cartesian distance between two points within storage boxes. (the distance formula) */
	public static double getDistance(Box2<Double, Double> point1, Box2<Double, Double> point2) {
		if (point1 != null && point2 != null) {
			if (point1.getA() != null && point1.getB() != null && point2.getA() != null && point2.getB() != null) {
				double x1 = point1.getA();
				double y1 = point1.getB();
				double x2 = point2.getA();
				double y2 = point2.getB();
				
				return (int) distance(x1, y1, x2, y2);
			}
		}
		return 0;
	}
	
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
	
	/** Returns the sum of all numbers within the given list. (does not check for null values!) */
	public static double sumValues(List<Number> valsIn) {
		if (valsIn != null) {
			double total = 0;
			for (Number n : valsIn) {
				total += n.doubleValue();
			}
			return total;
		}
		return Double.NaN;
	}
	
	/** Returns the sum of each number squared by the given expIn value. */
	public static double squareAndPowValues(List<Number> valsIn, double expIn) {
		if (valsIn != null) {
			double total = 0;
			for (Number n : valsIn) {
				total += Math.pow(n.doubleValue(), expIn);
			}
			return total;
		}
		return Double.NaN;
	}
	
	/** Returns the sum of each element multiplied by the value in the same index of the other list. */
	public static double sumOfProducts(List<Number> vals1, List<Number> vals2) {
		if (EUtil.notNull(vals1, vals2)) {
			if (vals1.size() == vals2.size()) {
				double total = 0;
				for (int i = 0; i < vals1.size(); i++) {
					total += (vals1.get(i).doubleValue() * vals2.get(i).doubleValue());
				}
				return total;
			}
		}
		return Double.NaN;
	}
	
	/** Returns the sum of each element squared by the value in the same index of the other list. */
	public static double sumSquareFirstProdSecond(List<Number> vals1, int expIn, List<Number> vals2) {
		if (EUtil.notNull(vals1, vals2)) {
			if (vals1.size() == vals2.size()) {
				double total = 0;
				for (int i = 0; i < vals1.size(); i++) {
					total += (Math.pow(vals1.get(i).doubleValue(), expIn) * vals2.get(i).doubleValue());
				}
				return total;
			}
		}
		return Double.NaN;
	}
	
}
