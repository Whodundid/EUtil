package eutil.math;

import static eutil.EUtil.*;

/**
 * The {@code Vec3f} class is a data type containing three individual {@code Float} values: x, y, and z.
 * The {@code Vec3f} class provides numerous functions for performing vector math both locally and statically.
 * 
 * <blockquote><pre>
 *     Vec3f vec = new Vec3f(x, y, z);
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class Vec3f {

	public float x = 0.0f, y = 0.0f, z = 0.0f;
	
	//--------------
	// Constructors
	//--------------
	
	public Vec3f() {}
	public Vec3f(Vec3f vecIn) { nullDo(vecIn, v -> set(v.x, v.y, v.z)); }
	public Vec3f(Vec3i vecIn) { nullDo(vecIn, v -> set(v.x, v.y, v.z)); }
	public Vec3f(Number n) { set(n, n, n); }
	public Vec3f(Number x, Number y, Number z) { set(x, y, z); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "<" + x + ", " + y + ", " + z + ">"; }
	
	//---------
	// Methods
	//---------
	
	/** Sets each value in this Vec3f to 0. */
	public Vec3f clear() { set(0.0, 0.0, 0.0); return this; }
	
	/** Floors each value in this Vec3f. */
	public Vec3f floor() { set(Math.floor(x), Math.floor(y), Math.floor(z)); return this; }
	
	/** Ceils each value in this Vec3f. */
	public Vec3f ceil() { set(Math.ceil(x), Math.ceil(y), Math.ceil(z)); return this; }
	
	/** Returns a Vec3fInt from floored values of this Vec3f. */
	public Vec3i asVector3i() { return new Vec3i(x, y, z); }
	
	/** Returns a Vec3fInt from floored values of this Vec3f. */
	public Vec3d asVector3d() { return new Vec3d(x, y, z); }
	
	/** Compares the contents of this Vec3f to another. */
	public boolean compare(Vec3f vecIn) { return nullDoR(vecIn, v -> compare(v.x, v.y, v.z), false); }
	
	/** Compares the contents of this Vec3f to each x, y and z value provided. */
	public boolean compare(float xIn, float yIn, float zIn) { return (x == xIn && y == yIn && z == zIn); }
	
	/** Returns true if this Vec3fs x is equal to the given value. */
	public boolean compareX(float xIn) { return x == xIn; }
	
	/** Returns true if this Vec3fs y is equal to the given value. */
	public boolean compareY(float yIn) { return y == yIn; }
	
	/** Returns true if this Vec3fs z is equal to the given value. */
	public boolean compareZ(float zIn) { return z == zIn; }
	
	//-------------
	// Vector Math
	//-------------
	
	public double multAdd() { return multAdd(this); }
	public double mag() { return mag(this); }
	public double dot(Vec3f vecIn) { return dot(this, vecIn); }
	public double angle(Vec3f vecIn) { return angle(this, vecIn); }
	public double angleDegrees(Vec3f vecIn) { return angleDegrees(this, vecIn); }
	public Vec3f scale(double val) { return scale(this, val);  }
	public Vec3f cross(Vec3f vecIn) { return cross(this, vecIn); }
	public Vec3f add(Vec3f vecIn) { return add(this, vecIn); }
	public Vec3f add(float x, float y, float z) { return new Vec3f(this.x + x, this.y + y, this.z + z); }
	public Vec3f add(Number x, Number y, Number z) { return new Vec3f(this.x + x.floatValue(), this.y + y.floatValue(), this.z + z.floatValue()); }
	public Vec3f sub(Vec3f vecIn) { return sub(this, vecIn); }
	public Vec3f sub(float x, float y, float z) { return new Vec3f(this.x - x, this.y - y, this.z - z); }
	public Vec3f sub(Number x, Number y, Number z) { return new Vec3f(this.x - x.floatValue(), this.y - y.floatValue(), this.z - z.floatValue()); }
	public Vec3f norm() { return norm(this); }
	public double comp(Vec3f b) { return compAB(this, b); }
	public Vec3f proj(Vec3f b) { return projAB(this, b); }

	//---------
	// Getters
	//---------
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	
	//---------
	// Setters
	//---------
	
	public Vec3f set(Vec3f vecIn) { return set(vecIn.x, vecIn.y, vecIn.z); }
	public Vec3f set(Vec3i vecIn) { return set(vecIn.x, vecIn.y, vecIn.z); }
	public Vec3f set(Number xIn, Number yIn, Number zIn) { x = xIn.floatValue(); y = yIn.floatValue(); z = zIn.floatValue(); return this; }
	
	public Vec3f setX(float xIn) { x = xIn; return this; }
	public Vec3f setY(float yIn) { y = yIn; return this; }
	public Vec3f setZ(float zIn) { z = zIn; return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	public static double multAdd(Vec3f vecIn) {
		Vec3f v = vecIn;
		return v.x*v.x + v.y*v.y + v.z*v.z;
	}
	
	/** Returns the magnitdue of the given Vec3f. */
	public static double mag(Vec3f vecIn) {
		return Math.sqrt(multAdd(vecIn));
	}
	
	/** Returns the dot product of the two given vectors. */
	public static double dot(Vec3f vec1, Vec3f vec2) {
		Vec3f a = vec1, b = vec2;
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	
	/** Returns the angle of the vector in radians. */
	public static double angle(Vec3f vec1, Vec3f vec2) {
		Vec3f a = vec1, b = vec2;
		return Math.acos(dot(a, b) / mag(b));
	}
	
	/** Returns the angle of the vector in degrees */
	public static double angleDegrees(Vec3f vec1, Vec3f vec2) {
		Vec3f a = vec1, b = vec2;
		return (angle(a, b) * 180) / Math.PI;
	}
	
	/** Returns the given Vec3f with each of its x, y and z multiplied by a given float value. */
	public static Vec3f scale(Vec3f vecIn, double scaler) {
		Vec3f v = vecIn; double s = scaler;
		return new Vec3f(v.x * s, v.y * s, v.z * s);
	}
	
	/** Returns a new Vec3f containing the cross product of the two given Vector3 objects. */
	public static Vec3f cross(Vec3f vec1, Vec3f vec2) {
		Vec3f a = vec1, b = vec2;
		return new Vec3f((a.y*b.z - a.z*b.y), (a.z*b.x - a.x*b.z), (a.x*b.y - a.y*b.x));
	}
	
	/** Returns a new Vec3f containing the difference of the given vec2 values from the given vec1 values. (vec1 - vec2) */
	public static Vec3f add(Vec3f vec1, Vec3f vec2) {
		Vec3f a = vec1, b = vec2;
		return new Vec3f(a.x + b.x, a.y + b.y, a.z + b.z);
	}
	
	/** Returns a new Vec3f containing the difference of the given vec1 values from the given vec1 values. (vec1 - vec2) */
	public static Vec3f sub(Vec3f vec1, Vec3f vec2) {
		Vec3f a = vec1, b = vec2;
		return new Vec3f(a.x - b.x, a.y - b.y, a.z - b.z);
	}
	
	/** Returns the given Vec3f with normalized values. */
	public static Vec3f norm(Vec3f vecIn) {
		Vec3f v = vecIn; double l = mag(vecIn);
		return new Vec3f(v.x / l, v.y / l, v.z / l);
	}
	
	public static double compAB(Vec3f vecA, Vec3f vecB) {
		Vec3f a = vecA, b = vecB;
		return dot(a, b) / mag(a);
	}
	
	public static double compBA(Vec3f vecA, Vec3f vecB) {
		Vec3f a = vecA, b = vecB;
		return dot(b, a) / mag(b);
	}
	
	public static Vec3f projAB(Vec3f vecA, Vec3f vecB) {
		Vec3f a = vecA, b = vecB;
		return norm(a).scale(compAB(a, b));
	}
	
	public static Vec3f projBA(Vec3f vecA, Vec3f vecB) {
		Vec3f a = vecA, b = vecB;
		return norm(b).scale(compBA(a, b));
	}
	
}
