package eutil.assertions.exceptions;

public class FalseAssertionException extends AssertionException {
	
	public FalseAssertionException() { super("The given value was expected to be false but was true!"); }
	public FalseAssertionException(Object o) { super("The object: '" + o + "' was expected to be false but was true!"); }
	public FalseAssertionException(String message) { super(message); }
	
}