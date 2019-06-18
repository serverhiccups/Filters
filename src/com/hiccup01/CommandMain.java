package com.hiccup01;

import com.beust.jcommander.Parameter;

public class CommandMain {
	@Parameter(names = {"--help", "-h"}, help = true, description = "Display a help message.", arity = 0)
    public boolean help = false;
}
