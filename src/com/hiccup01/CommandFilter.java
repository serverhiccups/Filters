package com.hiccup01;

import com.beust.jcommander.Parameter;
import java.util.*;

public class CommandFilter {
	@Parameter(description = "The file to filter. Use '-' to use stdin")
    private String file;

	@Parameter(names = "--filter",
			description = "The name of the filter to use. A list of filters can be obtained by running 'filters --listFilters'",
			validateWith = FilterNameValidator.class)
	private String filterName;

	@Parameter(names = "--radius",
			description = "For meanblur this is the radius of the kernel in pixels. For gaussian this is the value of sigma and the radius is adjusted accordingly.",
			validateWith = KernelRadiusValidator.class)
	private int radius;

	@Parameter(names = {"-o", "--output"}, description = "The file to output to. Leave blank for standard out")
	private String output;
}
