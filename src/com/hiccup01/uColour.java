package com.hiccup01;

public class uColour {
	// Colours are stored in an int as 0xAARRGGBB.
	int colour = 0xFF000000;

	uColour() {
		colour = 0xFF000000;
	}

	uColour(int rgb) {
		colour = rgb;
	}

	uColour(int r, int g, int b) {
		r = r > 255 ? 255 : r; r = r < 0 ? 0 : r;
		g = g > 255 ? 255 : g; g = g < 0 ? 0 : g;
		b = b > 255 ? 255 : b; b = b < 0 ? 0 : b;
		int makeColour = 0xFF000000;
		makeColour = makeColour | (r << 16);
		makeColour = makeColour | (g << 8);
		makeColour  = makeColour | b;
		colour = makeColour;
	}

	public int getRGB() {
		return colour;
	}

	public int getRed() {
		return (colour & 0x00FF0000) >> 16;
	}

	public int getGreen() {
		return (colour & 0x0000FF00) >> 8;
	}

	public int getBlue() {
		return colour & 0x000000FF;
	}

	public static uColour BLACK = new uColour(0xFF000000);

	public static uColour ORANGE = new uColour(0xFFFFA500);
}
