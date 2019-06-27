package com.hiccup01;

import java.awt.image.*;

/**
 * This is a helper class that allows for the transformation between the standard BufferedImage image format and the uColour[][] based one.
 * This class assumes the use of the sRGB colourspace and the TYPE_INT_RGB format.
 * @author serverhiccups
 * @see BufferedImage
 */
public class ArrayBufferedImageInterface {
	/**
	 * Convert a Buffered image into a uColour[][] array.
	 * @param image The buffered image to convert.
	 * @return The converted uColour[][] array.
	 */
	public uColour[][] intoArray(BufferedImage image) {
        uColour[][] newImage = new uColour[image.getWidth()][image.getWidth()];
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				newImage[i][j] = new uColour(image.getRGB(i, j)); // Take the colours out of the image as a single integer, and use uColour to parse it.
			}
		}
		return newImage;
	}

	/**
	 * Convert a uColour[][] array into a BufferedImage.
	 * @param image The uColour[][] array to convert.
	 * @return The converted BufferedImage.
	 */
	public BufferedImage intoBufferedImage(uColour[][] image) {
		BufferedImage newImage = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				newImage.setRGB(i, j, image[i][j].getRGB()); // Copy each colour from the array into the BufferedImage.
															 // There is probably a faster way of doing this, but this way is very clear what it is doing.
			}
		}
		return newImage;
	}
}
