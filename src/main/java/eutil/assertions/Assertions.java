package eutil.assertions;

import static eutil.EUtil.*;

import java.util.Collection;

import eutil.assertions.exceptions.AssertionException;
import eutil.assertions.exceptions.EqualsAssertionException;
import eutil.assertions.exceptions.FalseAssertionException;
import eutil.assertions.exceptions.NotEqualsAssertionException;
import eutil.assertions.exceptions.TrueAssertionException;
import eutil.debug.PoorlyDocumented;
import eutil.debug.Unused;

/**
 * A library of various assertion statements to ensure logic flow and proper values.
 * 
 * @author Hunter Bragg
 * @since 1.2.0
 */
@Unused
@PoorlyDocumented
public final class Assertions {
	
	//----------------------
	
	private Assertions() {}
	
	//----------------------
	
	public static void assertNull(Object o) { if (o != null) throw new AssertionException("'" + o + "' is not null!"); }
	public static void assertNotNull(Object o) { if (o == null) throw new AssertionException("'" + o + "' is null!"); }
	public static void assertTrue(boolean result) { if (!result) throw new TrueAssertionException(); }
	public static void assertFalse(boolean result) { if (result) throw new FalseAssertionException(); }
	public static void assertEquals(Object a, Object b) { if (!isEqual(a, b)) throw new EqualsAssertionException(); }
	public static void assertNotEquals(Object a, Object b) { if (isEqual(a, b)) throw new NotEqualsAssertionException(); }
	public static void assertType(Object a, Class<?> type) { if (!type.isAssignableFrom(a.getClass())) throw new AssertionException("Types are not equal!"); }
	public static void assertNotType(Object a, Class<?> type) { if (type.isAssignableFrom(a.getClass())) throw new AssertionException("Types are equal!"); }
	
	public static void assertGreaterThan(long a, long b) { if (!nullApplyR(a, b, (aa, bb) -> (aa > bb), false)) throw new AssertionException("a is not greater than b!"); }
	public static void assertGreaterThan(double a, double b) { if (!nullApplyR(a, b, (aa, bb) -> (aa > bb), false)) throw new AssertionException("a is not greater than b!"); }
	public static void assertGreaterThan(Number a, Number b) { if (!nullApplyR(a, b, (aa, bb) -> (aa.doubleValue() > bb.doubleValue()), false)) throw new AssertionException("a is not greater than b!"); }
	
	public static void assertGreaterThanEquals(long a, long b) { if (!nullApplyR(a, b, (aa, bb) -> (aa >= bb), false)) throw new AssertionException("a is not greater than or equal to b!"); }
	public static void assertGreaterThanEquals(double a, double b) { if (!nullApplyR(a, b, (aa, bb) -> (aa >= bb), false)) throw new AssertionException("a is not greater than or equal to b!"); }
	public static void assertGreaterThanEquals(Number a, Number b) { if (!nullApplyR(a, b, (aa, bb) -> (aa.doubleValue() >= bb.doubleValue()), false)) throw new AssertionException("a is not greater than or equal to b!"); }
	
	public static void assertLessThan(long a, long b) { if (!nullApplyR(a, b, (aa, bb) -> (aa < bb), false)) throw new AssertionException("a is not less than b!"); }
	public static void assertLessThan(double a, double b) { if (!nullApplyR(a, b, (aa, bb) -> (aa < bb), false)) throw new AssertionException("a is not less than b!"); }
	public static void assertLessThan(Number a, Number b) { if (!nullApplyR(a, b, (aa, bb) -> (aa.doubleValue() < bb.doubleValue()), false)) throw new AssertionException("a is not less than b!"); }
	
	public static void assertLessThanEquals(long a, long b) { if (!nullApplyR(a, b, (aa, bb) -> (aa <= bb), false)) throw new AssertionException("a is not less than or equal to b!"); }
	public static void assertLessThanEquals(double a, double b) { if (!nullApplyR(a, b, (aa, bb) -> (aa <= bb), false)) throw new AssertionException("a is not less than or equal to b!"); }
	public static void assertLessThanEquals(Number a, Number b) { if (!nullApplyR(a, b, (aa, bb) -> (aa.doubleValue() <= bb.doubleValue()), false)) throw new AssertionException("a is not less than or equal to b!"); }
	
	public static void assertEmpty(Collection c) { if (!c.isEmpty()) throw new AssertionException("The given collection is not empty!"); }
	public static void assertNotEmpty(Collection c) { if (c.isEmpty()) throw new AssertionException("The given collection is emtpy!"); }
	public static void assertSize(Collection c, Number expectedSize) { if (c.size() != expectedSize.intValue()) throw new AssertionException("The given collection's size does not match '" + expectedSize + "'!"); }
	public static void assertOnlyOne(Collection c) { if (c.size() != 1) throw new AssertionException("The given collection does not have a size of 1!"); }
	
}
