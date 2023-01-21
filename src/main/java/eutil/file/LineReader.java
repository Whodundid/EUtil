package eutil.file;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.stream.Stream;

import eutil.datatypes.util.EList;

/**
 * Creates an auto-closable stream that reads a specified file
 * line-by-line.
 * 
 * @author Hunter Bragg
 */
public class LineReader implements Closeable {
	
	private final BufferedReader reader;
	private long lineNum = 0;
	private boolean grabbed = false;
	private String grabbedLine;
	
	//--------------
	// Constructors
	//--------------
	
	public LineReader(String pathIn) throws IOException { this(new File(pathIn), Charset.forName("UTF8")); }
	public LineReader(File pathIn) throws IOException { this(pathIn, Charset.forName("UTF8")); }
	public LineReader(String pathIn, Charset charset) throws IOException { this(new File(pathIn), charset); }
	public LineReader(File pathIn, Charset charset) throws IOException {
		reader = new BufferedReader(new FileReader(pathIn, charset));
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public void close() throws IOException {
		if (reader != null) reader.close();
	}
	
	//---------
	// Methods
	//---------
	
	/**
	 * Returns true if this LineReader has another line to be read.
	 * The LineReader does not advance past any unread line.
	 * 
	 * @return true if and only if this LineReader has another line to read
	 * @throws IOException if any error occurs within the underlying BufferedReader
	 */
	public boolean hasNextLine() throws IOException {
		if (grabbed) return true;
		grabbedLine = reader.readLine();
		grabbed = grabbedLine != null;
		if (grabbed) lineNum++;
		return grabbed;
	}
	
	/**
	 * Returns the next unread line. If there are no line terminators ('\n' or '\r')
	 * within the input, the entire input is returned instead. If there is no input
	 * within the underlying BufferedReader, null is returned instead.
	 * 
	 * @return The next unread line (if there is one)
	 * @throws IOException if any error occurs within the underlying BufferedReader
	 */
	public String nextLine() throws IOException {
		// get the next line and increment line number
		if (hasNextLine()) grabbed = false;
		return grabbedLine;
	}
	
	public void mark(int readAheadLimit) throws IOException { reader.mark(readAheadLimit); }
	public void reset() throws IOException { reader.reset(); }
	
	public Stream<String> lines() { return reader.lines(); }
	public void forEach(Consumer<? super String> action) { reader.lines().forEach(action); }
	
	public EList<String> getAllLines() { return lines().collect(EList.toEList()); }
	
	//---------
	// Getters
	//---------
	
	/**
	 * Returns the current line's line number within the file.
	 * 
	 * @return The number of the current line
	 */
	public long getLineNum() {
		return lineNum;
	}
	
	/**
	 * Returns the total number of lines within this file.
	 * 
	 * @return The number of lines in the file
	 */
	public long getTotalLineNum() {
		return reader.lines().count();
	}
	
	//----------------
	// Static Methods
	//----------------
	
	/**
	 * Extracts the contents of the given file line-by-line and stores them
	 * within a single list.
	 * 
	 * @param fileIn The file to read from
	 * @return A list containing all of the lines from the given file
	 * @since 1.6.3
	 */
	public static EList<String> readAllLines(File fileIn) {
		try {
			var lr = new LineReader(fileIn);
			EList<String> lines = lr.getAllLines();
			lr.close();
			return lines;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
