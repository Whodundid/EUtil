package eutil.datatypes.boxes;

import static eutil.EUtil.*;

import eutil.EUtil;

/**
 * A type of box which contains three distinct types.
 * 
 * @param <A>
 * @param <B>
 * @param <C>
 * 
 * @author Hunter Bragg
 * @since 1.0.1
 */
public class Box3<A, B, C> {

	public A a;
	public B b;
	public C c;
	
	//--------------
	// Constructors
	//--------------
	
	public Box3() { this(null, null, null); }
	public Box3(A aIn, B bIn, C cIn) { set(aIn, bIn, cIn); }
	public Box3(Box3<A, B, C> box) { nullDo(box, b -> set(b.a, b.b, b.c)); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "[" + a + ", " + b + ", " + c + "]"; }
	
	//---------
	// Methods
	//---------
	
	public Box3<A, B, C> clear() { return set(null, null, null); }
	
	public boolean contains(Object obj) {
		if (obj == null) return a == null || b == null || c == null;
		return isAnyEqual(obj, a, b, c);
	}
	
	public boolean containsA(A obj) { return isEqual(a, obj); }
	public boolean containsB(B obj) { return isEqual(b, obj); }
	public boolean containsC(C obj) { return isEqual(c, obj); }
	
	public boolean compare(Box3<?, ?, ?> box) { return nullDoR(box, o -> compareInOrder(a, o.a, b, o.b, c, o.c), false); }
	public boolean compare(A aIn, B bIn, C cIn) { return compareInOrder(a, aIn, b, bIn, c, cIn); }
	
	public boolean compareA(A aIn) { return isEqual(a, aIn); }
	public boolean compareB(B bIn) { return isEqual(b, bIn); }
	public boolean compareC(C cIn) { return isEqual(c, cIn); }
	
	/**
	 * Returns true if any of this box's objects are null.
	 * 
	 * @return True if either A, B or C are null
	 * @since 1.6.6
	 */
	public boolean anyNull() {
		return EUtil.anyNull(a, b, c);
	}
	
	/**
	 * Returns true if none of this box's objects are null.
	 * 
	 * @return True if A, B and C are not null
	 * @since 1.6.6
	 */
	public boolean notNull() {
		return EUtil.notNull(a, b, c);
	}
	
	/**
	 * Returns true if all of this box's objects are null.
	 * 
	 * @return True if A, B and C are null
	 * @since 1.6.6
	 */
	public boolean allNull() {
		return EUtil.allNull(a, b, c);
	}
	
	//---------
	// Getters
	//---------
	
	public A getA() { return a; }
	public B getB() { return b; }
	public C getC() { return c; }
	
	public Box2<A, B> getAB() { return new Box2<A, B>(a, b); }
	public Box2<A, C> getAC() { return new Box2<A, C>(a, c); }
	public Box2<B, C> getBC() { return new Box2<B, C>(b, c); }
	
	//---------
	// Setters
	//---------
	
	public Box3<A, B, C> set(Box3<A, B, C> boxIn) { return nullDoR(boxIn, b -> set(b.a, b.b, b.c), this); }
	public Box3<A, B, C> set(A aIn, B bIn, C cIn) {
		a = aIn;
		b = bIn;
		c = cIn;
		return this;
	}
	
	public Box3<A, B, C> setA(A aIn) { a = aIn; return this; }
	public Box3<A, B, C> setB(B bIn) { b = bIn; return this; }
	public Box3<A, B, C> setC(C cIn) { c = cIn; return this; }
	
}
