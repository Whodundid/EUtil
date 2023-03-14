package eutil.datatypes;

import java.util.List;

import eutil.datatypes.util.AnchorPoint;
import eutil.debug.Experimental;
import eutil.misc.Direction;
import eutil.strings.EStringBuilder;
import eutil.strings.EStringUtil;

@Experimental(since = "1.5.4")
public class ExpandableGrid<E> {
	
	//--------
	// Fields
	//--------
	
	private int width, height;
	private EArrayList<EArrayList<E>> grid;
	
	//--------------
	// Constructors
	//--------------
	
	public ExpandableGrid() {
		grid = new EArrayList<>();
	}
	
	public ExpandableGrid(int widthIn, int heightIn) {
		width = widthIn;
		height = heightIn;
		grid = new EArrayList<>(height);
		for (int i = 0; i < height; i++) {
			var row = new EArrayList<E>(width);
			for (int j = 0; j < width; j++) row.add((E) null);
			grid.add(row);
		}
	}
	
	public ExpandableGrid(int widthIn, int heightIn, E defaultValue) {
		width = widthIn;
		height = heightIn;
		grid = new EArrayList<>(height);
		for (int i = 0; i < height; i++) {
			var row = new EArrayList<E>(width);
			for (int j = 0; j < width; j++) row.add(defaultValue);
			grid.add(row);
		}
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public String toString() {
		var sb = new EStringBuilder();
		
		if (grid.isEmpty()) return "Empty";
		
		int longest = 0;
		
		for (int i = 0; i < height; i++) {
			int rowLongest = EStringUtil.getLongestLength(grid.get(i));
			if (rowLongest > longest) longest = rowLongest;
		}
		
		for (int i = 0; i < height; i++) {
			sb.a("|");
			for (int j = 0; j < width; j++) {
				var val = get(j, i);
				String str = String.valueOf(get(j, i));
				int offset = longest - str.length();
				sb.a(EStringUtil.repeatString(" ", offset));
				sb.print(val, (j + 1 < width) ? ", " : "");
			}
			sb.println("|");
		}
		
		return sb.toString();
	}
	
	//---------
	// Methods
	//---------
	
	public void expand(AnchorPoint anchor, int amount) { expand(anchor, amount, null); }
	public void expand(AnchorPoint anchor, int amount, E defaultVal) {
		switch (anchor) {
		case TOP_LEFT:
			expand(Direction.SE, amount, defaultVal);
			break;
		case TOP_MID:
			expand(Direction.LATITUDE, amount, defaultVal);
			expand(Direction.S, amount, defaultVal);
			break;
		case TOP_RIGHT:
			expand(Direction.SW, amount, defaultVal);
			break;
		case MID_LEFT:
			expand(Direction.LONGITUDE, amount, defaultVal);
			expand(Direction.E, amount, defaultVal);
			break;
		case MID:
			expand(Direction.OUT, amount, defaultVal);
			break;
		case MID_RIGHT:
			expand(Direction.LONGITUDE, amount, defaultVal);
			expand(Direction.W, amount, defaultVal);
			break;
		case BOT_LEFT:
			expand(Direction.NE, amount, defaultVal);
			break;
		case BOT_MID:
			expand(Direction.LATITUDE, amount, defaultVal);
			expand(Direction.N, amount, defaultVal);
			break;
		case BOT_RIGHT:
			expand(Direction.NW, amount, defaultVal);
			break;
		}
	}
	
	public void expand(Direction dir, int amount) { expand(dir, amount, null); }
	public void expand(Direction dir, int amount, E defaultVal) {
		switch (dir) {
		case N: changeSize(0     , amount, 0     , 0     , defaultVal); break;
		case E: changeSize(0     , 0     , amount, 0     , defaultVal); break;
		case S: changeSize(0     , 0     , 0     , amount, defaultVal); break;
		case W: changeSize(amount, 0     , 0     , 0     , defaultVal); break;
		
		case NE:
			expand(Direction.N, amount, defaultVal);
			expand(Direction.E, amount, defaultVal);
			break;
			
		case SE:
			expand(Direction.S, amount, defaultVal);
			expand(Direction.E, amount, defaultVal);
			break;
		
		case SW:
			expand(Direction.S, amount, defaultVal);
			expand(Direction.W, amount, defaultVal);
			break;
			
		case NW:
			expand(Direction.N, amount, defaultVal);
			expand(Direction.W, amount, defaultVal);
			break;
			
		case LONGITUDE:
			expand(Direction.N, amount, defaultVal);
			expand(Direction.S, amount, defaultVal);
			break;
			
		case LATITUDE:
			expand(Direction.E, amount, defaultVal);
			expand(Direction.W, amount, defaultVal);
			break;
			
		case OUT:
			expand(Direction.N, amount, defaultVal);
			expand(Direction.E, amount, defaultVal);
			expand(Direction.S, amount, defaultVal);
			expand(Direction.W, amount, defaultVal);
			break;
		}
	}
	
	/**
	 * Not exactly what I was going for, but it works if each action happens
	 * independently from one another.
	 * 
	 * @param left
	 * @param up
	 * @param right
	 * @param down
	 * @param defaultVal
	 */
	private void changeSize(int left, int up, int right, int down, E defaultVal) {
		//don't do anything if the dimensions are not changing
		if (left == 0 && up == 0 && right == 0 && down == 0) return;
		
		//System.out.println("LEFT=" + left + " UP=" + up + " RIGHT=" + right + " DOWN=" + down);
		
		int newHeight = height + up + down;
		int newWidth = width + left + right;
		
		//System.out.println("newHeight=" + newHeight + " newWidth=" + newWidth);
		
		//------
		// LEFT
		//------
		
		if (left < 0) {
			var absLeft = Math.abs(left);
			for (int i = 0; i < height; i++) {
				for (int j = 0; (j < absLeft); j++) {
					grid.get(i).remove(0);
				}
			}
		}
		else {
			for (int i = 0; i < height; i++) {
				for (int j = 0; (j < left); j++) {
					grid.get(i).add(0, defaultVal);
				}
			}
		}
		
		//----
		// UP
		//----
		
		if (up < 0) {
			var absUp = Math.abs(up);
			for (int i = 0; (i < absUp); i++) {
				grid.remove(0);
			}
		}
		else {
			for (int i = 0; i < up; i++) {
				var nr = newRow(newWidth, defaultVal);
				grid.add(0, nr);
			}
		}
		
		//-------
		// RIGHT
		//-------
		
		if (right < 0) {
			var absRight = Math.abs(right);
			for (int i = 0; i < height; i++) {
				for (int j = 0; (j < absRight); j++) {
					grid.get(i).removeLast();
				}
			}
		}
		else {
			for (int i = 0; i < height; i++) {
				for (int j = 0; (j < right); j++) {
					grid.get(i).add(defaultVal);
				}
			}
		}
		
		//------
		// DOWN
		//------
		
		if (down < 0) {
			var absDown = Math.abs(down);
			for (int i = 0; (i < absDown); i++) {
				grid.removeLast();
			}
		}
		else {
			for (int i = 0; i < down; i++) {
				grid.add(newRow(newWidth, defaultVal));
			}
		}
		
		//-----------------------
		// update new dimensions
		//-----------------------
		
		height = newHeight;
		width = newWidth;
	}
	
	private static <E> EArrayList<E> newRow(int width, E defaultValue) {
		EArrayList<E> r = new EArrayList<>(width);
		for (int i = 0; i < width; i++) r.add(defaultValue);
		return r;
	}
	
	public void fill(E object) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid.get(i).set(j, object);
			}
		}
	}
	
	public void clear() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				set(null, j, i);
			}
		}
	}
	
	//---------
	// Getters
	//---------
	
	public E get(int x, int y) {
		return grid.get(y).get(x);
	}
	
	/** Gets an entire row of values in this Grid. */
	public EArrayList<E> getRow(int rowNum) {
		return new EArrayList<>(grid.get(rowNum));
	}
	
	/** Gets an entire column of values in this Grid. */
	public EArrayList<E> getCol(int colNum) {
		EArrayList<E> col = new EArrayList<>(height);
		for (int i = 0; i < height; i++) {
			col.add(grid.get(i).get(colNum));
		}
		return col;
	}
	
	/** Returns a sub-region of this grid. */
	public ExpandableGrid<E> getRegion(int sX, int sY, int eX, int eY) {
		ExpandableGrid<E> r = new ExpandableGrid<>(eX - sX, eY - sY);
		for (int i = sY, y = 0; i < eY; i++, y++) {
			for (int j = sX, x = 0; j < eX; j++, x++) {
				 r.set(get(i, j), y, x);
			}
		}
		return r;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public EArrayList<EArrayList<E>> getInternalList() { return grid; }
	
	//---------
	// Setters
	//---------
	
	public void set(E object, int x, int y) {
		grid.get(y).set(x, object);
	}
	
	/** Sets an entire row of values in this chunk. */
	public void setRow(List<E> in, int rowNum) {
		if (in.size() != width) return;
		if (rowNum >= 0 && rowNum < height) grid.set(rowNum, EArrayList.wrap(in));
	}
	
	/** Sets an entire column of values in this chunk. */
	public void setCol(List<E> in, int colNum) {
		if (in.size() != height) return;
		for (int i = 0; i < height; i++) grid.get(i).set(colNum, in.get(i));
	}
	
	/** Sets a sub-region of this grid. */
	public void setRegion(ExpandableGrid<E> in, int sX, int sY, int eX, int eY) {
		for (int i = sY, y = 0; i < eY; i++, y++) {
			for (int j = sX, x = 0; j < eX; j++, x++) {
				set(in.get(y, x), y, x);
			}
		}
	}
	
	//----------------
	// Static Methods
	//----------------
	
	/** Returns true if the given x and y coords are within the bounds for the grid's width and height. */
	public static boolean inRange(ExpandableGrid<?> g, int xIn, int yIn) {
		return (xIn >= 0 && xIn < g.width) && (yIn >= 0 && yIn < g.height);
	}
	
	/** Returns true if the given x and y coords are within the bounds for the grid's width and height. */
	public static boolean inRange(ExpandableGrid<?> g, int sX, int sY, int eX, int eY) {
		return (sX >= 0 && sX <= eX && eX < g.width) && (sY >= 0 && sY <= eY && eY < g.height);
	}
	
}
