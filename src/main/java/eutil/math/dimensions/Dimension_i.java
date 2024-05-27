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
    public int halfWidth = 0, halfHeight = 0;
    
    //==============
    // Constructors
    //==============
    
    public Dimension_i() {
        this(0, 0, 0, 0);
    }
    
    public Dimension_i(Number startX, Number startY, Number endX, Number endY) {
        this(startX.intValue(), startY.intValue(), endX.intValue(), endY.intValue());
    }
    
    public Dimension_i(int startX, int startY, int endX, int endY) {
        setDimensions(startX, startY, endX, endY);
    }
    
    public Dimension_i(int[] dimensionArray) {
        setDimensions(dimensionArray);
    }
    
    public Dimension_i(Dimension_i dimIn) {
        setDimensions(dimIn.startX, dimIn.startY, dimIn.endX, dimIn.endY);
    }
    
    public Dimension_i(IDimension<?> dimIn) {
        setDimensions(dimIn.startX_i(), dimIn.startY_i(), dimIn.endX_i(), dimIn.endY_i());
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
    @Override public int midX_i() { return midX; }
    @Override public int midY_i() { return midX; }
    @Override public int width_i() { return width; }
    @Override public int height_i() { return height; }
    
    @Override public long startX_l() { return startX; }
    @Override public long startY_l() { return startY; }
    @Override public long endX_l() { return endX; }
    @Override public long endY_l() { return endY; }
    @Override public long midX_l() { return midX; }
    @Override public long midY_l() { return midX; }
    @Override public long width_l() { return width; }
    @Override public long height_l() { return height; }
    
    @Override public float startX_f() { return startX; }
    @Override public float startY_f() { return startY; }
    @Override public float endX_f() { return endX; }
    @Override public float endY_f() { return endY; }
    @Override public float midX_f() { return midX; }
    @Override public float midY_f() { return midY; }
    @Override public float width_f() { return width; }
    @Override public float height_f() { return height; }
    
    @Override public double startX_d() { return startX; }
    @Override public double startY_d() { return startY; }
    @Override public double endX_d() { return endX; }
    @Override public double endY_d() { return endY; }
    @Override public double midX_d() { return midX; }
    @Override public double midY_d() { return midY; }
    @Override public double width_d() { return width; }
    @Override public double height_d() { return height; }
    
    @Override public void startX(Number startX) { this.startX = startX.intValue(); }
    @Override public void startY(Number startY) { this.startY = startY.intValue(); }
    @Override public void endX(Number endX) { this.endX = endX.intValue(); }
    @Override public void endY(Number endY) { this.endY = endY.intValue(); }
    @Override public void width(Number width) { this.width = width.intValue(); }
    @Override public void height(Number height) { this.height = height.intValue(); }
    
    /**
     * Returns the 2D area that this dimension occupies.
     * @since 1.6.1
     */
    @Override public Integer getArea() { return getArea_i(); }
    @Override protected int getArea_i() { return ((endX - startX) * (endY - startY)); }
    @Override protected long getArea_l() { return getArea_i(); }
    @Override protected float getArea_f() { return getArea_i(); }
    @Override protected double getArea_d() { return getArea_i(); }
    
    //=========
    // Methods
    //=========
    
    public Dimension_i setArea(int newX, int newY, int newWidth, int newHeight) {
        startX = newX;
        startY = newY;
        width = newWidth;
        height = newHeight;
        endX = startX + width;
        endY = startY + height;
        halfWidth = width / 2;
        halfHeight = height / 2;
        midX = startX + halfWidth;
        midY = startY + halfHeight;
        return this;
    }
    
    public Dimension_i setPosition(int newX, int newY) {
        startX = newX;
        startY = newY;
        endX = startX + width;
        endY = startY + height;
        return this;
    }
    
    public Dimension_i setWidth(int newWidth) {
        width = newWidth;
        endX = startX + width;
        halfWidth = width / 2;
        midX = startX + halfWidth;
        return this;
    }
    
    public Dimension_i setHeight(int newHeight) {
        height = newHeight;
        endY = startY + height;
        halfHeight = height / 2;
        midY = startY + halfHeight;
        return this;
    }
    
    public Dimension_i setDimensions(int[] dimensionArray) {
        if (dimensionArray.length != 4) throw new IllegalArgumentException("The given dimension array length != 4");
        setDimensions(dimensionArray[0], dimensionArray[1], dimensionArray[2], dimensionArray[3]);
        return this;
    }
    
    public Dimension_i setDimensions(int startXIn, int startYIn, int endXIn, int endYIn) {
        startX = startXIn;
        startY = startYIn;
        endX = endXIn;
        endY = endYIn;
        width = endXIn - startXIn;
        height = endYIn - startYIn;
        halfWidth = width / 2;
        halfHeight = height / 2;
        midX = startX + halfWidth;
        midY = startY + halfHeight;
        return this;
    }
    
    @Override
    protected void reDimension() {
        endX = startX + width;
        endY = startY + height;
        halfWidth = width / 2;
        halfHeight = height / 2;
        midX = startX + halfWidth;
        midY = startY + halfHeight;
    }
    
    public Dimension_i expand(int amount) {
        startX -= amount;
        startY -= amount;
        endX += amount;
        endY += amount;
        width += (amount * 2);
        height += (amount * 2);
        halfWidth += amount;
        halfHeight += amount;
        return this;
    }
    
    public Dimension_i contract(int amount) {
        startX += amount;
        startY += amount;
        endX -= amount;
        endY -= amount;
        width -= (amount * 2);
        height -= (amount * 2);
        halfWidth -= amount;
        halfHeight -= amount;
        return this;
    }
    
    public Dimension_i translate(int changeX, int changeY) {
        startX += changeX;
        startY += changeY;
        endX += changeX;
        endY += changeY;
        return this;
    }
    
    public Dimension_i translateHorizontal(int amount) {
        startX += amount;
        endX += amount;
        return this;
    }
    
    public Dimension_i translateVertical(int amount) {
        startY += amount;
        endY += amount;
        return this;
    }
    
    /**
     * Expands this dimension outward in all directions by the given amount.
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
     * Expands this dimension outward in all directions by the given amount.
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
    
    /**
     * Returns true if this dimension's bounds at least partially contains the
     * given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(IDimension<?> dimIn) {
        return startX <= dimIn.endX_i() &&
               startY <= dimIn.endY_i() &&
               endX >= dimIn.startX_i() &&
               endY >= dimIn.startY_i();
    }
    
    /**
     * Returns true if this dimension's bounds at least partially contains the
     * given dimension's bounds.
     * @since 1.6.1
     */
    public boolean partiallyContains(Dimension_i dimIn) {
        return startX <= dimIn.endX && startY <= dimIn.endY && endX >= dimIn.startX && endY >= dimIn.startY;
    }
    
    /**
     * Returns true if the given dimension is completely inside of this
     * dimension. As in, not just partially inside.
     * 
     * @return True if the given dimension completely 'fits' inside of this one
     */
    public boolean fullyContains(Dimension_i dimIn) {
        return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY;
    }
    
    /**
     * Returns true if the given dimension is completely inside of this
     * dimension. As in, not just partially inside.
     * 
     * @return True if the given dimension completely 'fits' inside of this one
     */
    public boolean fullyContains(IDimension<?> dimIn) {
        return startX < dimIn.startX_i() && startY < dimIn.startY_i() && endX > dimIn.endX_i() && endY > dimIn.endY_i();
    }
    
    /**
     * Returns true if the Area of this dimension is bigger than the area of
     * the given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is bigger
     */
    public boolean isGreaterThan(Dimension_i dimIn) {
        return getArea_i() > dimIn.getArea_i();
    }
    
    /**
     * Returns true if the Area of this dimension is bigger than the area of
     * the given dimension.
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
     * Returns true if the Area of this dimension is less than the area of the
     * given dimension.
     * 
     * @param dimIn The dimension to compare against
     * 
     * @return True if this dimension is smaller
     */
    public boolean isLessThan(Dimension_i dimIn) {
        return getArea_i() < dimIn.getArea_i();
    }
    
    /**
     * Returns true if the Area of this dimension is less than the area of the
     * given dimension.
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
        return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height;
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
