package eutil.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import eutil.EUtil;

/**
 * A collection of useful functions which help streamline various Java reflection operations.
 * 
 * @author Hunter Bragg
 * @since 1.0.1
 */
public final class EReflectionUtil {
	
	//----------------------------
	
	// prevent instantiation
	private EReflectionUtil() {}
	
	//----------------------------
	
	//---------
	// Methods
	//---------
	
	public static <E> E invoke(Object obj, String methodName, Object... paramsAndArgs) {
		try { return invoke_unsafe(obj, methodName, paramsAndArgs); }
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public static <E> E invoke(Object obj, String methodName, Class[] params, Object[] args) {
		try { return invoke_unsafe(obj, methodName, params, args); }
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public static <E> E invoke_unsafe(Object obj, String methodName, Object... paramsAndArgs) throws Exception {
		if (paramsAndArgs.length % 2 == 0) {
			Class[] params = new Class[paramsAndArgs.length / 2];
			Object[] args = new Object[params.length];
			
			for (int i = 0; i < paramsAndArgs.length; i++) {
				params[i] = (Class) paramsAndArgs[i];
				args[i] = paramsAndArgs[i + 1];
			}
			
			return (E) invoke_unsafe(obj, methodName, params, args);
		}
		
		return null;
	}
	
	public static <E> E invoke_unsafe(Object obj, String methodName, Class[] params, Object[] args) throws Exception {
		if (obj == null) return null;
		Method m = findMethod(obj.getClass(), methodName, params);
		// check if we have access up front -- static methods shouldn't pass an object
		boolean couldAccessBefore = m.canAccess(EModifier.isStatic(m) ? null : obj);
		// hack in for just a second
		if (!couldAccessBefore) m.setAccessible(true);
		var o = m.invoke(obj, args);
		if (!couldAccessBefore) m.setAccessible(false);
		return (E) o;
	}
	
	//--------
	// Fields
	//--------
	
	/** Attempts to set the value of a field of the given name from the given object's class hierarchy. */
	public static void setField(Object obj, String fieldName, Object value) throws Exception {
		if (obj == null) return;
		Field f = findField(obj.getClass(), fieldName);
		f.setAccessible(true);
		f.set(obj, value);
		f.setAccessible(false);
	}
	
	/** Attempts to return a value of a field of the given name from the given object's class hierarchy. */
	public static <E> E getValue(Object obj, String fieldName, Class<E> asClass) throws Exception {
		if (obj == null) return null;
		Field f = findField(obj.getClass(), fieldName);
		f.setAccessible(true);
		Object o = f.get(obj);
		f.setAccessible(false);
		return asClass.cast(o);
	}
	
	/** Attempts to return a corresponding constructor from the given name and class parameters from the given object's class hierarchy. */
	public static Constructor<?> getConstructor(Object obj, Class... parameters) throws Exception {
		if (obj == null) return null;
		return findConstructor(obj.getClass(), parameters);
	}
	
	/** Attempts to set the value of a field of the given name from the object's immediate fields. */
	public static void setDeclaredField(Object obj, String fieldName, Object value) throws Exception {
		if (obj == null) return;
		Field f = obj.getClass().getDeclaredField(fieldName);
		f.setAccessible(true);
		f.set(obj, value);
		f.setAccessible(false);
	}
	
	/** Attempts to return the value of a field of the given name from the object's immediate fields. 
	 *  If no matching field was found, null is returned instead. */
	public static <E> E getDeclaredValue(Object obj, String fieldName, Class<E> asClass) throws Exception {
		if (obj == null) return null;
		Field f = obj.getClass().getDeclaredField(fieldName);
		f.setAccessible(true);
		Object o = f.get(obj);
		f.setAccessible(false);
		return asClass.cast(o);
	}
	
	/** Attempts to return a corresponding constructor from the given name and class parameters from the given object's immediate constructors. */
	public static Constructor<?> getDeclaredConstructor(Object obj, Class... parameters) throws Exception {
		if (obj == null) return null;
		return obj.getClass().getDeclaredConstructor(parameters);
	}
	
    /**
     * Finds and returns a method under the given name with the given
     * parameters on the given class object.
     * 
     * @param obj        The object to find the method on
     * @param methodName The name of the method to find
     * @param parameters The parameters of the method to find
     * 
     * @return A method matching the given search criteria on the given
     *         object
     * 
     * @throws Exception Thrown if an error occurs while attempting to
     *                   find the method
     */
	public static Method getDeclaredMethod(Object obj, String methodName, Class<?>... parameters) throws Exception {
	    if (obj == null) return null;
	    return obj.getClass().getDeclaredMethod(methodName, parameters);
	}
	
	/** Returns true if the given object actually has a field of the given name in its class hierarchy. */
	public static boolean hasField(Object obj, String fieldName) {
		if (obj == null) return false;
		try {
			Field f = findField(obj.getClass(), fieldName);
			return f != null;
		}
		catch (NoSuchFieldException e) {}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/** Returns true if the given object actually has a constructor of the given name with the given parameters in its class hierarchy. */
	public static boolean hasConstructor(Object obj, Class... parameters) {
		if (obj == null) return false;
		try {
			Constructor<?> c = findConstructor(obj.getClass(), parameters);
			return c != null;
		}
		catch (NullPointerException | NoSuchMethodException e) {}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean assignableFrom(Field f, Class<?> c) { return assignableFrom(f, null, c); }
	public static boolean assignableFrom(Field f, Object obj, Class<?> c) {
		Object o = forceGet(f, obj);
		return c != null && o != null && c.isAssignableFrom(o.getClass());
	}
	
	public static boolean isNull(Field f) { return isNull(f, null); }
	public static boolean isNull(Field f, Object obj) { return forceGet(f, obj) == null; }
	
	public static boolean notNull(Field f) { return notNull(f, null); }
	public static boolean notNull(Field f, Object obj) { return forceGet(f, obj) != null; }
	
	public static Object forceGet(Field f) { return forceGet(f, null); }
	public static Object forceGet(Field f, Object obj) {
		try {
			f.setAccessible(true);
			Object value = f.get(obj);
			f.setAccessible(false);
			return value;
		}
		catch (Exception e) {
			throw new RuntimeException("Cannot return field value for '" + f + "'!", e);
		}
	}
	
	//-------------------
	// Protected Methods
	//-------------------
	
	/** Attempts to find a field of the given name by recursively checking the declared fields of each super class of its object hierarchy. */
	protected static Field findField(Class<?> c, String fieldName) throws Exception {
		Field f = null;
		
		while (c != null && f == null) {
			try {
				f = c.getDeclaredField(fieldName);
			}
			//Ignore this exception as this could be inherently true for multiple class layers
			catch (NoSuchFieldException e) {}
			//In the event of a much more serious error, propagate the error upwards
			catch (Exception e) { throw e; }
			c = c.getSuperclass();
		}
		
		//If the field does not actually exist anywhere, throw a NoSuchFieldException
		if (f == null) throw new NoSuchFieldException(fieldName);
		
		//Otherwise return the field
		return f;
	}
	
	/** Attempts to find a field of the given name by recursively checking the declared fields of each super class of its object hierarchy. */
	protected static Method findMethod(Class<?> c, String methodName, Class... params) throws Exception {
		Method m = null;
		
		while (c != null && m == null) {
			try {
				m = c.getDeclaredMethod(methodName, params);
			}
			//Ignore this exception as this could be inherently true for multiple class layers
			catch (NoSuchMethodException e) {}
			//In the event of a much more serious error, propagate the error upwards
			catch (Exception e) { throw e; }
			c = c.getSuperclass();
		}
		
		//If the field does not actually exist anywhere, throw a NoSuchFieldException
		if (m == null) throw new NoSuchMethodException(methodName);
		
		//Otherwise return the field
		return m;
	}
	
	
    protected static Constructor<?> findConstructor(Class<?> c, Class... parameters) throws Exception {
        if (c == null) throw new NullPointerException("The original class is null!");
        Class<?> original = c;
        Constructor<?> f = null;
        
        while (c != null && f == null) {
            try {
                f = c.getDeclaredConstructor(parameters);
            }
            catch (NoSuchMethodException e) {}
            catch (Exception e) {
                throw e;
            }
            c = c.getSuperclass();
        }
        
        if (f == null) {
            throw new NullPointerException("The class '" + original + "' does " +
                "not have a constructor with the given parameters: [" +
                EUtil.asList(parameters) + "]!");
        }
        
        return f;
    }
	
}
