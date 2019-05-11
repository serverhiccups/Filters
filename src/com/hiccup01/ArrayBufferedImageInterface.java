package com.hiccup01;

import java.awt.*;
import java.awt.image.*;

/**
 * This is a helper class that allows for the transformation between the standard BufferedImage image format and the Color[][] based one.
 * This class assumes the use of the sRGB colourspace and the TYPE_INT_RGB format.
 * @author serverhiccups
 * @see BufferedImage
 */
public class ArrayBufferedImageInterface {
	/**
	 * Convert a Buffered image into a Color[][] array.
	 * @param image The buffered image to convert.
	 * @return The converted Color[][] array.
	 */
	public Color[][] intoArray(BufferedImage image) {
        Color[][] newImage = new Color[image.getWidth()][image.getWidth()];
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				newImage[i][j] = new Color(image.getRGB(i, j));
			}
		}
		return newImage;
	}

	/**
	 * Convert a Color[][] array into a BufferedImage.
	 * @param image The Color[][] array to convert.
	 * @return The converted BufferedImage.
	 */
	public BufferedImage intoBufferedImage(Color[][] image) {
		BufferedImage newImage = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				newImage.setRGB(i, j, image[i][j].getRGB());
			}
		}
		return newImage;
	}
}
