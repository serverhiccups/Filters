package com.hiccup01;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.util.Arrays;

import static com.hiccup01.CommandFilter.filterNames; // This is so all the filter names are stored in one place.

/**
 * Makes sure the provided filter names are correct. Gets the filter names from CommandFilter
 * @see CommandFilter
 * @see BasicFilter
 * @see IParameterValidator
 */
public class FilterNameValidator implements IParameterValidator {
	@Override
	public void validate(String name, String value) throws ParameterException {
		if(!Arrays.asList(filterNames).contains(value)) {
			throw new ParameterException("Parameter " + name + " was not a valid filter, (found " + value + ")");
		}
	}
}
