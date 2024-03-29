package eutil.datatypes;

import java.lang.reflect.Array;

/**
 * A datatype representing a 2D grid layout for some arbitrary object type. 
 * 
 * @author Hunter Bragg
 * @since 1.0.1
 */
public class Grid<E> {
	
	//--------
	// Fields
	//--------
	
	private int width, height;
	private E[][] data;
	private Class<E> type;
	
	//--------------
	// Constructors
	//--------------
	
	public Grid() {}
	public Grid(Class<E> typeIn) { type = typeIn; }
	public Grid(Class<E> typeIn, int widthIn, int heightIn) {
		width = widthIn;
		height = heightIn;
		type = typeIn;
		data = (E[][]) Array.newInstance(type, width, height);
	}
	
	//---------
	// Methods
	//---------
	
	public void build() {
		data = (E[][]) Array.newInstance(type, width, height);
	}
	
	/** Clears existing value data and sets each value to null. */
	public Grid<E> clear() {
		data = (E[][]) Array.newInstance(type, width, height);
		return this;
	}
	
	//---------
	// Getters
	//---------
	
	/** Returns the value at given x and y coords of array. */
	public E get(int xIn, int yIn) {
		return (inRange(this, xIn, yIn)) ? data[xIn][yIn] : null;
	}
	/** Returns the value at given x and y coords of array. Ignores range checking. */
	public E getFast(int xIn, int yIn) { return data[xIn][yIn]; }
	
	public E[] getRow(int rowNum) {
		return (rowNum >= 0 && rowNum < height) ? data[rowNum] : null;
	}
	public E[] getRowFast(int rowNum) { return data[rowNum]; }
	
	public E[] getCol(int colNum) {
		return (colNum >= 0 && colNum < width) ? getColFast(colNum) : null;
	}
	public E[] getColFast(int colNum) {
		E[] r = (E[]) Array.newInstance(type, height);
		for (int i = 0; i < height; i++) {
			r[i] = data[i][colNum];
		}
		return r;
	}
	
	public E[][] getRegion(int sX, int sY, int eX, int eY) {
		return (inRange(this, sX, sY, eX, eY)) ? getRegionFast(sX, sY, eX, eY) : null;
	}
	public E[][] getRegionFast(int sX, int sY, int eX, int eY) {
		E[][] r = (E[][]) Array.newInstance(type, eX - sX, eY - sY);
		for (int i = sX, x = 0; i < eX; i++, x++) {
			for (int j = sY, y = 0; j < eY; j++, y++) {
				 r[x][y] = data[i][j];
			}
		}
		return (E[][]) r;
	}
	
	/** Returns the width of tiles in this grid. */
	public int getWidth() { return width; }
	/** Returns the height of tiles in this grid. */
	public int getHeight() { return height; }
	
	public E[][] getData() { return data; }
	
	//---------
	// Setters
	//---------
	
	public void setWidth(int widthIn) { width = widthIn; }
	public void setHeight(int heightIn) { height = heightIn; }
	public void setType(Class<E> typeIn) { type = typeIn; }
	
	/** Sets the value at given x and y coords of array. */
	public void set(E value, int xIn, int yIn) {
		if (inRange(this, xIn, yIn)) data[xIn][yIn] = value;
	}
	/** Sets the value at given x and y coords of array. Ignores range checking. */
	public void setFast(E value, int xIn, int yIn) { data[xIn][yIn] = value; }
	
	/** Sets an entire row of values in this chunk. */
	public void setRow(E[] in, int rowNum) {
		if (rowNum >= 0 && rowNum < height) data[rowNum] = in;
	}
	/** Sets an entire row of values in this chunk. Ignores range checking. */
	public void setRowFast(E[] in, int rowNum) { data[rowNum] = in; }
	
	/** Sets an entire column of values in this chunk. */
	public void setCol(E[] in, int colNum) {
		if (colNum >= 0 && colNum < width) setColFast(in, colNum);
	}
	/** Sets an entire column of values in this chunk. Ignores range checking. */
	public void setColFast(E[] in, int colNum) {
		for (int i = 0; i < height; i++) data[i][colNum] = in[i];
	}
	
	public void setRegion(E[][] in, int sX, int sY, int eX, int eY) {
		if (inRange(this, sX, sY, eX, eY)) setRegionFast(in, sX, sY, eX, eY);
	}
	public void setRegionFast(E[][] in, int sX, int sY, int eX, int eY) {
		for (int i = sX, x = 0; i < eX; i++, x++) {
			for (int j = sY, y = 0; j < eY; j++, y++) {
				data[i][j] = in[x][y];
			}
		}
	}
	
	//----------------
	// Static Methods
	//----------------
	
	/** Returns true if the given x and y coords are within the bounds for the grid's width and height. */
	public static boolean inRange(Grid g, int xIn, int yIn) {
		return (xIn >= 0 && xIn < g.width) && (yIn >= 0 && yIn < g.height);
	}
	
	public static boolean inRange(Grid g, int sX, int sY, int eX, int eY) {
		return (sX >= 0 && sX <= eX && eX < g.width) && (sY >= 0 && sY <= eY && eY < g.height);
	}
	
}
