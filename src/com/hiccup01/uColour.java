package com.hiccup01;

/**
 * A reimplementation of java's java.awt.Color;
 */
public class uColour {
	// Colours are stored in an int as 0xAARRGGBB.
	private int colour = 0xFF000000;

	/**
	 * Initialises uColour with the default colour, black.
	 */
	uColour() {
		colour = BLACK.getRGB();
	}

	/**
	 * Initialises uColour with the provided 24bit RGB colour value.
	 * @param rgb The colour to use.
	 */
	uColour(int rgb) {
		colour = rgb;
	}

	/**
	 * Initialises uColour with the different colour channels.
	 * @param r The red channel.
	 * @param g The green channel.
	 * @param b The blue channel.
	 */
	uColour(int r, int g, int b) {
		r = r > 255 ? 255 : r; r = r < 0 ? 0 : r; // Bounds check the values.
		g = g > 255 ? 255 : g; g = g < 0 ? 0 : g;
		b = b > 255 ? 255 : b; b = b < 0 ? 0 : b;
		int makeColour = 0xFF000000;
		makeColour = makeColour | (r << 16); // Use bit shifting to put the different colour values in.
		makeColour = makeColour | (g << 8);
		makeColour  = makeColour | b;
		colour = makeColour;
	}

	/**
	 * Get the 24 RGB colour value. Useful for interfacing with other things.
	 * @return The colour as an int.
	 */
	public int getRGB() {
		return colour;
	}

	/**
	 * Get the red channel of the colour.
	 * @return The red channel.
	 */
	public int getRed() {
		return (colour & 0x00FF0000) >> 16;
	}

	/**
	 * Get the green channel of the colour.
	 * @return The green channel.
	 */
	public int getGreen() {
		return (colour & 0x0000FF00) >> 8;
	}

	/**
	 * Get the blue channel of the colour.
	 * @return The blue channel.
	 */
	public int getBlue() {
		return colour & 0x000000FF;
	}

	public static uColour BLACK = new uColour(0xFF000000);

	public static uColour ORANGE = new uColour(0xFFFFA500);
}
