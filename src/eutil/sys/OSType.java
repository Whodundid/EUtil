package eutil.sys;

/**
 * An enum containing common operating system types.
 * 
 * @author Hunter
 * @since 1.0.1
 */
public enum OSType {
	WINDOWS,
	MAC,
	SOLARIS,
	LINUX,
	UNKNOWN;
	
	/** Returns the OS type. */
	public static OSType getOS() {
		
		String s = System.getProperty("os.name").toLowerCase();
		
		if (s.contains("windows")) { return OSType.WINDOWS; }
		if (s.contains("mac")) { return OSType.MAC; }
		if (s.contains("sunos")) { return OSType.SOLARIS; }
		if (s.contains("nix") || s.contains("nux") || s.contains("aix")) { return OSType.LINUX; }
		
		return OSType.UNKNOWN;
	}
	
}
