package miscUtil;

import storageUtil.EArrayList;

//Author: Hunter Bragg

public enum Direction {
	
	N("N", true),
	NE("NE", false),
	E("E", true),
	SE("SE", false),
	S("S", true),
	SW("SW", false),
	W("W", true),
	NW("NW", false),
	OUT("Unknown", false);
	
	private String direction = "";
	private boolean cardinal;
	
	Direction(String dir, boolean cardinalIn) {
		direction = dir;
		cardinal = cardinalIn;
	}
	
	public String getDir() { return direction; }
	
	public static Direction get(int ordinal) {
		return values()[ordinal];
	}
	
	public static EArrayList<Direction> getCardinals() { return new EArrayList(N, E, S, W); }
	public static EArrayList<Direction> getOrdinals() { return new EArrayList(NE, NW, SE, SW); }
	public static EArrayList<Direction> getDirections() { return new EArrayList(N, NE, E, NW, S, SE, W, SW); }
	
}
