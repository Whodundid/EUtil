package eutil.misc;

import eutil.sys.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class FileOpener {
	
	public static void openFile(File file) {
		String s = file.getAbsolutePath();
		if (OSType.getOS() == OSType.MAC) {
			try {
				//EnhancedMC.info(s);
				Runtime.getRuntime().exec(new String[] {"/usr/bin/open", s});
				return;
			}
			catch (IOException e) {
				//EnhancedMC.error("Couldn\'t open file", e);
			}
		}
		else if (OSType.getOS() == OSType.WINDOWS) {
			String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {s});

			try {
				Runtime.getRuntime().exec(s1);
				return;
			}
			catch (IOException e) {
				//EnhancedMC.error("Couldn\'t open file", e);
			}
		}

		boolean stillCantOpen = false;

		try {
			Class<?> oclass = Class.forName("java.awt.Desktop");
			Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
			oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { file.toURI() });
		}
		catch (Throwable e) {
			//EnhancedMC.error("Couldn\'t open link", e);
			stillCantOpen = true;
		}

		if (stillCantOpen) {
			//EnhancedMC.info("Opening via system class!");
			try {
				Desktop.getDesktop().open(file);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			//Sys.openURL("file://" + s);
		}
	}
	
}
