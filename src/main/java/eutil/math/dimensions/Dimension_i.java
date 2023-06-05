package eutil.math.dimensions;

/**
 * A datatype containing 2D object dimensions using integer precision. 
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public non-sealed class Dimension_i implements IDimension {
	
	//========
	// Fields
	//========
	
	public long startX = 0, endX = 0;
	public long startY = 0, endY = 0;
	public long midX = 0, midY = 0;
	public long width = 0, height = 0;
	
	//==============
	// Constructors
	//==============
	
	public Dimension_i() { this(0, 0, 0, 0); }
	public Dimension_i(long startXIn, long startYIn, long endXIn, long endYIn) {
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
	
	//===========
	// Overrides
	//===========
	
	@Override public double startX_d() { return (double) startX; }
	@Override public double startY_d() { return (double) startY; }
	@Override public double endX_d() { return (double) endX; }
	@Override public double endY_d() { return (double) endY; }
	@Override public double midX_d() { return (double) midX_i(); }
	@Override public double midY_d() { return (double) midY_i(); }
	@Override public double width_d() { return (double) width; }
	@Override public double height_d() { return (double) height; }
	
	@Override public float startX_f() { return (float) startX; }
	@Override public float startY_f() { return (float) startY; }
	@Override public float endX_f() { return (float) endX; }
	@Override public float endY_f() { return (float) endY; }
	@Override public float midX_f() { return (float) midX_i(); }
	@Override public float midY_f() { return (float) midY_i(); }
	@Override public float width_f() { return (float) width; }
	@Override public float height_f() { return (float) height; }
	
	@Override public long startX_i() { return startX; }
	@Override public long startY_i() { return startY; }
	@Override public long endX_i() { return endX; }
	@Override public long endY_i() { return endY; }
	@Override public long midX_i() { return startX + (width >> 1); }
	@Override public long midY_i() { return startY + (height >> 1); }
	@Override public long width_i() { return width; }
	@Override public long height_i() { return height; }
	
	@Override public void startX(Number startX) { this.startX = startX.longValue(); }
	@Override public void startY(Number startY) { this.startY = startY.longValue(); }
	@Override public void endX(Number endX) { this.endX = endX.longValue(); }
	@Override public void endY(Number endY) { this.endY = endY.longValue(); }
	@Override public void width(Number width) { this.width = width.longValue(); }
	@Override public void height(Number height) { this.height = height.longValue(); }
	
	@Override
	public String toString() {
		return "[startX/Y: " + startX + ", " + startY + "; endX/Y: " + endX + ", " + endY + "; width/Height: " + width + ", " + height + "]";
	}
	
	//=========
	// Methods
	//=========
	
	public Dimension_i move(long changeX, long changeY) {
		startX += changeX;
		startY += changeY;
		reDimension();
		return this;
	}
	
	public Dimension_i setPosition(long newX, long newY) {
		startX = newX;
		startY = newY;
		reDimension();
		return this;
	}
	
	public Dimension_i setWidth(long newWidth) {
		width = newWidth;
		reDimension();
		return this;
	}
	
	public Dimension_i setHeight(long newHeight) {
		height = newHeight;
		reDimension();
		return this;
	}
	
	private void reDimension() {
		endX = startX + width;
		endY = startY + height;
		midX = midX_i();
		midY = midY_i();
	}
	
	public Dimension_i expand(long amount) {
		Dimension_i d = new Dimension_i(this);
		d.startX -= amount;
		d.startY -= amount;
		d.endX += amount;
		d.endY += amount;
		d.width += (amount * 2);
		d.height += (amount * 2);
		return d;
	}
	
	public Dimension_i contract(long amount) {
		Dimension_i d = new Dimension_i(this);
		d.startX += amount;
		d.startY += amount;
		d.endX -= amount;
		d.endY -= amount;
		d.width -= (amount * 2);
		d.height -= (amount * 2);
		return d;
	}
	
	public Dimension_i moveDim(long x, long y) {
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
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public Dimension_i add(Number amount) {
		long l = amount.longValue();
		return new Dimension_i(startX - l, startY - l, endX + l, endY + l);
	}
	
	/**
	 * Contracts this dimension inward by in all directions by the given
	 * amount.
	 * 
	 * @param amount The amount to contract inwards by
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public Dimension_i sub(Number amount) {
		long l = amount.longValue();
		return new Dimension_i(startX + l, startY + l, endX - l, endY - l);
	}
	
	public Dimension_i translateHorizontal(long amount) { startX += amount; return this; }
	public Dimension_i translateVertical(long amount) { startY += amount; return this; }
	
	/**
	 * Returns the 2D area that this dimension occupies.
	 * @since 1.6.1
	 */
	public long getArea() {
		return ((endX - startX) * (endY - startY));
	}
	
	/**
	 * Returns true if this dimension's bounds at least partially contains the
	 * given dimension's bounds.
	 * @since 1.6.1
	 */
	public boolean partiallyContains(Dimension_d dimIn) {
		return startX <= dimIn.endX &&
			   startY <= dimIn.endY &&
			   endX >= dimIn.startX &&
			   endY >= dimIn.startY;
	}
	/**
	 * Returns true if this dimension's bounds at least partially contains the
	 * given dimension's bounds.
	 * @since 1.6.1
	 */
	public boolean partiallyContains(Dimension_i dimIn) {
		return startX <= dimIn.endX &&
			   startY <= dimIn.endY &&
			   endX >= dimIn.startX &&
			   endY >= dimIn.startY;
	}
	
	/** Returns true if the given dimension is completely inside of this dimension. As in, not just partially inside. */
	public boolean fullyContains(Dimension_d dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	/** Returns true if the given dimension is completely inside of this dimension. As in, not just partially inside. */
	public boolean fullyContains(Dimension_i dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	
	public boolean isGreaterThan(Dimension_d dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	public boolean isLessThan(Dimension_d dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	public boolean isEqualTo(Dimension_d dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	
	public boolean isGreaterThan(Dimension_i dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	public boolean isLessThan(Dimension_i dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	public boolean isEqualTo(Dimension_i dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	
	public static Dimension_i of(long startXIn, long startYIn, long endXIn, long endYIn) { return new Dimension_i(startXIn, startYIn, endXIn, endYIn); }
	public static Dimension_i of(Dimension_i in) { return new Dimension_i(in); }
	public static Dimension_i of(Dimension_d in) { return new Dimension_i((long) in.startX, (long) in.startY, (long) in.endX, (long) in.endY); }
	
}
