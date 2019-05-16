package com.hiccup01;

import java.awt.*;

public class Grayscale extends BasicFilter {
	public Color[][] filter() {
		Color[][] newImage = new Color[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				int gray = (backingImages[0][i][j].getRed() + backingImages[0][i][j].getGreen() + backingImages[0][i][j].getBlue())/3;
				newImage[i][j] = new Color(gray, gray, gray);
			}
		}
		return newImage;
	}
}
