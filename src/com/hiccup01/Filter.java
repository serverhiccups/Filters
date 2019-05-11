package com.hiccup01;

import java.awt.*;

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
	void setData(Color[][][] image) throws FilterException;

	/**
	 * Filters the provided data
	 * @return The filtered image in array format.
	 * @throws FilterException Thrown if there is an issue filtering.
	 */
	Color[][] filter() throws FilterException;
}

