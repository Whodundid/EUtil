package storageUtil;

import eutil.EUtil;

public class TrippleBox<A, B, C> {

	public A a;
	public B b;
	public C c;
	
	public TrippleBox() { this(null, null, null); }
	public TrippleBox(A aIn, B bIn, C cIn) { set(aIn, bIn, cIn); }
	public TrippleBox(TrippleBox<A, B, C> box) { EUtil.nullDo(box, b -> set(b.a, b.b, b.c)); }
	
	@Override
	public String toString() {
		return "[" + a + ", " + b + ", " + c + "]";
	}
	
	public boolean compare(TrippleBox<A, B, C> box) {
		if (box != null) {
			return EUtil.isEqual(box.a, a) && EUtil.isEqual(box.b, b) && EUtil.isEqual(box.c, c);
		}
		return false;
	}
	
	public TrippleBox set(A aIn, B bIn, C cIn) {
		a = aIn;
		b = bIn;
		c = cIn;
		return this;
	}
	
}
