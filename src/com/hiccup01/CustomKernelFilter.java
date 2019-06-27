package com.hiccup01;

/**
 * This class filters an image using a static integer kernel given to it.
 * @author serverhiccups
 * @see Filter
 * @see BasicFilter
 */
public class CustomKernelFilter extends BasicFilter {
	public int[][] kernel = null; // Stores the kernel that we are using.
	public int maxKernelVal = 0;
	public int minKernelVal = 0;

	/**
	 * Creates a CustomKernelFilter.
	 * This function does some maths to figure out the minimum and maximum values the kernel can output so that the image will never have out of bounds values.
	 * @param kernel The int[][] array to create the CustomKernelFilter with.
	 */
	public CustomKernelFilter(int[][] kernel) {
		for (int i = 0; i < kernel.length; i++) {
			for (int j = 0; j < kernel[0].length; j++) {
				if (kernel[i][j] > 0) { // If the pixel adds to the output,
					maxKernelVal += kernel[i][j] * 255; // Increase the maximum value by the pixels's maximum value.
				}
				if (kernel[i][j] < 0) { // If the pixels takes away from the output,
					minKernelVal += kernel[i][j] * 255; // Decrease the minimum kernel value.
				}
			}
			this.kernel = kernel; // Store the kernel away.
		}
	}

	/**
	 * Given an array of pixels, this multiplies them by the kernel, adds them up, then squishes them inbounds.
	 * @param imageK The pixels to process. Should be the same size as the kernel processing it.
	 * @return A uColour with all the colour channels set to the output value.
	 * @throws FilterException Throw if the array passed in is the wrong size.
	 */
	public uColour applyKernel(uColour[][] imageK) throws FilterException {
		if(kernel.length != imageK.length || kernel[0].length != imageK.length) throw new FilterException();
		int totalValue = 0;
		for(int i = 0; i < imageK.length; i++) { // For each value passed in.
			for(int j = 0; j < imageK[0].length; j++) {
				if(imageK[i][j] == null) continue;
				totalValue += kernel[i][j] * imageK[i][j].getRed(); // Add it to the total.
			}
		}
		totalValue += minKernelVal * -1; // Stops the results form being centered around zero.
		totalValue /= ((maxKernelVal + (minKernelVal * -1)) / 255); // Normalise the values in between 0 and 255.
		return new uColour(totalValue, totalValue, totalValue);
	}

	/**
	 * Applies the custom kernel on the provided image.
	 * @return The filtered image.
	 */
	public uColour[][] filter() {
		uColour[][] newImage = new uColour[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				try {
					newImage[i][j] = applyKernel(super.getKernel(0, i, j, kernel.length)); // For each pixels of the image, apply kernel, and copy it to the output.
				} catch (FilterException e) { // This should never happen.
					System.err.println("You messed up the filtering");
					System.exit(1);
				}
			}
		}
		return newImage;
	}
}
