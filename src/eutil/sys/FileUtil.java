package eutil.sys;

import eutil.EUtil;

import static eutil.lambda.Predicates.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

public class FileUtil {
	
	//-------------
	// File Checks
	//-------------
	
	/**
	 * Returns true if the given file is not null and actually exists on the system.
	 */
	public static boolean fileExists(File f) {
		return fileExists.test(f);
	}
	
	//--------------
	// File Helpers
	//--------------
	
	public static long countLines(File f) throws FileNotFoundException, IOException {
		if (fileExists(f)) {
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				return reader.lines().count();
			}
		}
		return -1;
	}
	
	public static long countChars(File f) throws FileNotFoundException, IOException {
		if (fileExists(f)) {
			int chars = 0;
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				while (reader.read() != -1) chars++;
			}
			return chars;
		}
		return -1;
	}
	
	//------------------
	// File Try-Helpers
	//------------------
	
	public static boolean tryFileCode(File fileIn, Runnable func) { return (fileExists(fileIn)) ? EUtil.tryCode(func) : false; }
	public static <R> R tryFileCodeR(File fileIn, Runnable func, R returnVal) { tryFileCode(fileIn, func); return returnVal; }
	public static <R> R tryFileCodeR(File fileIn, Runnable func, R ifPass, R ifFail) { return (tryFileCode(fileIn, func)) ? ifPass : ifFail; }
	public static <R> boolean tryFileCodeR(File fileIn, Function<Object, Boolean> func) { return (fileExists(fileIn)) ? func.apply(null) : false; }
	
}
