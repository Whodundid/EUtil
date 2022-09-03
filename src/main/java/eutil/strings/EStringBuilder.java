package eutil.strings;

import java.util.stream.IntStream;

public class EStringBuilder implements Appendable, CharSequence {
	
	//--------
	// Fields
	//--------
	
	private StringBuilder internalString;
	
	//--------------
	// Constructors
	//--------------
	
	public EStringBuilder() {
		internalString = new StringBuilder();
	}
	
	public EStringBuilder(int capacity) {
		internalString = new StringBuilder(capacity);
	}
	
	public EStringBuilder(String str) {
		internalString = new StringBuilder(str);
	}
	
	public EStringBuilder(CharSequence seq) {
		this(seq.length() + 16);
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public String toString() {
		return internalString.toString();
	}
	
	//-----------------
	// Wrapped Methods
	//-----------------
	
	public EStringBuilder append(Object obj) { return append(String.valueOf(obj)); }
	public EStringBuilder append(String str) { internalString.append(str); return this; }
	public EStringBuilder append(StringBuffer sb) { internalString.append(sb); return this; }
	public EStringBuilder append(CharSequence s) { internalString.append(s); return this; }
	public EStringBuilder append(CharSequence s, int start, int end) { internalString.append(s, start, end); return this; }
	public EStringBuilder append(char[] str) { internalString.append(str); return this; }
	public EStringBuilder append(char[] str, int offset, int len) { internalString.append(str, offset, len); return this; }
	public EStringBuilder append(boolean b) { internalString.append(b); return this; }
	public EStringBuilder append(char c) { internalString.append(c); return this; }
	public EStringBuilder append(int i) { internalString.append(i); return this; }
	public EStringBuilder append(long lng) { internalString.append(lng); return this; }
	public EStringBuilder append(float f) { internalString.append(f); return this; }
	public EStringBuilder append(double d) { internalString.append(d); return this; }
	public EStringBuilder appendCodePoint(int codePoint) { internalString.appendCodePoint(codePoint); return this; }
	public EStringBuilder delete(int start, int end) { internalString.delete(start, end); return this; }
	public EStringBuilder deleteCharAt(int index) { internalString.deleteCharAt(index); return this; }
	public EStringBuilder replace(int start, int end, String str) { internalString.replace(start, end, str); return this; }
	public EStringBuilder insert(int index, char[] str, int offset, int len) { internalString.insert(index, str, offset, len); return this; }
	public EStringBuilder insert(int offset, Object obj) { internalString.insert(offset, obj); return this; }
	public EStringBuilder insert(int offset, String str) { internalString.insert(offset, str); return this; }
	public EStringBuilder insert(int offset, char[] str) { internalString.insert(offset, str); return this; }
	public EStringBuilder insert(int dstOffset, CharSequence s) { internalString.insert(dstOffset, s); return this; }
	public EStringBuilder insert(int dstOffset, CharSequence s, int start, int end) { internalString.insert(dstOffset, s, start, end); return this; }
	public EStringBuilder insert(int offset, boolean b) { internalString.insert(offset, b); return this; }
	public EStringBuilder insert(int offset, char c) { internalString.insert(offset, c); return this; }
	public EStringBuilder insert(int offset, int i) { internalString.insert(offset, i); return this; }
	public EStringBuilder insert(int offset, long l) { internalString.insert(offset, l); return this; }
	public EStringBuilder insert(int offset, float f) { internalString.insert(offset, f); return this; }
	public EStringBuilder insert(int offset, double d) { internalString.insert(offset, d); return this; }
	public int indexOf(String str) { return internalString.indexOf(str); }
	public int indexOf(String str, int fromIndex) { return internalString.indexOf(str, fromIndex); }
	public int lastIndexOf(String str) { return internalString.lastIndexOf(str); }
	public int lastIndexOf(String str, int fromIndex) { return internalString.lastIndexOf(str, fromIndex); }
	public EStringBuilder reverse() { internalString.reverse(); return this; }
	public int length() { return internalString.length(); }
	public int capacity() { return internalString.capacity(); }
	public void ensureCapacity(int minimumCapacity) { internalString.ensureCapacity(minimumCapacity); }
	public void trimToSize() { internalString.trimToSize(); }
	public void setLength(int newLength) { internalString.setLength(newLength); }
	public char charAt(int index) { return internalString.charAt(index); }
	public int codePointAt(int index) { return internalString.codePointAt(index); }
	public int codePointBefore(int index) { return internalString.codePointBefore(index); }
	public int codePointCount(int beginIndex, int endIndex) { return internalString.codePointCount(beginIndex, endIndex); }
	public int offsetByCodePoints(int index, int codePointOffset) { return internalString.offsetByCodePoints(index, codePointOffset); }
	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) { internalString.getChars(srcBegin, srcEnd, dst, dstBegin); }
	public void setCharAt(int index, char ch) { internalString.setCharAt(index, ch); }
	public String substring(int start) { return internalString.substring(start); }
	public CharSequence subSequence(int start, int end) { return internalString.subSequence(start, end); }
	public String substring(int start, int end) { return internalString.substring(start, end); }
	public IntStream chars() { return internalString.chars(); }
	public IntStream codePoints() { return internalString.codePoints(); }
	
	//---------
	// Methods
	//---------
	
	public EStringBuilder clear() { internalString = new StringBuilder(); return this; }
	public EStringBuilder clear(char c) { internalString = new StringBuilder(String.valueOf(c)); return this; }
	public EStringBuilder clear(String in) { internalString = new StringBuilder(in); return this; }
	public EStringBuilder clear(CharSequence in) { internalString = new StringBuilder(in); return this; }
	
	public EStringBuilder trimRT() { internalString = new StringBuilder(internalString.toString().trim()); return this; }
	public String trim() { return trimRT().toString(); }
	public boolean isBlank() { return toString().trim().isEmpty(); }
	
	public String tempAdd(char c) { return tempAdd(String.valueOf(c)); }
	public String tempAdd(CharSequence cs) { return new StringBuilder(toString()).append(cs).toString(); }
	public String tempAdd(String s) { return new StringBuilder(toString()).append(s).toString(); }
	
	public EStringBuilder setSubstringRT(int start) { internalString = new StringBuilder(internalString.substring(start)); return this; }
	public EStringBuilder setSubstringRT(int start, int end) { internalString = new StringBuilder(internalString.substring(start, end)); return this; }
	public String setSubstring(int start) { return setSubstringRT(start).toString(); }
	public String setSubstring(int start, int end) { return setSubstringRT(start, end).toString(); }
	
}
