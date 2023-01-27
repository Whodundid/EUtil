package eutil.math.vectors;

import static eutil.EUtil.*;

/**
 * This vector version primarily deals with 4-value vectors.
 * 
 * <blockquote><pre>
 *     Vec4f vec = new Vec4f(x, y, z, w);
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.2.1
 */
public class Vec4f {

	public float x = 0.0f, y = 0.0f, z = 0.0f, w = 0.0f;
	
	//--------------
	// Constructors
	//--------------
	
	public Vec4f() {}
	public Vec4f(Vec4f vecIn) { nullDo(vecIn, v -> set(v.x, v.y, v.z, 0.0f)); }
	public Vec4f(Vec3i vecIn) { nullDo(vecIn, v -> set(v.x, v.y, v.z, 0.0f)); }
	public Vec4f(Vec4f vecIn, float w) { nullDo(vecIn, v -> set(v.x, v.y, v.z, w)); }
	public Vec4f(Vec3i vecIn, float w) { nullDo(vecIn, v -> set(v.x, v.y, v.z, w)); }
	public Vec4f(Number n) { set(n, n, n, n); }
	public Vec4f(Number x, Number y, Number z) { set(x, y, z, 0.0f); }
	public Vec4f(Number x, Number y, Number z, Number w) { set(x, y, z, w); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "<" + x + ", " + y + ", " + z + ", " + w + ">"; }
	
	//---------
	// Methods
	//---------
	
	/** Sets each value in this Vec3f to 0. */
	public Vec4f clear() { set(0.0f, 0.0f, 0.0f, 0.0f); return this; }
	
	/** Floors each value in this Vec3f. */
	public Vec4f floor() { set(Math.floor(x), Math.floor(y), Math.floor(z), Math.floor(w)); return this; }
	
	/** Ceils each value in this Vec3f. */
	public Vec4f ceil() { set(Math.ceil(x), Math.ceil(y), Math.ceil(z), Math.ceil(w)); return this; }
	
	/** Compares the contents of this Vec3f to another. */
	public boolean compare(Vec4f vecIn) { return nullDoR(vecIn, v -> compare(v.x, v.y, v.z, v.w), false); }
	
	/** Compares the contents of this Vec3f to each x, y and z value provided. */
	public boolean compare(float xIn, float yIn, float zIn, float aIn) { return (x == xIn && y == yIn && z == zIn && w == aIn); }
	
	/** Returns true if this Vec3fs x is equal to the given value. */
	public boolean compareX(float xIn) { return x == xIn; }
	
	/** Returns true if this Vec3fs y is equal to the given value. */
	public boolean compareY(float yIn) { return y == yIn; }
	
	/** Returns true if this Vec3fs z is equal to the given value. */
	public boolean compareZ(float zIn) { return z == zIn; }
	
	/** Returns true if this Vec3fs z is equal to the given value. */
	public boolean compareW(float aIn) { return w == aIn; }

	//---------
	// Getters
	//---------
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	public float getW() { return w; }
	
	//---------
	// Setters
	//---------
	
	public Vec4f set(Vec4f vecIn, float wIn) { return set(vecIn.x, vecIn.y, vecIn.z, wIn); }
	public Vec4f set(Vec3i vecIn, float wIn) { return set(vecIn.x, vecIn.y, vecIn.z, wIn); }
	public Vec4f set(Number xIn, Number yIn, Number zIn, Number wIn) {
		x = xIn.floatValue();
		y = yIn.floatValue();
		z = zIn.floatValue();
		w = wIn.floatValue();
		return this;
	}
	
	public Vec4f setX(float xIn) { x = xIn; return this; }
	public Vec4f setY(float yIn) { y = yIn; return this; }
	public Vec4f setZ(float zIn) { z = zIn; return this; }
	public Vec4f setW(float aIn) { w = aIn; return this; }
	
	public Vec4f of(Number xIn, Number yIn, Number zIn, Number wIn) { return new Vec4f(xIn, yIn, zIn, wIn); }
	
}
