package com.hiccup01;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class Main {

	public static void main(String[] args) {
		BufferedImage startingImage = null;
		try { // Try to read in an image to filter
			startingImage = ImageIO.read(new File("strawberry.jpg"));
		} catch (IOException e) {
			System.out.println("Could not find the file");
			System.exit(1);
		}
		uColour[][] startingArray = new ArrayBufferedImageInterface().intoArray(startingImage); // Turn the BufferedImage we read in into a uColour[][] array.
		int[][] sobelOperator = {{1, 0, -1},
								{2, 0, -2},
								{1, 0, -2}};
		BasicFilter blur = null;
		blur = new Grayscale();
		uColour[][][] backingImages = new uColour[1][startingArray.length][startingArray[0].length];
		backingImages[0] = startingArray;
		try { // Add our image to the filter.
			blur.setData(backingImages);
		} catch (FilterException e) {
			System.out.println("Couldn't set the data for the filter");
			System.exit(1);
		}
		uColour[][] filteredArray = null;
		try { // Try to blur our image.
			filteredArray = blur.filter();
		} catch (FilterException e) {
			System.out.println("We got a FilterException");
			System.exit(1);
		}
		BasicFilter edges = null;
		try {
			edges = new customKernelFilter(sobelOperator);
			backingImages[0] = filteredArray;
			edges.setData(backingImages);
			filteredArray = edges.filter();
		} catch (FilterException e) {
			System.out.println("Failed the make the edge detect filter");
			System.exit(1);
		}
		BufferedImage filteredImage = new ArrayBufferedImageInterface().intoBufferedImage(filteredArray); // Turn our filtered array into a Buffered Image.
		String fileName = String.format("out-%Ts.png", Calendar.getInstance());
		try { // Create a new file and write our image out to it.
			ImageIO.write(filteredImage, "png", new File(fileName));
		} catch (IOException e) {
			System.out.println("Could not write out the image");
			System.exit(1);
		}
		try {
			Process openFile = new ProcessBuilder("/usr/bin/open", fileName).start(); // Open up the image in your image viewer of choice.
		} catch (IOException e) {
			System.out.println("Could not start 'open'");
			System.exit(1);
		}
		System.out.println("The image has been filtered");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.exit(0);
		}
		System.exit(0);
	}
}
