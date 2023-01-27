package eutil.math.vectors;

import static eutil.EUtil.*;

/**
 * This vector version primarily deals with 4-value vectors.
 * 
 * <blockquote><pre>
 *     Vec4d vec = new Vec4d(x, y, z, w);
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.2.1
 */
public class Vec4d {

	public double x = 0.0, y = 0.0, z = 0.0, w = 0.0;
	
	//--------------
	// Constructors
	//--------------
	
	public Vec4d() {}
	public Vec4d(Vec4d vecIn) { nullDo(vecIn, v -> set(v.x, v.y, v.z, 0.0)); }
	public Vec4d(Vec3i vecIn) { nullDo(vecIn, v -> set(v.x, v.y, v.z, 0.0)); }
	public Vec4d(Vec4d vecIn, double w) { nullDo(vecIn, v -> set(v.x, v.y, v.z, w)); }
	public Vec4d(Vec3i vecIn, double w) { nullDo(vecIn, v -> set(v.x, v.y, v.z, w)); }
	public Vec4d(Number n) { set(n, n, n, n); }
	public Vec4d(Number x, Number y, Number z) { set(x, y, z, 0.0); }
	public Vec4d(Number x, Number y, Number z, Number w) { set(x, y, z, w); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "<" + x + ", " + y + ", " + z + ", " + w + ">"; }
	
	//---------
	// Methods
	//---------
	
	/** Sets each value in this Vec3d to 0. */
	public Vec4d clear() { set(0.0, 0.0, 0.0, 0.0); return this; }
	
	/** Floors each value in this Vec3d. */
	public Vec4d floor() { set(Math.floor(x), Math.floor(y), Math.floor(z), Math.floor(w)); return this; }
	
	/** Ceils each value in this Vec3d. */
	public Vec4d ceil() { set(Math.ceil(x), Math.ceil(y), Math.ceil(z), Math.ceil(w)); return this; }
	
	/** Compares the contents of this Vec3d to another. */
	public boolean compare(Vec4d vecIn) { return nullDoR(vecIn, v -> compare(v.x, v.y, v.z, v.w), false); }
	
	/** Compares the contents of this Vec3d to each x, y and z value provided. */
	public boolean compare(double xIn, double yIn, double zIn, double aIn) { return (x == xIn && y == yIn && z == zIn && w == aIn); }
	
	/** Returns true if this Vec3ds x is equal to the given value. */
	public boolean compareX(double xIn) { return x == xIn; }
	
	/** Returns true if this Vec3ds y is equal to the given value. */
	public boolean compareY(double yIn) { return y == yIn; }
	
	/** Returns true if this Vec3ds z is equal to the given value. */
	public boolean compareZ(double zIn) { return z == zIn; }
	
	/** Returns true if this Vec3ds z is equal to the given value. */
	public boolean compareW(double aIn) { return w == aIn; }

	//---------
	// Getters
	//---------
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public double getW() { return w; }
	
	//---------
	// Setters
	//---------
	
	public Vec4d set(Vec4d vecIn, double wIn) { return set(vecIn.x, vecIn.y, vecIn.z, wIn); }
	public Vec4d set(Vec3i vecIn, double wIn) { return set(vecIn.x, vecIn.y, vecIn.z, wIn); }
	public Vec4d set(Number xIn, Number yIn, Number zIn, Number wIn) {
		x = xIn.doubleValue();
		y = yIn.doubleValue();
		z = zIn.doubleValue();
		w = wIn.doubleValue();
		return this;
	}
	
	public Vec4d setX(double xIn) { x = xIn; return this; }
	public Vec4d setY(double yIn) { y = yIn; return this; }
	public Vec4d setZ(double zIn) { z = zIn; return this; }
	public Vec4d setW(double aIn) { w = aIn; return this; }
	
	public Vec4d of(Number xIn, Number yIn, Number zIn, Number wIn) { return new Vec4d(xIn, yIn, zIn, wIn); }
	
}
