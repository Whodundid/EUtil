package eutil.math;

/**
 * A datatype containing 2D object dimensions using double floating point precision. 
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class EDimension {
	
	public double startX = 0, endX = 0;
	public double startY = 0, endY = 0;
	public double midX = 0, midY = 0;
	public double width = 0, height = 0;
	
	public EDimension() { this(0, 0, 0, 0); }
	public EDimension(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
		this(startXIn.doubleValue(), startYIn.doubleValue(), endXIn.doubleValue(), endYIn.doubleValue());
	}
	
	public EDimension(double startXIn, double startYIn, double endXIn, double endYIn) {
		startX = startXIn;
		startY = startYIn;
		endX = endXIn;
		endY = endYIn;
		width = endXIn - startXIn;
		height = endYIn - startYIn;
		midX = getMidX();
		midY = getMidY();
	}
	
	public EDimension(EDimension dimIn) {
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
	
	public EDimension move(double changeX, double changeY) {
		startX += changeX;
		startY += changeY;
		reDimension();
		return this;
	}
	
	public EDimension setPosition(double newX, double newY) {
		startX = newX;
		startY = newY;
		reDimension();
		return this;
	}
	
	public EDimension setWidth(double newWidth) {
		width = newWidth;
		reDimension();
		return this;
	}
	
	public EDimension setHeight(double newHeight) {
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
	
	public EDimensionI toLong() { return new EDimensionI((long) startX, (long) startY, (long) endX, (long) endY); }
	
	public boolean contains(Number xIn, Number yIn) {
		double x = xIn.doubleValue();
		double y = yIn.doubleValue();
		return x >= startX && x <= endX && y >= startY && y <= endY;
	}
	
	public double getMidX() { return startX + (width / 2); }
	public double getMidY() { return startY + (height / 2); }
	
	public EDimension translateHorizontal(double amount) { startX += amount; return this; }
	public EDimension translateVertical(double amount) { startY += amount; return this; }
	
	public boolean contains(EDimension dimIn) { return startX <= dimIn.endX && startY <= dimIn.endY && endX >= dimIn.startX && endY >= dimIn.startY; }
	public boolean fullyContains(EDimension dimIn) { return startX < dimIn.startX && startY < dimIn.startY && endX > dimIn.endX && endY > dimIn.endY; }
	public boolean isGreaterThan(EDimension dimIn) { return startX > dimIn.startX && startY > dimIn.startY && width > dimIn.width && height > dimIn.height; }
	public boolean isLessThan(EDimension dimIn) { return startX < dimIn.startX && startY < dimIn.startY && width < dimIn.width && height < dimIn.height; }
	public boolean isEqualTo(EDimension dimIn) { return startX == dimIn.startX && startY == dimIn.startY && width == dimIn.width && height == dimIn.height; }
	
	public static EDimension of(Number startXIn, Number startYIn, Number endXIn, Number endYIn) {
		return new EDimension(startXIn, startYIn, endXIn, endYIn);
	}
	
}
