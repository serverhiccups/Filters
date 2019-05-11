package com.hiccup01;

import java.awt.*;

public class BasicFilter implements Filter {
	Color[][][] backingImages = null;
	public void setData(Color[][][] image) throws FilterException {
		if(image.length != 1) throw new FilterException();
		if(image[0].length < 1) throw new FilterException();
		if(image[0][0].length < 1) throw new FilterException();
		backingImages = java.util.Arrays.copyOf(image, image.length);
	}

	public Color[][] filter() throws FilterException {
		if(backingImages == null) throw new NoDataSetException();
		Color[][] newImage = new Color[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < backingImages[0].length; i++) {
			for(int j = 0; j < backingImages[0][0].length; j++) {
				newImage[i][j] = backingImages[0][i][j];
			}
		}
		return newImage;
	}

	public Color[][] getKernel(int imageNumber, int x, int y, int size) throws FilterException {
		Color[][] kernel = new Color[size][size];
		if(size % 2 == 0) throw new FilterException();
		int ii = 0;
		for(int i = x - (size / 2); i < x + 1 + (size / 2); i++) {
			int jj = 0;
			for(int j = y - (size / 2); j < y + 1 + (size / 2); j++) {
				if(i < 0 || i >= backingImages[imageNumber].length || j < 0 || j >= backingImages[imageNumber][0].length) {
					kernel[ii][jj] = null;
					continue;
				}
				kernel[ii][jj] = backingImages[imageNumber][i][j];
			jj++;
			}
			ii++;
		}
		return kernel;
	}
}
