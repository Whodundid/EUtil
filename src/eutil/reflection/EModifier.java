package eutil.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * A quicker way of dealing with Java Field Modifier properties.
 * 
 * @author Hunter Bragg
 * @since 1.2.0
 */
public class EModifier {
	
	private final int m;
	
	public EModifier(Field f) { this(f.getModifiers()); }
	public EModifier(int mods) { m = mods; }
	
	@Override
	public String toString() {
		String r = "";
		
		r += (isPublic()) ? "public " : "";
		r += (isPrivate()) ? "private " : "";
		r += (isProtected()) ? "protected " : "";
		r += (isStatic()) ? "static " : "";
		r += (isFinal()) ? "final " : "";
		r += (isSynchronized()) ? "synchronized " : "";
		r += (isVolatile()) ? "volatile " : "";
		r += (isTransient()) ? "transient " : "";
		r += (isNative()) ? "native " : "";
		r += (isInterface()) ? "interface " : "";
		r += (isAbstract()) ? "abstract " : "";
		r += (isStrict()) ? "strict " : "";
		
		return r.trim();
	}
	
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
	
	public static boolean publicFinal(Field f) { return new EModifier(f).publicFinal(); }
	public static boolean publicStatic(Field f) { return new EModifier(f).publicStatic(); }
	
	public static EModifier of(Field f) { return new EModifier(f); }
	public static EModifier of(int mods) { return new EModifier(mods); }
	
}
