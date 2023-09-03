package eutil.math.dimensions;

/**
 * A datatype containing 2D object dimensions using float. 
 * 
 * @author Hunter Bragg
 * @since 1.9
 */
public class Dimension_f extends IDimension<Float> {
	
	//========
	// Fields
	//========
	
	public float startX = 0.0f, endX = 0.0f;
	public float startY = 0.0f, endY = 0.0f;
	public float midX = 0.0f, midY = 0.0f;
	public float width = 0.0f, height = 0.0f;
	
	//==============
	// Constructors
	//==============
	
	public Dimension_f() { this(0.0f, 0.0f, 0.0f, 0.0f); }
	public Dimension_f(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
		this(startXIn.floatValue(), startYIn.floatValue(), endXIn.floatValue(), endYIn.floatValue());
	}
	
	public Dimension_f(float startXIn, float startYIn, float endXIn, float endYIn) {
		startX = startXIn;
		startY = startYIn;
		endX = endXIn;
		endY = endYIn;
		width = endXIn - startXIn;
		height = endYIn - startYIn;
		midX = midX_f();
		midY = midY_f();
	}
	
	public Dimension_f(Dimension_f dimIn) {
		startX = dimIn.startX;
		startY = dimIn.startY;
		endX = dimIn.endX;
		endY = dimIn.endY;
		width = endX - startX;
		height = endY - startY;
		midX = midX_f();
		midY = midY_f();
	}
	
	public Dimension_f(IDimension<?> dimIn) {
		startX = dimIn.startX_f();
		startY = dimIn.startY_f();
		endX = dimIn.endX_f();
		endY = dimIn.endY_f();
		width = endX - startX;
		height = endY - startY;
		midX = midX_f();
		midY = midY_f();
	}
	
	//===========
	// Overrides
	//===========
	
    @Override public Float startX() { return startX; }
    @Override public Float startY() { return startY; }
    @Override public Float endX() { return endX; }
    @Override public Float endY() { return endY; }
    @Override public Float midX() { return midX; }
    @Override public Float midY() { return midY; }
    @Override public Float width() { return width; }
    @Override public Float height() { return height; }
	
	@Override public double startX_d() { return startX; }
	@Override public double startY_d() { return startY; }
	@Override public double endX_d() { return endX; }
	@Override public double endY_d() { return endY; }
	@Override public double midX_d() { return midX_f(); }
	@Override public double midY_d() { return midY_f(); }
	@Override public double width_d() { return width; }
	@Override public double height_d() { return height; }
	
	@Override public float startX_f() { return startX; }
	@Override public float startY_f() { return startY; }
	@Override public float endX_f() { return endX; }
	@Override public float endY_f() { return endY; }
	@Override public float midX_f() { return startX + (width * 0.5f); }
	@Override public float midY_f() { return startY + (height * 0.5f); }
	@Override public float width_f() { return width; }
	@Override public float height_f() { return height; }
	
    @Override public int startX_i() { return (int) startX; }
    @Override public int startY_i() { return (int) startY; }
    @Override public int endX_i() { return (int) endX; }
    @Override public int endY_i() { return (int) endY; }
    @Override public int midX_i() { return (int) midX_f(); }
    @Override public int midY_i() { return (int) midX_f(); }
    @Override public int width_i() { return (int) width; }
    @Override public int height_i() { return (int) height; }
    
    @Override public long startX_l() { return (long) startX; }
    @Override public long startY_l() { return (long) startY; }
    @Override public long endX_l() { return (long) endX; }
    @Override public long endY_l() { return (long) endY; }
    @Override public long midX_l() { return (long) midX_f(); }
    @Override public long midY_l() { return (long) midX_f(); }
    @Override public long width_l() { return (long) width; }
    @Override public long height_l() { return (long) height; }
	
    /**
     * Returns the 2D area that this dimension occupies.
     * @since 1.6.1
     */
    @Override public Float getArea() { return getArea_f(); }
    @Override protected int getArea_i() { return (int) getArea_f(); }
    @Override protected long getArea_l() { return (long) getArea_f(); }
    @Override protected float getArea_f() { return ((endX - startX) * (endY - startY)); }
    @Override protected double getArea_d() { return getArea_f(); }
    
	@Override public void startX(Number startX) { this.startX = startX.floatValue(); }
	@Override public void startY(Number startY) { this.startY = startY.floatValue(); }
	@Override public void endX(Number endX) { this.endX = endX.floatValue(); }
	@Override public void endY(Number endY) { this.endY = endY.floatValue(); }
	@Override public void width(Number width) { this.width = width.floatValue(); }
	@Override public void height(Number height) { this.height = height.floatValue(); }
	
    @Override
    public String toString() {
        return "[startX/Y: " + startX + ", " + startY +
                "; endX/Y: " + endX + ", " + endY +
                "; width/Height: " + width + ", " + height + "]";
    }
	
	//=========
	// Methods
	//=========
	
	public Dimension_f move(float changeX, float changeY) {
		startX += changeX;
		startY += changeY;
		reDimension();
		return this;
	}
	
	public Dimension_f set(float newX, float newY, float newWidth, float newHeight) {
		startX = newX;
		startY = newY;
		width = newWidth;
		height = newHeight;
		reDimension();
		return this;
	}
	
	public Dimension_f setPosition(float newX, float newY) {
		startX = newX;
		startY = newY;
		reDimension();
		return this;
	}
	
	public Dimension_f setWidth(float newWidth) {
		width = newWidth;
		reDimension();
		return this;
	}
	
	public Dimension_f setHeight(float newHeight) {
		height = newHeight;
		reDimension();
		return this;
	}
	
	@Override
	protected void reDimension() {
		endX = startX + width;
		endY = startY + height;
		midX = midX_f();
		midY = midY_f();
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
    public Dimension_f add(float amount) {
        return new Dimension_f(startX - amount, startY - amount, endX + amount, endY + amount);
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
    public Dimension_f add(Number amount) {
        float f = amount.floatValue();
        return new Dimension_f(startX - f, startY - f, endX + f, endY + f);
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
    public Dimension_f sub(float amount) {
        return new Dimension_f(startX + amount, startY + amount, endX - amount, endY - amount);
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
    public Dimension_f sub(Number amount) {
        float f = amount.floatValue();
        return new Dimension_f(startX + f, startY + f, endX - f, endY - f);
    }
    
    public Dimension_f translateHorizontal(float amount) {
        startX += amount;
        return this;
    }
    
    public Dimension_f translateVertical(float amount) {
        startY += amount;
        return this;
    }
    
    /**
     * Returns true if this dimension's bounds at least partially contains
     * the given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(IDimension<?> dimIn) {
        return startX <= dimIn.endX_f() &&
               startY <= dimIn.endY_f() &&
               endX >= dimIn.startX_f() &&
               endY >= dimIn.startY_f();
    }
    
    /**
     * Returns true if this dimension's bounds at least partially contains
     * the given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(Dimension_f dimIn) {
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
    public boolean fullyContains(Dimension_f dimIn) {
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
        return startX < dimIn.startX_f() &&
               startY < dimIn.startY_f() &&
               endX > dimIn.endX_f() &&
               endY > dimIn.endY_f();
    }
    
    /**
     * Returns true if the Area of this dimension is bigger than the area
     * of the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is bigger
     */
    public boolean isGreaterThan(Dimension_f dimIn) {
        return getArea_f() > dimIn.getArea_f();
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
        return getArea_f() > dimIn.getArea_f();
    }
    
    /**
     * Returns true if the Area of this dimension is less than the area of
     * the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is smaller
     */
    public boolean isLessThan(Dimension_f dimIn) {
        return getArea_f() < dimIn.getArea_f();
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
        return getArea_f() < dimIn.getArea_f();
    }
    
    public boolean isEqualTo(Dimension_f dimIn) {
        return startX == dimIn.startX &&
               startY == dimIn.startY &&
               width == dimIn.width &&
               height == dimIn.height;
    }
    
    @Override
    public boolean isEqualTo(IDimension<?> dimIn) {
        return startX == dimIn.startX_f() &&
               startY == dimIn.startY_f() &&
               width == dimIn.width_f() &&
               height == dimIn.height_f();
    }
    
    //================
    // Static Methods
    //================
    
    public static Dimension_f of(float startXIn, float startYIn, float endXIn, float endYIn) {
        return new Dimension_f(startXIn, startYIn, endXIn, endYIn);
    }
    
    public static Dimension_f of(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
        return new Dimension_f(startXIn, startYIn, endXIn, endYIn);
    }
    
    public static Dimension_f of(Dimension_f in) {
        return new Dimension_f(in.startX, in.startY, in.endX, in.endY);
    }
    
    public static Dimension_f of(IDimension<?> in) {
        return new Dimension_f(in.startX_f(), in.startY_f(), in.endX_f(), in.endY_f());
    }
	
}
