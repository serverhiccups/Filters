package com.hiccup01;

/**
 * A basic mean blur that averages all the pixels in the provided kernel radius.
 * @author serverhiccups
 * @see Filter
 * @see BasicFilter
 */
public class MeanBlur extends BasicFilter {
	int kernelSize = 3;

	/**
	 * Initialises MeanBlur with the default kernel size of 3.
	 */
	MeanBlur(){
	}

	/**
	 * Initialses MeanBlur with a custom kernel size.
	 * @param size The size for kernel. This must be odd.
	 */
	MeanBlur(int size) {
		kernelSize = size;
	}

	/**
	 * Takes a kernel (or any uColour[][] array) and averages all the pixels in it.
	 * @param kernel The uColour[][] array to average.
	 * @return The average of the provided pixels.
	 */
	private uColour average(uColour[][] kernel) {
		int r = 0, g = 0, b = 0;
		int numberOfPixels = 0;
		for(int i = 0; i < kernel.length; i++) {
			for(int j = 0; j < kernel[0].length; j++) {
				if(kernel[i][j] == null) continue;
				numberOfPixels++;
				r += kernel[i][j].getRed();
				g += kernel[i][j].getGreen();
				b += kernel[i][j].getBlue();
			}
		}
		if(numberOfPixels == 0) return uColour.BLACK;
		return new uColour(r/numberOfPixels, g/numberOfPixels, b/numberOfPixels);
	}

	/**
	 * Mean blurs the provided image.
	 * @return The blurred image.
	 * @throws FilterException Thrown if kernel size is even
	 */
	@Override
	public uColour[][] filter() throws FilterException {
		uColour[][] newImage = new uColour[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				newImage[i][j] = average(super.getKernel(0, i, j, kernelSize));
			}
		}

		return newImage;
	}
}
