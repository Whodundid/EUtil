package eutil.math;

import static eutil.EUtil.*;

import org.apache.commons.math3.util.FastMath;

public class Vec1i {
	
	public long x = 0L;
	
	//--------------
	// Constructors
	//--------------
	
	/** Creates a new Vec1i with 0L for x. */
	public Vec1i() { this(0L); }
	/** Creates a new Vec1i using the given long 'x' for this Vec1i's x coordinate value. */
	public Vec1i(long x) {
		this.x = x;
	}
	
	/** Creates a new Vec1i using the given NUMBER 'n' for this Vec1i's x coordinate value. */
	public Vec1i(Number n) {
		this.x = n.longValue();
	}
	
	/**
	 * Creates a new Vec1i from an existing Vec1i's coordinate value.
	 * Note: If the given Vec1i 'vecIn' is null, then this Vec1i's coordinate will be set to {0L}.
	 */
	public Vec1i(Vec1i vecIn) {
		if (vecIn == null) return;
		this.x = vecIn.x;
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "<" + x + ">"; }
	
	//---------
	// Methods
	//---------
	
	/** Sets each value in this Vec2i to 0. */
	public Vec1i clear() { set(0L); return this; }
	
	/** Floors each value in this Vec1i. */
	public Vec1i floor() { set(Math.floor(x)); return this; }
	
	/** Ceils each value in this Vec1i. */
	public Vec1i ceil() { set(Math.ceil(x)); return this; }
	
	/** Compares the contents of this Vec1i to another. */
	public boolean compare(Vec1i vecIn) { return nullDoR(vecIn, v -> compare(v.x), false); }
	
	/** Compares the contents of this Vec1i to each x, y and z value provided. */
	public boolean compare(long xIn) { return (x == xIn); }
	
	/** Returns true if this Vec1i's x is equal to the given value. */
	public boolean compareX(long xIn) { return x == xIn; }
	
	/** Returns this vector's coordinate in the form of an array. */
	public long[] toArray() {
		return new long[] { x };
	}
	
	//-------------
	// Vector Math
	//-------------
	
	public Vec1i scale(long val) { return scale(this, val); }
	public Vec1i add(Vec1i vecIn) { return add(this, vecIn); }
	public Vec1i sub(Vec1i vecIn) { return sub(this, vecIn); }
	
	public long distance(Vec1i vecIn) {
		final long dx = vecIn.x - x;
		return FastMath.abs(dx);
	}
	
	public long distance(long point) {
		final long dx = point - x;
		return FastMath.abs(dx);
	}

	//---------
	// Getters
	//---------
	
	public long getX() { return x; }
	
	//---------
	// Setters
	//---------
	
	public Vec1i set(Vec1i vecIn) { set(vecIn.x); return this; }
	public Vec1i set(Vec2i vecIn) { set(vecIn.x); return this; }
	public Vec1i set(Vec3i vecIn) { set(vecIn.x); return this; }
	public Vec1i set(long xIn) { x = xIn; return this; }
	public Vec1i set(Number xIn) { x = xIn.longValue(); return this; }
	
	public Vec1i setX(long xIn) { x = xIn; return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	/** Returns the given Vec1i with each of its x value multiplied by a given long value. */
	public static Vec1i scale(Vec1i vecIn, long val) {
		return nullApplyR(vecIn, v -> new Vec1i(v.x * val));
	}
	
	/** Returns a new Vec1i containing the difference of the given Vec1i values from the given vec1i values. (vec1 - vec2) */
	public static Vec1i add(Vec1i vec1, Vec1i vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec1i(a.x + b.x), null);
	}
	
	/** Returns a new Vec1i containing the difference of the given Vec1i values from the given vec1i values. (vec1 - vec2) */
	public static Vec1i sub(Vec1i vec1, Vec1i vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec1i(a.x - b.x), null);
	}
	
	/** Returns the given Vec1i with normalized values. */
	public static Vec1i normalize(Vec1i vecIn) {
		vecIn.x = FastMath.abs(vecIn.x);
		return vecIn;
	}
	
}
