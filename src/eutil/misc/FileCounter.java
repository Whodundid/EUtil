package eutil.misc;

import eutil.EUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileCounter {
	
	public static long countLines(File f) throws FileNotFoundException, IOException {
		if (EUtil.fileExists(f)) {
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				return reader.lines().count();
			}
		}
		return -1;
	}
	
	public static long countChars(File f) throws FileNotFoundException, IOException {
		if (EUtil.fileExists(f)) {
			int chars = 0;
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				while (reader.read() != -1) chars++;
			}
			return chars;
		}
		return -1;
	}
	
}
