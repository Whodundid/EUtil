package eutil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import eutil.random.RandomUtil;

/**
 * A series of unit tests for the RandomUtil library.
 * 
 * @author Hunter Bragg
 * @since 1.5.3
 */
public class Tests_RandomUtil {
	
	/**
	 * Tests 'getNRolls' for each of its datatype overrides. Also verifies
	 * produced array lengths and produced value ranges.
	 * 
	 * @since 1.5.3
	 */
	@Test
	public void test_getNRolls() {
		int[] randInts = RandomUtil.getNRolls(5, 0, 9);
		long[] randLongs = RandomUtil.getNRolls(5, 0l, 9l);
		double[] randDoubles = RandomUtil.getNRolls(5, 0d, 9d);
		
		assertEquals(5, randInts.length);
		assertEquals(5, randLongs.length);
		assertEquals(5, randDoubles.length);
		
		for (int i = 0; i < randInts.length; i++) assertBetween(randInts[i], 0, 9);
		for (int i = 0; i < randLongs.length; i++) assertBetween(randLongs[i], 0l, 9l);
		for (int i = 0; i < randDoubles.length; i++) assertBetween(randDoubles[i], 0d, 9d);
	}
	
	//------------------------------
	// Internal Test Helper Methods
	//------------------------------
	
	private static void assertBetween(byte v, byte l, byte u) 		{ assertTrue(v >= l && v <= u); }
	private static void assertBetween(short v, short l, short u) 	{ assertTrue(v >= l && v <= u); }
	private static void assertBetween(int v, int l, int u) 			{ assertTrue(v >= l && v <= u); }
	private static void assertBetween(long v, long l, long u) 		{ assertTrue(v >= l && v <= u); }
	private static void assertBetween(float v, float l, float u) 	{ assertTrue(v >= l && v <= u); }
	private static void assertBetween(double v, double l, double u) { assertTrue(v >= l && v <= u); }
	
}
