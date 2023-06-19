package eutil.reflection;

import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import eutil.strings.EStringBuilder;

/**
 * A quicker way of dealing with Java Field Modifier properties.
 * 
 * @author Hunter Bragg
 * @since 1.2.0
 */
public class EModifier {
	
	//========
	// Fields
	//========
	
	private final int m;
	private final String toString;
	
	//==============
	// Constructors
	//==============
	
	public EModifier(Member m) { this(m.getModifiers()); }
	public EModifier(Class<?> c) { this(c.getModifiers()); }
	public EModifier(int mods) {
		m = mods;
		
		//Realistically, the modifiers of a field shouldn't change,
		//nor should the 'toString' representation of them.
		var sb = new EStringBuilder();
		
		sb.println((isPublic()) ? "public " : "");
		sb.println((isPrivate()) ? "private " : "");
		sb.println((isProtected()) ? "protected " : "");
		sb.println((isStatic()) ? "static " : "");
		sb.println((isFinal()) ? "final " : "");
		sb.println((isSynchronized()) ? "synchronized " : "");
		sb.println((isVolatile()) ? "volatile " : "");
		sb.println((isTransient()) ? "transient " : "");
		sb.println((isNative()) ? "native " : "");
		sb.println((isInterface()) ? "interface " : "");
		sb.println((isAbstract()) ? "abstract " : "");
		sb.println((isStrict()) ? "strict " : "");
		
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
	
	public static EModifier of(Object o) {
		if (o == null) return null;
		if (o instanceof Member m) return of(m);
		if (o instanceof Class<?> c) return of(c.getModifiers());
		if (o instanceof Integer i) return of(i.intValue());
		return of(o.getClass().getModifiers());
	}
	
	public static EModifier of(Member m) { return new EModifier(m); }
	public static EModifier of(Class<?> c) { return new EModifier(c); }
	public static EModifier of(Executable m) { return new EModifier(m); }
	public static EModifier of(int mods) { return new EModifier(mods); }
	
	//-------------------------------
	// Static Helper Methods - 1.6.0
	//-------------------------------
	
	public static boolean isPublic(Member f) { return Modifier.isPublic(f.getModifiers()); }
	public static boolean isPublic(Class<?> c) { return Modifier.isPublic(c.getModifiers()); }
	
	public static boolean isPrivate(Member f) { return Modifier.isPrivate(f.getModifiers()); }
	public static boolean isPrivate(Class<?> c) { return Modifier.isPrivate(c.getModifiers()); }
	
	public static boolean isProtected(Member f) { return Modifier.isProtected(f.getModifiers()); }
	public static boolean isProtected(Class<?> c) { return Modifier.isProtected(c.getModifiers()); }
	
	public static boolean isStatic(Member f) { return Modifier.isStatic(f.getModifiers()); }
	public static boolean isStatic(Class<?> c) { return Modifier.isStatic(c.getModifiers()); }
	
	public static boolean isFinal(Member f) { return Modifier.isFinal(f.getModifiers()); }
	public static boolean isFinal(Class<?> c) { return Modifier.isFinal(c.getModifiers()); }
	
	public static boolean isSynchronized(Member f) { return Modifier.isSynchronized(f.getModifiers()); }
	public static boolean isSynchronized(Class<?> c) { return Modifier.isSynchronized(c.getModifiers()); }
	
	public static boolean isVolatile(Member f) { return Modifier.isVolatile(f.getModifiers()); }
	public static boolean isVolatile(Class<?> c) { return Modifier.isVolatile(c.getModifiers()); }
	
	public static boolean isTransient(Member f) { return Modifier.isTransient(f.getModifiers()); }
	public static boolean isTransient(Class<?> c) { return Modifier.isTransient(c.getModifiers()); }
	
	public static boolean isNative(Member f) { return Modifier.isNative(f.getModifiers()); }
	public static boolean isNative(Class<?> c) { return Modifier.isNative(c.getModifiers()); }
	
	public static boolean isInterface(Member f){ return Modifier.isInterface(f.getModifiers()); }
	public static boolean isInterface(Class<?> c) { return Modifier.isInterface(c.getModifiers()); }
	
	public static boolean isAbstract(Member f) { return Modifier.isAbstract(f.getModifiers()); }
	public static boolean isAbstract(Class<?> c) { return Modifier.isAbstract(c.getModifiers()); }
	
	public static boolean isStrict(Member f) { return Modifier.isStrict(f.getModifiers()); }
	public static boolean isStrict(Class<?> c) { return Modifier.isStrict(c.getModifiers()); }
	
	public static boolean publicFinal(Member f) { return isPublic(f) && isFinal(f); }
	public static boolean publicFinal(Class<?> c) { return isPublic(c) && isFinal(c); }
	
	public static boolean publicStatic(Member f) { return isPublic(f) && isStatic(f); }
	public static boolean publicStatic(Class<?> c) { return isPublic(c) && isStatic(c); }
	
}
