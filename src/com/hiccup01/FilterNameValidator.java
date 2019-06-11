package com.hiccup01;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;

public class FilterNameValidator implements IParameterValidator {
	@Override
	public void validate(String name, String value) throws ParameterException {
		String[] filterNames = {"meanblur", "gaussianblur", "exposure", "grayscale", "sobelx", "sobely", "edgedetection"};
		if(!Arrays.asList(filterNames).contains(value)) {
			throw new ParameterException("Parameter " + name + " was not a valid filter, (found " + value + ")");
		}
	}
}
