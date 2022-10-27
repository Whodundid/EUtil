package eutil.reflection;

/**
 * An enum representing the visibility modifier types of Java objects.
 * 
 * @author Hunter Bragg
 * 
 * @since 1.8.1
 */
public enum ObjectVisibility {
	
	PUBLIC,
	PROTECTED,
	PACKAGE_PRIVATE,
	PRIVATE;
	
	/**
	 * Returns the ObjectVisibility of the given object.
	 * <p>
	 * If the given object is null, null is returned instead.
	 * 
	 * @param objectIn The object to get visibility from
	 * 
	 * @return The ObjectVisibility of the given object
	 */
	public static ObjectVisibility of(Object objectIn) {
		if (objectIn == null) return null;
		
		EModifier mods = EModifier.of(objectIn);
		
		if (mods.isPublic()) return PUBLIC;
		if (mods.isProtected()) return PROTECTED;
		if (mods.isPrivate()) return PRIVATE;
		
		return PACKAGE_PRIVATE;
	}
	
}
