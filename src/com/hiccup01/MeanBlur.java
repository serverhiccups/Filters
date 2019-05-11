package com.hiccup01;

import java.awt.*;

public class MeanBlur extends BasicFilter {
	int kernelSize = 3;

	MeanBlur(){
	}

	MeanBlur(int size) {
		kernelSize = size;
	}

	private Color average(Color[][] kernel) {
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
		return new Color(r/numberOfPixels, g/numberOfPixels, b/numberOfPixels);
	}


	@Override
	public Color[][] filter() throws FilterException {
		Color[][] newImage = new Color[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				newImage[i][j] = average(super.getKernel(0, i, j, 3));
			}
		}
		return newImage;
	}
}
