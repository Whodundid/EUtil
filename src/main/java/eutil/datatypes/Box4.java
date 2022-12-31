package eutil.datatypes;

import static eutil.EUtil.*;

import eutil.EUtil;

/**
 * A type of box which contains four distinct types.
 * 
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 * 
 * @author Hunter Bragg
 * @since 1.0.1
 */
public class Box4<A, B, C, D> {

	public A a;
	public B b;
	public C c;
	public D d;
	
	//--------------
	// Constructors
	//--------------
	
	public Box4() { this(null, null, null, null); }
	public Box4(A aIn, B bIn, C cIn, D dIn) { set(aIn, bIn, cIn, dIn); }
	public Box4(Box4<A, B, C, D> box) { nullDo(box, b -> set(b.a, b.b, b.c, b.d)); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public String toString() {
		return "[" + a + ", " + b + ", " + c + ", " + d + "]";
	}
	
	//---------
	// Methods
	//---------
	
	public boolean contains(Object obj) {
		if (obj == null) return a == null || b == null || c == null || d == null;
		return isAnyEqual(obj, a, b, c, d);
	}
	
	public boolean containsA(A obj) { return isEqual(a, obj); }
	public boolean containsB(B obj) { return isEqual(b, obj); }
	public boolean containsC(C obj) { return isEqual(c, obj); }
	public boolean containsD(D obj) { return isEqual(d, obj); }
	
	public boolean compare(Box4<?, ?, ?, ?> box) { return nullDoR(box, o -> compareInOrder(a, o.a, b, o.b, c, o.c, d, o.d), false); }
	public boolean compare(A aIn, B bIn, C cIn, D dIn) { return compareInOrder(a, aIn, b, bIn, c, cIn, d, dIn); }
	
	public boolean compareA(A aIn) { return isEqual(a, aIn); }
	public boolean compareB(B bIn) { return isEqual(b, bIn); }
	public boolean compareC(C cIn) { return isEqual(c, cIn); }
	public boolean compareD(D dIn) { return isEqual(d, dIn); }
	
	/**
	 * Returns true if any of this box's objects are null.
	 * 
	 * @return True if either A, B, C or D are null
	 * @since 1.6.6
	 */
	public boolean anyNull() {
		return EUtil.anyNull(a, b, c, d);
	}
	
	/**
	 * Returns true if none of this box's objects are null.
	 * 
	 * @return True if A, B, C and D are not null
	 * @since 1.6.6
	 */
	public boolean notNull() {
		return EUtil.notNull(a, b, c, d);
	}
	
	/**
	 * Returns true if all of this box's objects are null.
	 * 
	 * @return True if A, B, C and D are null
	 * @since 1.6.6
	 */
	public boolean allNull() {
		return EUtil.allNull(a, b, c, d);
	}
	
	//---------
	// Getters
	//---------
	
	public A getA() { return a; }
	public B getB() { return b; }
	public C getC() { return c; }
	public D getD() { return d; }
	
	//---------
	// Setters
	//---------
	
	public Box4<A, B, C, D> set(Box4<A, B, C, D> boxIn) { return nullDoR(boxIn, o -> set(o.a, o.b, o.c, o.d), this); }
	public Box4<A, B, C, D> set(A aIn, B bIn, C cIn, D dIn) {
		a = aIn;
		b = bIn;
		c = cIn;
		d = dIn;
		return this;
	}
	
	public Box4<A, B, C, D> setA(A aIn) { a = aIn; return this; }
	public Box4<A, B, C, D> setB(B bIn) { b = bIn; return this; }
	public Box4<A, B, C, D> setC(C cIn) { c = cIn; return this; }
	public Box4<A, B, C, D> setD(D dIn) { d = dIn; return this; }
	
}
