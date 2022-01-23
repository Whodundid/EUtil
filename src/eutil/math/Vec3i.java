package eutil.storage;

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
public class Vector3I {
	
	public long x = 0, y = 0, z = 0;
	
	//--------------
	// Constructors
	//--------------
	
	public Vector3I() { set(0, 0 , 0); }
	public Vector3I(Vector3I vecIn) { set(vecIn.x, vecIn.y, vecIn.z); }
	public Vector3I(Vector3D vecIn) { set(vecIn.x, vecIn.y, vecIn.z); }
	public Vector3I(Number x, Number y, Number z) { set(x, y, z); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "[" + x + ", " + y + ", " + z + "]"; }
	
	//---------
	// Methods
	//---------
	
	public Vector3I clear() { x = 0; y = 0; z = 0; return this; }
	public Vector3D asVector3D() { return new Vector3D(x, y, z); }
	
	public boolean compare(Vector3I in) { return (in != null) ? (x == in.x && y == in.y && z == in.z) : false; }
	public boolean compare(long xIn, long yIn, long zIn) { return (x == xIn && y == yIn && z == zIn); }
	
	//-------------
	// Vector Math
	//-------------
	
	public Vector3I add(Vector3I in) { return nullDoR(in, v -> set(x + in.x, y + in.y, z + in.z), this); }
	public Vector3I subtract(Vector3I in) { return nullDoR(in, v -> set(x - in.x, y - in.y, z - in.z), this); }
	public Vector3I multiply(Vector3I in) { return nullDoR(in, v -> set(x * in.x, y * in.y, z * in.z), this); }
	public Vector3I divide(Vector3I in) { return nullDoR(in, v -> set(x / in.x, y / in.y, z / in.z), this); }
	
	//---------
	// Getters
	//---------
	
	public long getX() { return x; }
	public long getY() { return y; }
	public long getZ() { return z; }
	
	//---------
	// Setters
	//---------
	
	public Vector3I setX(long xIn) { x = xIn; return this; }
	public Vector3I setY(long yIn) { y = yIn; return this; }
	public Vector3I setZ(long zIn) { z = zIn; return this; }
	
	public Vector3I set(Vector3I vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y, v.z), this); }
	public Vector3I set(Vector3D vecIn) { return nullDoR(vecIn, v -> set(v.x, v.y, v.z), this); }
	public Vector3I set(Number xIn, Number yIn, Number zIn) { x = xIn.longValue(); y = yIn.longValue(); z = zIn.longValue(); return this; }
	
	//--------------------
	// Static Vector Math
	//--------------------
	
	public static Vector3I add(Vector3I in1, Vector3I in2) { return nullApplyR(in1, in2, (a, b) -> new Vector3I(a.x + b.x, a.y + b.y, a.z + b.z), null); }
	public static Vector3I subtract(Vector3I in1, Vector3I in2) { return nullApplyR(in1, in2, (a, b) -> new Vector3I(a.x - b.x, a.y - b.y, a.z - b.z), null); }
	public static Vector3I multiply(Vector3I in1, Vector3I in2) { return nullApplyR(in1, in2, (a, b) -> new Vector3I(a.x * b.x, a.y * b.y, a.z * b.z), null); }
	public static Vector3I divide(Vector3I in1, Vector3I in2) { return nullApplyR(in1, in2, (a, b) -> new Vector3I(a.x / b.x, a.y / b.y, a.z / b.z), null); }
	
}
