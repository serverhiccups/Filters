package com.hiccup01;

/**
 * A filter that converts an image to grayscale.
 * @author serverhiccups
 * @see BasicFilter
 * @see Filter
 */
public class Grayscale extends BasicFilter {
	public uColour[][] filter() {
		uColour[][] newImage = new uColour[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				int gray = (backingImages[0][i][j].getRed() + backingImages[0][i][j].getGreen() + backingImages[0][i][j].getBlue())/3; // Get the average of all the channels. There might be a better way of doing this, but it seems to hard.
				newImage[i][j] = new uColour(gray, gray, gray);
			}
		}
		return newImage;
	}
}
