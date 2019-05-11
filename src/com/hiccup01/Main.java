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
        try {
            startingImage = ImageIO.read(new File("strawberry.jpg"));
        } catch (IOException e) {
            System.out.println("Could not find the file");
            System.exit(1);
        }
        Color[][] startingArray = new ArrayBufferedImageInterface().intoArray(startingImage);
        BasicFilter blur = new MeanBlur();
        Color[][][] backingImages = new Color[1][startingArray.length][startingArray[0].length];
        backingImages[0] = startingArray;
        try {
            blur.setData(backingImages);
        } catch (FilterException e) {
            System.out.println("Couldn't set the data for the filter");
            System.exit(1);
        }
        Color[][] filteredArray = null;
        try {
			filteredArray = blur.filter();
        } catch (FilterException e) {
            System.out.println("We got a FilterException");
            System.exit(1);
        }
        BufferedImage filteredImage = new ArrayBufferedImageInterface().intoBufferedImage(filteredArray);
        String fileName = String.format("out-%Ts", Calendar.getInstance());
        try {
            ImageIO.write(filteredImage, "png", new File(fileName));
        } catch (IOException e) {
            System.out.println("Could not write out the image");
            System.exit(1);
        }
        try {
            Process openFile = new ProcessBuilder("/usr/bin/open", fileName).start();
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
