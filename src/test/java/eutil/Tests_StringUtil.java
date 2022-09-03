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
 */
public class Tests_StringUtil {
	
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
	
}
