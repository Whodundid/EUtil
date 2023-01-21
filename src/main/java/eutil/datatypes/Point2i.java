package eutil.datatypes;

import eutil.math.ENumUtil;

/**
 * A simple int 2D (x, y) point holder.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.1.0
 */
public class Point2i {
	
	public int x, y;
	
	public Point2i() { this(0, 0); }
	public Point2i(int xIn, int yIn) {
		x = xIn;
		y = yIn;
	}
	
	public int distance(Point2i p) {
		return (int) ENumUtil.distance(x, y, p.x, p.y);
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void setX(int xIn) { x = xIn; }
	public void setY(int yIn) { y = yIn; }
	
}
