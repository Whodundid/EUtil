package eutil;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import eutil.strings.EStringUtil;

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
		int arr_longestLen = EStringUtil.getLongestLength(arr_objects);
		
		assertEquals(6, arr_longestLen);
		
		//test object collection
		Collection<?> col_objects = List.of("aa", 5432, '4', "Banana", new Test());
		int col_longestLen = EStringUtil.getLongestLength(col_objects);
		
		assertEquals(6, col_longestLen);
	}
	
	@Test
	public void test_equalsAny() {
		class Match_Class { public String toString() { return "BASE"; } }
		class Not_Match_Class { public String toString() { return "NOT_MATCH"; } }
		
		String base = "BASE";
		
		assertFalse(EStringUtil.equalsAny(base));
		assertFalse(EStringUtil.equalsAny(base, "a", "b", "c"));
		assertFalse(EStringUtil.equalsAny(base, new Not_Match_Class()));
		assertFalse(EStringUtil.equalsAny(base, "a", "b", "c", new Not_Match_Class()));
		
		assertTrue(EStringUtil.equalsAny(base, base));
		assertTrue(EStringUtil.equalsAny(base, "BASE"));
		assertTrue(EStringUtil.equalsAny(base, new Match_Class()));
		assertTrue(EStringUtil.equalsAny(base, "a", "b", "c", new Match_Class()));
		assertTrue(EStringUtil.equalsAny(base, "a", "b", "c", "BASE", new Not_Match_Class()));
		
		var match = new Match_Class();
		var not_match = new Not_Match_Class();
		
		assertNull(EStringUtil.equalsAnyR(base, "a", "b", "c"));
		assertEquals("BASE", EStringUtil.equalsAnyR(base, base));
		assertEquals(match, EStringUtil.equalsAnyR(base, "a", 'b', "c", match, "d", "e"));
		assertEquals(null, EStringUtil.equalsAnyR(base, "a", 'b', "c", not_match, "d", "e"));
	}
	
}
