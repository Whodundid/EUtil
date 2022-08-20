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
	
}
