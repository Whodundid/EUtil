package eutil.math;

import java.util.List;

import eutil.datatypes.Box2;
import eutil.datatypes.BoxList;
import eutil.datatypes.EArrayList;
import eutil.datatypes.EList;
import eutil.datatypes.util.ENumType;

/**
 * The {@code Matrix} class is a data type representing a matrix structure with default {@code Double} values.
 * The {@code Matrix} class provides numerous functions for performing matrix operations both locally and statically.
 * 
 * <blockquote><pre>
 *     Matrix m = new Matrix(2, 2, 1.0, 2.0, 3.0, 4.0);
 *     
 *     			|1.0 2.0|
 *     			|3.0 4.0|
 * </pre></blockquote><p>
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class Matrix {
	
	BoxList<Integer, List<Number>> table = new BoxList<>();
	private int rSize = 0, cSize = 0;
	private boolean printCommas = false;
	private ENumType origType = ENumType.NULL;
	
	public Matrix() { this(0, 0, ENumType.DOUBLE); }
	public Matrix(Box2<Integer, Integer> dimIn) { this(dimIn, ENumType.DOUBLE); }
	public Matrix(Box2<Integer, Integer> dimIn, ENumType typeIn) { this(dimIn.getA(), dimIn.getB(), typeIn); }
	public Matrix(int rowSizeIn, int columnSizeIn) { this(rowSizeIn, columnSizeIn, ENumType.DOUBLE); }
	public Matrix(int rowSizeIn, int columnSizeIn, ENumType typeIn) {
		create(rowSizeIn, columnSizeIn, typeIn);
	}
	
	public Matrix(int rowSizeIn, int columnSizeIn, Number... vals) { this(rowSizeIn, columnSizeIn, ENumType.DOUBLE, vals); }
	public Matrix(int rowSizeIn, int columnSizeIn, ENumType typeIn, Number... vals) {
		create(rowSizeIn, columnSizeIn, typeIn);
		if (vals.length == rowSizeIn * columnSizeIn) {
			int q = 0;
			for (int j = 0; j < rSize; j++) {
				for (int i = 0; i < cSize; i++) {
					setVal(j, i, vals[q]);
					q++;
				}
			}
		}
	}
	
	public Matrix(int rowSizeIn, int columnSizeIn, List<Number> vals)  { this(rowSizeIn, columnSizeIn, ENumType.DOUBLE, vals); }
	public Matrix(int rowSizeIn, int columnSizeIn, ENumType typeIn, List<Number> vals) {
		create(rowSizeIn, columnSizeIn, typeIn);
		if (vals.size() == rowSizeIn * columnSizeIn) {
			int q = 0;
			for (int j = 0; j < rSize; j++) {
				for (int i = 0; i < cSize; i++) {
					setVal(j, i, vals.get(q));
					q++;
				}
			}
		}
	}
	
	public Matrix(Matrix matrixIn) { if (matrixIn != null) { setValues(matrixIn); } else { create(0, 0, ENumType.DOUBLE); } }
	
	//--------------
	// Base Methods	
	//--------------
	
	public boolean printsCommas() { return printCommas; }
	public Matrix setPrintCommas(boolean valIn) { printCommas = valIn; return this; }
	public int numRows() { return rSize; }
	public int numCols() { return cSize; }
	public Box2<Integer, Integer> getDimensions() { return new Box2<Integer, Integer>(rSize, cSize); }
	public ENumType getNumType() { return origType; }
	
	public Matrix draw() { return draw(""); }
	public Matrix draw(String title) { return draw(title, ""); }
	public Matrix draw(String title, String end) {
		String t = (title != null && !title.isEmpty()) ? title + "\n" : "";
		String e = (end != null && !end.isEmpty()) ? "\n" + end : "";
		System.out.println(t + this + e);
		return this;
	}
	
	public Matrix clear() {
		for (int i = 0; i < rSize; i++) {
			List<Number> r = getRow(i);
			for (int j = 0; j < r.size(); j++) r.set(j, 0);
		}
		return this;
	}
	
	/*
	public Matrix save(String fileName) { return save(new File(fileName), false); }
	public Matrix save(String fileName, boolean asPrint) { return save(new File(fileName), asPrint); }
	public Matrix save(File fileIn) { return save(fileIn, false); }
	public Matrix save(File fileIn, boolean asPrint) {
		try {
			PrintWriter w = new PrintWriter(fileIn);
			if (asPrint) { w.print(this); }
			else {
				w.print(rSize + " " + cSize + (table.isNotEmpty() ? " " : ""));
				toList().forEach(n -> w.print(n + " "));
			}
			w.close();
		}
		catch (Exception e) { e.printStackTrace(); }
		return this;
	}
	*/
	
	
	//-----------------------
	// Matrix Util Functions
	//-----------------------
	
	private void create(int rowSizeIn, int columnSizeIn) { create(rowSizeIn, columnSizeIn, ENumType.DOUBLE); }
	private void create(int rowSizeIn, int columnSizeIn, ENumType typeIn) {
		rSize = rowSizeIn;
		cSize = columnSizeIn;
		origType = typeIn;
		clearMatrix();
	}
	
	public double get(int x, int y) { return table.getBoxWithA(y).getB().get(x).doubleValue(); }
	
	protected Number getI(int rowPos, int colPos) {
		try {
			return rangeCheck(rowPos, colPos) ? table.getBoxWithA(rowPos).getB().get(colPos) : -1;
		}
		catch (Exception e) { return Double.NaN; }
	}
	
	public Matrix setVal(int rowPos, int colPos, Number valIn) {
		if (rangeCheck(rowPos, colPos)) {
			table.getBoxWithA(rowPos).getB().set(colPos, valIn);
		}
		return this;
	}
	
	public Matrix setValAsType(int rowPos, int colPos, Number valIn, ENumType typeIn) {
		if (rangeCheck(rowPos, colPos)) {
			switch (typeIn) {
			case BYTE: table.getBoxWithA(rowPos).getB().set(colPos, valIn.byteValue()); break;
			case SHORT: table.getBoxWithA(rowPos).getB().set(colPos, valIn.shortValue()); break;
			case INTEGER: table.getBoxWithA(rowPos).getB().set(colPos, valIn.intValue()); break;
			case LONG: table.getBoxWithA(rowPos).getB().set(colPos, valIn.longValue()); break;
			case FLOAT: table.getBoxWithA(rowPos).getB().set(colPos, valIn.floatValue()); break;
			case DOUBLE: table.getBoxWithA(rowPos).getB().set(colPos, valIn.doubleValue()); break;
			default: throw new NumberFormatException();
			}
		}
		return this;
	}
	
	public List<Number> getRow(int rowNumIn) {
		return rangeCheck(rowNumIn, 0) ? table.getBoxWithA(rowNumIn).getB() : null;
	}
	
	public List<Number> getColumn(int colNumIn) {
		if (rangeCheck(0, colNumIn)) {
			EArrayList<Number> l = new EArrayList(cSize);
			for (int i = 0; i < rSize; i++) {
				Box2<Integer, List<Number>> box = table.getBoxWithA(i);
				if (box != null && box.getB() != null && box.getB().get(colNumIn) != null) {
					l.add(i, box.getB().get(colNumIn));
				}
				else { l.set(i, Double.NaN); }
			}
			return l;
		}
		return null;
	}
	
	public Matrix setRow(int rowNumIn, Number... rowIn) { return setRow(rowNumIn, new EArrayList<Number>().addA(rowIn)); }
	public Matrix setRow(int rowNumIn, List<Number> rowIn) {
		if (rangeCheck(rowNumIn, 0)) {
			table.setBInBox(rowNumIn, rowIn);
		}
		return this;
	}
	
	public Matrix setCol(int columnNumIn, Number... rowIn) { return setColumn(columnNumIn, new EArrayList<Number>().addA(rowIn)); }
	public Matrix setCol(int columnNumIn, List<Number> columnIn) { return setColumn(columnNumIn, columnIn); }
	public Matrix setColumn(int columnNumIn, Number... rowIn) { return setColumn(columnNumIn, new EArrayList<Number>().addA(rowIn)); }
	public Matrix setColumn(int columnNumIn, List<Number> columnIn) {
		if (rangeCheck(0, columnNumIn)) {
			for (int i = 0; i < rSize; i++) {
				Box2<Integer, List<Number>> box = table.getBoxWithA(i);
				if (box != null) {
					box.getB().set(i, columnIn.get(i));
				}
			}
		}
		return this;
	}
	
	public Matrix setValues(Matrix matrixIn) {
		if (matrixIn != null) {
			rSize = matrixIn.numRows();
			cSize = matrixIn.numCols();
			origType = matrixIn.getNumType();
			for (int i = 0; i < rSize; i++) {
				table.add(i, matrixIn.getRow(i));
			}
		}
		return this;
	}
	
	public Matrix clearMatrix() {
		table.clear();
		for (int i = 0; i < rSize; i++) {
			EArrayList<Number> row = new EArrayList();
			table.add(i, row);
			for (int j = 0; j < cSize; j++) { row.add(0.0); }
		}
		return this;
	}
	
	private boolean rangeCheck(int rowPos, int colPos) {
		return (rowPos >= 0 && rowPos < rSize) && (colPos >= 0 && colPos < cSize);
	}
	
	public Matrix asByte() { return convertTo(ENumType.BYTE); }
	public Matrix asShort() { return convertTo(ENumType.SHORT); }
	public Matrix asInt() { return convertTo(ENumType.INTEGER); }
	public Matrix asLong() { return convertTo(ENumType.LONG); }
	public Matrix asFloat() { return convertTo(ENumType.FLOAT); }
	public Matrix asDouble() { return convertTo(ENumType.DOUBLE); }
	
	public Matrix convertTo(ENumType typeIn) {
		for (int i = 0; i < rSize; i++) {
			for (int j = 0; j < cSize; j++) {
				setValAsType(i, j, getI(i, j), typeIn);
			}
		}
		origType = typeIn;
		return this;
	}
	
	public List<Number> toList() {
		EList<Number> l = new EArrayList<>();
		for (int i = 0; i < rSize; i++) {
			for (int j = 0; j < cSize; j++) {
				l.add(getI(i, j));
			}
		}
		return l;
	}
	
	
	//math functions
	
	public Matrix scale(Number scaleFactor) {
		for (int i = 0; i < rSize; i++) {
			for (int j = 0; j < cSize; j++) {
				setValAsType(i, j, getI(i, j).doubleValue() * scaleFactor.doubleValue(), origType);
			}
		}
		return this;
	}
	
	public Matrix add(Matrix matrixIn) {
		if (compareDimensions(this, matrixIn)) {
			for (int i = 0; i < rSize; i++) {
				for (int j = 0; j < cSize; j++) {
					setValAsType(i, j, getI(i, j).doubleValue() + matrixIn.getI(i, j).doubleValue(), origType);
				}
			}
		}
		return this;
	}
	
	public Matrix subtract(Matrix matrixIn) {
		if (compareDimensions(this, matrixIn)) {
			for (int i = 0; i < rSize; i++) {
				for (int j = 0; j < cSize; j++) {
					setValAsType(i, j, getI(i, j).doubleValue() - matrixIn.getI(i, j).doubleValue(), origType);
				}
			}
		}
		return this;
	}
	
	public Matrix multiply(Matrix matrixIn) {
		if (multiplyCheck(this, matrixIn)) {
			Matrix m = new Matrix(rSize, matrixIn.numCols());
			for (int t = 0; t < matrixIn.numCols(); t++) {
				for (int i = 0; i < rSize; i++) {
					double sum = 0.0;
					for (int j = 0; j < cSize; j++) {
						sum += (getI(i, j).doubleValue() * matrixIn.getI(j, t).doubleValue());
					}
					m.setValAsType(i, t, sum, origType);
				}
			}
			return m;
		}
		return this;
	}
	
	public Matrix transpose() {
		List<List<Number>> columns = new EArrayList();
		for (int i = 0; i < cSize; i++) { columns.add(getColumn(i)); } //grab old columns
		
		create(cSize, rSize);
		for (int i = 0; i < rSize; i++) { setRow(i, columns.get(i)); } //set rows as old columns
		
		return this;
	}
	
	
	//static methods
	
	public static Matrix add(Matrix m1, Matrix m2) { return m1 != null ? (m2 != null ? m1.add(m2) : m1) : m2 != null ? m2 : null; }
	public static Matrix subtract(Matrix m1, Matrix m2) { return m1 != null ? (m2 != null ? m1.subtract(m2) : m1) : m2 != null ? m2 : null; }
	public static Matrix multiply(Matrix m1, Matrix m2) { return m1 != null ? (m2 != null ? m1.multiply(m2) : m1) : m2 != null ? m2 : null; }
	public static Matrix transpose(Matrix matrixIn) { return matrixIn != null ? matrixIn.transpose() : null; }
	
	public static List<Number> toList(Matrix matrixIn) {
		EList<Number> l = new EArrayList<>();
		for (int i = 0; i < matrixIn.numRows(); i++) {
			for (int j = 0; j < matrixIn.numCols(); j++) {
				l.add(matrixIn.getI(i, j));
			}
		}
		return l;
	}
	
	public static boolean compareDimensions(Matrix m1, Matrix m2) {
		 return m1 != null && m2 != null ? m1.getDimensions().compare(m2.getDimensions()) : false;
	}
	
	public static boolean multiplyCheck(Matrix m1, Matrix m2) {
		return m1 != null && m2 != null ? (m1.numCols() == m2.numRows()) : false;
	}
	
	
	//object overrides
	
	@Override
	public String toString() {
		String returnString = "";
		int longest = getLongest(toList());
		int long1 = getLongest(getColumn(0));
		
		for (int i = 0; i < rSize; i++) {
			returnString += "|";
			for (int j = 0; j < cSize; j++) {
				String val = String.valueOf(getI(i, j));
				int offset = j == 0 ? long1 - val.length() : longest - val.length();
				for (int p = 0; p < offset; p++) { returnString += " "; }
				
				returnString += getI(i, j) + (printCommas ? ", " : " ");
			}
			returnString = returnString.substring(0, returnString.length() >= (printCommas ? 2 : 1) ? returnString.length() - (printCommas ? 2 : 1) : returnString.length());
			returnString += "|\n";
		}
		
		returnString = returnString.substring(0, returnString.length() >= 1 ? returnString.length() - 1 : returnString.length());
		
		return returnString;
	}
	
	private int getLongest(List<Number> list) {
		int returnVal = 0;
		
		for (Number i : list) {
			String s = String.valueOf(i);
			if (s.length() > returnVal) { returnVal = s.length(); }
		}
		
		return returnVal;
	}
	
}
