package eutil.math;

import static eutil.EUtil.*;

/**
 * The {@code Vector2D} class is a data type containing three individual {@code Float} values: x and y.
 * The {@code Vector2D} class provides numerous functions for performing vector math both locally and statically.
 * 
 * <blockquote><pre>
 *     Vector2D vec = new Vector2D(x, y);
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.2.0
 */
public class Vec2f {

	/** 2D float vector of {0.0x, 0.0y}. */
	public static final Vec2f ZERO = new Vec2f(0.0f, 0.0f);
	/** 2D float vector of {Float.NaNx, Float.NaNy}. */ 
	public static final Vec2f NaN = new Vec2f(Float.NaN, Float.NaN);
	/** 2D float vector of {Float.POSITIVE_INFINITYx, FLOAT.POSITIVE_INFINITYy}. */
	public static final Vec2f POSITIVE_INFINITY = new Vec2f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
	/** 2D float vector of {Float.NEGATIVE_INFINITYx, FLOAT.NEGATIVE_INFINITYy}. */
	public static final Vec2f NEGATIVE_INFINITY = new Vec2f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
	
	public float x = 0.0f, y = 0.0f;
	
	//--------------
	// Constructors
	//--------------
	
	/** Creates a new Vec2f with 0.0f for x and 0.0f for y. */
	public Vec2f() { this(0.0f, 0.0f); }
	/** Creates a new Vec2f using the given float 'n' for this Vec2f's x and y coordinate values. */
	public Vec2f(float n) { this(n, n); }
	/** Creates a new Vec2f using the given floats 'x' and 'y' for this Vec2f's x and y coordinate values. */
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/** Creates a new Vec2f using the given NUMBER 'n' for this Vec2f's x and y coordinate values. */
	public Vec2f(Number n) { this(n, n); }
	/** Creates a new Vec2f using the given NUMBERS 'x' and 'y' for this Vec2f's x and y coordinate values. */
	public Vec2f(Number x, Number y) {
		this.x = x.floatValue();
		this.y = y.floatValue();
	}
	
	/**
	 * Creates a new Vec2f from an existing Vec2f's coordinates.
	 * Note: If the given Vec2f 'vecIn' is null, then this Vec2f's coordinates will be set to {0.0f, 0.0f}.
	 */
	public Vec2f(Vec2f vecIn) {
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
	public Vec2f clear() { set(0, 0); return this; }
	
	/** Floors each value in this Vector3D. */
	public Vec2f floor() { set(Math.floor(x), Math.floor(y)); return this; }
	
	/** Ceils each value in this Vector3D. */
	public Vec2f ceil() { set(Math.ceil(x), Math.ceil(y)); return this; }
	
	/** Compares the contents of this Vector3D to another. */
	public boolean compare(Vec2f vecIn) { return nullDoR(vecIn, v -> compare(v.x, v.y), false); }
	
	/** Compares the contents of this Vector3D to each x, y and z value provided. */
	public boolean compare(float xIn, float yIn) { return (x == xIn && y == yIn); }
	
	/** Returns true if this Vector3Ds x is equal to the given value. */
	public boolean compareX(float xIn) { return x == xIn; }
	
	/** Returns true if this Vector3Ds y is equal to the given value. */
	public boolean compareY(float yIn) { return y == yIn; }
	
	/** Returns this vector's coordinates in the form of an array. */
	public float[] toArray() {
		return new float[] { x, y };
	}
	
	//-------------
	// Vector Math
	//-------------
	
	public double magnitude() { return Math.sqrt(x * x + y * y); }
	public double dotProduct(Vec2f vecIn) { return Math.acos(multiplyAndAdd(vecIn) / (magnitude() * vecIn.magnitude())); }
	public double dotProductDegrees(Vec2f vecIn) { return (dotProduct(vecIn) * 180) / Math.PI; }
	public float multiplyAndAdd(Vec2f vecIn) { return nullApplyR(vecIn, v -> x * v.x + y * v.y, Float.NaN); }
	public Vec2f scale(double val) { return scale(this, val);  }
	//public Vector2D cross(Vector2D vecIn) { return nullApplyR(vecIn, v -> new Vector2D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x), null); }
	public Vec2f add(Vec2f vecIn) { return nullApplyR(vecIn, v -> new Vec2f(x + v.x, y + v.y), this); }
	public Vec2f sub(Vec2f vecIn) { return nullApplyR(vecIn, v -> new Vec2f(x - v.x, y - v.y), null); }
	public Vec2f normalize() { return nullDoR(magnitude(), l -> { x /= l; y /= l; }, this); }

	//---------
	// Getters
	//---------
	
	public float getX() { return x; }
	public float getY() { return y; }
	
	//---------
	// Setters
	//---------
	
	public Vec2f set(Vec2f vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y), this); }
	public Vec2f set(Vec3i vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y), this); }
	public Vec2f set(float xIn, float yIn) { x = xIn; y = yIn; return this; }
	public Vec2f set(Number xIn, Number yIn) { x = xIn.floatValue(); y = yIn.floatValue(); return this; }
	
	public Vec2f setX(float xIn) { x = xIn; return this; }
	public Vec2f setY(float yIn) { y = yIn; return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	/** Returns the magnitude of the given Vector3. */
	public static double magnitude(Vec2f vecIn) {
		return nullApplyR(vecIn, v -> Math.sqrt(v.x * v.x + v.y * v.y), null);
	}
	
	/** Returns the dot product of the two given vectors. */
	public static double dotProduct(Vec2f vec1, Vec2f vec2) {
		return nullApplyR(vec1, vec2, (a, b) -> Math.acos(multiplyAndAdd(a, b) / magnitude(b)), null);
	}
	
	/** Returns a float that is the result of each given vector3s x, y, and z values being multiplied and added together. */
	public static float multiplyAndAdd(Vec2f vec1, Vec2f vec2) {
		return nullApplyR(vec1, vec2, (a, b) -> a.x * b.x + a.y * b.y, null);
	}
	
	/** Returns the given Vector3 with each of its x, y and z multiplied by a given float value. */
	public static Vec2f scale(Vec2f vecIn, double val) {
		return nullApplyR(vecIn, v -> new Vec2f(v.x * val, v.y * val));
	}
	
	/** Returns a new Vector3 containing the cross product of the two given Vector3 objects. */
	//public static Vector2D crossProduct(Vector2D vec1, Vector2D vec2) {
	//	return nullDoR(vec1, vec2, (a, b) -> new Vector2D(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x), null);
	//}
	
	/** Returns a new Vector3 containing the difference of the given vec2 values from the given vec1 values. (vec1 - vec2) */
	public static Vec2f add(Vec2f vec1, Vec2f vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec2f(a.x + b.x, a.y + b.y), null);
	}
	
	/** Returns a new Vector3 containing the difference of the given vec2 values from the given vec1 values. (vec1 - vec2) */
	public static Vec2f sub(Vec2f vec1, Vec2f vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec2f(a.x - b.x, a.y - b.y), null);
	}
	
	/** Returns the given Vector3 with normalized values. */
	public static Vec2f normalize(Vec2f vecIn) {
		return nullDoR(vecIn, magnitude(vecIn), (v, l) -> { v.x /= l; v.y /= l; }, vecIn);
	}
}
