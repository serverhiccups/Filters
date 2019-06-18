package com.hiccup01;

import com.beust.jcommander.JCommander;

public class Main {

	public static void main(String[] args) {
		CommandMain cm = new CommandMain();
		CommandFilter filter = new CommandFilter();
		JCommander jc = JCommander.newBuilder()
				.addObject(filter)
				.build();
		jc.parse(args);
		if(filter.help) {
			jc.usage();
		} else {
			filter.run();
		}
	}
}
