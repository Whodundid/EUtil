package eutil.math.vectors;

import static eutil.EUtil.*;

/**
 * The {@code Vector4i} class is a data type containing three individual {@code Long} values: x, y, z and w.
 * 
 * <blockquote><pre>
 *     Vector4i vec = new Vector4i(x, y, z, w);
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.2.1
 */
public class Vec4i {
	
	public long x = 0, y = 0, z = 0, w = 0;
	
	//--------------
	// Constructors
	//--------------
	
	public Vec4i() { set(0, 0, 0 , 0); }
	public Vec4i(Vec4i vecIn) { set(vecIn.x, vecIn.y, vecIn.z, vecIn.w); }
	public Vec4i(Vec3i vecIn) { set(vecIn.x, vecIn.y, vecIn.z, 0); }
	public Vec4i(Vec3d vecIn) { set(vecIn.x, vecIn.y, vecIn.z, 0); }
	public Vec4i(Number n) { set(n, n, n, n); }
	public Vec4i(Number x, Number y, Number z) { set(x, y, z, 0); }
	public Vec4i(Number x, Number y, Number z, Number w) { set(x, y, z, w); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "[" + x + ", " + y + ", " + z + ", " + w + "]"; }
	
	//---------
	// Methods
	//---------
	
	public Vec4i clear() { x = 0; y = 0; z = 0; w = 0; return this; }
	public Vec4d toVec4d() { return new Vec4d(x, y, z, w); }
	
	public boolean compare(Vec4i in) { return (in != null) ? (x == in.x && y == in.y && z == in.z && w == in.w) : false; }
	public boolean compare(long wIn, long xIn, long yIn, long zIn) { return (x == xIn && y == yIn && z == zIn && w == wIn); }
	
	//-------------
	// Vector Math
	//-------------
	
	public Vec4i add(Vec4i in) { return nullDoR(in, v -> set(w + in.w, x + in.x, y + in.y, z + in.z), this); }
	public Vec4i subtract(Vec4i in) { return nullDoR(in, v -> set(w - in.w, x - in.x, y - in.y, z - in.z), this); }
	public Vec4i multiply(Vec4i in) { return nullDoR(in, v -> set(w * in.w, x * in.x, y * in.y, z * in.z), this); }
	public Vec4i divide(Vec4i in) { return nullDoR(in, v -> set(w / in.w, x / in.x, y / in.y, z / in.z), this); }
	
	//---------
	// Getters
	//---------
	
	public long getX() { return x; }
	public long getY() { return y; }
	public long getZ() { return z; }
	public long getW() { return w; }
	
	//---------
	// Setters
	//---------
	
	public Vec4i setX(long xIn) { x = xIn; return this; }
	public Vec4i setY(long yIn) { y = yIn; return this; }
	public Vec4i setZ(long zIn) { z = zIn; return this; }
	public Vec4i setW(long wIn) { w = wIn; return this; }
	
	public Vec4i set(Vec4i vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y, v.z, v.w), this); }
	public Vec4i set(Vec3d vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y, v.z, 0), this); }
	public Vec4i set(Number xIn, Number yIn, Number zIn, Number wIn) {
		x = xIn.longValue();
		y = yIn.longValue();
		z = zIn.longValue();
		w = wIn.longValue();
		return this;
	}
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	public static Vec4i add(Vec4i in1, Vec4i in2) { return nullApplyR(in1, in2, (a, b) -> new Vec4i(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w), null); }
	public static Vec4i subtract(Vec4i in1, Vec4i in2) { return nullApplyR(in1, in2, (a, b) -> new Vec4i(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w), null); }
	public static Vec4i multiply(Vec4i in1, Vec4i in2) { return nullApplyR(in1, in2, (a, b) -> new Vec4i(a.x * b.x, a.y * b.y, a.z * b.z, a.w * b.w), null); }
	public static Vec4i divide(Vec4i in1, Vec4i in2) { return nullApplyR(in1, in2, (a, b) -> new Vec4i(a.x / b.x, a.y / b.y, a.z / b.z, a.w / b.w), null); }
	
}
