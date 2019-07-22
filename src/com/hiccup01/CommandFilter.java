package com.hiccup01;

import com.beust.jcommander.Parameter;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is the class the implements the image processing pipeline. All the arguments are set during argument parsing.
 * @author serverhiccups
 * @see Main
 */
public class CommandFilter {
	@Parameter(description = "The file to filter. Use '-' to use stdin.")
    public String file;

	@Parameter(names = {"--help", "-h"},
	description = "Display a help message",
	help = true,
	order = 0)
	public boolean help = false;

	@Parameter(names ={"-v", "--version"},
	description = "Display the current version.",
	order = 7)
	boolean version = false;

	@Parameter(names = {"--filter", "-f"},
			description = "The name of the filter to use. A list of filters can be obtained by running 'filters --listFilters'.",
			validateWith = FilterNameValidator.class,
			order = 2)
	public String filterName;

	@Parameter(names = {"--radius", "-r", "--sigma", "-s"},
			description = "For meanblur this is the radius of the kernel in pixels. For gaussian this is the value of sigma and the radius is adjusted accordingly.",
			validateWith = KernelRadiusValidator.class,
			order = 3)
	public int radius = 3;

	@Parameter(names = {"--exposureMultiplier", "--em", "-e"},
	description = "The value to multiply the pixels by when changing the exposure.",
	validateWith = ExposureValueValidator.class,
			order = 4)
	public double exposureMultiplier = 1.5;

	@Parameter(names = {"--output", "-o"}, description = "The file to output to. Leave blank for standard out.", order = 5)
	public String output;

	@Parameter(names = {"--format", "--fo"},
			description = "The name of the format the output should be in. Supports png and jpeg.",
			validateWith = FiletypeValidator.class,
			order = 6)
	public String format = "png";

	@Parameter(names = {"--listFilters"},
			description = "Print out a list of valid filters to use with --filter.",
			order = 1)
	public boolean listFilters = false;

	public static String[] filterNames = {"meanblur", "gaussianblur", "exposure", "grayscale", "sobelx", "sobely", "edgedetection"};
	private static int[][] sobelx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}; // | These are the sobel operators to use, these ones are the non-edge snapping version.
	private static int[][] sobely = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}}; // |

	public void run() {
		if(listFilters) { // If the user wants a list of filters.
			System.out.println("The available filters are:");
			for(int i = 0; i < filterNames.length - 1; i++) {
				System.out.printf("%s, ", filterNames[i]);
			}
			System.out.printf("%s \n", filterNames[filterNames.length-1]); // We print this one out separately so that there is not a comma on the end.
			System.exit(0); // This argument should only ever be used on its own so we exit here.
		}
		if(version) {
			System.out.printf("Filters.jar version %s", Main.version);
			System.exit(0);
		}
		BufferedImage startingImage = null;
		if(file == null) { // If the user didn't provide an input file.
			System.err.printf("No input file was provided\n");
			System.exit(0);
		}
		if(!file.equals("-")) { // The dash is short hand for standard input.
			try {
				startingImage = ImageIO.read(new File(file)); // Try reading from the provided file.
				if(startingImage == null) throw new Exception();
			} catch (Exception e) {
				System.err.println("Could not find the input file. Maybe there is a spelling mistake in the provided filename or the file is corrupt?");
				System.exit(1);
			}
		} else { // If the input file name is '-' then we read from standard input. This allows chaining of multiple of my programs together.
			try {
				ImageInputStream iip = ImageIO.createImageInputStream(System.in);
				startingImage = ImageIO.read(iip); // Try to read the image from standard input
			} catch (IOException e) {
				System.err.println("Failed reading the image from standard input. Perhaps the input was malformed or corrupted.");
				System.exit(1);
			}
		}
		if(startingImage.getWidth() == 0 || startingImage.getHeight() == 0) { // Make sure that image we are about to process doesn't have a zero dimension.
			System.err.println("One the the provided image's dimensions is 0. Try inputting a larger image.");
			System.exit(1);
		}
		uColour[][] startingArray = new ArrayBufferedImageInterface().intoArray(startingImage); // Turn the image that we read in into an array.
		uColour[][][] backingArray = new uColour[2][startingArray.length][startingArray[0].length];
		backingArray[0] = startingArray; // Put the image into an array of images, suitable for use with a Filter.
		BasicFilter filter = null;
		switch (filterName) { // Create the filter to use based on the name we were passed. This functionality could be moved into an IStringConverter, but that seems unnecessary.
			case "meanblur":
				filter = new MeanBlur(radius);
				break;
			case "gaussianblur":
				filter = new GaussianFilter(radius);
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
			filter.setData(backingArray); // Set the data in the filter so that it can be used.
		} catch (FilterException e) {
			System.err.println("An error occurred while passing the image to the filter. Is your image file corrupt?");
			System.exit(1);
		}
		uColour[][] filteredArray = null;
		try {
			filteredArray = filter.filter(); // Filter the image into filteredArray.
		} catch (FilterException e) {
			System.err.println("An error occurred while filtering the image. This may be a bug, try upgrading to the latest version.");
			System.exit(1);
		}
		BufferedImage filteredImage = new ArrayBufferedImageInterface().intoBufferedImage(filteredArray); // Turn our filtered image back into a BufferedImage
		if(output == null) { // If output it not set we assume that the image should be output to standard out.
			try {
				ImageOutputStream ios = ImageIO.createImageOutputStream(System.out);
				ImageIO.write(filteredImage, format, ios); // Write the image to standard out.
			} catch (IOException e) { // This should never happen as System.out should always work and our program shouldn't create malformed inages.
				System.err.println("An error occured when writing out the image to standard out. Is your system configured properly?");
				System.exit(1);
			}
		} else { // If we were given a filename, write out to it.
			try {
				ImageIO.write(filteredImage, format, new File(output));
			} catch (IOException e) {
				System.err.println("An error occured when writing the image to disk. Do you have disk space free and do you have write permissions?");
			}
		}
		System.exit(0); // No errors occurred, so we must have filtered successfully.
	}
}
