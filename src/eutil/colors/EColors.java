package eutil.colors;

import eutil.math.Vec3f;
import eutil.math.Vec3i;
import eutil.math.Vec4f;
import eutil.math.Vec4i;
import eutil.random.RandomUtil;

import java.awt.Color;

//Author: Hunter Bragg

/**
 * A palette of useful common colors.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public enum EColors {
	
	//Envision colors
	lred(0xffff5555, "Light Red"),
	red(0xffff0000, "Red"),
	maroon(0xff9e0012,"Maroon"),
	brown(0xff7F3000, "Brown"),
	dorange(0xffB24400, "Dark Orange"),
	borange(0xffff6600, "Bright Orange"),
	orange(0xffff9900, "Orange"),
	lorange(0xffffaa00, "Light Orange"),
	yellow(0xffffff00, "Yellow"),
	lime(0xffaaff00, "Lime"),
	green(0xff00ff00, "Light Green"),
	seafoam(0xff00ff8c, "Seafoam"),
	lgreen(0xff55ff55, "Green"),
	dgreen(0xff00af00, "Dark Green"),
	turquoise(0xff00c1ae, "Turquoise"),
	aquamarine(0xff00ffdc, "Aquamarine"),
	cyan(0xff00ffff, "Cyan"),
	skyblue(0xff00baff, "Sky Blue"),
	blue(0xff0065ff, "Blue"),
	regal(0xff004EC4, "Regal Blue"),
	navy(0xff0000ff, "Navy"),
	grape(0xff4200ff, "Grape"),
	violet(0xff430093, "Violet"),
	eggplant(0xff772789, "Eggplant"),
	purple(0xffdd49ff, "Purple"),
	pink(0xfff872e9, "Pink"),
	hotpink(0xffff00dc, "Hot Pink"),
	magenta(0xffff0061, "Magenta"),
	white(0xffffffff, "White"),
	chalk(0xffdcdcdc, "Chalk"),
	lgray(0xffb2b2b2, "Light Gray"),
	gray(0xff8d8d8d, "Gray"),
	mgray(0xff636363, "Medium Gray"),
	dgray(0xff474747, "Dark Gray"),
	pdgray(0xff303030, "Pretty Dark Gray"),
	steel(0xff1f1f1f, "Steel"),
	dsteel(0xff191919, "Dark Steel"),
	vdgray(0xff111111, "Very Dark Gray"),
	black(0xff000000, "Black"),
	
	//MC colors
	mc_darkred(0xffaa0000, "MC Dark Red"),
	mc_red(0xffff5555, "MC Red"),
	mc_gold(0xffffaa00, "MC Gold"),
	mc_yellow(0xffffff55, "MC Yellow"),
	mc_darkgreen(0xff00aa00, "MC Dark Green"),
	mc_green(0xff55ff55, "MC Green"),
	mc_aqua(0xff55ffff, "MC Aqua"),
	mc_darkaqua(0xff00aaaa, "MC Dark Aqua"),
	mc_darkblue(0xff0000aa, "MC Dark Blue"),
	mc_blue(0xff5555ff, "MC Blue"),
	mc_lightpurple(0xffff55ff, "MC Light Purple"),
	mc_darkpurple(0xffaa00aa, "MC Dark Purple"),
	mc_white(0xffffffff, "MC White"),
	mc_gray(0xffaaaaaa, "MC Gray"),
	mc_darkgray(0xff555555, "MC Dark Gray"),
	mc_black(0xff000000, "MC Black");
	
	//------------------------------------------------------------------
	
	/** The integer color value. */
	public final int intVal;
	/** The internal color code for this EColor's color. This should not be referenced directly as it is dynamically generated! */
	private final int color_replacement_code;
	/** The String that corresponds to this EColor's internal color code value. */
	public final String color_replacement_code_string;
	/** The name of this color. */
	public final String name;
	
	private EColors(final int colorIn, final String nameIn) {
		intVal = colorIn;
		color_replacement_code = ColorID.next();
		color_replacement_code_string = ColorID.pad(color_replacement_code);
		name = nameIn;
	}
	
	//------------------------------------------------------------------
	
	@Override public String toString() { return "\u222e" + color_replacement_code_string; }

	//------------------------------------------------------------------
	
	/** Returns the color's integer value. */
	public int c() { return intVal; }
	
	/** Returns the color's name. */
	public String n() { return name; }
	
	/** Returns the EColor's integer color with modified opacity. */
	public int opacity(int val) {
		return (intVal & 0x00ffffff) | val << 24;
	}
	
	/** Returns the integer color value of this EColor broken up into it's [A, R, G, B] parts. */
	public int[] argb() {
		int a = (intVal >> 24) & 0xff;
		int r = (int) ((intVal >> 16) & 0xff);
		int g = (int) ((intVal >> 8) & 0xff);
		int b = (int) (intVal & 0xff);
		return new int[] { a, r, g, b };
	}
	
	/** Returns the integer color value of this EColor broken up into [A, R, G, B] float (each / 255) parts. */
	public float[] argbf() {
		float a = ((intVal >> 24) & 0xff) / 0xff;
		float r = (int) ((intVal >> 16) & 0xff) / 0xff;
		float g = (int) ((intVal >> 8) & 0xff) / 0xff;
		float b = (int) (intVal & 0xff) / 0xff;
		return new float[] { a, r, g, b };
	}
	
	/** Converts this EColor's integer parts to a Vec3i. (does not include alpha values) */
	public Vec3i toVec3i() { int[] c = argb(); return new Vec3i(c[1], c[2], c[3]); }
	
	/** Converts this EColor's integer parts to a Vec3i. */
	public Vec4i toVec4i() { int[] c = argb(); return new Vec4i(c[0], c[1], c[2], c[3]); }
	
	/** Converts this EColor's integer parts to a Vec3f. (does not include alpha values) */
	public Vec3f toVec3f() { float[] c = argbf(); return new Vec3f(c[1], c[2], c[3]); }
	
	/** Converts this EColor's integer parts to a Vec4f. */
	public Vec4f toVec4f() { float[] c = argbf(); return new Vec4f(c[0], c[1], c[2], c[3]); }
	
	//------------------------------------------------------------------
	
	/** Replaces the given color's alpha values. New opacity values should be between 0 and 255 inclusively. */
	public static int changeOpacity(int color, int newValue) {
		return (color & 0x00ffffff) | newValue << 24;
	}
	
	/** Modifies the given color's brightness values. New brightness values should be between 0 and 255 inclusively. */
	public static int changeBrightness(int color, int br) {
		float factor = (float) br / 255f;
		int a = (color >> 24) & 0xff;
		int r = (int) (((color >> 16) & 0xFF) * factor);
		int g = (int) (((color >> 8) & 0xFF) * factor);
		int b = (int) ((color & 0xFF) * factor);
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
	
	public static Vec3i convertToVec3i(EColors color) { int[] c = color.argb(); return new Vec3i(c[1], c[2], c[3]); }
	public static Vec4i convertToVec4i(EColors color) { int[] c = color.argb(); return new Vec4i(c[0], c[1], c[2], c[3]); }
	public static Vec3f convertToVec3f(EColors color) { float[] c = color.argbf(); return new Vec3f(c[1], c[2], c[3]); }
	public static Vec4f convertToVec4f(EColors color) { float[] c = color.argbf(); return new Vec4f(c[0], c[1], c[2], c[3]); }
	
	public static Vec3i convertToVec3i(int color) {
		int r = (int) ((color >> 16) & 0xff);
		int g = (int) ((color >> 8) & 0xff);
		int b = (int) (color & 0xff);
		return new Vec3i(r, g, b);
	}
	
	public static Vec4i convertToVec4i(int color) {
		int a = (int) ((color >> 24) & 0xff);
		int r = (int) ((color >> 16) & 0xff);
		int g = (int) ((color >> 8) & 0xff);
		int b = (int) (color & 0xff);
		return new Vec4i(a, r, g, b);
	}
	
	public static Vec3f convertToVec3f(int color) {
		float r = (int) ((color >> 16) & 0xff) / 0xff;
		float g = (int) ((color >> 8) & 0xff) / 0xff;
		float b = (int) (color & 0xff) / 0xff;
		return new Vec3f(r, g, b);
	}
	
	public static Vec4f convertToVec4f(int color) {
		float a = ((color >> 24) & 0xff) / 0xff;
		float r = (int) ((color >> 16) & 0xff) / 0xff;
		float g = (int) ((color >> 8) & 0xff) / 0xff;
		float b = (int) (color & 0xff) / 0xff;
		return new Vec4f(a, r, g, b);
	}
	
	/** Returns an EColors with the corresponding integer color (if any). */
	public static EColors byIntVal(int colorIn) {
		for (EColors c : values()) {
			if (c.intVal == colorIn) { return c; }
		}
		return null;
	}
	
	public static EColors getEColor(String nameIn) {
		for (EColors c : values()) {
			if (c.getClass().getSimpleName().equalsIgnoreCase(nameIn)) { return c; }
		}
		return null;
	}
	
	/** Returns an EColors with the corresponding code (if any). */
	public static EColors byCode(int codeIn) {
		for (EColors c : values()) {
			if (c.color_replacement_code == codeIn) { return c; }
		}
		return null;
	}
	
	/** Returns an EColors with the corresponding String name (if any). */
	public static EColors byName(String colorNameIn) {
		if (colorNameIn != null) {
			for (EColors c : values()) {
				if (c.name.toLowerCase().equals(colorNameIn.toLowerCase())) { return c; }
			}
		}
		return null;
	}
	
	public static EColors random() {
		return values()[RandomUtil.getRoll(0, values().length - 1)];
	}
	
	/** Needs to be consistently called in order for any color change to occur. */
	public static int rainbow() {
		return Color.HSBtoRGB(System.currentTimeMillis() % 10000L / 10000.0f, 0.8f, 1f);
	}
	
	/** Returns green if true and red if false. */
	public static EColors bool(boolean val) { return (val) ? EColors.green : EColors.lred; }
	/** Returns specified true color if true and false color if fales. */
	public static EColors bool(boolean val, EColors ifTrue, EColors ifFalse) { return (val) ? ifTrue : ifFalse; }
	
	//------------------------------------------------------------------
	
	/** Static class to hold static color id. */
	private static final class ColorID {
		private static int ID = 0;
		private ColorID() {}
		public static int next() { return ID++; }
		public static String pad(int id) {
			String s = String.valueOf(id);
			return (s.length() == 1) ? "0" + s : s;
		}
	}
	
}
