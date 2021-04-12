package predicates;

import java.util.function.Predicate;

public class NotNull<T> implements Predicate {

	@Override public boolean test(Object t) { return t != null; }
	
}