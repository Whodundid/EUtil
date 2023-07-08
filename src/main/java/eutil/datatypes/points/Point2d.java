package eutil.datatypes.points;

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
    
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
    
    public double distance(double xIn, double yIn) {
        return ENumUtil.distance(x, y, xIn, yIn);
    }
    
    public double distance(Point2d p) {
        return ENumUtil.distance(x, y, p.x, p.y);
    }
    
    public boolean compare(double xIn, double yIn) {
        return x - xIn < 0.000000000001 && y - yIn < 0.000000000001;
    }
    public boolean compare(Point2d p) {
        return compare(p.x, p.y);
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    public void setX(double xIn) { x = xIn; }
    public void setY(double yIn) { y = yIn; }
    
    public void set(double xIn, double yIn) {
        x = xIn;
        y = yIn;
    }
    
}
