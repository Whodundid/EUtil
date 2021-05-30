package eutil.misc;

/**
 * A enum representing a location or direction on the physical screen.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public enum ScreenLocation {
	
	TOP,
	TOP_RIGHT,
	RIGHT,
	BOT_RIGHT,
	BOT,
	BOT_LEFT,
	LEFT,
	TOP_LEFT,
	CENTER,
	CUSTOM,
	OUT;
	
	/** Returns true if the specified ScreenLocation is located at the corner of the screen. */
	public static boolean isCorner(ScreenLocation locIn) {
		switch (locIn) {
		case TOP_RIGHT:
		case BOT_RIGHT:
		case TOP_LEFT:
		case BOT_LEFT: return true;
		default: return false;
		}
	}
	
	/** Returns true if the specified ScreenLocation is located at the side of the screen. */
	public static boolean isSide(ScreenLocation locIn) {
		switch (locIn) {
		case TOP:
		case LEFT:
		case BOT:
		case RIGHT: return true;
		default: return false;
		}
	}
	
}
