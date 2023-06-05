package eutil.math.dimensions;

/**
 * A datatype containing 2D object dimensions using double floating point precision. 
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public non-sealed class Dimension_d implements IDimension {
	
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
		this(0.0, 0.0, 0.0, 0.0);
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
	
	public Dimension_d(IDimension dimIn) {
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
	
	@Override public double startX_d() { return startX; }
	@Override public double startY_d() { return startY; }
	@Override public double endX_d() { return endX; }
	@Override public double endY_d() { return endY; }
	@Override public double midX_d() { return startX + (width * 0.5); }
	@Override public double midY_d() { return startY + (height * 0.5); }
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
	
	@Override public long startX_i() { return (long) startX; }
	@Override public long startY_i() { return (long) startY; }
	@Override public long endX_i() { return (long) endX; }
	@Override public long endY_i() { return (long) endY; }
	@Override public long midX_i() { return (long) midX_d(); }
	@Override public long midY_i() { return (long) midX_d(); }
	@Override public long width_i() { return (long) width; }
	@Override public long height_i() { return (long) height; }
	
	@Override public void startX(Number startX) { this.startX = startX.doubleValue(); }
	@Override public void startY(Number startY) { this.startY = startY.doubleValue(); }
	@Override public void endX(Number endX) { this.endX = endX.doubleValue(); }
	@Override public void endY(Number endY) { this.endY = endY.doubleValue(); }
	@Override public void width(Number width) { this.width = width.doubleValue(); }
	@Override public void height(Number height) { this.height = height.doubleValue(); }
	
	@Override
	public String toString() {
		return "[startX/Y: " + startX + ", " + startY + "; endX/Y: " + endX + ", " + endY + "; width/Height: " + width + ", " + height + "]";
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
	
	private void reDimension() {
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
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public Dimension_d add(final double amount) {
		double d = amount;
		return new Dimension_d(startX - d, startY - d, endX + d, endY + d);
	}
	
	/**
	 * Contracts this dimension inward by in all directions by the given
	 * amount.
	 * 
	 * @param amount The amount to contract inwards by
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public Dimension_d sub(Number amount) {
		double d = amount.doubleValue();
		return new Dimension_d(startX + d, startY + d, endX - d, endY - d);
	}
	
	public Dimension_i toLong() { return new Dimension_i((long) startX, (long) startY, (long) endX, (long) endY); }
	public Dimension_f toFloat() { return new Dimension_f((float) startX, (float) startY, (float) endX, (float) endY); }
	
	public boolean contains(IDimension dims) {
		return contains(dims.startX_d(), dims.startY_d(), dims.endX_d(), dims.endY_d());
	}
	
	public boolean contains(Number xIn, Number yIn) {
		double x = xIn.doubleValue();
		double y = yIn.doubleValue();
		return x >= startX && x <= endX && y >= startY && y <= endY;
	}
	
	public boolean contains(Number left, Number top, Number right, Number bot) {
		if (left.doubleValue() < right.doubleValue()) {
			Number t = left;
			left = right;
			right = t;
		}
		
		if (top.doubleValue() < bot.doubleValue()) {
			Number t = top;
			top = bot;
			bot = t;
		}
		
		return startX < left.doubleValue() && startY < top.doubleValue() &&
			   endX > right.doubleValue() && endY > bot.doubleValue();
	}
	
	public Dimension_d translateHorizontal(double amount) { startX += amount; return this; }
	public Dimension_d translateVertical(double amount) { startY += amount; return this; }
	
	/**
	 * Returns the 2D area that this dimension occupies.
	 * @since 1.6.1
	 */
	public double getArea() {
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
	
	//================
	// Static Methods
	//================
	
	public static Dimension_d of(double startXIn, double startYIn, double endXIn, double endYIn) {
		return new Dimension_d(startXIn, startYIn, endXIn, endYIn);
	}
	
	public static Dimension_d of(IDimension in) { return new Dimension_d(in); }
	
}
