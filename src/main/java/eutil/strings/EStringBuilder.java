package eutil.strings;

import java.util.stream.IntStream;

import eutil.datatypes.util.EList;

public class EStringBuilder implements Appendable, CharSequence {
	
	//--------
	// Fields
	//--------
	
	private StringBuilder sb;
	
	//--------------
	// Constructors
	//--------------
	
	public EStringBuilder() {
		sb = new StringBuilder();
	}
	
	public EStringBuilder(int capacity) {
		sb = new StringBuilder(capacity);
	}
	
	public EStringBuilder(String str) {
		sb = new StringBuilder(str);
	}
	
	public EStringBuilder(CharSequence seq) {
		this(seq.length() + 16);
	}
	
	/**
	 * Creates an EStringBuilder and appends each of the given arguments
	 * 'toString' variants.
	 * 
	 * @param arguments
	 * @return EStringBuilder
	 */
	public static EStringBuilder of(Object... arguments) {
		//assuming each argument 'could' be 20 characters long
		var esb = new EStringBuilder(arguments.length * 20);
		esb.print(arguments);
		return esb;
	}
	
	public static String ofR(Object... arguments) {
		return of(arguments).toString();
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	//-----------------
	// Wrapped Methods
	//-----------------
	
	public EStringBuilder append(Object obj) { return append(String.valueOf(obj)); }
	public EStringBuilder append(String str) { sb.append(str); return this; }
	public EStringBuilder append(StringBuffer sb) { sb.append(sb); return this; }
	public EStringBuilder append(CharSequence s) { sb.append(s); return this; }
	public EStringBuilder append(CharSequence s, int start, int end) { sb.append(s, start, end); return this; }
	public EStringBuilder append(char[] str) { sb.append(str); return this; }
	public EStringBuilder append(char[] str, int offset, int len) { sb.append(str, offset, len); return this; }
	public EStringBuilder append(boolean b) { sb.append(b); return this; }
	public EStringBuilder append(char c) { sb.append(c); return this; }
	public EStringBuilder append(int i) { sb.append(i); return this; }
	public EStringBuilder append(long lng) { sb.append(lng); return this; }
	public EStringBuilder append(float f) { sb.append(f); return this; }
	public EStringBuilder append(double d) { sb.append(d); return this; }
	public EStringBuilder appendCodePoint(int codePoint) { sb.appendCodePoint(codePoint); return this; }
	public EStringBuilder delete(int start, int end) { sb.delete(start, end); return this; }
	public EStringBuilder deleteCharAt(int index) { sb.deleteCharAt(index); return this; }
	public EStringBuilder replace(int start, int end, String str) { sb.replace(start, end, str); return this; }
	public EStringBuilder insert(int index, char[] str, int offset, int len) { sb.insert(index, str, offset, len); return this; }
	public EStringBuilder insert(int offset, Object obj) { sb.insert(offset, obj); return this; }
	public EStringBuilder insert(int offset, String str) { sb.insert(offset, str); return this; }
	public EStringBuilder insert(int offset, char[] str) { sb.insert(offset, str); return this; }
	public EStringBuilder insert(int dstOffset, CharSequence s) { sb.insert(dstOffset, s); return this; }
	public EStringBuilder insert(int dstOffset, CharSequence s, int start, int end) { sb.insert(dstOffset, s, start, end); return this; }
	public EStringBuilder insert(int offset, boolean b) { sb.insert(offset, b); return this; }
	public EStringBuilder insert(int offset, char c) { sb.insert(offset, c); return this; }
	public EStringBuilder insert(int offset, int i) { sb.insert(offset, i); return this; }
	public EStringBuilder insert(int offset, long l) { sb.insert(offset, l); return this; }
	public EStringBuilder insert(int offset, float f) { sb.insert(offset, f); return this; }
	public EStringBuilder insert(int offset, double d) { sb.insert(offset, d); return this; }
	public int indexOf(String str) { return sb.indexOf(str); }
	public int indexOf(String str, int fromIndex) { return sb.indexOf(str, fromIndex); }
	public int lastIndexOf(String str) { return sb.lastIndexOf(str); }
	public int lastIndexOf(String str, int fromIndex) { return sb.lastIndexOf(str, fromIndex); }
	public EStringBuilder reverse() { sb.reverse(); return this; }
	public int length() { return sb.length(); }
	public int capacity() { return sb.capacity(); }
	public void ensureCapacity(int minimumCapacity) { sb.ensureCapacity(minimumCapacity); }
	public void trimToSize() { sb.trimToSize(); }
	public void setLength(int newLength) { sb.setLength(newLength); }
	public char charAt(int index) { return sb.charAt(index); }
	public int codePointAt(int index) { return sb.codePointAt(index); }
	public int codePointBefore(int index) { return sb.codePointBefore(index); }
	public int codePointCount(int beginIndex, int endIndex) { return sb.codePointCount(beginIndex, endIndex); }
	public int offsetByCodePoints(int index, int codePointOffset) { return sb.offsetByCodePoints(index, codePointOffset); }
	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) { sb.getChars(srcBegin, srcEnd, dst, dstBegin); }
	public void setCharAt(int index, char ch) { sb.setCharAt(index, ch); }
	public String substring(int start) { return sb.substring(start); }
	public CharSequence subSequence(int start, int end) { return sb.subSequence(start, end); }
	public String substring(int start, int end) { return sb.substring(start, end); }
	public IntStream chars() { return sb.chars(); }
	public IntStream codePoints() { return sb.codePoints(); }
	public boolean isNotEmpty() { return !isEmpty(); }
	public boolean isNotBlank() { return !isBlank(); }
	
	public String[] splitA(String regex) { return sb.toString().split(regex); }
	public EList<String> split(String regex) { return EList.of(splitA(regex)); }
	
	//---------
	// Methods
	//---------
	
	/** Shorthand 'substring' method. */
	public String sub(int index) { return sb.substring(index); }
	/** Shorthand 'substring' method. */
	public String sub(int start, int end) { return sb.substring(start, end); }
	
	/** Shorthand 'reverse' method. */
	public EStringBuilder rev() { sb.reverse(); return this; }
	
	/** Shorthand 'length' method. */
	public int l() { return sb.length(); }
	
	/** Shorthand 'append' method. */
	public EStringBuilder a(Object o) { sb.append(o); return this; }
	/** Shorthand batch 'append' method. */
	public EStringBuilder a(Object... a) { for (var o : a) sb.append(o); return this; }
	
	/** Shorthand 'clear' method. */
	public String c() { return clear(); }
	public String clear() { String r = sb.toString(); sb = new StringBuilder(); return r; }
	public String clear(char c) { String r = sb.toString(); sb = new StringBuilder(String.valueOf(c)); return r; }
	public String clear(String in) { String r = sb.toString(); sb = new StringBuilder(in); return r; }
	public String clear(CharSequence in) { String r = sb.toString(); sb = new StringBuilder(in); return r; }
	
	public boolean contains(CharSequence in) { return sb.toString().contains(in); }
	
	public EStringBuilder trimRT() { sb = new StringBuilder(sb.toString().trim()); return this; }
	public String trim() { return trimRT().toString(); }
	public String trimClear() { String r = trim(); clear(); return r; }
	public boolean isBlank() { return toString().trim().isEmpty(); }
	
	public String tempAdd(char c) { return tempAdd(String.valueOf(c)); }
	public String tempAdd(CharSequence cs) { return new StringBuilder(toString()).append(cs).toString(); }
	public String tempAdd(String s) { return new StringBuilder(toString()).append(s).toString(); }
	
	public EStringBuilder print(Object... values) { for (var v : values) append(v); return this; }
	public EStringBuilder println(Object... values) { print(values); append("\n"); return this; }
	
	public EStringBuilder setSubstringRT(int start) { sb = new StringBuilder(sb.substring(start)); return this; }
	public EStringBuilder setSubstringRT(int start, int end) { sb = new StringBuilder(sb.substring(start, end)); return this; }
	public String setSubstring(int start) { return setSubstringRT(start).toString(); }
	public String setSubstring(int start, int end) { return setSubstringRT(start, end).toString(); }
	
	/** Returns the internally wrapped StringBuilder. */
	public StringBuilder getSB() { return sb; }
}
