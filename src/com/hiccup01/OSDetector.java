package com.hiccup01;

public class OSDetector {
	private static String OSname = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {
		return (OSname.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OSname.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OSname.indexOf("nix") >= 0 || OSname.indexOf("nux") >= 0);
	}
}
