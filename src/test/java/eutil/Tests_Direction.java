package eutil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import eutil.misc.Direction;

/**
 * A series of tests for the Direction enum.
 * 
 * @author Hunter Bragg
 */
public class Tests_Direction {
	
	@Test
	public void test_longitudes() {
		var longitudes = Direction.longitudes();
		
		assertEquals(2, longitudes.size());
		assertTrue(longitudes.contains(Direction.N) && longitudes.contains(Direction.S));
	}
	
	@Test
	public void test_latitudes() {
		var latitudes = Direction.latitudes();
		
		assertEquals(2, latitudes.size());
		assertTrue(latitudes.contains(Direction.E) && latitudes.contains(Direction.W));
	}
	
}
