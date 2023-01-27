package eutil.file;

import static eutil.lambda.Predicates.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;

import eutil.EUtil;
import eutil.datatypes.util.EList;
import eutil.debug.PoorlyDocumented;
import eutil.random.ERandomUtil;

/**
 * A collection of functions that assist with common file operations.
 * 
 * @apiNote Previous name: 'FileUtil'
 * 
 * @author Hunter Bragg
 * @since 1.4.1
 */
public class EFileUtil {
	
	//------------------
	// Hide Constructor
	//------------------
	
	private EFileUtil() {}
	
	//-------------
	// File Checks
	//-------------
	
	/**
	 * Returns true if the given file is not null and actually exists on the system.
	 */
	public static boolean fileExists(File f) {
		return fileExists.test(f);
	}
	
	/**
	 * Returns true if each of the given files are not null and actually exist
	 * on the file system.
	 * 
	 * @param files The files to check
	 * @return True if all files are not null and exist
	 */
	public static boolean allFilesExist(File... files) {
		if (files.length == 0) return false;
		for (File f : files)
			if (!fileExists(f)) return false;
		return true;
	}
	
	//--------------
	// File Helpers
	//--------------
	
	/**
	 * Returns the JVM working directory File.
	 * 
	 * @return A File pointing to the JVM's working directory
	 * @since 1.6.5
	 */
	public static File userDir() {
		return new File(System.getProperty("user.dir"));
	}
	
	/**
	 * Returns a list of all of the non-directory files within the given
	 * directory. Note: This method does not extract files from internal
	 * directories it finds. Instead, it simply extracts the top-level
	 * files it finds directly within the given directory.
	 * 
	 * @param dir The directory to search through
	 * @return A list of all immediate files found
	 * @since 1.6.0
	 */
	public static EList<File> getAllFiles(File dir) {
		if (!fileExists(dir)) return null;
		if (!dir.isDirectory()) return null;
		
		try {
			EList<File> files = EList.newList();
			for (File f : dir.listFiles()) {
				if (f.isDirectory()) continue;
				files.add(f);
			}
			return files;
		}
		catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a flattened list of all files found under the given directory.
	 * 
	 * @param dir The directory to search through
	 * @return A list of all files found
	 * @since 1.6.0
	 */
	public static EList<File> getAllFilesRecursive(File dir) {
		if (!fileExists(dir)) return null;
		if (!dir.isDirectory()) return null;
		
		try {
			return getAllFiles_i(dir);
		}
		catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
	
	/** Recursively collects all files found within the given parent directory. */
	private static EList<File> getAllFiles_i(File dir) {
		EList<File> files = EList.newList();
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) files.addAll(getAllFiles_i(f));
			else files.add(f);
		}
		return files;
	}
	
	/**
	 * Recursively deletes all of the contents from a given directory as well
	 * as the given directory itself. True is returned on a successful deletion
	 * and false is returned if any error occurred during deletion.
	 * 
	 * @param dir The directory to be deleted
	 * @return True if successful
	 * @since 1.6.0
	 */
	public static boolean deleteDirectory(File dir) {
		if (!fileExists(dir)) return false;
		if (!dir.isDirectory()) return false;
		if (!clearDir_i(dir)) return false;
		return dir.delete();
	}
	
	/**
	 * Recursively deletes all of the internal files and directories from a
	 * given directory. If the given file is not a directory, no action is
	 * performed. This function returns true on a successful deletion or false
	 * if any error occurred while deleting.
	 * <p>
	 * NOTE: This function does not actually delete the parent directory itself.
	 * 
	 * @param dir The parent directory to be cleared
	 * @return True if successful
	 * @since 1.6.0
	 */
	public static boolean clearDirectory(File dir) {
		if (!fileExists(dir)) return false;
		if (!dir.isDirectory()) return false;
		return clearDir_i(dir);
	}
	
	/** Recursively deletes all of the internal files and directories from a parent dir. */
	private static boolean clearDir_i(File dir) {
		for (File f : dir.listFiles()) {
			if (f.isDirectory() && !clearDir_i(f)) return false;
			if (!f.delete()) return false;
		}
		
		return true;
	}
	
	/**
	 * Returns the total number of lines in the given file.
	 * Returns -1 if there was an issue with reading the file.
	 * 
	 * @param f The file to parse through
	 * @return The number of lines
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static long countLines(File f) throws FileNotFoundException, IOException {
		if (!fileExists(f)) return -1;
		
		return Files.lines(f.toPath()).count();
	}
	
	/**
	 * Returns the total number of characters in the given file.
	 * Returns -1 if there was an issue with reading the file.
	 * 
	 * @param f The file to parse through
	 * @return The number of characters
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static long countChars(File f) throws FileNotFoundException, IOException {
		if (!fileExists(f)) return -1;
		
		int chars = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
			while (reader.read() != -1) chars++;
		}
		catch (Throwable t) {
			return -1;
		}
		
		return chars;
	}
	
	/**
	 * Returns a random line from the given file.
	 * <p>
	 * If the file doesn't exist or an issue occurred while reading it, null
	 * will be returned instead.
	 * 
	 * @param fileIn The file to parse through
	 * 
	 * @return A random line from the given file
	 * 
	 * @since 2.1.0
	 */
	public static String randomLine(File fileIn) {
		return randomLine(fileIn, null);
	}
	
	/**
	 * Returns a random line from the given file.
	 * <p>
	 * If the file doesn't exist or an issue occurred while reading it, the
	 * given 'defaultVal' will be returned instead.
	 * 
	 * @param fileIn The file to parse through
	 * 
	 * @return A random line from the given file
	 * 
	 * @since 2.1.0
	 */
	public static String randomLine(File fileIn, String defaultVal) {
		if (!fileExists(fileIn)) return defaultVal;
		
		try {
			var p = fileIn.toPath();
			var count = Files.lines(p).count();
			var randIndex = ERandomUtil.getRoll(0L, count - 1);
			var randLine = Files.lines(p).skip(randIndex).findFirst().orElse(null);
			return randLine;
		}
		catch (IOException e) {
			return defaultVal;
		}
	}
	
	//------------------
	// File Try-Helpers
	//------------------
	
	@PoorlyDocumented
	public static boolean tryFileCode(File fileIn, Runnable func) {
		return (fileExists(fileIn)) ? EUtil.tryCode(func) : false;
	}
	
	@PoorlyDocumented
	public static <R> R tryFileCodeR(File fileIn, Runnable func, R returnVal) {
		tryFileCode(fileIn, func);
		return returnVal;
	}
	
	@PoorlyDocumented
	public static <R> R tryFileCodeR(File fileIn, Runnable func, R ifPass, R ifFail) {
		return (tryFileCode(fileIn, func)) ? ifPass : ifFail;
	}
	
	@PoorlyDocumented
	public static <R> boolean tryFileCodeR(File fileIn, Function<Object, Boolean> func) {
		return (fileExists(fileIn)) ? func.apply(null) : false;
	}
	
}
