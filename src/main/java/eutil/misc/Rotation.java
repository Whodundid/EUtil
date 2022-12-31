package eutil.misc;

import eutil.random.ERandomUtil;

/**
 * A simple enum used to represent four different object rotations.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public enum Rotation {
	
	UP,
	DOWN,
	LEFT,
	RIGHT;
	
	/**
	 * Returns a random rotation. 
	 * 
	 * @return Rotation
	 * @since 1.1
	 */
	public static Rotation random() {
		return values()[ERandomUtil.getRoll(0, values().length - 1)];
	}
	
}
