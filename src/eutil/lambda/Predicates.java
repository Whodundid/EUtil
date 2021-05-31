package eutil.lambda;

import java.io.File;
import java.util.function.Predicate;

/**
 * A collection of various reduction logic structures.
 * 
 * @author Hunter Bragg
 * @since 1.1.1
 */
public final class Predicates {
	
	//----------------------
	
	private Predicates() {}
	
	//----------------------
	
	/** A general condition that reduces to only null values. */
	public static final Predicate<Object> isNull = (o) -> o == null;
	
	/** A general condition that reduces to only non-null values. */
	public static final Predicate<Object> notNull = (o) -> o != null;
	
	/** Checks that a given File is both not null and actually exists. */
	public static final Predicate<File> fileExists = (f) -> f != null && f.exists();
	
}
