package eutil.datatypes.points;

/**
 * A simple double 3D (x, y, z) point holder.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.7.1
 */
public class Point3d {
    
    //========
    // Fields
    //========
    
    public double x, y, z;
    
    //==============
    // Constructors
    //==============
    
    public Point3d() { this(0.0d, 0.0d, 0.0d); }
    public Point3d(double xIn, double yIn, double zIn) {
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
    
    public double distance(double xIn, double yIn, double zIn) {
        return Math.sqrt((xIn-x)*(xIn-x) + (yIn-y)*(yIn-y) + (zIn-z)*(zIn-z));
    }
    
    public double distance(Point3d p) {
        return Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y) + (p.z-z)*(p.z-z));
    }
    
    public boolean compare(double xIn, double yIn, double zIn) {
        return x - xIn < 0.000000000001 && y - yIn < 0.000000000001 && z - zIn < 0.000000000001;
    }
    public boolean compare(Point3d p) {
        return compare(p.x, p.y, p.z);
    }
    
    //=========
    // Getters
    //=========
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    
    //=========
    // Setters
    //=========
    
    public void setX(double xIn) { x = xIn; }
    public void setY(double yIn) { y = yIn; }
    public void setZ(double zIn) { z = zIn; }
    
    public void set(double xIn, double yIn, double zIn) {
        x = xIn;
        y = yIn;
        z = zIn;
    }
    
}
