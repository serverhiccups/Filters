package com.hiccup01;

import com.beust.jcommander.Parameter;

public class CommandMain {
	@Parameter(names = {"--help", "-h"}, help = true, description = "Display a help message")
    private boolean help;
}
