package eutil;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import eutil.strings.StringUtil;

/**
 * A series of unit tests for the StringUtil library.
 * 
 * @author Hunter Bragg
 * @since 1.5.2
 */
public class Tests_StringUtil {
	
	/**
	 * Tests 'getLongestLength' by passing either an object array or a generic
	 * collection and verifies that the method returns the expected length.
	 * 
	 * @since 1.5.2
	 */
	@Test
	public void test_getLongestLength() {
		//test class to check 'toString' on custom objects
		class Test {
			public String toString() { return "Testy"; }
		}
		
		//test object array
		Object[] arr_objects = new Object[] { "aa", 5432, '4', "Banana", new Test() };
		int arr_longestLen = StringUtil.getLongestLength(arr_objects);
		
		assertEquals(6, arr_longestLen);
		
		//test object collection
		Collection<?> col_objects = List.of("aa", 5432, '4', "Banana", new Test());
		int col_longestLen = StringUtil.getLongestLength(col_objects);
		
		assertEquals(6, col_longestLen);
	}
	
	@Test
	public void test_equalsAny() {
		class Match_Class { public String toString() { return "BASE"; } }
		class Not_Match_Class { public String toString() { return "NOT_MATCH"; } }
		
		String base = "BASE";
		
		assertFalse(StringUtil.equalsAny(base));
		assertFalse(StringUtil.equalsAny(base, "a", "b", "c"));
		assertFalse(StringUtil.equalsAny(base, new Not_Match_Class()));
		assertFalse(StringUtil.equalsAny(base, "a", "b", "c", new Not_Match_Class()));
		
		assertTrue(StringUtil.equalsAny(base, base));
		assertTrue(StringUtil.equalsAny(base, "BASE"));
		assertTrue(StringUtil.equalsAny(base, new Match_Class()));
		assertTrue(StringUtil.equalsAny(base, "a", "b", "c", new Match_Class()));
		assertTrue(StringUtil.equalsAny(base, "a", "b", "c", "BASE", new Not_Match_Class()));
		
		var match = new Match_Class();
		var not_match = new Not_Match_Class();
		
		assertNull(StringUtil.equalsAnyR(base, "a", "b", "c"));
		assertEquals("BASE", StringUtil.equalsAnyR(base, base));
		assertEquals(match, StringUtil.equalsAnyR(base, "a", 'b', "c", match, "d", "e"));
		assertEquals(null, StringUtil.equalsAnyR(base, "a", 'b', "c", not_match, "d", "e"));
	}
	
}
