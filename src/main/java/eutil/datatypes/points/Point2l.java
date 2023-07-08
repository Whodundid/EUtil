package eutil.datatypes.points;

import eutil.math.ENumUtil;

/**
 * A simple long 2D (x, y) point holder.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.1.0
 */
public class Point2l {
    
    public long x, y;
    
    public Point2l() { this(0, 0); }
    public Point2l(long xIn, long yIn) {
        x = xIn;
        y = yIn;
    }
    
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
    
    public long distance(Point2l p) {
        return (long) ENumUtil.distance(x, y, p.x, p.y);
    }
    
    public long getX() { return x; }
    public long getY() { return y; }
    
    public void setX(long xIn) { x = xIn; }
    public void setY(long yIn) { y = yIn; }
    
}
