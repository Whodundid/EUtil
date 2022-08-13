package eutil.math;

import static eutil.EUtil.*;

/**
 * The {@code Vec2i} class is a data type containing two individual {@code Integer} values: x and y.
 * The {@code Vec2i} class provides numerous functions for performing vector math both locally and statically.
 * 
 * <blockquote><pre>
 *     Vec2i vec = new Vec2i(x, y);
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.2.0
 */
public class Vec2i {

	public long x = 0, y = 0;
	
	//--------------
	// Constructors
	//--------------
	
	public Vec2i() { set(0, 0); }
	public Vec2i(Vec2i vecIn) { nullDo(vecIn, v -> set(v.x, v.y)); }
	public Vec2i(Number n) { set(n, n); }
	public Vec2i(Number x, Number y) { set(x, y); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "<" + x + ", " + y + ">"; }
	
	//---------
	// Methods
	//---------
	
	/** Sets each value in this Vector3D to 0. */
	public Vec2i clear() { set(0, 0); return this; }
	
	/** Floors each value in this Vector3D. */
	public Vec2i floor() { set(Math.floor(x), Math.floor(y)); return this; }
	
	/** Ceils each value in this Vector3D. */
	public Vec2i ceil() { set(Math.ceil(x), Math.ceil(y)); return this; }
	
	/** Compares the contents of this Vector3D to another. */
	public boolean compare(Vec2i vecIn) { return nullDoR(vecIn, v -> compare(v.x, v.y), false); }
	
	/** Compares the contents of this Vector3D to each x, y and z value provided. */
	public boolean compare(long xIn, long yIn) { return (x == xIn && y == yIn); }
	
	/** Returns true if this Vector3Ds x is equal to the given value. */
	public boolean compareX(long xIn) { return x == xIn; }
	
	/** Returns true if this Vector3Ds y is equal to the given value. */
	public boolean compareY(long yIn) { return y == yIn; }
	
	//-------------
	// Vector Math
	//-------------
	
	public long magnitude() { return (long) Math.sqrt(x * x + y * y); }
	public long dotProduct(Vec2i vecIn) { return (long) Math.acos(multiplyAndAdd(vecIn) / (magnitude() * vecIn.magnitude())); }
	public long dotProductDegrees(Vec2i vecIn) { return (long) ((dotProduct(vecIn) * 180) / Math.PI); }
	public long multiplyAndAdd(Vec2i vecIn) { return nullApplyR(vecIn, v -> x * v.x + y * v.y, (long) -0); }
	public Vec2i scale(long val) { return scale(this, val);  }
	//public Vector2D cross(Vector2D vecIn) { return nullApplyR(vecIn, v -> new Vector2D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x), null); }
	public Vec2i add(Vec2i vecIn) { return nullApplyR(vecIn, v -> new Vec2i(x + v.x, y + v.y), this); }
	public Vec2i sub(Vec2i vecIn) { return nullApplyR(vecIn, v -> new Vec2i(x - v.x, y - v.y), null); }
	public Vec2i normalize() { return nullDoR(magnitude(), l -> { x /= l; y /= l; }, this); }

	//---------
	// Getters
	//---------
	
	public long getX() { return x; }
	public long getY() { return y; }
	
	//---------
	// Setters
	//---------
	
	public Vec2i set(Vec2i vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y), this); }
	public Vec2i set(Vec3i vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y), this); }
	public Vec2i set(Number xIn, Number yIn) { x = xIn.longValue(); y = yIn.longValue(); return this; }
	
	public Vec2i setX(long xIn) { x = xIn; return this; }
	public Vec2i setY(long yIn) { y = yIn; return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	/** Returns the magnitude of the given Vector3. */
	public static long magnitude(Vec2i vecIn) {
		return nullApplyR(vecIn, v -> (long) Math.sqrt(v.x * v.x + v.y * v.y), null);
	}
	
	/** Returns the dot product of the two given vectors. */
	public static long dotProduct(Vec2i vec1, Vec2i vec2) {
		return nullApplyR(vec1, vec2, (a, b) -> (long) Math.acos(multiplyAndAdd(a, b) / magnitude(b)), null);
	}
	
	/** Returns a long that is the result of each given vector3s x, y, and z values being multiplied and added together. */
	public static long multiplyAndAdd(Vec2i vec1, Vec2i vec2) {
		return nullApplyR(vec1, vec2, (a, b) -> a.x * b.x + a.y * b.y, null);
	}
	
	/** Returns the given Vector3 with each of its x, y and z multiplied by a given long value. */
	public static Vec2i scale(Vec2i vecIn, long val) {
		return nullApplyR(vecIn, v -> new Vec2i(v.x * val, v.y * val));
	}
	
	/** Returns a new Vector3 containing the cross product of the two given Vector3 objects. */
	//public static Vector2D crossProduct(Vector2D vec1, Vector2D vec2) {
	//	return nullDoR(vec1, vec2, (a, b) -> new Vector2D(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x), null);
	//}
	
	/** Returns a new Vector3 containing the difference of the given vec2 values from the given vec1 values. (vec1 - vec2) */
	public static Vec2i add(Vec2i vec1, Vec2i vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec2i(a.x + b.x, a.y + b.y), null);
	}
	
	/** Returns a new Vector3 containing the difference of the given vec2 values from the given vec1 values. (vec1 - vec2) */
	public static Vec2i sub(Vec2i vec1, Vec2i vec2) {
		return nullDoR(vec1, vec2, (a, b) -> new Vec2i(a.x - b.x, a.y - b.y), null);
	}
	
	/** Returns the given Vector3 with normalized values. */
	public static Vec2i normalize(Vec2i vecIn) {
		return nullDoR(vecIn, magnitude(vecIn), (v, l) -> { v.x /= l; v.y /= l; }, vecIn);
	}
}
