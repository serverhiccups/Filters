package com.hiccup01;

import java.lang.*;

/**
 * A Filter that preforms a gaussian blur.
 * @author serverhiccups
 * @see com.hiccup01.Filter
 * @see BasicFilter
 */
public class GaussianFilter extends MeanBlur {
	/**
	 * Sigma is the measure of standard deviation.
	 */
	double sigma = 1;

	/**
	 * Initialises GaussianFilter with the default sigma value of 1.
	 */
	GaussianFilter() {
		super();
		sigma = 1;
	}

	/**
	 * Initialises GaussianFilter with the provided sigma value.
	 * @param sigma The value of sigma to use.
	 */
	GaussianFilter(double sigma) {
		super();
		this.sigma = sigma;
	}

	/**
	 * Takes the image and the multipliers and condenses them into a single colour. Doesn't let the colour channels bleed.
	 * @param kernel The image to use.
	 * @param multipliers The multipliers to use.
	 * @return The condensed uColour.
	 */
	public uColour average(uColour[][] kernel, double[][] multipliers) {
		double r = 0, g = 0, b = 0;
		double totalWeight = 0;
		for (int i = 0; i < kernel.length; i++) {
			for (int j = 0; j < kernel[0].length; j++) {
				if (kernel[i][j] == null) continue;
				totalWeight += multipliers[i][j];
				r += kernel[i][j].getRed() * multipliers[i][j];
				g += kernel[i][j].getGreen() * multipliers[i][j];
				b += kernel[i][j].getBlue() * multipliers[i][j];
			}
		}
		return new uColour((int)(r/totalWeight), (int)(g/totalWeight), (int)(b/totalWeight));
	}

	/**
	 * Generates a Gaussian kernel given a size and the standard deviation.
	 * Formula is from https://en.wikipedia.org/wiki/Gaussian_blur
	 * @param sigma The value of sigma to use.
	 * @param size The size of the outputted kernel.
	 * @return The Gaussian kernel.
	 */
	public double[][] generateMultipliers(double sigma, int size) {
		double gaussianConstant = 1/(2*Math.PI*Math.pow(sigma, 2)); // This part is constant, and calculating it once at the beginning makes this code faster.
		double[][] multipliers = new double[size][size];
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				int rx = x - (size / 2); // This is needed because the function uses values centered at 0, 0 and our for loop is not.
				int ry = y - (size / 2);
				multipliers[x][y] = gaussianConstant * Math.pow(Math.E, -1*((Math.pow(rx, 2) + Math.pow(ry, 2))/2*Math.pow(sigma, 2)));
			}
		}
		return multipliers;
	}

	/**
	 * Gaussian blurs the provided image.
	 * @return The blurred image.
	 * @throws FilterException Thrown if sigma is even.
	 */
	public uColour[][] filter() throws FilterException {
		uColour[][] newImage = new uColour[backingImages[0].length][backingImages[0][0].length];
		double[][] multipliers = generateMultipliers(1, (int)(3*sigma)); // Generates the multipliers to use.
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				try {
					newImage[i][j] = average(super.getKernel(0, i, j, (int)(3*sigma)), multipliers);
				} catch (FilterException e) { // This should never happen with the default interface.
					throw e;
				}
			}
		}
		return newImage;
	}

}
