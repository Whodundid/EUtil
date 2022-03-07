package eutil.lambda;

import eutil.EUtil;
import eutil.util.Experimental;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * A collection of various comparison logic structures.
 * 
 * @author Hunter Bragg
 * @since 1.1.1
 */
public final class Functions {
	
	private Functions() {}
	
	/** A general string length comparision. */
	public static final Comparator<String> strlen = Comparator.comparingInt(String::length);
	
	@Experimental
	public static final Function<List<?>, List<?>> reverse = (a) -> EUtil.reverse(a);
	
}
