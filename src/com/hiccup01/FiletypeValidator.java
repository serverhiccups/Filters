package com.hiccup01;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;

public class FiletypeValidator implements IParameterValidator {
	@Override
	public void validate(String name, String value) throws ParameterException {
		value = value.toLowerCase();
		if(value.equals("jpeg")) {
			value = "jpg";
		}
		String[] types = {"png", "jpg"};
		if(!Arrays.asList(types).contains(value)) {
			throw new ParameterException("Parameter " + name + " was not a valid format (found " + value + ")");
		}
	}
}
