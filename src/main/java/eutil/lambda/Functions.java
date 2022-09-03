package eutil.lambda;

import java.util.List;
import java.util.function.Function;

import eutil.EUtil;
import eutil.debug.Experimental;

/**
 * A collection of lambda functions.
 * 
 * @author Hunter Bragg
 * @since 1.1.1
 */
public final class Functions {
	
	private Functions() {}
	
	@Experimental
	public static final Function<List<?>, List<?>> reverse = (a) -> EUtil.reverseList(a);
	
	/**
	 * Returns the 'toString' value of the given input, even if null.
	 */
	public static final Function<Object, String> toString = (o) -> String.valueOf(o);
	
	/**
	 * Returns the length of the object's 'toString' value, even if null.
	 * @since 1.5.2
	 */
	public static final Function<Object, Integer> toStringLen = (o) -> String.valueOf(o).length();
}
