package storageUtil;

import eutil.EUtil;

public class QuadBox<A, B, C, D> {

	public A a;
	public B b;
	public C c;
	public D d;
	
	public QuadBox() { this(null, null, null, null); }
	public QuadBox(A aIn, B bIn, C cIn, D dIn) { set(aIn, bIn, cIn, dIn); }
	public QuadBox(QuadBox<A, B, C, D> box) { EUtil.nullDo(box, b -> set(b.a, b.b, b.c, b.d)); }
	
	@Override
	public String toString() {
		return "[" + a + ", " + b + ", " + c + ", " + d + "]";
	}
	
	public boolean compare(QuadBox<A, B, C, D> box) {
		if (box != null) {
			return EUtil.isEqual(box.a, a) &&
				   EUtil.isEqual(box.b, b) &&
				   EUtil.isEqual(box.c, c) &&
				   EUtil.isEqual(box.d, d);
		}
		return false;
	}
	
	public QuadBox set(A aIn, B bIn, C cIn, D dIn) {
		a = aIn;
		b = bIn;
		c = cIn;
		d = dIn;
		return this;
	}
	
}
