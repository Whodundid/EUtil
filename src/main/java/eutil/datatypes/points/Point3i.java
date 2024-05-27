package eutil.datatypes.points;

/**
 * A simple int 3D (x, y, z) point holder.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.7.1
 */
public class Point3i {
    
    //========
    // Fields
    //========
    
    public int x, y, z;
    
    //==============
    // Constructors
    //==============
    
    public Point3i() { this(0, 0, 0); }
    public Point3i(int xIn, int yIn, int zIn) {
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
    
    public int distance(int xIn, int yIn, int zIn) {
        return (int) Math.sqrt((xIn-x)*(xIn-x) + (yIn-y)*(yIn-y) + (zIn-z)*(zIn-z));
    }
    
    public int distance(Point3i p) {
        return (int) Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y) + (p.z-z)*(p.z-z));
    }
    
    public boolean compare(int xIn, int yIn, int zIn) {
        return x == xIn && y == yIn && z == zIn;
    }
    public boolean compare(Point3i p) {
        return compare(p.x, p.y, p.z);
    }
    
    //=========
    // Getters
    //=========
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
    
    //=========
    // Setters
    //=========
    
    public void setX(int xIn) { x = xIn; }
    public void setY(int yIn) { y = yIn; }
    public void setZ(int zIn) { z = zIn; }
    
    public void set(int xIn, int yIn, int zIn) {
        x = xIn;
        y = yIn;
        z = zIn;
    }
    
}
