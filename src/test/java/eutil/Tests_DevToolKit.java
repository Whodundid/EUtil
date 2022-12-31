package eutil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import eutil.debug.DebugToolKit;

public class Tests_DevToolKit {
	
	@Test
	public void test_format() {
		//check that empty arguments produce an empty output
		assertEquals("", DebugToolKit.format(""));
		
		//check that normal argument insertion works as intended
		assertEquals("Hello World!", DebugToolKit.format("{0} {1}!", "Hello", "World"));
		
		//check that any datatype can be inserted as an argument
		assertEquals("123 Banana a 17.2", DebugToolKit.format("{0} {1} {2} {3}", 123, "Banana", 'a', 17.2));
		
		//check that non-number types aren't used as indexes
		assertEquals("123 Banana {Wombat} a 17.2", DebugToolKit.format("{0} {1} {Wombat} {2} {3}", 123, "Banana", 'a', 17.2));
		
		//check that args nested inside of bad args only insert the good args
		assertEquals("{0b}", DebugToolKit.format("{0{1}}", "123", 'b'));
	}
	
}
