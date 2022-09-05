package eutil.math;

import static eutil.EUtil.*;

/**
 * The {@code Vector3D} class is a data type containing three individual {@code Double} values: x, y, and z.
 * The {@code Vector3D} class provides numerous functions for performing vector math both locally and statically.
 * 
 * <blockquote><pre>
 *     Vector3D vec = new Vector3D(x, y, z);
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class Vec3d {

	public double x = 0.0, y = 0.0, z = 0.0;
	
	//--------------
	// Constructors
	//--------------
	
	public Vec3d() {}
	public Vec3d(Vec3d vecIn) { nullDo(vecIn, v -> set(v.x, v.y, v.z)); }
	public Vec3d(Vec3i vecIn) { nullDo(vecIn, v -> set(v.x, v.y, v.z)); }
	public Vec3d(Number n) { set(n, n, n); }
	public Vec3d(Number x, Number y, Number z) { set(x, y, z); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "<" + x + ", " + y + ", " + z + ">"; }
	
	//---------
	// Methods
	//---------
	
	/** Sets each value in this Vector3D to 0. */
	public Vec3d clear() { set(0.0, 0.0, 0.0); return this; }
	
	/** Floors each value in this Vector3D. */
	public Vec3d floor() { set(Math.floor(x), Math.floor(y), Math.floor(z)); return this; }
	
	/** Ceils each value in this Vector3D. */
	public Vec3d ceil() { set(Math.ceil(x), Math.ceil(y), Math.ceil(z)); return this; }
	
	/** Returns a Vector3DInt from floored values of this Vector3D. */
	public Vec3i toVec3i() { return new Vec3i(x, y, z); }
	
	/** Compares the contents of this Vector3D to another. */
	public boolean compare(Vec3d vecIn) { return nullDoR(vecIn, v -> compare(v.x, v.y, v.z), false); }
	
	/** Compares the contents of this Vector3D to each x, y and z value provided. */
	public boolean compare(double xIn, double yIn, double zIn) { return (x == xIn && y == yIn && z == zIn); }
	
	/** Returns true if this Vector3Ds x is equal to the given value. */
	public boolean compareX(double xIn) { return x == xIn; }
	
	/** Returns true if this Vector3Ds y is equal to the given value. */
	public boolean compareY(double yIn) { return y == yIn; }
	
	/** Returns true if this Vector3Ds z is equal to the given value. */
	public boolean compareZ(double zIn) { return z == zIn; }
	
	//-------------
	// Vector Math
	//-------------
	
	public double multAdd() { return multAdd(this); }
	public double mag() { return mag(this); }
	public double dot(Vec3d vecIn) { return dot(this, vecIn); }
	public double angle(Vec3d vecIn) { return angle(this, vecIn); }
	public double angleDegrees(Vec3d vecIn) { return angleDegrees(this, vecIn); }
	public Vec3d scale(double val) { return scale(this, val);  }
	public Vec3d cross(Vec3d vecIn) { return cross(this, vecIn); }
	public Vec3d add(Vec3d vecIn) { return add(this, vecIn); }
	public Vec3d sub(Vec3d vecIn) { return sub(this, vecIn); }
	public Vec3d norm() { return norm(this); }
	public double comp(Vec3d b) { return compAB(this, b); }
	public Vec3d proj(Vec3d b) { return projAB(this, b); }

	//---------
	// Getters
	//---------
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	
	//---------
	// Setters
	//---------
	
	public Vec3d set(Vec3d vecIn) { return set(vecIn.x, vecIn.y, vecIn.z); }
	public Vec3d set(Vec3i vecIn) { return set(vecIn.x, vecIn.y, vecIn.z); }
	public Vec3d set(Number xIn, Number yIn, Number zIn) { x = xIn.doubleValue(); y = yIn.doubleValue(); z = zIn.doubleValue(); return this; }
	
	public Vec3d setX(double xIn) { x = xIn; return this; }
	public Vec3d setY(double yIn) { y = yIn; return this; }
	public Vec3d setZ(double zIn) { z = zIn; return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	public static double multAdd(Vec3d vecIn) {
		Vec3d v = vecIn;
		return v.x*v.x + v.y*v.y + v.z*v.z;
	}
	
	/** Returns the magnitude of the given Vector3D. */
	public static double mag(Vec3d vecIn) {
		return Math.sqrt(multAdd(vecIn));
	}
	
	/** Returns the dot product of the two given vectors. */
	public static double dot(Vec3d vec1, Vec3d vec2) {
		Vec3d a = vec1, b = vec2;
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	
	/** Returns the angle of the vector in radians. */
	public static double angle(Vec3d vec1, Vec3d vec2) {
		Vec3d a = vec1, b = vec2;
		return Math.acos(dot(a, b) / mag(b));
	}
	
	/** Returns the angle of the vector in degrees */
	public static double angleDegrees(Vec3d vec1, Vec3d vec2) {
		Vec3d a = vec1, b = vec2;
		return (angle(a, b) * 180) / Math.PI;
	}
	
	/** Returns the given Vector3D with each of its x, y and z multiplied by a given double value. */
	public static Vec3d scale(Vec3d vecIn, double scaler) {
		Vec3d v = vecIn; double s = scaler;
		return new Vec3d(v.x * s, v.y * s, v.z * s);
	}
	
	/** Returns a new Vector3D containing the cross product of the two given Vector3 objects. */
	public static Vec3d cross(Vec3d vec1, Vec3d vec2) {
		Vec3d a = vec1, b = vec2;
		return new Vec3d((a.y*b.z - a.z*b.y), (a.z*b.x - a.x*b.z), (a.x*b.y - a.y*b.x));
	}
	
	/** Returns a new Vector3D containing the difference of the given vec2 values from the given vec1 values. (vec1 - vec2) */
	public static Vec3d add(Vec3d vec1, Vec3d vec2) {
		Vec3d a = vec1, b = vec2;
		return new Vec3d(a.x + b.x, a.y + b.y, a.z + b.z);
	}
	
	/** Returns a new Vector3D containing the difference of the given vec2 values from the given vec1 values. (vec1 - vec2) */
	public static Vec3d sub(Vec3d vec1, Vec3d vec2) {
		Vec3d a = vec1, b = vec2;
		return new Vec3d(a.x - b.x, a.y - b.y, a.z - b.z);
	}
	
	/** Returns the given Vector3D with normalized values. */
	public static Vec3d norm(Vec3d vecIn) {
		Vec3d v = vecIn; double l = mag(vecIn);
		return new Vec3d(v.x / l, v.y / l, v.z / l);
	}
	
	public static double compAB(Vec3d vecA, Vec3d vecB) {
		Vec3d a = vecA, b = vecB;
		return dot(a, b) / mag(a);
	}
	
	public static double compBA(Vec3d vecA, Vec3d vecB) {
		Vec3d a = vecA, b = vecB;
		return dot(b, a) / mag(b);
	}
	
	public static Vec3d projAB(Vec3d vecA, Vec3d vecB) {
		Vec3d a = vecA, b = vecB;
		return norm(a).scale(compAB(a, b));
	}
	
	public static Vec3d projBA(Vec3d vecA, Vec3d vecB) {
		Vec3d a = vecA, b = vecB;
		return norm(b).scale(compBA(a, b));
	}
	
}
