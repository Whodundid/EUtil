package eutil.lambda;

import java.io.File;
import java.util.List;
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
	
	/** A general condition that checks if a list has exactly one entry. */
	public static final Predicate<List> hasOne = (s) -> s.size() == 1;
	
	/** A general condition that checks if a list is empty. */
	public static final Predicate<List> isEmpty = (s) -> s.isEmpty();
	
	/** A general condition that checks if a list is not empty. */
	public static final Predicate<List> isNotEmpty = (s) -> !s.isEmpty();
	
	/** Checks that a given File is both not null and actually exists. */
	public static final Predicate<File> fileExists = (f) -> f != null && f.exists();
	
}
