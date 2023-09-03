package eutil.math.dimensions;

public abstract class IDimension<T extends Number> {
    
    //==============
    // Field Values
    // ==============
    
    protected abstract T startX();
    protected abstract T startY();
    protected abstract T endX();
    protected abstract T endY();
    protected abstract T midX();
    protected abstract T midY();
    protected abstract T width();
    protected abstract T height();
    
    protected abstract int startX_i();
    protected abstract int startY_i();
    protected abstract int endX_i();
    protected abstract int endY_i();
    protected abstract int midX_i();
    protected abstract int midY_i();
    protected abstract int width_i();
    protected abstract int height_i();
    
    protected abstract long startX_l();
    protected abstract long startY_l();
    protected abstract long endX_l();
    protected abstract long endY_l();
    protected abstract long midX_l();
    protected abstract long midY_l();
    protected abstract long width_l();
    protected abstract long height_l();
    
    protected abstract float startX_f();
    protected abstract float startY_f();
    protected abstract float endX_f();
    protected abstract float endY_f();
    protected abstract float midX_f();
    protected abstract float midY_f();
    protected abstract float width_f();
    protected abstract float height_f();
    
    protected abstract double startX_d();
    protected abstract double startY_d();
    protected abstract double endX_d();
    protected abstract double endY_d();
    protected abstract double midX_d();
    protected abstract double midY_d();
    protected abstract double width_d();
    protected abstract double height_d();
    
    public abstract void startX(Number startX);
    public abstract void startY(Number startY);
    public abstract void endX(Number endX);
    public abstract void endY(Number endY);
    public abstract void width(Number width);
    public abstract void height(Number height);
    
    public abstract T getArea();
    protected abstract int getArea_i();
    protected abstract long getArea_l();
    protected abstract float getArea_f();
    protected abstract double getArea_d();
    
    @Override
    public String toString() {
        return "[startX/Y: " + startX() + ", " + startY() +
               "; endX/Y: " + endX() + ", " + endY() +
               "; width/Height: " + width() + ", " + height() + "]";
    }
    
    //=========
    // Methods
    //=========
    
    protected abstract void reDimension();
    
    /**
     * Returns true if the Area of this dimension is bigger than the area
     * of the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is bigger
     */
    public boolean isGreaterThan(IDimension<?> dimIn) {
        return getArea_d() > dimIn.getArea_d();
    }
    
    /**
     * Returns true if the Area of this dimension is less than the area of
     * the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is smaller
     */
    public boolean isLessThan(IDimension<?> dimIn) {
        return getArea_d() < dimIn.getArea_d();
    }
    
    public boolean isEqualTo(IDimension<?> dimIn) {
        return startX().equals(dimIn.startX()) &&
               startY() == dimIn.startY() &&
               width() == dimIn.width() &&
               height() == dimIn.height();
    }
    
    public boolean contains(T xIn, T yIn) {
        double x = xIn.doubleValue();
        double y = yIn.doubleValue();
        return x >= startX_d() && x <= endX_d() && y >= startY_d() && y <= endY_d();
    }
    
    public boolean contains(T left, T top, T right, T bot) {
        double leftD = left.doubleValue();
        double topD = top.doubleValue();
        double rightD = right.doubleValue();
        double botD = bot.doubleValue();
        
        if (leftD < rightD) {
            double t = leftD;
            leftD = rightD;
            rightD = t;
        }
        
        if (topD < botD) {
            double t = topD;
            topD = botD;
            botD = t;
        }
        
        return (startX_d() < leftD && startY_d() < topD) &&
               (endX_d() > rightD && endY_d() > botD);
    }
    
    //=============
    // Conversions
    //=============
    
    public Dimension_i toInt() { return new Dimension_i(startX_i(), startY_i(), endX_i(), endY_i()); }
    public Dimension_l toLong() { return new Dimension_l(startX_l(), startY_l(), endX_l(), endY_l()); }
    public Dimension_f toFloat() { return new Dimension_f(startX_f(), startY_f(), endX_f(), endY_f()); }
    public Dimension_d toDouble() { return new Dimension_d(startX_d(), startY_d(), endX_d(), endY_d()); }
    
}
