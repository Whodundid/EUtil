package eutil.datatypes;

import eutil.math.ENumUtil;

/**
 * A simple double 2D (x, y) point holder.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.1.0
 */
public class Point2d {
	
	public double x, y;
	
	public Point2d() { this(0.0d, 0.0d); }
	public Point2d(double xIn, double yIn) {
		x = xIn;
		y = yIn;
	}
	
	public double distance(Point2d p) {
		return ENumUtil.distance(x, y, p.x, p.y);
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	
	public void setX(double xIn) { x = xIn; }
	public void setY(double yIn) { y = yIn; }
	
}
