package com.hiccup01;

/**
 * An interface that describes the minimal amount of interface that Filters have to provide.
 * @see BasicFilter
 */
public interface Filter {
	/**
	 * Sets the data for a filter.
	 * @param image The array of images for the filter to use.
	 * @throws FilterException Thrown if the data is malformed for the filter in use.
	 */
	void setData(uColour[][][] image) throws FilterException;

	/**
	 * Filters the provided data
	 * @return The filtered image in array format.
	 * @throws FilterException Thrown if there is an issue filtering.
	 */
	uColour[][] filter() throws FilterException;
}

