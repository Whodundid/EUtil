package eutil.math.dimensions;

/**
 * A datatype containing 2D object dimensions using integer precision.
 * 
 * @author Hunter Bragg
 * 
 * @since 1.0.0
 */
public class Dimension_i extends IDimension<Integer> {
    
    //========
    // Fields
    //========
    
    public int startX = 0, endX = 0;
    public int startY = 0, endY = 0;
    public int midX = 0, midY = 0;
    public int width = 0, height = 0;
    
    //==============
    // Constructors
    //==============
    
    public Dimension_i() {
        this(0, 0, 0, 0);
    }
    
    public Dimension_i(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
        this(startXIn.intValue(), startYIn.intValue(), endXIn.intValue(), endYIn.intValue());
    }
    
    public Dimension_i(int startXIn, int startYIn, int endXIn, int endYIn) {
        startX = startXIn;
        startY = startYIn;
        endX = endXIn;
        endY = endYIn;
        width = endXIn - startXIn;
        height = endYIn - startYIn;
        midX = midX_i();
        midY = midY_i();
    }
    
    public Dimension_i(Dimension_i dimIn) {
        startX = dimIn.startX;
        startY = dimIn.startY;
        endX = dimIn.endX;
        endY = dimIn.endY;
        width = endX - startX;
        height = endY - startY;
        midX = midX_i();
        midY = midY_i();
    }
    
    public Dimension_i(IDimension<?> dimIn) {
        startX = dimIn.startX_i();
        startY = dimIn.startY_i();
        endX = dimIn.endX_i();
        endY = dimIn.endY_i();
        width = endX - startX;
        height = endY - startY;
        midX = midX_i();
        midY = midY_i();
    }
    
    //===========
    // Overrides
    //===========
    
    @Override public Integer startX() { return startX; }
    @Override public Integer startY() { return startY; }
    @Override public Integer endX() { return endX; }
    @Override public Integer endY() { return endY; }
    @Override public Integer midX() { return midX; }
    @Override public Integer midY() { return midY; }
    @Override public Integer width() { return width; }
    @Override public Integer height() { return height; }
    
    @Override public int startX_i() { return startX; }
    @Override public int startY_i() { return startY; }
    @Override public int endX_i() { return endX; }
    @Override public int endY_i() { return endY; }
    @Override public int midX_i() { return startX + (width >> 1); }
    @Override public int midY_i() { return startY + (height >> 1); }
    @Override public int width_i() { return width; }
    @Override public int height_i() { return height; }
    
    @Override public long startX_l() { return startX; }
    @Override public long startY_l() { return startY; }
    @Override public long endX_l() { return endX; }
    @Override public long endY_l() { return endY; }
    @Override public long midX_l() { return midX_i(); }
    @Override public long midY_l() { return midX_i(); }
    @Override public long width_l() { return width; }
    @Override public long height_l() { return height; }
    
    @Override public float startX_f() { return startX; }
    @Override public float startY_f() { return startY; }
    @Override public float endX_f() { return endX; }
    @Override public float endY_f() { return endY; }
    @Override public float midX_f() { return midX_i(); }
    @Override public float midY_f() { return midY_i(); }
    @Override public float width_f() { return width; }
    @Override public float height_f() { return height; }
    
    @Override public double startX_d() { return startX; }
    @Override public double startY_d() { return startY; }
    @Override public double endX_d() { return endX; }
    @Override public double endY_d() { return endY; }
    @Override public double midX_d() { return midX_i(); }
    @Override public double midY_d() { return midY_i(); }
    @Override public double width_d() { return width; }
    @Override public double height_d() { return height; }
    
    /**
     * Returns the 2D area that this dimension occupies.
     * @since 1.6.1
     */
    @Override public Integer getArea() { return getArea_i(); }
    @Override protected int getArea_i() { return ((endX - startX) * (endY - startY)); }
    @Override protected long getArea_l() { return getArea_i(); }
    @Override protected float getArea_f() { return getArea_i(); }
    @Override protected double getArea_d() { return getArea_i(); }
    
    @Override public void startX(Number startX) { this.startX = startX.intValue(); }
    @Override public void startY(Number startY) { this.startY = startY.intValue(); }
    @Override public void endX(Number endX) { this.endX = endX.intValue(); }
    @Override public void endY(Number endY) { this.endY = endY.intValue(); }
    @Override public void width(Number width) { this.width = width.intValue(); }
    @Override public void height(Number height) { this.height = height.intValue(); }
    
    //=========
    // Methods
    //=========
    
    public Dimension_i move(int changeX, int changeY) {
        startX += changeX;
        startY += changeY;
        reDimension();
        return this;
    }
    
    public Dimension_i setPosition(int newX, int newY) {
        startX = newX;
        startY = newY;
        reDimension();
        return this;
    }
    
    public Dimension_i setWidth(int newWidth) {
        width = newWidth;
        reDimension();
        return this;
    }
    
    public Dimension_i setHeight(int newHeight) {
        height = newHeight;
        reDimension();
        return this;
    }
    
    @Override
    protected void reDimension() {
        endX = startX + width;
        endY = startY + height;
        midX = midX_i();
        midY = midY_i();
    }
    
    public Dimension_i expand(int amount) {
        Dimension_i d = new Dimension_i(this);
        d.startX -= amount;
        d.startY -= amount;
        d.endX += amount;
        d.endY += amount;
        d.width += (amount * 2);
        d.height += (amount * 2);
        return d;
    }
    
    public Dimension_i contract(int amount) {
        Dimension_i d = new Dimension_i(this);
        d.startX += amount;
        d.startY += amount;
        d.endX -= amount;
        d.endY -= amount;
        d.width -= (amount * 2);
        d.height -= (amount * 2);
        return d;
    }
    
    public Dimension_i moveDim(int x, int y) {
        Dimension_i d = new Dimension_i(this);
        d.startX += x;
        d.startY += y;
        d.endX = d.startX + d.width;
        d.endY = d.startY + d.height;
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
    public Dimension_i add(int amount) {
        return new Dimension_i(startX - amount, startY - amount, endX + amount, endY + amount);
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
    public Dimension_i add(Number amount) {
        int i = amount.intValue();
        return new Dimension_i(startX - i, startY - i, endX + i, endY + i);
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
    public Dimension_i sub(int amount) {
        return new Dimension_i(startX + amount, startY + amount, endX - amount, endY - amount);
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
    public Dimension_i sub(Number amount) {
        int i = amount.intValue();
        return new Dimension_i(startX + i, startY + i, endX - i, endY - i);
    }
    
    public Dimension_i translateHorizontal(int amount) {
        startX += amount;
        return this;
    }
    
    public Dimension_i translateVertical(int amount) {
        startY += amount;
        return this;
    }
    
    /**
     * Returns true if this dimension's bounds at least partially contains
     * the given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(IDimension<?> dimIn) {
        return startX <= dimIn.endX_i() &&
               startY <= dimIn.endY_i() &&
               endX >= dimIn.startX_i() &&
               endY >= dimIn.startY_i();
    }
    
    /**
     * Returns true if this dimension's bounds at least partially contains
     * the given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(Dimension_i dimIn) {
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
    public boolean fullyContains(Dimension_i dimIn) {
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
        return startX < dimIn.startX_i() &&
               startY < dimIn.startY_i() &&
               endX > dimIn.endX_i() &&
               endY > dimIn.endY_i();
    }
    
    /**
     * Returns true if the Area of this dimension is bigger than the area
     * of the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is bigger
     */
    public boolean isGreaterThan(Dimension_i dimIn) {
        return getArea_i() > dimIn.getArea_i();
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
        return getArea_i() > dimIn.getArea_i();
    }
    
    /**
     * Returns true if the Area of this dimension is less than the area of
     * the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is smaller
     */
    public boolean isLessThan(Dimension_i dimIn) {
        return getArea_i() < dimIn.getArea_i();
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
        return getArea_i() < dimIn.getArea_i();
    }
    
    public boolean isEqualTo(Dimension_i dimIn) {
        return startX == dimIn.startX &&
               startY == dimIn.startY &&
               width == dimIn.width &&
               height == dimIn.height;
    }
    
    @Override
    public boolean isEqualTo(IDimension<?> dimIn) {
        return startX == dimIn.startX_i() &&
               startY == dimIn.startY_i() &&
               width == dimIn.width_i() &&
               height == dimIn.height_i();
    }
    
    //================
    // Static Methods
    //================
    
    public static Dimension_i of(int startXIn, int startYIn, int endXIn, int endYIn) {
        return new Dimension_i(startXIn, startYIn, endXIn, endYIn);
    }
    
    public static Dimension_i of(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
        return new Dimension_i(startXIn, startYIn, endXIn, endYIn);
    }
    
    public static Dimension_i of(Dimension_i in) {
        return new Dimension_i(in.startX, in.startY, in.endX, in.endY);
    }
    
    public static Dimension_i of(IDimension<?> in) {
        return new Dimension_i(in.startX_i(), in.startY_i(), in.endX_i(), in.endY_i());
    }
    
}
