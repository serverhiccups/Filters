package com.hiccup01;

import com.beust.jcommander.ParameterException;
import com.beust.jcommander.IParameterValidator;

public class ExposureValueValidator implements IParameterValidator {
	@Override
	public void validate(String name, String value) throws ParameterException {
		double n = Double.parseDouble(value);
		if(n < 0) {
			throw new ParameterException("Parameter " + name + " should be positive (found " + value + ")");
		}
	}
}
