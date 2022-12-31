package eutil.sys;

/**
 * An enum containing common operating system types.
 * 
 * @apiNote 1.6.5: added checks for current OS type
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
	
	/** Returns the current system's OS type. */
	public static OSType getSystemOS() {
		String s = System.getProperty("os.name").toLowerCase();
		
		if (s.contains("windows")) return OSType.WINDOWS;
		if (s.contains("mac")) return OSType.MAC;
		if (s.contains("sunos")) return OSType.SOLARIS;
		if (s.contains("nix") || s.contains("nux") || s.contains("aix")) return OSType.LINUX;
		
		return OSType.UNKNOWN;
	}
	
	/** Returns true if the current system's operating system is Windows. */
	public static boolean isWindows() { return getSystemOS() == OSType.WINDOWS; }
	/** Returns true if the current system's operating system is Mac OS. */
	public static boolean isMac() { return getSystemOS() == OSType.MAC; }
	/** Returns true if the current system's operating system is Solaris. */
	public static boolean isSolaris() { return getSystemOS() == OSType.SOLARIS; }
	/** Returns true if the current system's operating system is a type of LINUX distro. */
	public static boolean isLinux() { return getSystemOS() == OSType.LINUX; }
	
}
