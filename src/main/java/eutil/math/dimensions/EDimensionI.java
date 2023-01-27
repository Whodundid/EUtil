package eutil.math.dimensions;

/**
 * A datatype containing 2D object dimensions using integer precision. 
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class EDimensionI {
	
	public long startX = 0, endX = 0;
	public long startY = 0, endY = 0;
	public long midX = 0, midY = 0;
	public long width = 0, height = 0;
	
	public EDimensionI() { this(0, 0, 0, 0); }
	public EDimensionI(long startXIn, long startYIn, long endXIn, long endYIn) {
		startX = startXIn;
		startY = startYIn;
		endX = endXIn;
		endY = endYIn;
		width = endXIn - startXIn;
		height = endYIn - startYIn;
		midX = getMidX();
		midY = getMidY();
	}
	
	public EDimensionI(EDimensionI dimIn) {
		startX = dimIn.startX;
		startY = dimIn.startY;
		endX = dimIn.endX;
		endY = dimIn.endY;
		width = endX - startX;
		height = endY - startY;
		midX = getMidX();
		midY = getMidY();
	}
	
	@Override
	public String toString() {
		return "[startX/Y: " + startX + ", " + startY + "; endX/Y: " + endX + ", " + endY + "; width/Height: " + width + ", " + height + "]";
	}
	
	public EDimensionI move(long changeX, long changeY) {
		startX += changeX;
		startY += changeY;
		reDimension();
		return this;
	}
	
	public EDimensionI setPosition(long newX, long newY) {
		startX = newX;
		startY = newY;
		reDimension();
		return this;
	}
	
	public EDimensionI setWidth(long newWidth) {
		width = newWidth;
		reDimension();
		return this;
	}
	
	public EDimensionI setHeight(long newHeight) {
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
	
	public EDimensionI expand(long amount) {
		EDimensionI d = new EDimensionI(this);
		d.startX -= amount;
		d.startY -= amount;
		d.endX += amount;
		d.endY += amount;
		d.width += (amount * 2);
		d.height += (amount * 2);
		return d;
	}
	
	public EDimensionI contract(long amount) {
		EDimensionI d = new EDimensionI(this);
		d.startX += amount;
		d.startY += amount;
		d.endX -= amount;
		d.endY -= amount;
		d.width -= (amount * 2);
		d.height -= (amount * 2);
		return d;
	}
	
	public EDimensionI moveDim(long x, long y) {
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
	public EDimensionI add(Number amount) {
		long l = amount.longValue();
		return new EDimensionI(startX - l, startY - l, endX + l, endY + l);
	}
	
	/**
	 * Contracts this dimension inward by in all directions by the given
	 * amount.
	 * 
	 * @param amount The amount to contract inwards by
	 * @return A modified dimension using this one as a starting point
	 * @since 1.5.1
	 */
	public EDimensionI sub(Number amount) {
		long l = amount.longValue();
		return new EDimensionI(startX + l, startY + l, endX - l, endY - l);
	}
	
	public EDimension toDouble() { return new EDimension(startX, startY, endX, endY); }
	
	public boolean contains(Number xIn, Number yIn) {
		long x = xIn.longValue();
		long y = yIn.longValue();
		return x >= startX && x <= endX && y >= startY && y <= endY;
	}
	
	public boolean contains(Number left, Number top, Number right, Number bot) {
		if (left.longValue() < right.longValue()) {
			Number t = left;
			left = right;
			right = t;
		}
		
		if (top.longValue() < bot.longValue()) {
			Number t = top;
			top = bot;
			bot = t;
		}
		
		return startX < left.longValue() && startY < top.longValue() &&
			   endX > right.longValue() && endY > bot.longValue();
	}
	
	public long getMidX() { return startX + (width / 2); }
	public long getMidY() { return startY + (height / 2); }
	
	public EDimensionI translateHorizontal(long amount) { startX += amount; return this; }
	public EDimensionI translateVertical(long amount) { startY += amount; return this; }
	
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
	public boolean partiallyContains(EDimensionI dimIn) {
		return startX <= dimIn.endX &&
			   startY <= dimIn.endY &&
			   endX >= dimIn.startX &&
			   endY >= dimIn.startY;
	}
	
	/** Returns true if the given dimension is completely inside of this dimension. As in, not just partially inside. */
	public boolean fullyContains(EDimension dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	/** Returns true if the given dimension is completely inside of this dimension. As in, not just partially inside. */
	public boolean fullyContains(EDimensionI dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	
	public boolean isGreaterThan(EDimension dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	public boolean isLessThan(EDimension dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	public boolean isEqualTo(EDimension dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	
	public boolean isGreaterThan(EDimensionI dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	public boolean isLessThan(EDimensionI dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	public boolean isEqualTo(EDimensionI dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	
	public static EDimensionI of(long startXIn, long startYIn, long endXIn, long endYIn) { return new EDimensionI(startXIn, startYIn, endXIn, endYIn); }
	public static EDimensionI of(EDimensionI in) { return new EDimensionI(in); }
	public static EDimensionI of(EDimension in) { return new EDimensionI((long) in.startX, (long) in.startY, (long) in.endX, (long) in.endY); }
	
}
