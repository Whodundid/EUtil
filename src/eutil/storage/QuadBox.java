package eutil.storage;

import static eutil.EUtil.*;

public class QuadBox<A, B, C, D> {

	public A a;
	public B b;
	public C c;
	public D d;
	
	//--------------
	// Constructors
	//--------------
	
	public QuadBox() { this(null, null, null, null); }
	public QuadBox(A aIn, B bIn, C cIn, D dIn) { set(aIn, bIn, cIn, dIn); }
	public QuadBox(QuadBox<A, B, C, D> box) { nullDo(box, b -> set(b.a, b.b, b.c, b.d)); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "[" + a + ", " + b + ", " + c + ", " + d + "]"; }
	
	//---------
	// Methods
	//---------
	
	public boolean compare(QuadBox<?, ?, ?, ?> box) { return nullDoR(box, o -> compareInOrder(a, o.a, b, o.b, c, o.c, d, o.d), false); }
	public boolean compare(A aIn, B bIn, C cIn, D dIn) { return compareInOrder(a, aIn, b, bIn, c, cIn, d, dIn); }
	
	public boolean compareA(A aIn) { return isEqual(a, aIn); }
	public boolean compareB(B bIn) { return isEqual(b, bIn); }
	public boolean compareC(C cIn) { return isEqual(c, cIn); }
	public boolean compareD(D dIn) { return isEqual(d, dIn); }
	
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
	
	public QuadBox<A, B, C, D> set(QuadBox<A, B, C, D> boxIn) { return nullDoR(boxIn, o -> set(o.a, o.b, o.c, o.d), this); }
	public QuadBox<A, B, C, D> set(A aIn, B bIn, C cIn, D dIn) {
		a = aIn;
		b = bIn;
		c = cIn;
		d = dIn;
		return this;
	}
	
	public QuadBox<A, B, C, D> setA(A aIn) { a = aIn; return this; }
	public QuadBox<A, B, C, D> setB(B bIn) { b = bIn; return this; }
	public QuadBox<A, B, C, D> setC(C cIn) { c = cIn; return this; }
	public QuadBox<A, B, C, D> setD(D dIn) { d = dIn; return this; }
	
}
