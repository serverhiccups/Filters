package com.hiccup01;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class KernelRadiusValidator implements IParameterValidator {
	@Override
	public void validate(String name, String value) throws ParameterException {
		int n = Integer.parseInt(value);
		if(n < 0) {
			throw new ParameterException("Parameter " + name + " should be positive (found " + value + ")");

		}
		if(n % 2 != 0) {
			throw new ParameterException("Parameter " + name + " should be odd (found " + value + ")");
		}
	}
}
