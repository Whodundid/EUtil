package eutil.predicates;

import java.util.function.Predicate;

public class IsNull<T> implements Predicate {

	@Override public boolean test(Object t) { return t == null; }
	
}
