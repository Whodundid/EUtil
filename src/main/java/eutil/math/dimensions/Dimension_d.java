package eutil.math.dimensions;

/**
 * A datatype containing 2D object dimensions using double floating point precision. 
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class Dimension_d extends IDimension<Double> {
	
	//========
	// Fields
	//========
	
	public double startX = 0.0, endX = 0.0;
	public double startY = 0.0, endY = 0.0;
	public double midX = 0.0, midY = 0.0;
	public double width = 0.0, height = 0.0;
	
	//==============
	// Constructors
	//==============
	
    public Dimension_d() {
        this(0.0D, 0.0D, 0.0D, 0.0D);
    }
    
    public Dimension_d(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
        this(startXIn.doubleValue(), startYIn.doubleValue(), endXIn.doubleValue(), endYIn.doubleValue());
    }
    
    public Dimension_d(double startXIn, double startYIn, double endXIn, double endYIn) {
        startX = startXIn;
        startY = startYIn;
        endX = endXIn;
        endY = endYIn;
        width = endXIn - startXIn;
        height = endYIn - startYIn;
        midX = midX_d();
        midY = midY_d();
    }
    
    public Dimension_d(Dimension_d dimIn) {
        startX = dimIn.startX;
        startY = dimIn.startY;
        endX = dimIn.endX;
        endY = dimIn.endY;
        width = endX - startX;
        height = endY - startY;
        midX = midX_d();
        midY = midY_d();
    }
    
    public Dimension_d(IDimension<?> dimIn) {
        startX = dimIn.startX_d();
        startY = dimIn.startY_d();
        endX = dimIn.endX_d();
        endY = dimIn.endY_d();
        width = endX - startX;
        height = endY - startY;
        midX = midX_d();
        midY = midY_d();
    }
	
	//===========
	// Overrides
	//===========
	
	@Override public Double startX() { return startX; }
	@Override public Double startY() { return startY; }
	@Override public Double endX() { return endX; }
	@Override public Double endY() { return endY; }
	@Override public Double midX() { return midX; }
	@Override public Double midY() { return midY; }
	@Override public Double width() { return width; }
	@Override public Double height() { return height; }
	
	@Override public double startX_d() { return startX; }
	@Override public double startY_d() { return startY; }
	@Override public double endX_d() { return endX; }
	@Override public double endY_d() { return endY; }
	@Override public double midX_d() { return startX + (width * 0.5D); }
	@Override public double midY_d() { return startY + (height * 0.5D); }
	@Override public double width_d() { return width; }
	@Override public double height_d() { return height; }
	
	@Override public float startX_f() { return (float) startX; }
	@Override public float startY_f() { return (float) startY; }
	@Override public float endX_f() { return (float) endX; }
	@Override public float endY_f() { return (float) endY; }
	@Override public float midX_f() { return (float) midX_d(); }
	@Override public float midY_f() { return (float) midY_d(); }
	@Override public float width_f() { return (float) width; }
	@Override public float height_f() { return (float) height; }
	
    @Override public int startX_i() { return (int) startX; }
    @Override public int startY_i() { return (int) startY; }
    @Override public int endX_i() { return (int) endX; }
    @Override public int endY_i() { return (int) endY; }
    @Override public int midX_i() { return (int) midX_d(); }
    @Override public int midY_i() { return (int) midX_d(); }
    @Override public int width_i() { return (int) width; }
    @Override public int height_i() { return (int) height; }
    
    @Override public long startX_l() { return (long) startX; }
    @Override public long startY_l() { return (long) startY; }
    @Override public long endX_l() { return (long) endX; }
    @Override public long endY_l() { return (long) endY; }
    @Override public long midX_l() { return (long) midX_d(); }
    @Override public long midY_l() { return (long) midX_d(); }
    @Override public long width_l() { return (long) width; }
    @Override public long height_l() { return (long) height; }
	
	@Override public void startX(Number startX) { this.startX = startX.doubleValue(); }
	@Override public void startY(Number startY) { this.startY = startY.doubleValue(); }
	@Override public void endX(Number endX) { this.endX = endX.doubleValue(); }
	@Override public void endY(Number endY) { this.endY = endY.doubleValue(); }
	@Override public void width(Number width) { this.width = width.doubleValue(); }
	@Override public void height(Number height) { this.height = height.doubleValue(); }
	
    /**
     * Returns the 2D area that this dimension occupies.
     * @since 1.6.1
     */
    @Override public Double getArea() { return getArea_d(); }
    @Override protected int getArea_i() { return (int) getArea_d(); }
    @Override protected long getArea_l() { return (long) getArea_d(); }
    @Override protected float getArea_f() { return (float) getArea_d(); }
    @Override protected double getArea_d() { return ((endX - startX) * (endY - startY)); }
	
    @Override
    public String toString() {
        return "[startX/Y: " + startX + ", " + startY +
                "; endX/Y: " + endX + ", " + endY +
                "; width/Height: " + width + ", " + height + "]";
    }
	
	//=========
	// Methods
	//=========
	
	public Dimension_d move(final double changeX, final double changeY) {
		startX += changeX;
		startY += changeY;
		reDimension();
		return this;
	}
	
	public Dimension_d set(final double newX, final double newY, final double newWidth, final double newHeight) {
		startX = newX;
		startY = newY;
		width = newWidth;
		height = newHeight;
		reDimension();
		return this;
	}
	
	public Dimension_d setPosition(final double newX, final double newY) {
		startX = newX;
		startY = newY;
		reDimension();
		return this;
	}
	
	public Dimension_d setWidth(final double newWidth) {
		width = newWidth;
		reDimension();
		return this;
	}
	
	public Dimension_d setHeight(final double newHeight) {
		height = newHeight;
		reDimension();
		return this;
	}
	
	@Override
	protected void reDimension() {
		endX = startX + width;
		endY = startY + height;
		midX = midX_d();
		midY = midY_d();
	}
	
    /**
     * Expands this dimension outward in all directions by the given
     * amount.
     * 
     * @param amount The amount to expand outwards by
     * 
     * @return A modified dimension using this one as a starting point
     * 
     * @since 1.5.1
     */
    public Dimension_d add(double amount) {
        return new Dimension_d(startX - amount, startY - amount, endX + amount, endY + amount);
    }
    
    /**
     * Expands this dimension outward in all directions by the given
     * amount.
     * 
     * @param amount The amount to expand outwards by
     * 
     * @return A modified dimension using this one as a starting point
     * 
     * @since 1.5.1
     */
    public Dimension_d add(Number amount) {
        double d = amount.doubleValue();
        return new Dimension_d(startX - d, startY - d, endX + d, endY + d);
    }
    
    /**
     * Contracts this dimension inward by in all directions by the given
     * amount.
     * 
     * @param amount The amount to contract inwards by
     * 
     * @return A modified dimension using this one as a starting point
     * 
     * @since 1.5.1
     */
    public Dimension_d sub(double amount) {
        return new Dimension_d(startX + amount, startY + amount, endX - amount, endY - amount);
    }
    
    /**
     * Contracts this dimension inward by in all directions by the given
     * amount.
     * 
     * @param amount The amount to contract inwards by
     * 
     * @return A modified dimension using this one as a starting point
     * 
     * @since 1.5.1
     */
    public Dimension_d sub(Number amount) {
        double d = amount.doubleValue();
        return new Dimension_d(startX + d, startY + d, endX - d, endY - d);
    }
    
    public Dimension_d translateHorizontal(double amount) {
        startX += amount;
        return this;
    }
    
    public Dimension_d translateVertical(double amount) {
        startY += amount;
        return this;
    }
    
    /**
     * Returns true if this dimension's bounds at least partially contains
     * the given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(IDimension<?> dimIn) {
        return startX <= dimIn.endX_d() &&
               startY <= dimIn.endY_d() &&
               endX >= dimIn.startX_d() &&
               endY >= dimIn.startY_d();
    }
    
    /**
     * Returns true if this dimension's bounds at least partially contains
     * the given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(Dimension_d dimIn) {
        return startX <= dimIn.endX &&
               startY <= dimIn.endY &&
               endX >= dimIn.startX &&
               endY >= dimIn.startY;
    }
    
    /**
     * Returns true if the given dimension is completely inside of this
     * dimension. As in, not just partially inside.
     * 
     * @return True if the given dimension completely 'fits' inside of this
     *         one
     */
    public boolean fullyContains(Dimension_d dimIn) {
        return startX < dimIn.startX &&
               startY < dimIn.startY &&
               endX > dimIn.endX &&
               endY > dimIn.endY;
    }
    
    /**
     * Returns true if the given dimension is completely inside of this
     * dimension. As in, not just partially inside.
     * 
     * @return True if the given dimension completely 'fits' inside of this
     *         one
     */
    public boolean fullyContains(IDimension<?> dimIn) {
        return startX < dimIn.startX_d() &&
               startY < dimIn.startY_d() &&
               endX > dimIn.endX_d() &&
               endY > dimIn.endY_d();
    }
    
    /**
     * Returns true if the Area of this dimension is bigger than the area
     * of the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is bigger
     */
    public boolean isGreaterThan(Dimension_d dimIn) {
        return getArea_d() > dimIn.getArea_d();
    }
    
    /**
     * Returns true if the Area of this dimension is bigger than the area
     * of the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is bigger
     */
    @Override
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
    public boolean isLessThan(Dimension_d dimIn) {
        return getArea_d() < dimIn.getArea_d();
    }
    
    /**
     * Returns true if the Area of this dimension is less than the area of
     * the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is smaller
     */
    @Override
    public boolean isLessThan(IDimension<?> dimIn) {
        return getArea_d() < dimIn.getArea_d();
    }
    
    public boolean isEqualTo(Dimension_d dimIn) {
        return startX == dimIn.startX &&
               startY == dimIn.startY &&
               width == dimIn.width &&
               height == dimIn.height;
    }
    
    @Override
    public boolean isEqualTo(IDimension<?> dimIn) {
        return startX == dimIn.startX_d() &&
               startY == dimIn.startY_d() &&
               width == dimIn.width_d() &&
               height == dimIn.height_d();
    }
    
    //================
    // Static Methods
    //================
    
    public static Dimension_d of(double startXIn, double startYIn, double endXIn, double endYIn) {
        return new Dimension_d(startXIn, startYIn, endXIn, endYIn);
    }
    
    public static Dimension_d of(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
        return new Dimension_d(startXIn, startYIn, endXIn, endYIn);
    }
    
    public static Dimension_d of(Dimension_f in) {
        return new Dimension_d(in.startX, in.startY, in.endX, in.endY);
    }
    
    public static Dimension_d of(IDimension<?> in) {
        return new Dimension_d(in.startX_d(), in.startY_d(), in.endX_d(), in.endY_d());
    }
	
}
