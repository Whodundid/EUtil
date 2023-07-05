package eutil.file;

import static eutil.lambda.Predicates.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

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
		return FILE_EXISTS.test(f);
	}
	
	/**
	 * Returns true if the given file is not null and actually exists on the system.
	 * @since 2.5.0
	 */
	public static boolean fileExists(Path f) {
		return PATH_EXISTS.test(f);
	}
	
	/**
	 * Returns true if the given directory is empty.
	 * <p>
	 * NOTE: If the given file is null or does not actually exist,
	 * 
	 * @param f The directory to check
	 * @return True if empty
	 * @since 2.3.4
	 */
	public static boolean isDirectoryEmpty(File f) {
		if (f == null || !f.exists()) return false;
		return f.list().length == 0;
	}
	
	/**
	 * Returns true if the given directory is empty.
	 * <p>
	 * NOTE: If the given file is null or does not actually exist,
	 * 
	 * @param f The directory to check
	 * @return True if empty
	 * @since 2.5.0
	 */
	public static boolean isDirectoryEmpty(Path f) {
		if (f == null || !fileExists(f)) return false;
		return f.toFile().length() == 0;
	}
	
	/**
	 * Returns true if the given file is a symbolic link to another file.
	 * 
	 * @param f The file to check
	 * @return True if a symbolic link
	 * @since 2.3.4
	 */
	public static boolean isSymbolicLink(File f) {
		if (f == null) return false;
		return Files.isSymbolicLink(f.toPath());
	}
	
	/**
	 * Returns true if the given file is a symbolic link to another file.
	 * 
	 * @param f The file to check
	 * @return True if a symbolic link
	 * @since 2.5.0
	 */
	public static boolean isSymbolicLink(Path p) {
		if (p == null) return false;
		return Files.isSymbolicLink(p);
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
	
    /**
     * Returns true if each of the given files are not null and actually exist
     * on the file system.
     * 
     * @param files The files to check
     * @return True if all files are not null and exist
     * @since 2.5.2
     */
    public static boolean allFilesExist(Collection<File> files) {
        if (files == null || files.isEmpty()) return false;
        for (File f : files)
            if (!fileExists(f)) return false;
        return true;
    }
	
	/**
	 * Returns true if each of the given files are not null and actually exist
	 * on the file system.
	 * 
	 * @param paths The files to check
	 * @return True if all files are not null and exist
	 * @since 2.5.0
	 */
	public static boolean allPathsExist(Path... paths) {
		if (paths.length == 0) return false;
		for (Path p : paths)
			if (!fileExists(p)) return false;
		return true;
	}
	
	/**
     * Returns true if each of the given files are not null and actually exist
     * on the file system.
     * 
     * @param paths The files to check
     * @return True if all files are not null and exist
     * @since 2.5.2
     */
    public static boolean allPathsExist(Collection<Path> paths) {
        if (paths == null || paths.isEmpty()) return false;
        for (Path p : paths)
            if (!fileExists(p)) return false;
        return true;
    }
	
	//--------------
	// File Helpers
	//--------------
	
	/**
	 * Returns the extension String of the given file.
	 * 
	 * @param fileIn The file to parse
	 * @return The file's extension string
	 * @since 2.5.0
	 */
	public static String getFileExtension(File fileIn) {
		if (fileIn == null) return null;
		final String fname = fileIn.getName();
		final int index = fname.lastIndexOf('.');
		return (index > 0) ? fname.substring(index) : "";
	}
	
	/**
	 * Returns the extension String of the given path.
	 * 
	 * @param fileIn The file to parse
	 * @return The file's extension string
	 * @since 2.5.0
	 */
	public static String getFileExtension(Path pathIn) {
		if (pathIn == null) return null;
		final String fname = pathIn.toString();
		final int index = fname.lastIndexOf('.');
		return (index > 0) ? fname.substring(index) : "";
	}
	
	/**
	 * Compares the file extension of the given file to the set of incoming
	 * ones and returns true if any match.
	 * 
	 * @param fileIn     The file to check the extension of against
	 * @param extensions The extensions to compare
	 * 
	 * @return True if any of the given extensions match the extension of the
	 *         given file
	 * 
	 * @since 2.5.0
	 */
	public static boolean checkExtension(File fileIn, String... extensions) {
		if (fileIn == null) return false;
		return EUtil.anyMatch(getFileExtension(fileIn), extensions);
	}
	
	/**
	 * Compares the file extension of the given path to the set of incoming
	 * ones and returns true if any match.
	 * 
	 * @param pathIn     The path to check the extension of against
	 * @param extensions The extensions to compare
	 * 
	 * @return True if any of the given extensions match the extension of the
	 *         given path
	 * 
	 * @since 2.5.0
	 */
	public static boolean checkExtension(Path pathIn, String... extensions) {
		if (pathIn == null) return false;
		return EUtil.anyMatch(getFileExtension(pathIn), extensions);
	}
	
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
	 * Attempts to create a temporary file with the give name.
	 * 
	 * @param name The name of the temporary file to make
	 * @return The created file, if successfully made
	 * @since 2.3.4
	 */
	public static File createTempFile(String name) {
		try {
			var parts = name.split(".");
			var sub = (parts.length >= 1) ? parts[parts.length - 1] : null;
			return File.createTempFile(name, sub);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Attempts to create a temporary file with a random name.
	 * 
	 * @return The created file, if successfully made
	 * @since 2.3.4
	 */
	public static File createRandomTempFile() {
		return createTempFile(UUID.randomUUID().toString());
	}
	
	/**
	 * Attempts to create the given directory or clear it if it already exists.
	 * 
	 * @param dir The dir to create or clear
	 * @return True if successful
	 * @since 2.3.4
	 */
	public static boolean createOrClearDirectory(File dir) {
		if (dir == null) return false;
		if (!dir.exists()) return dir.mkdirs();
		return clearDirectory(dir);
	}
	
	/**
	 * Attempts to create the given directory or clear it if it already exists.
	 * 
	 * @param dir The dir to create or clear
	 * @return True if successful
	 * @since 2.5.0
	 */
	public static boolean createOrClearDirectory(Path dir) {
		if (dir == null) return false;
		if (Files.exists(dir)) {
			try {
				Files.createDirectories(dir);
			}
			catch (Exception e) {
				return false;
			}
		}
		return clearDirectory(dir);
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
	 * Attempts to find a matching file of the given name within the given
	 * 'startDir'.
	 * <p>
	 * NOTE: This method does not recursively search through the given
	 * 'startDir' only its top level elements.
	 * 
	 * @param startDir The directory to search in
	 * @param toFind The name of a file to find
	 * 
	 * @return The found File or null
	 * 
	 * @since 2.3.4
	 */
	public static File findFile(File startDir, String toFind) {
		return findFile(startDir, new File(toFind));
	}
	
	/**
	 * Attempts to find the 'toFind' file within the given 'startDir'.
	 * <p>
	 * NOTE: This method does not recursively search through the given
	 * 'startDir' only its top level elements.
	 * 
	 * @param startDir The directory to search in
	 * @param toFind   A file to find
	 * 
	 * @return The found File or null
	 * 
	 * @since 2.3.4
	 */
	public static File findFile(File startDir, File toFind) {
		// can't find anything if either is null
		if (startDir == null || toFind == null) return null;
		// check for self-find condition
		if (startDir.equals(toFind)) return startDir;
		// if the 'startDir' is not a dir, then this won't work either
		if (!startDir.isDirectory()) return null;
		
		final var contents = startDir.listFiles();
		final int size = contents.length;
		
		for (int i = 0; i < size; i++) {
			final File f = contents[i];
			
			// check equivalence based on file name
			if (f.getName().equals(toFind.getName())) return f;
		}
		
		return null;
	}
	
	/**
	 * Attempts to find a matching file of the given name within the given
	 * 'startDir' recursively.
	 * 
	 * @param startDir The directory to search in
	 * @param toFind The name of a file to find
	 * 
	 * @return The found File or null
	 * 
	 * @since 2.3.4
	 */
	public static File findFileRecursive(File startDir, String toFind) {
		return findFileRecursive(startDir, new File(toFind));
	}
	
	/**
	 * Attempts to find the 'toFind' file within the given 'startDir'
	 * recursively.
	 * 
	 * @param startDir The directory to search in
	 * @param toFind   A file to find
	 * 
	 * @return The found File or null
	 * 
	 * @since 2.3.4
	 */
	public static File findFileRecursive(File startDir, File toFind) {
		// can't find anything if either is null
		if (startDir == null || toFind == null) return null;
		// check for self-find condition
		if (startDir.equals(toFind)) return startDir;
		// if the 'startDir' is not a dir, then this won't work either
		if (!startDir.isDirectory()) return null;
		
		final var contents = startDir.listFiles();
		final int size = contents.length;
		
		for (int i = 0; i < size; i++) {
			final File f = contents[i];
			
			if (f.isDirectory()) {
				// check the directory for a match
				if (f.getName().equals(toFind.getName())) return f;
				// if no match, search recursively
				final File recursiveFind = findFileRecursive(f, toFind);
				
				// check if we've found the file
				if (recursiveFind != null) {
					return recursiveFind;
				}
			}
			// check equivalence based on file name
			else if (f.getName().equals(toFind.getName())) {
				return f;
			}
		}
		
		return null;
	}
	
	/**
	 * Attempts to find the 'toFind' file within the given 'startDir'.
	 * <p>
	 * NOTE: This method does not recursively search through the given
	 * 'startDir' only its top level elements.
	 * 
	 * @param startDir The directory to search in
	 * @param toFind   A file to find
	 * 
	 * @return The found File or null
	 * 
	 * @since 2.3.4
	 */
	public static File findFile(File startDir, Predicate<? super File> condition) {
		// can't find anything if either is null
		if (startDir == null || condition == null) return null;
		// if the startDir isn't actually a dir, check if it matches the given predicate
		if (!startDir.isDirectory() && !condition.test(startDir)) return null;
		
		final var contents = startDir.listFiles();
		final int size = contents.length;
		
		for (int i = 0; i < size; i++) {
			final File f = contents[i];
			
			// check if current file matches predicate
			if (condition.test(f)) return f;
		}
		
		return null;
	}
	
	/**
	 * Attempts to find the 'toFind' file within the given 'startDir' recursively.
	 * 
	 * @param startDir The directory to search in
	 * @param toFind   A file to find
	 * 
	 * @return The found File or null
	 * 
	 * @since 2.3.4
	 */
	public static File findFileRecursive(File startDir, Predicate<? super File> condition) {
		// can't find anything if either is null
		if (startDir == null || condition == null) return null;
		// if the startDir isn't actually a dir, check if it matches the given predicate
		if (!startDir.isDirectory() && !condition.test(startDir)) return null;
		
		final var contents = startDir.listFiles();
		final int size = contents.length;
		
		for (int i = 0; i < size; i++) {
			final File f = contents[i];
			
			if (f.isDirectory()) {
				// check the directory for a match
				if (condition.test(f)) return f;
				// if no match, search recursively
				final File recursiveFind = findFileRecursive(f, condition);
				
				// check if we've found the file
				if (recursiveFind != null) {
					return recursiveFind;
				}
			}
			// check equivalence based on file name
			else if (condition.test(f)) {
				return f;
			}
		}
		
		return null;
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
	 * @since 2.5.0
	 */
	public static boolean clearDirectory(Path dir) {
		if (!fileExists(dir)) return false;
		if (!dir.toFile().isDirectory()) return false;
		return clearDir_i(dir.toFile());
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
