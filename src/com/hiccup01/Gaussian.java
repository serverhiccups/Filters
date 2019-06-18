package com.hiccup01;

import java.awt.*;
import java.lang.*;

public class Gaussian extends MeanBlur {
	double sigma = 1;
	Gaussian() {
		super();
		sigma = 1;
	}

	Gaussian(double sigma) {
		super();
		this.sigma = sigma;
	}

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

	public double[][] generateMultipliers(double sigma, int size) {
		double gaussianConstant = 1/(2*Math.PI*Math.pow(sigma, 2));
		double[][] multipliers = new double[size][size];
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				int rx = x - (size / 2);
				int ry = y - (size / 2);
				multipliers[x][y] = gaussianConstant * Math.pow(Math.E, -1*((Math.pow(rx, 2) + Math.pow(ry, 2))/2*Math.pow(sigma, 2)));
			}
		}
		return multipliers;
	}

	public uColour[][] filter() throws FilterException {
		uColour[][] newImage = new uColour[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				newImage[i][j] = uColour.ORANGE;
			}
		}
		double[][] multipliers = generateMultipliers(1, (int)(3*sigma));
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				try {
					newImage[i][j] = average(super.getKernel(0, i, j, (int)(3*sigma)), multipliers);
				} catch (FilterException e) {
					throw e;
				}
			}
		}
		return newImage;
	}

}
