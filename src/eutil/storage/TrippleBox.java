package eutil.storage;

import static eutil.EUtil.*;

public class TrippleBox<A, B, C> {

	public A a;
	public B b;
	public C c;
	
	//--------------
	// Constructors
	//--------------
	
	public TrippleBox() { this(null, null, null); }
	public TrippleBox(A aIn, B bIn, C cIn) { set(aIn, bIn, cIn); }
	public TrippleBox(TrippleBox<A, B, C> box) { nullDo(box, b -> set(b.a, b.b, b.c)); }
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return "[" + a + ", " + b + ", " + c + "]"; }
	
	//---------
	// Methods
	//---------
	
	public TrippleBox<A, B, C> clear() { return set(null, null, null); }
	
	public boolean compare(TrippleBox<?, ?, ?> box) { return nullDoR(box, o -> compareInOrder(a, o.a, b, o.b, c, o.c), false); }
	public boolean compare(A aIn, B bIn, C cIn) { return compareInOrder(a, aIn, b, bIn, c, cIn); }
	
	public boolean compareA(A aIn) { return isEqual(a, aIn); }
	public boolean compareB(B bIn) { return isEqual(b, bIn); }
	public boolean compareC(C cIn) { return isEqual(c, cIn); }
	
	//---------
	// Getters
	//---------
	
	public A getA() { return a; }
	public B getB() { return b; }
	public C getC() { return c; }
	
	public Box<A, B> getAB() { return new Box<A, B>(a, b); }
	public Box<A, C> getAC() { return new Box<A, C>(a, c); }
	public Box<B, C> getBC() { return new Box<B, C>(b, c); }
	
	//---------
	// Setters
	//---------
	
	public TrippleBox<A, B, C> set(TrippleBox<A, B, C> boxIn) { return nullDoR(boxIn, b -> set(b.a, b.b, b.c), this); }
	public TrippleBox<A, B, C> set(A aIn, B bIn, C cIn) {
		a = aIn;
		b = bIn;
		c = cIn;
		return this;
	}
	
	public TrippleBox<A, B, C> setA(A aIn) { a = aIn; return this; }
	public TrippleBox<A, B, C> setB(B bIn) { b = bIn; return this; }
	public TrippleBox<A, B, C> setC(C cIn) { c = cIn; return this; }
	
}
