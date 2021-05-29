package eutil.storage;

/**
 * A gropuing of two distinct object types.
 * 
 * @param <A>
 * @param <B>
 * 
 * @author Hunter Bragg
 */
public class Box<A, B> {
	
	private A a;
	private B b;
	
	//--------------
	// Constructors
	//--------------
	
	/** Creates a new StorageBox comprised of null values. */
	public Box() { this(null, null); }
	/** Creates a new StorageBox comprised from given A and B values. */
	public Box(A aIn, B bIn) { a = aIn; b = bIn; }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "[" + a + ", " + b + "]"; }
	
	//---------
	// Methods
	//---------
	
	/** Returns true if this box's A or B value matches the given obj. */
	public boolean contains(Object obj) {
		if (obj == null) { return a == null || b == null; }
		return ((a != null ? a.equals(obj) : false) || (b != null ? b.equals(obj) : false));
	}
	
	/** Returns true if this box's A value matches the given object. */
	public boolean containsA(Object obj) { return (obj == null ) ? a == null : obj.equals(a); }
	/** Returns true if this box's B value matches the given object. */
	public boolean containsB(Object obj) { return (obj == null) ? b == null : obj.equals(b); }
	
	/** Returns true if this box's contents match the given box's contents. */
	public boolean compare(Box<?, ?> boxIn) { return compare(boxIn.getA(), boxIn.getB()); }
	/** Returns true if this box's contents match the given A and B values. */
	public boolean compare(Object inA, Object inB) { return (a.equals(inA) && b.equals(inB)); }
	
	//---------
	// Getters
	//---------
	
	/** Returns the A value on this box. */
	public A getA() { return a; }
	/** Returns the B value on this box. */
	public B getB() { return b; }
	
	//---------
	// Setters
	//---------
	
	/** Sets this box's values to the values of another box. */
	public Box<A, B> set(Box<A, B> boxIn) { a = (boxIn != null) ? boxIn.a : null; b = (boxIn != null) ? boxIn.b : null; return this; }
	/** Sets this box's values to the given A and B values. */
	public Box<A, B> set(A obj, B val) { a = obj; b = val; return this; }
	/** Sets this box's A value to the given object. */
	public Box<A, B> setA(A obj) { a = obj; return this; }
	/** Sets this box's B value to the given object. */
	public Box<A, B> setB(B obj) { b = obj; return this; }
	/** Sets this box's A and B values to null. */
	public Box<A, B> clear() { a = null; b = null; return this; }
	
	//----------------
	// Static Methods
	//----------------
	
	/** Returns true if both boxes have the same contents. NOTE: returns false if either box (or both) is null.*/
	public static boolean compare(Box<?, ?> box1, Box<?, ?> box2) {
		return (box1 != null && box2 != null) ? box1.compare(box2) : false;
	}
	
}
