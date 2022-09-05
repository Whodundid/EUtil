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

	public double x = 0, y = 0;
	
	//--------------
	// Constructors
	//--------------
	
	public Vec2d() { set(0, 0); }
	public Vec2d(Vec2d vecIn) { nullDo(vecIn, v -> set(v.x, v.y)); }
	public Vec2d(Number n) { set(n, n); }
	public Vec2d(Number x, Number y) { set(x, y); }
	
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
