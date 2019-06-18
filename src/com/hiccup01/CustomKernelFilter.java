package com.hiccup01;

public class CustomKernelFilter extends BasicFilter {
	public int[][] kernel = null;
	public int maxKernelVal = 0;
	public int minKernelVal = 0;

	public CustomKernelFilter(int[][] kernel) {
		for (int i = 0; i < kernel.length; i++) {
			for (int j = 0; j < kernel[0].length; j++) {
				if (kernel[i][j] > 0) {
					maxKernelVal += kernel[i][j] * 255;
				}
				if (kernel[i][j] < 0) {
					minKernelVal += kernel[i][j] * 255;
				}
			}
			this.kernel = kernel;
		}
	}


	public uColour applyKernel(uColour[][] imageK) throws FilterException {
		if(kernel.length != imageK.length || kernel[0].length != imageK.length) throw new FilterException();
		int totalValue = 0;
		for(int i = 0; i < imageK.length; i++) {
			for(int j = 0; j < imageK[0].length; j++) {
				if(imageK[i][j] == null) continue;
				totalValue += kernel[i][j] * imageK[i][j].getRed();
			}
		}
		totalValue += minKernelVal * -1;
		totalValue /= ((maxKernelVal + (minKernelVal * -1)) / 255);
		return new uColour(totalValue, totalValue, totalValue);
	}

	public uColour[][] filter() {
		uColour[][] newImage = new uColour[backingImages[0].length][backingImages[0][0].length];
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				try {
					newImage[i][j] = applyKernel(super.getKernel(0, i, j, kernel.length));
				} catch (FilterException e) {
					System.out.println("You messed up the filtering");
					System.exit(1);
				}
			}
		}
		return newImage;
	}
}
