package eutil.datatypes.points;

import eutil.math.ENumUtil;

/**
 * A simple float 2D (x, y) point holder.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.1.0
 */
public class Point2f {
	
	public float x, y;
	
	public Point2f() { this(0, 0); }
	public Point2f(float xIn, float yIn) {
		x = xIn;
		y = yIn;
	}
	
	public float distance(Point2f p) {
		return (float) ENumUtil.distance(x, y, p.x, p.y);
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	
	public void setX(float xIn) { x = xIn; }
	public void setY(float yIn) { y = yIn; }
	
}
