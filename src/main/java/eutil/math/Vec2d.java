package eutil.math;

import static eutil.EUtil.*;

/**
 * The {@code Vector2D} class is a data type containing three individual {@code Double} values: x and y.
 * The {@code Vector2D} class provides numerous functions for performing vector math both locally and statically.
 * 
 * <blockquote><pre>
 *     Vector2D vec = new Vector2D(x, y);
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.2.0
 */
public class Vec2d {

	/** 2D float vector of {0.0x, 0.0y}. */
	public static final Vec2d ZERO = new Vec2d(0.0, 0.0);
	/** 2D float vector of {Float.NaNx, Float.NaNy}. */ 
	public static final Vec2d NaN = new Vec2d(Double.NaN, Double.NaN);
	/** 2D float vector of {Float.POSITIVE_INFINITYx, FLOAT.POSITIVE_INFINITYy}. */
	public static final Vec2d POSITIVE_INFINITY = new Vec2d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	/** 2D float vector of {Float.NEGATIVE_INFINITYx, FLOAT.NEGATIVE_INFINITYy}. */
	public static final Vec2d NEGATIVE_INFINITY = new Vec2d(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	
	public double x = 0.0d, y = 0.0d;
	
	//--------------
	// Constructors
	//--------------
	
	/** Creates a new Vec2d with 0.0d for x and 0.0d for y. */
	public Vec2d() { this(0.0d, 0.0d); }
	/** Creates a new Vec2d using the given doubles 'n' for this Vec2d's x and y coordinate values. */
	public Vec2d(double n) { this(n, n); }
	/** Creates a new Vec2d using the given doubles 'x' and 'y' for this Vec2d's x and y coordinate values. */
	public Vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/** Creates a new Vec2d using the given NUMBER 'n' for this Vec2d's x and y coordinate values. */
	public Vec2d(Number n) { this(n, n); }
	/** Creates a new Vec2d using the given NUMBERS 'x' and 'y' for this Vec2d's x and y coordinate values. */
	public Vec2d(Number x, Number y) {
		this.x = x.doubleValue();
		this.y = y.doubleValue();
	}
	
	/**
	 * Creates a new Vec2d from an existing Vec2d's coordinates.
	 * Note: If the given Vec2d 'vecIn' is null, then this Vec2d's coordinates will be set to {0.0d, 0.0d}.
	 */
	public Vec2d(Vec2d vecIn) {
		if (vecIn == null) return;
		this.x = vecIn.x;
		this.y = vecIn.y;
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "<" + x + ", " + y + ">"; }
	
	//---------
	// Methods
	//---------
	
	/** Sets each value in this Vector3D to 0. */
	public Vec2d clear() { set(0, 0); return this; }
	
	/** Floors each value in this Vector3D. */
	public Vec2d floor() { set(Math.floor(x), Math.floor(y)); return this; }
	
	/** Ceils each value in this Vector3D. */
	public Vec2d ceil() { set(Math.ceil(x), Math.ceil(y)); return this; }
	
	/** Compares the contents of this Vector3D to another. */
	public boolean compare(Vec2d vecIn) { return nullDoR(vecIn, v -> compare(v.x, v.y), false); }
	
	/** Compares the contents of this Vector3D to each x, y and z value provided. */
	public boolean compare(double xIn, double yIn) { return (x == xIn && y == yIn); }
	
	/** Returns true if this Vector3Ds x is equal to the given value. */
	public boolean compareX(double xIn) { return x == xIn; }
	
	/** Returns true if this Vector3Ds y is equal to the given value. */
	public boolean compareY(double yIn) { return y == yIn; }
	
	/** Returns this vector's coordinates in the form of an array. */
	public double[] toArray() {
		return new double[] { x, y };
	}
	
	//-------------
	// Vector Math
	//-------------
	
	public double magnitude() { return Math.sqrt(x * x + y * y); }
	public double dotProduct(Vec2d vecIn) { return Math.acos(multiplyAndAdd(vecIn) / (magnitude() * vecIn.magnitude())); }
	public double dotProductDegrees(Vec2d vecIn) { return (dotProduct(vecIn) * 180) / Math.PI; }
	public double multiplyAndAdd(Vec2d vecIn) { return nullApplyR(vecIn, v -> x * v.x + y * v.y, Double.NaN); }
	public Vec2d scale(double val) { return scale(this, val);  }
	//public Vector2D cross(Vector2D vecIn) { return nullApplyR(vecIn, v -> new Vector2D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x), null); }
	public Vec2d add(Vec2d vecIn) { return nullApplyR(vecIn, v -> new Vec2d(x + v.x, y + v.y), this); }
	public Vec2d sub(Vec2d vecIn) { return nullApplyR(vecIn, v -> new Vec2d(x - v.x, y - v.y), null); }
	public Vec2d normalize() { return nullDoR(magnitude(), l -> { x /= l; y /= l; }, this); }

	//---------
	// Getters
	//---------
	
	public double getX() { return x; }
	public double getY() { return y; }
	
	//---------
	// Setters
	//---------
	
	public Vec2d set(Vec2d vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y), this); }
	public Vec2d set(Vec3i vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y), this); }
	public Vec2d set(double xIn, double yIn) { x = xIn; y = yIn; return this; }
	public Vec2d set(Number xIn, Number yIn) { x = xIn.doubleValue(); y = yIn.doubleValue(); return this; }
	
	public Vec2d setX(double xIn) { x = xIn; return this; }
	public Vec2d setY(double yIn) { y = yIn; return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	/** Returns the magnitude of the given Vector3. */
	public static double magnitude(Vec2d vecIn) {
		return nullApplyR(vecIn, v -> Math.sqrt(v.x * v.x + v.y * v.y), null);
	}
	
	/** Returns the dot product of the two given vectors. */
	public static double dotProduct(Vec2d vec1, Vec2d vec2) {
		return nullApplyR(vec1, vec2, (a, b) -> Math.acos(multiplyAndAdd(a, b) / magnitude(b)), null);
	}
	
	/** Returns a double that is the result of each given vector3s x, y, and z values being multiplied and added together. */
	public static double multiplyAndAdd(Vec2d vec1, Vec2d vec2) {
		return nullApplyR(vec1, vec2, (a, b) -> a.x * b.x + a.y * b.y, null);
	}
	
	/** Returns the given Vector3 with each of its x, y and z multiplied by a given double value. */
	public static Vec2d scale(Vec2d vecIn, double val) {
		return nullApplyR(vecIn, v -> new Vec2d(v.x * val, v.y * val));
	}
	
	/** Returns a new Vector3 containing the cross product of the two given Vector3 objects. */
	//public static Vector2D crossProduct(Vector2D vec1, Vector2D vec2) {
	//	return nullDoR(vec1, vec2, (a, b) -> new Vector2D(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x), null);
	//}
	
	/** Returns a new Vector3 containing the difference of the given vec2 values from the given vec1 values. (vec1 - vec2) */
	public static Vec2d add(Vec2d vec1, Vec2d vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec2d(a.x + b.x, a.y + b.y), null);
	}
	
	/** Returns a new Vector3 containing the difference of the given vec2 values from the given vec1 values. (vec1 - vec2) */
	public static Vec2d sub(Vec2d vec1, Vec2d vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec2d(a.x - b.x, a.y - b.y), null);
	}
	
	/** Returns the given Vector3 with normalized values. */
	public static Vec2d normalize(Vec2d vecIn) {
		return nullDoR(vecIn, magnitude(vecIn), (v, l) -> { v.x /= l; v.y /= l; }, vecIn);
	}
}
