package com.hiccup01;

import java.awt.*;

public class ExposureFilter extends BasicFilter {
	float exposureMultiplier = (float) 1.5;

	ExposureFilter(float multiplier) {
		exposureMultiplier = multiplier;
	}

	public ExposureFilter(double v) {
		this((float)v);
	}

	@Override
	public uColour[][] filter() throws FilterException {
		uColour[][] newImage = new uColour[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				uColour newValue = backingImages[0][i][j];
                int r = (int)((float)newValue.getRed() * exposureMultiplier);
				int g = (int)((float)newValue.getGreen() * exposureMultiplier);
				int b = (int)((float)newValue.getBlue() * exposureMultiplier);
				r = r > 255 ? 255 : r; r = r < 0 ? 0 : r;
				g = g > 255 ? 255 : g; g = g < 0 ? 0 : g;
				b = b > 255 ? 255 : b; b = b < 0 ? 0 : b;
				newImage[i][j] = new uColour(r, g, b);
			}
		}
		return newImage;
	}
}
