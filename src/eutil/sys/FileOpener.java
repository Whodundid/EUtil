package eutil.sys;

import eutil.EUtil;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class FileOpener {
	
	public static void openFile(File file) {
		String s = file.getAbsolutePath();
		if (OSType.getOS() == OSType.MAC) {
			try {
				Runtime.getRuntime().exec(new String[] {"/usr/bin/open", s});
				return;
			}
			catch (IOException e) {
				EUtil.error("Couldn\'t open file", e);
			}
		}
		else if (OSType.getOS() == OSType.WINDOWS) {
			String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {s});
			String[] args = new String[] { s1 };

			try {
				Runtime.getRuntime().exec(args);
				return;
			}
			catch (IOException e) {
				//EUtil.error("Couldn\'t open file", e);
			}
		}

		boolean stillCantOpen = false;

		try {
			Class<?> oclass = Class.forName("java.awt.Desktop");
			Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
			oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { file.toURI() });
		}
		catch (Throwable e) {
			//EUtil.error("Couldn\'t open link", e);
			stillCantOpen = true;
		}

		if (stillCantOpen) {
			EUtil.info("Attempting to open '" + file + "' via system class!");
			try {
				Desktop.getDesktop().open(file);
			}
			catch (IOException e) {
				EUtil.error("Couldn\'t open link", e);
				//e.printStackTrace();
			}
			//Sys.openURL("file://" + s);
		}
	}
	
}
