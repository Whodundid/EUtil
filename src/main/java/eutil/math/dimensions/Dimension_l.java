package eutil.math.dimensions;

/**
 * A datatype containing 2D object dimensions using integer precision. 
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class Dimension_l extends IDimension<Long> {
	
	//========
	// Fields
	//========
	
	public long startX = 0L, endX = 0L;
	public long startY = 0L, endY = 0L;
	public long midX = 0L, midY = 0L;
	public long width = 0L, height = 0L;
	
	//==============
	// Constructors
	//==============
	
    public Dimension_l() {
        this(0L, 0L, 0L, 0L);
    }
    
    public Dimension_l(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
        this(startXIn.longValue(), startYIn.longValue(), endXIn.longValue(), endYIn.longValue());
    }
    
    public Dimension_l(long startXIn, long startYIn, long endXIn, long endYIn) {
        startX = startXIn;
        startY = startYIn;
        endX = endXIn;
        endY = endYIn;
        width = endXIn - startXIn;
        height = endYIn - startYIn;
        midX = midX_l();
        midY = midY_l();
    }
    
    public Dimension_l(Dimension_l dimIn) {
        startX = dimIn.startX;
        startY = dimIn.startY;
        endX = dimIn.endX;
        endY = dimIn.endY;
        width = endX - startX;
        height = endY - startY;
        midX = midX_l();
        midY = midY_l();
    }
    
    public Dimension_l(IDimension<?> dimIn) {
        startX = dimIn.startX_l();
        startY = dimIn.startY_l();
        endX = dimIn.endX_l();
        endY = dimIn.endY_l();
        width = endX - startX;
        height = endY - startY;
        midX = midX_l();
        midY = midY_l();
    }
	
	//===========
	// Overrides
	//===========
	
    @Override public Long startX() { return startX; }
    @Override public Long startY() { return startY; }
    @Override public Long endX() { return endX; }
    @Override public Long endY() { return endY; }
    @Override public Long midX() { return midX; }
    @Override public Long midY() { return midY; }
    @Override public Long width() { return width; }
    @Override public Long height() { return height; }
	
	@Override public double startX_d() { return startX; }
	@Override public double startY_d() { return startY; }
	@Override public double endX_d() { return endX; }
	@Override public double endY_d() { return endY; }
	@Override public double midX_d() { return midX_l(); }
	@Override public double midY_d() { return midY_l(); }
	@Override public double width_d() { return width; }
	@Override public double height_d() { return height; }
	
	@Override public float startX_f() { return startX; }
	@Override public float startY_f() { return startY; }
	@Override public float endX_f() { return endX; }
	@Override public float endY_f() { return endY; }
	@Override public float midX_f() { return midX_l(); }
	@Override public float midY_f() { return midY_l(); }
	@Override public float width_f() { return width; }
	@Override public float height_f() { return height; }
	
	@Override public int startX_i() { return (int) startX; }
	@Override public int startY_i() { return (int) startY; }
	@Override public int endX_i() { return (int) endX; }
    @Override public int endY_i() { return (int) endY; }
    @Override public int midX_i() { return (int) midX_l(); }
    @Override public int midY_i() { return (int) midX_l(); }
    @Override public int width_i() { return (int) width; }
    @Override public int height_i() { return (int) height; }
	
	@Override public long startX_l() { return startX; }
	@Override public long startY_l() { return startY; }
	@Override public long endX_l() { return endX; }
	@Override public long endY_l() { return endY; }
	@Override public long midX_l() { return startX + (width >> 1); }
	@Override public long midY_l() { return startY + (height >> 1); }
	@Override public long width_l() { return width; }
	@Override public long height_l() { return height; }
	
    /**
     * Returns the 2D area that this dimension occupies.
     * @since 1.6.1
     */
    @Override public Long getArea() { return getArea_l(); }
    @Override protected int getArea_i() { return (int) getArea_l(); }
    @Override protected long getArea_l() { return ((endX - startX) * (endY - startY)); }
    @Override protected float getArea_f() { return getArea_l(); }
    @Override protected double getArea_d() { return getArea_l(); }
	
	@Override public void startX(Number startX) { this.startX = startX.longValue(); }
	@Override public void startY(Number startY) { this.startY = startY.longValue(); }
	@Override public void endX(Number endX) { this.endX = endX.longValue(); }
	@Override public void endY(Number endY) { this.endY = endY.longValue(); }
	@Override public void width(Number width) { this.width = width.longValue(); }
	@Override public void height(Number height) { this.height = height.longValue(); }
	
    @Override
    public String toString() {
        return "[startX/Y: " + startX + ", " + startY +
                "; endX/Y: " + endX + ", " + endY +
                "; width/Height: " + width + ", " + height + "]";
    }
	
	//=========
	// Methods
	//=========
	
	public Dimension_l move(long changeX, long changeY) {
		startX += changeX;
		startY += changeY;
		reDimension();
		return this;
	}
	
	public Dimension_l setPosition(long newX, long newY) {
		startX = newX;
		startY = newY;
		reDimension();
		return this;
	}
	
	public Dimension_l setWidth(long newWidth) {
		width = newWidth;
		reDimension();
		return this;
	}
	
	public Dimension_l setHeight(long newHeight) {
		height = newHeight;
		reDimension();
		return this;
	}
	
	@Override
	protected void reDimension() {
		endX = startX + width;
		endY = startY + height;
		midX = midX_l();
		midY = midY_l();
	}
	
	public Dimension_l expand(long amount) {
		Dimension_l d = new Dimension_l(this);
		d.startX -= amount;
		d.startY -= amount;
		d.endX += amount;
		d.endY += amount;
		d.width += (amount * 2);
		d.height += (amount * 2);
		return d;
	}
	
	public Dimension_l contract(long amount) {
		Dimension_l d = new Dimension_l(this);
		d.startX += amount;
		d.startY += amount;
		d.endX -= amount;
		d.endY -= amount;
		d.width -= (amount * 2);
		d.height -= (amount * 2);
		return d;
	}
	
	public Dimension_l moveDim(long x, long y) {
		startX += x;
		startY += y;
		endX = startX + width;
		endY = startY + height;
		return this;
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
    public Dimension_l add(long amount) {
        return new Dimension_l(startX - amount, startY - amount, endX + amount, endY + amount);
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
    public Dimension_l add(Number amount) {
        long l = amount.longValue();
        return new Dimension_l(startX - l, startY - l, endX + l, endY + l);
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
    public Dimension_l sub(long amount) {
        return new Dimension_l(startX + amount, startY + amount, endX - amount, endY - amount);
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
    public Dimension_l sub(Number amount) {
        long l = amount.longValue();
        return new Dimension_l(startX + l, startY + l, endX - l, endY - l);
    }
    
    public Dimension_l translateHorizontal(long amount) {
        startX += amount;
        return this;
    }
    
    public Dimension_l translateVertical(long amount) {
        startY += amount;
        return this;
    }
    
    /**
     * Returns true if this dimension's bounds at least partially contains
     * the given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(IDimension<?> dimIn) {
        return startX <= dimIn.endX_l() &&
               startY <= dimIn.endY_l() &&
               endX >= dimIn.startX_l() &&
               endY >= dimIn.startY_l();
    }
    
    /**
     * Returns true if this dimension's bounds at least partially contains
     * the given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(Dimension_l dimIn) {
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
    public boolean fullyContains(Dimension_l dimIn) {
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
        return startX < dimIn.startX_l() &&
               startY < dimIn.startY_l() &&
               endX > dimIn.endX_l() &&
               endY > dimIn.endY_l();
    }
    
    /**
     * Returns true if the Area of this dimension is bigger than the area
     * of the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is bigger
     */
    public boolean isGreaterThan(Dimension_l dimIn) {
        return getArea_l() > dimIn.getArea_l();
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
        return getArea_l() > dimIn.getArea_l();
    }
    
    /**
     * Returns true if the Area of this dimension is less than the area of
     * the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is smaller
     */
    public boolean isLessThan(Dimension_l dimIn) {
        return getArea_l() < dimIn.getArea_l();
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
        return getArea_l() < dimIn.getArea_l();
    }
    
    public boolean isEqualTo(Dimension_l dimIn) {
        return startX == dimIn.startX &&
               startY == dimIn.startY &&
               width == dimIn.width &&
               height == dimIn.height;
    }
    
    @Override
    public boolean isEqualTo(IDimension<?> dimIn) {
        return startX == dimIn.startX_l() &&
               startY == dimIn.startY_l() &&
               width == dimIn.width_l() &&
               height == dimIn.height_l();
    }
    
    //================
    // Static Methods
    //================
    
    public static Dimension_l of(long startXIn, long startYIn, long endXIn, long endYIn) {
        return new Dimension_l(startXIn, startYIn, endXIn, endYIn);
    }
    
    public static Dimension_l of(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
        return new Dimension_l(startXIn, startYIn, endXIn, endYIn);
    }
    
    public static Dimension_l of(Dimension_l in) {
        return new Dimension_l(in.startX, in.startY, in.endX, in.endY);
    }
    
    public static Dimension_l of(IDimension<?> in) {
        return new Dimension_l(in.startX_i(), in.startY_i(), in.endX_i(), in.endY_i());
    }
}
