package eutil.misc;

import eutil.debug.Experimental;

@Experimental
public interface IEnumHelper<E extends Enum<E>> {
	
	/** Returns true if the given object is not equal to this enum value. */
	default boolean notEquals(Object o) {
		return !this.equals(o);
	}
	
}
