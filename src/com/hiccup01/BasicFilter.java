package com.hiccup01;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * A class that exists to provide a reference Filter implementation and to provide helper functions to subclassing filters.
 * @author serverhiccups
 * @see Filter
 */
public class BasicFilter implements Filter {
	uColour[][][] backingImages = null;

	/**
	 * Sets the data to use.
	 * @param image The array of images for the filter to use.
	 * @throws FilterException Thrown if the data is malformed.
	 */
	public void setData(uColour[][][] image) throws FilterException {
		if(image.length != 1) throw new FilterException();
		if(image[0].length < 1) throw new FilterException();
		if(image[0][0].length < 1) throw new FilterException();
		backingImages = java.util.Arrays.copyOf(image, image.length);
	}

	/**
	 * Filters the provided data. Ideally, in subclassed methods this might do something useful, but right now it passes through the pixels untouched.
	 * @return The filtered image.
	 * @throws FilterException Thrown if no data is set.
	 */
	public uColour[][] filter() throws FilterException {
		if(backingImages == null) throw new NoDataSetException();
		uColour[][] newImage = new uColour[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < backingImages[0].length; i++) {
			for(int j = 0; j < backingImages[0][0].length; j++) {
				newImage[i][j] = backingImages[0][i][j];
			}
		}
		return newImage;
	}

	/**
	 * This is a helper method for subclasses that returns a uColour[][] array of the kernel around the position requested.
	 * Because the bounds-checking code here is guaranteed to work I would recommend using it.
	 * @param imageNumber The number of the backing image to request the kernel from.
	 * @param x The x position of the center of the kernel.
	 * @param y The y positino of teh the center of the kernel.
	 * @param size The size of the edge of the kernel in pixels. This must be an odd number.
	 * @return A uColour[][] array of the pixels in the requested kernel.
	 * @throws FilterException Thrown if size is even.
	 */
	public uColour[][] getKernel(int imageNumber, int x, int y, int size) throws FilterException {
		uColour[][] kernel = new uColour[size][size]; // Create a new array for our kernel to go into.
		if(size % 2 == 0) throw new FilterException(); // We can't have uncentered kernels.
		int ii = 0; // X index into our kernel
		for(int i = x - (size / 2); /* X index into our image */ i < x + 1 + (size / 2); i++) { // Do some maths to figure out where to iterate.
			int jj = 0; // Y index into our kernel
			for(int j = y - (size / 2); /* Y index into our image */ j < y + 1 + (size / 2); j++) {
				if(i < 0 || i >= backingImages[imageNumber].length || j < 0 || j >= backingImages[imageNumber][0].length) { // If a kernel pixel is out of bounds
					kernel[ii][jj] = null; // Make it null.
					continue;
				}
				kernel[ii][jj] = backingImages[imageNumber][i][j]; // Copy a pixel from the image to the kernel.
			jj++;
			}
			ii++;
		}
		return kernel;
	}
}
