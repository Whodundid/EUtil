package eutil.misc;

import java.io.IOException;
import java.io.OutputStream;

import eutil.datatypes.EArrayList;
import eutil.datatypes.util.EList;

/**
 * Similar to a StringBuilder but for bytes.
 * 
 * @author Hunter Bragg
 * @since 1.6.3
 */
public class EByteBuilder {
	
	private EList<Byte> bytes;
	
	//--------------
	// Constructors
	//--------------
	
	public EByteBuilder() {
		bytes = EList.newList();
	}
	
	public EByteBuilder(Byte[] bytesIn) {
		bytes = EList.newList(bytesIn);
	}
	
	public EByteBuilder(byte[] bytesIn) {
		bytes = new EArrayList<Byte>(bytesIn.length);
		for (byte b : bytesIn) bytes.add(b);
	}
	
	public EByteBuilder(String[] stringsIn) {
		bytes = EList.newList();
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
	public EByteBuilder append(byte b) { bytes.add(b); return this; }
	
	public EByteBuilder a(Byte[] bytes) { for (byte b : bytes) append(b); return this; }
	public EByteBuilder a(byte[] bytes) { for (byte b : bytes) append(b); return this; }
	public EByteBuilder append(Byte[] bytes) { for (byte b : bytes) append(b); return this; }
	public EByteBuilder append(byte[] bytes) { for (byte b : bytes) append(b); return this; }
	
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
	
	public EList<Byte> toByteList() {
		return EList.newList(bytes);
	}
	
	public void write(OutputStream stream) {
		try {
			stream.write(toByteArray());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeln(OutputStream stream) {
		write(stream);
		try { stream.write('\n'); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
}
