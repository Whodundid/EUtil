package eutil.misc;

import java.util.List;

import eutil.datatypes.EArrayList;

public class EByteBuilder {
	
	private EArrayList<Byte> bytes;
	
	//--------------
	// Constructors
	//--------------
	
	public EByteBuilder() {
		bytes = new EArrayList<Byte>();
	}
	
	public EByteBuilder(Byte[] bytesIn) {
		bytes = new EArrayList<Byte>(bytesIn.length);
		for (Byte b : bytesIn) bytes.add(b);
	}
	
	public EByteBuilder(byte[] bytesIn) {
		bytes = new EArrayList<Byte>(bytesIn.length);
		for (byte b : bytesIn) bytes.add(b);
	}
	
	public EByteBuilder(String[] stringsIn) {
		bytes = new EArrayList<Byte>();
		for (String s : stringsIn) {
			var total = s.getBytes();
			bytes.ensureCapacity(bytes.size() + total.length);
			for (byte b : total)
				bytes.add(b);
		}
	}
	
	//---------
	// Methods
	//---------
	
	public EByteBuilder a(Byte b) { return append(b.byteValue()); }
	public EByteBuilder a(byte b) { return append(b); }
	public EByteBuilder append(Byte b) { return append(b.byteValue()); }
	public EByteBuilder append(byte b) {
		bytes.add(b);
		return this;
	}
	
	public void clear() {
		bytes.clear();
	}
	
	public void reverse() {
		bytes = bytes.reverse();
	}
	
	public byte[] toByteArray() {
		byte[] r = new byte[bytes.length()];
		for (int i = 0; i < bytes.length(); i++) {
			r[i] = bytes.get(i);
		}
		return r;
	}
	
	public List<Byte> toByteList() {
		return new EArrayList<Byte>(bytes);
	}
	
}
