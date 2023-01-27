package eutil.math.vectors;

import static eutil.EUtil.*;

import org.apache.commons.math3.util.FastMath;

public class Vec1d {
	
	public double x = 0L;
	
	//--------------
	// Constructors
	//--------------
	
	/** Creates a new Vec1d with 0L for x. */
	public Vec1d() { this(0L); }
	/** Creates a new Vec1d using the given double 'x' for this Vec1d's x coordinate value. */
	public Vec1d(double x) {
		this.x = x;
	}
	
	/** Creates a new Vec1d using the given NUMBER 'n' for this Vec1d's x coordinate value. */
	public Vec1d(Number n) {
		this.x = n.doubleValue();
	}
	
	/**
	 * Creates a new Vec1d from an existing Vec1d's coordinate value.
	 * Note: If the given Vec1d 'vecIn' is null, then this Vec1d's coordinate will be set to {0L}.
	 */
	public Vec1d(Vec1d vecIn) {
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
	public Vec1d clear() { set(0L); return this; }
	
	/** Floors each value in this Vec1d. */
	public Vec1d floor() { set(Math.floor(x)); return this; }
	
	/** Ceils each value in this Vec1d. */
	public Vec1d ceil() { set(Math.ceil(x)); return this; }
	
	/** Compares the contents of this Vec1d to another. */
	public boolean compare(Vec1d vecIn) { return nullDoR(vecIn, v -> compare(v.x), false); }
	
	/** Compares the contents of this Vec1d to each x, y and z value provided. */
	public boolean compare(double xIn) { return (x == xIn); }
	
	/** Returns true if this Vec1d's x is equal to the given value. */
	public boolean compareX(double xIn) { return x == xIn; }
	
	/** Returns this vector's coordinate in the form of an array. */
	public double[] toArray() {
		return new double[] { x };
	}
	
	//-------------
	// Vector Math
	//-------------
	
	public Vec1d scale(double val) { return scale(this, val); }
	public Vec1d add(Vec1d vecIn) { return add(this, vecIn); }
	public Vec1d sub(Vec1d vecIn) { return sub(this, vecIn); }
	
	public double distance(Vec1d vecIn) {
		final double dx = vecIn.x - x;
		return FastMath.abs(dx);
	}
	
	public double distance(double point) {
		final double dx = point - x;
		return FastMath.abs(dx);
	}

	//---------
	// Getters
	//---------
	
	public double getX() { return x; }
	
	//---------
	// Setters
	//---------
	
	public Vec1d set(Vec1d vecIn) { set(vecIn.x); return this; }
	public Vec1d set(Vec2i vecIn) { set(vecIn.x); return this; }
	public Vec1d set(Vec3i vecIn) { set(vecIn.x); return this; }
	public Vec1d set(double xIn) { x = xIn; return this; }
	public Vec1d set(Number xIn) { x = xIn.doubleValue(); return this; }
	
	public Vec1d setX(double xIn) { x = xIn; return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	/** Returns the given Vec1d with each of its x value multiplied by a given double value. */
	public static Vec1d scale(Vec1d vecIn, double val) {
		return nullApplyR(vecIn, v -> new Vec1d(v.x * val));
	}
	
	/** Returns a new Vec1d containing the difference of the given Vec1d values from the given Vec1d values. (vec1 - vec2) */
	public static Vec1d add(Vec1d vec1, Vec1d vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec1d(a.x + b.x), null);
	}
	
	/** Returns a new Vec1d containing the difference of the given Vec1d values from the given Vec1d values. (vec1 - vec2) */
	public static Vec1d sub(Vec1d vec1, Vec1d vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec1d(a.x - b.x), null);
	}
	
	/** Returns the given Vec1d with normalized values. */
	public static Vec1d normalize(Vec1d vecIn) {
		vecIn.x = FastMath.abs(vecIn.x);
		return vecIn;
	}
	
}
