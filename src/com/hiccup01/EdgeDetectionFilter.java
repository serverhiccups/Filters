package com.hiccup01;

import java.util.Arrays;

public class EdgeDetectionFilter extends BasicFilter {
	private static int[][] sobelx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
	private static int[][] sobely = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

	@Override
	public uColour[][] filter() throws FilterException {
		CustomKernelFilter ckf = new CustomKernelFilter(sobelx);
		uColour[][][] backingData = new uColour[1][backingImages[0].length][backingImages[0][0].length];
		backingData[0] = Arrays.copyOf(backingImages[0], backingImages[0].length);
		try {
			ckf.setData(backingData);
		} catch (FilterException e) {
			throw e;
		}
		uColour[][][] dimensions = new uColour[2][backingData[0].length][backingData[0][0].length];
		dimensions[0] = ckf.filter();
		backingData[0] = Arrays.copyOf(backingImages[0], backingImages[0].length);
		ckf = new CustomKernelFilter(sobely);
		try {
			ckf.setData(backingData);
		} catch (FilterException e) {
			throw e;
		}
		dimensions[1] = ckf.filter();
		uColour[][] filteredImage = new uColour[dimensions[0].length][dimensions[0][0].length];
		// Remember that the total of sqrt(255^2 + 255^2) is ~361
		for (int i = 0; i < filteredImage.length; i++) {
			for (int j = 0; j < filteredImage[0].length; j++) {
                int intensity = (int)(Math.hypot(dimensions[0][i][j].getRed(), dimensions[1][i][j].getRed()) / 361f * 255f);
				filteredImage[i][j] = new uColour(intensity, intensity, intensity);
			}
		}
		return filteredImage;
	}
}
