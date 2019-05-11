package com.hiccup01;

import java.awt.*;
import java.awt.image.*;

public class ArrayBufferedImageInterface {
	public Color[][] intoArray(BufferedImage image) {
        Color[][] newImage = new Color[image.getWidth()][image.getWidth()];
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				newImage[i][j] = new Color(image.getRGB(i, j));
			}
		}
		return newImage;
	}

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
