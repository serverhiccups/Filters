package com.hiccup01;

import com.beust.jcommander.JCommander; // This is the library that I'm using to parse the commandline arguments

public class Main {

	public static String version = "1.0.0";

	public static void main(String[] args) {
		CommandFilter filter = new CommandFilter();
		JCommander jc = JCommander.newBuilder() // Build our argument parser
				.addObject(filter)
				.build();
		jc.parse(args); // Parse the arguments
		if(filter.help) { // If the user wants help, give it to them
			jc.usage();
		} else {
			filter.run(); // Run the filter (the arguments are set upon parsing)
		}
	}
}
