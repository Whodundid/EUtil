package eutil.colors;

import eutil.debug.Broken;
import eutil.debug.Experimental;

/**
 * An incomplete experimental implementation of a standard integer color blending algorithm.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
@Experimental(since="Its inception")
@Broken(reason="Fairly certain this code has never worked properly", since="2.2.0")
public class ColorBlender {
	
	public static int overlayColor(int background, int overlay) {
		long base = (long) background;
		long over = (long) overlay;
		
		long bA = (base >> 24) & 0xff;
		long bR = (base >> 16) & 0xff;
		long bG = (base >> 8) & 0xff;
		long bB = (base) & 0xff;
		
		//long colorA = (bR << 16) + (bG << 8) + bB;
		
		long oA = (over >> 24) & 0xff;
		long oR = (over >> 16) & 0xff;
		long oG = (over >> 8) & 0xff;
		long oB = (over) & 0xff;
		
		//long colorB = (oR << 16) + (oG << 8) + oB;
		long newAlpha = (long) (255 - (255 - oA) * (255 - bA));
		
		double newRed = oR * oA / newAlpha + (bR * bA * (255 - oA) / newAlpha) / 255;
		double newGreen = oG * oA / newAlpha + (bG * bA * (255 - oA) / newAlpha) / 255;
		double newBlue = oB * oA / newAlpha + (bB * bA * (255 - oA) / newAlpha) / 255;
		
		long newColor = ((long) newAlpha << 24) + ((long) newRed << 16) + ((long) newGreen << 8) + (long) newBlue;
		
		return (int) newColor;
	}

}
