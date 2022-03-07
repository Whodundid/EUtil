package eutil.assertions.exceptions;

public class NotEqualsAssertionException extends AssertionException {
	
	public NotEqualsAssertionException() { super("The given values were expected to not be equal but were!"); }
	public NotEqualsAssertionException(Object a, Object b) { super("The objects: '" + a + "' and '" + b + "' were expected to not be equal but were!"); }
	public NotEqualsAssertionException(String message) { super(message); }
	
}
