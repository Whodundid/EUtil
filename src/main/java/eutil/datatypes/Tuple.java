package eutil.datatypes;

/** Immutable array of values. */
public class Tuple {
	
	private final Object[] values;
	
	//--------------
	// Constructors
	//--------------
	
	public Tuple(Object... valuesIn) {
		values = valuesIn;
	}
	
	//---------
	// Methods
	//---------
	
	public int length() { return values.length; }
	
	public Tuple from(int start) { return from(this, start, values.length - 1); }
	public Tuple from(int start, int end) { return from(this, start, end); }
	
	//---------
	// Getters
	//---------
	
	public Object get(int i) { return values[i]; }
	
	//----------------
	// Static Methods
	//----------------
	
	public static Tuple from(Tuple in, int start) { return from(in, start, in.length() - 1); }
	public static Tuple from(Tuple in, int start, int end) {
		if (in == null) return null;
		if (end - start < 0) throw new IllegalArgumentException("Invalid index range!");
		if (end >= in.length()) throw new IndexOutOfBoundsException(end + " : (" + in.length() + ")");
		
		Object[] arr = new Object[end - start];
		for (int i = start; i < end; i++) {
			arr[i] = in.get(i);
		}
		
		return new Tuple(arr);
	}
	
}
