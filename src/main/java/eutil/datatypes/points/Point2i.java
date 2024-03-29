package eutil.datatypes.points;

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
    
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
    
    public int distance(Point2i p) {
        return (int) ENumUtil.distance(x, y, p.x, p.y);
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    public void setX(int xIn) { x = xIn; }
    public void setY(int yIn) { y = yIn; }
    
    public void set(int xIn, int yIn) {
        x = xIn;
        y = yIn;
    }
    
    public void set(Point2i pointIn) {
        x = pointIn.x;
        y = pointIn.y;
    }
    
    public boolean compare(Point2i pointIn) {
        if (pointIn == null) return false;
        return compare(pointIn.x, pointIn.y);
    }
    
    public boolean compare(int xIn, int yIn) {
        return x == xIn && y == yIn;
    }
    
}
