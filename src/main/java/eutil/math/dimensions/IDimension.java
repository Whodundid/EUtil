package eutil.math.dimensions;

public sealed interface IDimension permits Dimension_d, Dimension_f, Dimension_i {
	
	//==============
	// Field Values
	//==============
	
	double startX_d();
	double startY_d();
	double endX_d();
	double endY_d();
	double midX_d();
	double midY_d();
	double width_d();
	double height_d();
	
	float startX_f();
	float startY_f();
	float endX_f();
	float endY_f();
	float midX_f();
	float midY_f();
	float width_f();
	float height_f();
	
	long startX_i();
	long startY_i();
	long endX_i();
	long endY_i();
	long midX_i();
	long midY_i();
	long width_i();
	long height_i();
	
	void startX(Number startX);
	void startY(Number startY);
	void endX(Number endX);
	void endY(Number endY);
	void width(Number width);
	void height(Number height);
	
	//=========
	// Methods
	//=========
	
	default boolean contains(Number xIn, Number yIn) {
		double x = xIn.doubleValue();
		double y = yIn.doubleValue();
		return x >= startX_d() && x <= endX_d() && y >= startY_d() && y <= endY_d();
	}
	
	default boolean contains(Number left, Number top, Number right, Number bot) {
		double leftD = left.doubleValue();
		double topD = top.doubleValue();
		double rightD = right.doubleValue();
		double botD = bot.doubleValue();
		
		if (leftD < rightD) {
			double t = leftD;
			leftD = rightD;
			rightD = t;
		}
		
		if (topD < botD) {
			double t = topD;
			topD = botD;
			botD = t;
		}
		
		return (startX_d() < leftD && startY_d() < topD) && (endX_d() > rightD && endY_d() > botD);
	}
	
	//=============
	// Conversions
	//=============
	
	default Dimension_d toDouble() { return new Dimension_d(startX_d(), startY_d(), endX_d(), endY_d()); }
	default Dimension_f toFloat() { return new Dimension_f(startX_f(), startY_f(), endX_f(), endY_f()); }
	default Dimension_i toLong() { return new Dimension_i(startX_i(), startY_i(), endX_i(), endY_i()); }
	
}
