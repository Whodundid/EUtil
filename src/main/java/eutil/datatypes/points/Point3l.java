package eutil.datatypes.points;

/**
 * A simple long 3D (x, y, z) point holder.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.7.1
 */
public class Point3l {
    
    //========
    // Fields
    //========
    
    public long x, y, z;
    
    //==============
    // Constructors
    //==============
    
    public Point3l() { this(0l, 0l, 0l); }
    public Point3l(long xIn, long yIn, long zIn) {
        x = xIn;
        y = yIn;
        z = zIn;
    }
    
    //===========
    // Overrides
    //===========
    
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
    
    //=========
    // Methods
    //=========
    
    public long distance(long xIn, long yIn, long zIn) {
        return (long) Math.sqrt((xIn-x)*(xIn-x) + (yIn-y)*(yIn-y) + (zIn-z)*(zIn-z));
    }
    
    public long distance(Point3d p) {
        return (long) Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y) + (p.z-z)*(p.z-z));
    }
    
    public boolean compare(long xIn, long yIn, long zIn) {
        return x == xIn && y == yIn && z == zIn;
    }
    public boolean compare(Point3l p) {
        return compare(p.x, p.y, p.z);
    }
    
    //=========
    // Getters
    //=========
    
    public long getX() { return x; }
    public long getY() { return y; }
    public long getZ() { return z; }
    
    //=========
    // Setters
    //=========
    
    public void setX(long xIn) { x = xIn; }
    public void setY(long yIn) { y = yIn; }
    public void setZ(long zIn) { z = zIn; }
    
    public void set(long xIn, long yIn, long zIn) {
        x = xIn;
        y = yIn;
        z = zIn;
    }
    
}
