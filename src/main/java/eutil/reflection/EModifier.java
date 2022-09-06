package eutil.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import eutil.strings.EStringBuilder;

/**
 * A quicker way of dealing with Java Field Modifier properties.
 * 
 * @author Hunter Bragg
 * @since 1.2.0
 */
public class EModifier {
	
	private final int m;
	private final String toString;
	
	public EModifier(Field f) { this(f.getModifiers()); }
	public EModifier(int mods) {
		m = mods;
		
		//Realistically, the modifiers of a field shouldn't change,
		//nor should the 'toString' representation of them.
		var sb = new EStringBuilder();
		
		sb.a((isPublic()) ? "public " : "");
		sb.a((isPrivate()) ? "private " : "");
		sb.a((isProtected()) ? "protected " : "");
		sb.a((isStatic()) ? "static " : "");
		sb.a((isFinal()) ? "final " : "");
		sb.a((isSynchronized()) ? "synchronized " : "");
		sb.a((isVolatile()) ? "volatile " : "");
		sb.a((isTransient()) ? "transient " : "");
		sb.a((isNative()) ? "native " : "");
		sb.a((isInterface()) ? "interface " : "");
		sb.a((isAbstract()) ? "abstract " : "");
		sb.a((isStrict()) ? "strict " : "");
		
		toString = sb.trim();
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override public String toString() { return toString; }
	
	//---------
	// Methods
	//---------
	
	public boolean isPublic() { return Modifier.isPublic(m); }
	public boolean isPrivate() { return Modifier.isPrivate(m); }
	public boolean isProtected() { return Modifier.isProtected(m); }
	public boolean isStatic() { return Modifier.isStatic(m); }
	public boolean isFinal() { return Modifier.isFinal(m); }
	public boolean isSynchronized() { return Modifier.isSynchronized(m); }
	public boolean isVolatile() { return Modifier.isVolatile(m); }
	public boolean isTransient() { return Modifier.isTransient(m); }
	public boolean isNative() { return Modifier.isNative(m); }
	public boolean isInterface() { return Modifier.isInterface(m); }
	public boolean isAbstract() { return Modifier.isAbstract(m); }
	public boolean isStrict() { return Modifier.isStrict(m); }
	
	public boolean publicFinal() { return isPublic() && isFinal(); }
	public boolean publicStatic() { return isPublic() && isStatic(); }
	
	//----------------
	// Static Methods
	//----------------
	
	public static EModifier of(Field f) { return new EModifier(f); }
	public static EModifier of(int mods) { return new EModifier(mods); }
	
	//-------------------------------
	// Static Helper Methods - 1.6.0
	//-------------------------------
	
	public static boolean isPublic(Field f) { return (f.getModifiers() & Modifier.PUBLIC) != 0; }
	public static boolean isPrivate(Field f) { return (f.getModifiers() & Modifier.PRIVATE) != 0; }
	public static boolean isProtected(Field f) { return (f.getModifiers() & Modifier.PROTECTED) != 0; }
	public static boolean isStatic(Field f) { return (f.getModifiers() & Modifier.STATIC) != 0; }
	public static boolean isFinal(Field f) { return (f.getModifiers() & Modifier.FINAL) != 0; }
	public static boolean isSynchronized(Field f) { return (f.getModifiers() & Modifier.SYNCHRONIZED) != 0; }
	public static boolean isVolatile(Field f) { return (f.getModifiers() & Modifier.VOLATILE) != 0; }
	public static boolean isTransient(Field f) { return (f.getModifiers() & Modifier.TRANSIENT) != 0; }
	public static boolean isNative(Field f) { return (f.getModifiers() & Modifier.NATIVE) != 0; }
	public static boolean isInterface(Field f) { return (f.getModifiers() & Modifier.INTERFACE) != 0; }
	public static boolean isAbstract(Field f) { return (f.getModifiers() & Modifier.ABSTRACT) != 0; }
	public static boolean isStrict(Field f) { return (f.getModifiers() & Modifier.STRICT) != 0; }
	
	public static boolean publicFinal(Field f) { return isPublic(f) && isFinal(f); }
	public static boolean publicStatic(Field f) { return isPublic(f) && isStatic(f); }
	
}
