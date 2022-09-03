package eutil.assertions.exceptions;

public class EqualsAssertionException extends AssertionException {
	
	public EqualsAssertionException() { super("The given values were expected to be equal but were not!"); }
	public EqualsAssertionException(Object a, Object b) { super("The objects: '" + a + "' and '" + b + "' were expected to be equal but were not!"); }
	public EqualsAssertionException(String message) { super(message); }
	
}