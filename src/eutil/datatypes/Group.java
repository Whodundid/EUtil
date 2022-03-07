package eutil.datatypes;

import eutil.EUtil;
import eutil.strings.StringUtil;

/**
 * A grouping of numerous values.
 * 
 * @author Hunter Bragg
 * @since 1.2.0
 * @param <E>
 */
public class Group<E> {
	
	public E[] data;
	
	public Group(E... vals) { set(vals); }
	
	@Override public String toString() { return "<" + StringUtil.toString(data, ", ") + ">"; }
	
	public int size() { return (data != null) ? data.length : 0; }
	
	public E get(int index) { return data[index]; }
	public E[] getAll() { return data; }
	
	public Group<E> set(int index, E val) { data[index] = val; return this; }
	public Group<E> set(E... vals) { data = EUtil.asArray(vals); return this; }
	
}
