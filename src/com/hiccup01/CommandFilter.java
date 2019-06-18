package com.hiccup01;

import com.beust.jcommander.Parameter;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CommandFilter {
	@Parameter(description = "The file to filter. Use '-' to use stdin.",
	required = true)
    public String file;

	@Parameter(names = {"--help", "-h"},
	description = "Display a help message",
	help = true)
	public boolean help = false;

	@Parameter(names = {"--filter", "-f"},
			description = "The name of the filter to use. A list of filters can be obtained by running 'filters --listFilters'.",
			validateWith = FilterNameValidator.class)
	public String filterName;

	@Parameter(names = {"--radius", "-r", "--sigma", "-s"},
			description = "For meanblur this is the radius of the kernel in pixels. For gaussian this is the value of sigma and the radius is adjusted accordingly.",
			validateWith = KernelRadiusValidator.class)
	public int radius = 3;

	@Parameter(names = {"--exposureMultiplier", "--em", "-e"},
	description = "The value to multiply the pixels by when changing the exposure.",
	validateWith = ExposureValueValidator.class)
	public double exposureMultiplier = 1.5;

	@Parameter(names = {"--output", "-o"}, description = "The file to output to. Leave blank for standard out.")
	public String output;

	@Parameter(names = {"--format", "--fo"},
	description = "The name of the format the output should be in. Supports png and jpeg.",
	validateWith = FiletypeValidator.class)
	public String format = "png";

	public static String[] filterNames = {"meanblur", "gaussianblur", "exposure", "grayscale", "sobelx", "sobely", "edgedetection"};
	private static int[][] sobelx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
	private static int[][] sobely = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

	public void run() {

		BufferedImage startingImage = null;
		if(!file.equals("-")) {
			try {
				startingImage = ImageIO.read(new File(file));
			} catch (IOException e) {
				System.err.println("Could not find the input file. Maybe there is a spelling mistake in the provided filename?");
				System.exit(1);
			}
		} else {
			try {
				ImageInputStream iip = ImageIO.createImageInputStream(System.in);
				startingImage = ImageIO.read(iip);
			} catch (IOException e) {
				System.err.println("Failed reading the image from standard input. Perhaps the input was malformed or corrupted.");
				System.exit(1);
			}
		}
		if(startingImage.getWidth() == 0 || startingImage.getHeight() == 0) {
			System.err.println("One the the provided image's dimensions is 0. Try inputting a larger image.");
			System.exit(1);
		}
		uColour[][] startingArray = new ArrayBufferedImageInterface().intoArray(startingImage);
		uColour[][][] backingArray = new uColour[2][startingArray.length][startingArray[0].length];
		backingArray[0] = startingArray;
		BasicFilter filter = null;
		switch (filterName) {
			case "meanblur":
				filter = new MeanBlur(radius);
				break;
			case "gaussianblur":
				filter = new Gaussian(radius);
				break;
			case "exposure":
				filter = new ExposureFilter(exposureMultiplier);
				break;
			case "grayscale":
				filter = new Grayscale();
				break;
			case "sobelx":
				filter = new CustomKernelFilter(sobelx);
				break;
			case "sobely":
				filter = new CustomKernelFilter(sobely);
				break;
			case "edgedetection":
				filter = new EdgeDetectionFilter();
				break;
			default:
				filter = new BasicFilter();
				break;
		}
		try {
			filter.setData(backingArray);
		} catch (FilterException e) {
			System.err.println("An error occured while passing the image to the filter. Is your image file corrupt?");
			System.exit(1);
		}
		uColour[][] filteredArray = null;
		try {
			filteredArray = filter.filter();
		} catch (FilterException e) {
			System.err.println("An error occured while filtering the image. This may be a bug, try upgrading to the latest version.");
			System.exit(1);
		}
		BufferedImage filteredImage = new ArrayBufferedImageInterface().intoBufferedImage(filteredArray);
		if(output == null) {
			try {
				ImageOutputStream ios = ImageIO.createImageOutputStream(System.out);
				ImageIO.write(filteredImage, format, ios);
			} catch (IOException e) {
				System.err.println("An error occured when writing out the image to standard out. Is your system configured properly?");
				System.exit(1);
			}
		} else {
			try {
				ImageIO.write(filteredImage, format, new File(output));
			} catch (IOException e) {
				System.err.println("An error occured when writing the image to disk. Do you have disk space free and do you have write permissions?");
			}
		}
		System.exit(0);
	}
}
