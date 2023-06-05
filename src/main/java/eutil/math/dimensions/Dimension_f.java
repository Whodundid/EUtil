package eutil.math.dimensions;

/**
 * A datatype containing 2D object dimensions using float. 
 * 
 * @author Hunter Bragg
 * @since 1.9
 */
public non-sealed class Dimension_f implements IDimension {
	
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
		midX = getMidX();
		midY = getMidY();
	}
	
	public Dimension_f(Dimension_f dimIn) {
		startX = dimIn.startX;
		startY = dimIn.startY;
		endX = dimIn.endX;
		endY = dimIn.endY;
		width = endX - startX;
		height = endY - startY;
		midX = getMidX();
		midY = getMidY();
	}
	
	public Dimension_f(Dimension_d dimIn) {
		startX = (float) dimIn.startX;
		startY = (float) dimIn.startY;
		endX = (float) dimIn.endX;
		endY = (float) dimIn.endY;
		width = endX - startX;
		height = endY - startY;
		midX = getMidX();
		midY = getMidY();
	}
	
	public Dimension_f(Dimension_i dimIn) {
		startX = (float) dimIn.startX;
		startY = (float) dimIn.startY;
		endX = (float) dimIn.endX;
		endY = (float) dimIn.endY;
		width = endX - startX;
		height = endY - startY;
		midX = getMidX();
		midY = getMidY();
	}
	
	//===========
	// Overrides
	//===========
	
	@Override public double startX_d() { return (double) startX; }
	@Override public double startY_d() { return (double) startY; }
	@Override public double endX_d() { return (double) endX; }
	@Override public double endY_d() { return (double) endY; }
	@Override public double midX_d() { return (double) midX_f(); }
	@Override public double midY_d() { return (double) midY_f(); }
	@Override public double width_d() { return (double) width; }
	@Override public double height_d() { return (double) height; }
	
	@Override public float startX_f() { return startX; }
	@Override public float startY_f() { return startY; }
	@Override public float endX_f() { return endX; }
	@Override public float endY_f() { return endY; }
	@Override public float midX_f() { return startX + (width * 0.5f); }
	@Override public float midY_f() { return startY + (height * 0.5f); }
	@Override public float width_f() { return width; }
	@Override public float height_f() { return height; }
	
	@Override public long startX_i() { return (long) startX; }
	@Override public long startY_i() { return (long) startY; }
	@Override public long endX_i() { return (long) endX; }
	@Override public long endY_i() { return (long) endY; }
	@Override public long midX_i() { return (long) midX_f(); }
	@Override public long midY_i() { return (long) midX_f(); }
	@Override public long width_i() { return (long) width; }
	@Override public long height_i() { return (long) height; }
	
	@Override public void startX(Number startX) { this.startX = startX.floatValue(); }
	@Override public void startY(Number startY) { this.startY = startY.floatValue(); }
	@Override public void endX(Number endX) { this.endX = endX.floatValue(); }
	@Override public void endY(Number endY) { this.endY = endY.floatValue(); }
	@Override public void width(Number width) { this.width = width.floatValue(); }
	@Override public void height(Number height) { this.height = height.floatValue(); }
	
	@Override
	public String toString() {
		return "[startX/Y: " + startX + ", " + startY + "; endX/Y: " + endX + ", " + endY + "; width/Height: " + width + ", " + height + "]";
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
	
	private void reDimension() {
		endX = startX + width;
		endY = startY + height;
		midX = getMidX();
		midY = getMidY();
	}
	
	/**
	 * Expands this dimension outward in all directions by the given
	 * amount.
	 * 
	 * @param amount The amount to expand outwards by
	 * @return A modified dimension using this one as a starting point
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
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public Dimension_f add(Number amount) {
		float d = amount.floatValue();
		return new Dimension_f(startX - d, startY - d, endX + d, endY + d);
	}
	
	/**
	 * Contracts this dimension inward by in all directions by the given
	 * amount.
	 * 
	 * @param amount The amount to contract inwards by
	 * @return A modified dimension using this one as a starting point
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
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public Dimension_f sub(Number amount) {
		float d = amount.floatValue();
		return new Dimension_f(startX + d, startY + d, endX - d, endY - d);
	}
	
	/**
	 * Returns true if this dimension contains the given x and y points.
	 * 
	 * @param xIn
	 * @param yIn
	 * 
	 * @return true if the given x and y points are within this dimension
	 */
	public boolean contains(float xIn, float yIn) {
		return xIn >= startX && xIn <= endX && yIn >= startY && yIn <= endY;
	}
	
	/**
	 * Returns true if this dimension contains the given x and y points.
	 * 
	 * @param xIn
	 * @param yIn
	 * 
	 * @return true if the given x and y points are within this dimension
	 */
	public boolean contains(Number xIn, Number yIn) {
		float x = xIn.floatValue();
		float y = yIn.floatValue();
		return x >= startX && x <= endX && y >= startY && y <= endY;
	}
	
	public boolean contains(float left, float top, float right, float bot) {
		if (left < right) {
			float t = left;
			left = right;
			right = t;
		}
		
		if (top < bot) {
			float t = top;
			top = bot;
			bot = t;
		}
		
		return startX < left && startY < top && endX > right && endY > bot;
	}
	
	public boolean contains(Number left, Number top, Number right, Number bot) {
		if (left.floatValue() < right.floatValue()) {
			Number t = left;
			left = right;
			right = t;
		}
		
		if (top.floatValue() < bot.floatValue()) {
			Number t = top;
			top = bot;
			bot = t;
		}
		
		return startX < left.floatValue() && startY < top.floatValue() &&
			   endX > right.floatValue() && endY > bot.floatValue();
	}
	
	public float getStartX() { return startX; }
	public float getStartY() { return startY; }
	public float getEndX() { return endX; }
	public float getEndY() { return endY; }
	public float getMidX() { return startX + (width / 2.0f); }
	public float getMidY() { return startY + (height / 2.0f); }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	
	public Dimension_f translateHorizontal(float amount) { startX += amount; return this; }
	public Dimension_f translateVertical(float amount) { startY += amount; return this; }
	
	/**
	 * Returns the 2D area that this dimension occupies.
	 * @since 1.6.1
	 */
	public float getArea() {
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
	public boolean partiallyContains(Dimension_f dimIn) {
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
	public boolean fullyContains(Dimension_f dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	/** Returns true if the given dimension is completely inside of this dimension. As in, not just partially inside. */
	public boolean fullyContains(Dimension_i dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	
	public boolean isGreaterThan(Dimension_d dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	public boolean isGreaterThan(Dimension_f dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	public boolean isGreaterThan(Dimension_i dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	
	public boolean isLessThan(Dimension_d dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	public boolean isLessThan(Dimension_f dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	public boolean isLessThan(Dimension_i dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	
	public boolean isEqualTo(Dimension_d dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	public boolean isEqualTo(Dimension_f dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	public boolean isEqualTo(Dimension_i dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	
	//================
	// Static Methods
	//================
	
	public static Dimension_f of(float startXIn, float startYIn, float endXIn, float endYIn) {
		return new Dimension_f(startXIn, startYIn, endXIn, endYIn);
	}
	
	public static Dimension_f of(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
		return new Dimension_f(startXIn, startYIn, endXIn, endYIn);
	}
	
	public static Dimension_f of(Dimension_d in) { return new Dimension_f(in); }
	public static Dimension_f of(Dimension_i in) { return new Dimension_f(in); }
	public static Dimension_f of(Dimension_f in) { return new Dimension_f(in.startX, in.startY, in.endX, in.endY); }
	
}
