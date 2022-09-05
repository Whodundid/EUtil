package eutil.assertions.exceptions;

public class TrueAssertionException extends AssertionException {
	
	public TrueAssertionException() { super("The given value was expected to be true but was false!"); }
	public TrueAssertionException(Object o) { super("The object: '" + o + "' was expected to be true but was false!"); }
	public TrueAssertionException(String message) { super(message); }
	
}