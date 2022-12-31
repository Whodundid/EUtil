package eutil.math;

/**
 * A datatype containing 2D object dimensions using float. 
 * 
 * @author Hunter Bragg
 * @since 1.9
 */
public class EDimensionf {
	
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
	
	public EDimensionf() { this(0.0f, 0.0f, 0.0f, 0.0f); }
	public EDimensionf(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
		this(startXIn.floatValue(), startYIn.floatValue(), endXIn.floatValue(), endYIn.floatValue());
	}
	
	public EDimensionf(float startXIn, float startYIn, float endXIn, float endYIn) {
		startX = startXIn;
		startY = startYIn;
		endX = endXIn;
		endY = endYIn;
		width = endXIn - startXIn;
		height = endYIn - startYIn;
		midX = getMidX();
		midY = getMidY();
	}
	
	public EDimensionf(EDimensionf dimIn) {
		startX = dimIn.startX;
		startY = dimIn.startY;
		endX = dimIn.endX;
		endY = dimIn.endY;
		width = endX - startX;
		height = endY - startY;
		midX = getMidX();
		midY = getMidY();
	}
	
	public EDimensionf(EDimension dimIn) {
		startX = (float) dimIn.startX;
		startY = (float) dimIn.startY;
		endX = (float) dimIn.endX;
		endY = (float) dimIn.endY;
		width = endX - startX;
		height = endY - startY;
		midX = getMidX();
		midY = getMidY();
	}
	
	public EDimensionf(EDimensionI dimIn) {
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
	
	@Override
	public String toString() {
		return "[startX/Y: " + startX + ", " + startY + "; endX/Y: " + endX + ", " + endY + "; width/Height: " + width + ", " + height + "]";
	}
	
	//=========
	// Methods
	//=========
	
	public EDimensionf move(float changeX, float changeY) {
		startX += changeX;
		startY += changeY;
		reDimension();
		return this;
	}
	
	public EDimensionf set(float newX, float newY, float newWidth, float newHeight) {
		startX = newX;
		startY = newY;
		width = newWidth;
		height = newHeight;
		reDimension();
		return this;
	}
	
	public EDimensionf setPosition(float newX, float newY) {
		startX = newX;
		startY = newY;
		reDimension();
		return this;
	}
	
	public EDimensionf setWidth(float newWidth) {
		width = newWidth;
		reDimension();
		return this;
	}
	
	public EDimensionf setHeight(float newHeight) {
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
	public EDimensionf add(float amount) {
		return new EDimensionf(startX - amount, startY - amount, endX + amount, endY + amount);
	}
	
	/**
	 * Expands this dimension outward in all directions by the given
	 * amount.
	 * 
	 * @param amount The amount to expand outwards by
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public EDimensionf add(Number amount) {
		float d = amount.floatValue();
		return new EDimensionf(startX - d, startY - d, endX + d, endY + d);
	}
	
	/**
	 * Contracts this dimension inward by in all directions by the given
	 * amount.
	 * 
	 * @param amount The amount to contract inwards by
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public EDimensionf sub(float amount) {
		return new EDimensionf(startX + amount, startY + amount, endX - amount, endY - amount);
	}
	
	/**
	 * Contracts this dimension inward by in all directions by the given
	 * amount.
	 * 
	 * @param amount The amount to contract inwards by
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public EDimensionf sub(Number amount) {
		float d = amount.floatValue();
		return new EDimensionf(startX + d, startY + d, endX - d, endY - d);
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
	
	public float getMidX() { return startX + (width / 2.0f); }
	public float getMidY() { return startY + (height / 2.0f); }
	
	public EDimensionf translateHorizontal(float amount) { startX += amount; return this; }
	public EDimensionf translateVertical(float amount) { startY += amount; return this; }
	
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
	public boolean partiallyContains(EDimension dimIn) {
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
	public boolean partiallyContains(EDimensionf dimIn) {
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
	public boolean partiallyContains(EDimensionI dimIn) {
		return startX <= dimIn.endX &&
			   startY <= dimIn.endY &&
			   endX >= dimIn.startX &&
			   endY >= dimIn.startY;
	}
	
	/** Returns true if the given dimension is completely inside of this dimension. As in, not just partially inside. */
	public boolean fullyContains(EDimension dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	/** Returns true if the given dimension is completely inside of this dimension. As in, not just partially inside. */
	public boolean fullyContains(EDimensionf dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	/** Returns true if the given dimension is completely inside of this dimension. As in, not just partially inside. */
	public boolean fullyContains(EDimensionI dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	
	public boolean isGreaterThan(EDimension dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	public boolean isGreaterThan(EDimensionf dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	public boolean isGreaterThan(EDimensionI dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	
	public boolean isLessThan(EDimension dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	public boolean isLessThan(EDimensionf dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	public boolean isLessThan(EDimensionI dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	
	public boolean isEqualTo(EDimension dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	public boolean isEqualTo(EDimensionf dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	public boolean isEqualTo(EDimensionI dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	
	//================
	// Static Methods
	//================
	
	public static EDimensionf of(float startXIn, float startYIn, float endXIn, float endYIn) {
		return new EDimensionf(startXIn, startYIn, endXIn, endYIn);
	}
	
	public static EDimensionf of(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
		return new EDimensionf(startXIn, startYIn, endXIn, endYIn);
	}
	
	public static EDimensionf of(EDimension in) { return new EDimensionf(in); }
	public static EDimensionf of(EDimensionI in) { return new EDimensionf(in); }
	public static EDimensionf of(EDimensionf in) { return new EDimensionf(in.startX, in.startY, in.endX, in.endY); }
	
}
