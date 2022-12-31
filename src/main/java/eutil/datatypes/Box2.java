package eutil.datatypes;

import static eutil.EUtil.*;

import eutil.EUtil;

/**
 * A grouping of two distinct object types.
 * 
 * @param <A>
 * @param <B>
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class Box2<A, B> implements Comparable<Box2<A, B>> {
	
	private A a;
	private B b;
	
	//--------------
	// Constructors
	//--------------
	
	/** Creates a new Box2 comprised of null values. */
	public Box2() { this(null, null); }
	/** Creates a new Box2 comprised from given A and B values. */
	public Box2(A aIn, B bIn) { a = aIn; b = bIn; }
	/** Creates a new Box2 from a given Box2's values. */
	public Box2(Box2<A, B> boxIn) { a = boxIn.a; b = boxIn.b; }
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public String toString() {
		return "[" + a + ", " + b + "]";
	}
	
	@Override
	public int compareTo(Box2<A, B> o) {
		return (a instanceof Comparable c) ? c.compareTo(b) : 0;
	}
	
	//---------
	// Methods
	//---------
	
	/** Sets this box's A and B values to null. */
	public Box2<A, B> clear() { a = null; b = null; return this; }
	
	/** Returns true if this box's A or B value matches the given obj. */
	public boolean contains(Object obj) {
		if (obj == null) return a == null || b == null;
		return isAnyEqual(obj, a, b);
	}
	
	/** Returns true if this box's A value matches the given object. */
	public boolean containsA(Object obj) { return isEqual(a, obj); }
	/** Returns true if this box's B value matches the given object. */
	public boolean containsB(Object obj) { return isEqual(b, obj); }
	
	/** Returns true if this box's contents match the given box's contents. */
	public boolean compare(Box2<?, ?> boxIn) { return compare(boxIn.getA(), boxIn.getB()); }
	/** Returns true if this box's contents match the given A and B values. */
	public boolean compare(Object inA, Object inB) { return isEqual(a, inA) && isEqual(b, inB); }
	
	/**
	 * Returns true if any of this box's objects are null.
	 * 
	 * @return True if both A or B are null
	 * @since 1.6.6
	 */
	public boolean anyNull() {
		return EUtil.anyNull(a, b);
	}
	
	/**
	 * Returns true if none of this box's objects are null.
	 * 
	 * @return True if both A and B are not null
	 * @since 1.6.6
	 */
	public boolean notNull() {
		return EUtil.notNull(a, b);
	}
	
	/**
	 * Returns true if all of this box's objects are null.
	 * 
	 * @return True if both A and B are null
	 * @since 1.6.6
	 */
	public boolean allNull() {
		return EUtil.allNull(a, b);
	}
	
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
	public Box2<A, B> set(Box2<A, B> boxIn) {
		a = (boxIn != null) ? boxIn.a : null;
		b = (boxIn != null) ? boxIn.b : null;
		return this;
	}
	
	/** Sets this box's values to the given A and B values. */
	public Box2<A, B> set(A obj, B val) { a = obj; b = val; return this; }
	/** Sets this box's A value to the given object. */
	public Box2<A, B> setA(A obj) { a = obj; return this; }
	/** Sets this box's B value to the given object. */
	public Box2<A, B> setB(B obj) { b = obj; return this; }
	
	//----------------
	// Static Methods
	//----------------
	
	/** Returns true if both boxes have the same contents. NOTE: returns false if either box (or both) is null.*/
	public static boolean compare(Box2<?, ?> box1, Box2<?, ?> box2) {
		return (box1 != null && box2 != null) ? box1.compare(box2) : false;
	}
	
}
