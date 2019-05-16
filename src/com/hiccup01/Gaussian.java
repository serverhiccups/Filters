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

	public Color average(Color[][] kernel, double[][] multipliers) {
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
		return new Color((int)(r/totalWeight), (int)(g/totalWeight), (int)(b/totalWeight));
	}

	public double[][] generateMultipliers(double sigma, int size) {
		double gaussianConstant = (1/Math.sqrt(2*Math.PI*(Math.pow(sigma, 2))));
		double[] multipliers = new double[size];
		for(int i = 0 - size / 2; i < size/2; i++) {
			multipliers[i+(size / 2)] = gaussianConstant * Math.pow(Math.E, -1*((i^2)/(2*Math.pow(sigma, 2))));
		}
		double[][] multipliers2d = new double[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				multipliers2d[i][j] = multipliers[i] * multipliers[j];
			}
		}
		return multipliers2d;
	}

	public Color[][] filter() throws FilterException {
		Color[][] newImage = new Color[backingImages[0].length][backingImages[0][0].length];
		double[][] multipliers = generateMultipliers(1, (int)(3*sigma)+1);
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				try {
					newImage[i][j] = average(super.getKernel(0, i, j, (int)(3*sigma)+1), multipliers);
				} catch (FilterException e) {
					throw e;
				}
			}
		}
		return newImage;
	}

}
