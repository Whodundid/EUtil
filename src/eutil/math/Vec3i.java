package eutil.math;

import static eutil.EUtil.*;

/**
 * The {@code Vector3I} class is a data type containing three individual {@code Long} values: x, y, and z.
 * 
 * <blockquote><pre>
 *     Vector3I vec = new Vector3I(x, y, z);
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class Vec3i {
	
	public long x = 0, y = 0, z = 0;
	
	//--------------
	// Constructors
	//--------------
	
	public Vec3i() { set(0, 0 , 0); }
	public Vec3i(Vec3i vecIn) { set(vecIn.x, vecIn.y, vecIn.z); }
	public Vec3i(Vec3d vecIn) { set(vecIn.x, vecIn.y, vecIn.z); }
	public Vec3i(Number n) { set(n, n, n); }
	public Vec3i(Number x, Number y, Number z) { set(x, y, z); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "[" + x + ", " + y + ", " + z + "]"; }
	
	//---------
	// Methods
	//---------
	
	public Vec3i clear() { x = 0; y = 0; z = 0; return this; }
	public Vec3d toVec3d() { return new Vec3d(x, y, z); }
	
	public boolean compare(Vec3i in) { return (in != null) ? (x == in.x && y == in.y && z == in.z) : false; }
	public boolean compare(long xIn, long yIn, long zIn) { return (x == xIn && y == yIn && z == zIn); }
	
	//-------------
	// Vector Math
	//-------------
	
	public Vec3i add(Vec3i in) { return nullDoR(in, v -> set(x + in.x, y + in.y, z + in.z), this); }
	public Vec3i subtract(Vec3i in) { return nullDoR(in, v -> set(x - in.x, y - in.y, z - in.z), this); }
	public Vec3i multiply(Vec3i in) { return nullDoR(in, v -> set(x * in.x, y * in.y, z * in.z), this); }
	public Vec3i divide(Vec3i in) { return nullDoR(in, v -> set(x / in.x, y / in.y, z / in.z), this); }
	
	//---------
	// Getters
	//---------
	
	public long getX() { return x; }
	public long getY() { return y; }
	public long getZ() { return z; }
	
	//---------
	// Setters
	//---------
	
	public Vec3i setX(long xIn) { x = xIn; return this; }
	public Vec3i setY(long yIn) { y = yIn; return this; }
	public Vec3i setZ(long zIn) { z = zIn; return this; }
	
	public Vec3i set(Vec3i vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y, v.z), this); }
	public Vec3i set(Vec3d vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y, v.z), this); }
	public Vec3i set(Number xIn, Number yIn, Number zIn) { x = xIn.longValue(); y = yIn.longValue(); z = zIn.longValue(); return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	public static Vec3i add(Vec3i in1, Vec3i in2) { return nullApplyR(in1, in2, (a, b) -> new Vec3i(a.x + b.x, a.y + b.y, a.z + b.z), null); }
	public static Vec3i subtract(Vec3i in1, Vec3i in2) { return nullApplyR(in1, in2, (a, b) -> new Vec3i(a.x - b.x, a.y - b.y, a.z - b.z), null); }
	public static Vec3i multiply(Vec3i in1, Vec3i in2) { return nullApplyR(in1, in2, (a, b) -> new Vec3i(a.x * b.x, a.y * b.y, a.z * b.z), null); }
	public static Vec3i divide(Vec3i in1, Vec3i in2) { return nullApplyR(in1, in2, (a, b) -> new Vec3i(a.x / b.x, a.y / b.y, a.z / b.z), null); }
	
}
