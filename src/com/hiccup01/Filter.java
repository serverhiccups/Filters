package com.hiccup01;

import java.awt.*;

public interface Filter {
	void setData(Color[][][] image) throws FilterException;
	Color[][] filter() throws FilterException;
}

