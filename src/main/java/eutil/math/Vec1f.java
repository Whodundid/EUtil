package eutil.math;

import static eutil.EUtil.*;

import org.apache.commons.math3.util.FastMath;

public class Vec1f {
	
	public float x = 0L;
	
	//--------------
	// Constructors
	//--------------
	
	/** Creates a new Vec1f with 0L for x. */
	public Vec1f() { this(0L); }
	/** Creates a new Vec1f using the given float 'x' for this Vec1f's x coordinate value. */
	public Vec1f(float x) {
		this.x = x;
	}
	
	/** Creates a new Vec1f using the given NUMBER 'n' for this Vec1f's x coordinate value. */
	public Vec1f(Number n) {
		this.x = n.floatValue();
	}
	
	/**
	 * Creates a new Vec1f from an existing Vec1f's coordinate value.
	 * Note: If the given Vec1f 'vecIn' is null, then this Vec1f's coordinate will be set to {0L}.
	 */
	public Vec1f(Vec1f vecIn) {
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
	public Vec1f clear() { set(0L); return this; }
	
	/** Floors each value in this Vec1f. */
	public Vec1f floor() { set(Math.floor(x)); return this; }
	
	/** Ceils each value in this Vec1f. */
	public Vec1f ceil() { set(Math.ceil(x)); return this; }
	
	/** Compares the contents of this Vec1f to another. */
	public boolean compare(Vec1f vecIn) { return nullDoR(vecIn, v -> compare(v.x), false); }
	
	/** Compares the contents of this Vec1f to each x, y and z value provided. */
	public boolean compare(float xIn) { return (x == xIn); }
	
	/** Returns true if this Vec1f's x is equal to the given value. */
	public boolean compareX(float xIn) { return x == xIn; }
	
	/** Returns this vector's coordinate in the form of an array. */
	public float[] toArray() {
		return new float[] { x };
	}
	
	//-------------
	// Vector Math
	//-------------
	
	public Vec1f scale(float val) { return scale(this, val); }
	public Vec1f add(Vec1f vecIn) { return add(this, vecIn); }
	public Vec1f sub(Vec1f vecIn) { return sub(this, vecIn); }
	
	public float distance(Vec1f vecIn) {
		final float dx = vecIn.x - x;
		return FastMath.abs(dx);
	}
	
	public float distance(float point) {
		final float dx = point - x;
		return FastMath.abs(dx);
	}

	//---------
	// Getters
	//---------
	
	public float getX() { return x; }
	
	//---------
	// Setters
	//---------
	
	public Vec1f set(Vec1f vecIn) { set(vecIn.x); return this; }
	public Vec1f set(Vec2i vecIn) { set(vecIn.x); return this; }
	public Vec1f set(Vec3i vecIn) { set(vecIn.x); return this; }
	public Vec1f set(float xIn) { x = xIn; return this; }
	public Vec1f set(Number xIn) { x = xIn.floatValue(); return this; }
	
	public Vec1f setX(float xIn) { x = xIn; return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	/** Returns the given Vec1f with each of its x value multiplied by a given float value. */
	public static Vec1f scale(Vec1f vecIn, float val) {
		return nullApplyR(vecIn, v -> new Vec1f(v.x * val));
	}
	
	/** Returns a new Vec1f containing the difference of the given Vec1f values from the given vec1f values. (vec1 - vec2) */
	public static Vec1f add(Vec1f vec1, Vec1f vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec1f(a.x + b.x), null);
	}
	
	/** Returns a new Vec1f containing the difference of the given Vec1f values from the given vec1f values. (vec1 - vec2) */
	public static Vec1f sub(Vec1f vec1, Vec1f vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec1f(a.x - b.x), null);
	}
	
	/** Returns the given Vec1f with normalized values. */
	public static Vec1f normalize(Vec1f vecIn) {
		vecIn.x = FastMath.abs(vecIn.x);
		return vecIn;
	}
	
}
