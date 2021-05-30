package eutil.colors;

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
	
	public int intVal;
	public int code;
	public String name;
	
	EColors(int colorIn, String nameIn) {
		intVal = colorIn;
		code = ColorID.next();
		name = nameIn;
	}
	
	//------------------------------------------------------------------
	
	@Override public String toString() { return "\u222e" + code; }

	//------------------------------------------------------------------
	
	/** Returns the color integer. */
	public int c() { return intVal; }
	
	/** Returns the color name. */
	public String n() { return name; }
	
	//------------------------------------------------------------------
	
	/** Returns an EColors with the corresponding integer color (if any). */
	public static EColors getEColor(int colorIn) {
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
	public static EColors getEColorByCode(int codeIn) {
		for (EColors c : values()) {
			if (c.code == codeIn) { return c; }
		}
		return null;
	}
	
	/** Returns an EColors with the corresponding String name (if any). */
	public static EColors getEColorByName(String colorNameIn) {
		if (colorNameIn != null) {
			for (EColors c : values()) {
				if (c.name.toLowerCase().equals(colorNameIn.toLowerCase())) { return c; }
			}
		}
		return null;
	}
	
	/** Needs to be consistently called in order for any color change to occur. */
	public static int rainbow() {
		return Color.HSBtoRGB(System.currentTimeMillis() % 10000L / 10000.0f, 0.8f, 1f);
	}
	
	public static EColors bool(boolean val) { return (val) ? EColors.green : EColors.lred; }
	public static EColors bool(boolean val, EColors ifTrue, EColors ifFalse) { return (val) ? ifTrue : ifFalse; }
	
	//------------------------------------------------------------------
	
	/** Static class to hold static color id. */
	private static final class ColorID {
		private static int ID = 0;
		private ColorID() {}
		public static int next() { return ID++; }
	}
	
}
