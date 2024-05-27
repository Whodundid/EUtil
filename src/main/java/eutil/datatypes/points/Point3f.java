package eutil.datatypes.points;

/**
 * A simple float 3D (x, y, z) point holder.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.7.1
 */
public class Point3f {
    
    //========
    // Fields
    //========
    
    public float x, y, z;
    
    //==============
    // Constructors
    //==============
    
    public Point3f() { this(0.0f, 0.0f, 0.0f); }
    public Point3f(float xIn, float yIn, float zIn) {
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
    
    public float distance(float xIn, float yIn, float zIn) {
        return (float) Math.sqrt((xIn-x)*(xIn-x) + (yIn-y)*(yIn-y) + (zIn-z)*(zIn-z));
    }
    
    public float distance(Point3f p) {
        return (float) Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y) + (p.z-z)*(p.z-z));
    }
    
    public boolean compare(float xIn, float yIn, float zIn) {
        return x - xIn < 0.000000000001f && y - yIn < 0.000000000001f && z - zIn < 0.000000000001f;
    }
    public boolean compare(Point3f p) {
        return compare(p.x, p.y, p.z);
    }
    
    //=========
    // Getters
    //=========
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    
    //=========
    // Setters
    //=========
    
    public void setX(float xIn) { x = xIn; }
    public void setY(float yIn) { y = yIn; }
    public void setZ(float zIn) { z = zIn; }
    
    public void set(float xIn, float yIn, float zIn) {
        x = xIn;
        y = yIn;
        z = zIn;
    }
    
}
