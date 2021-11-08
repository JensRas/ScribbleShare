package com.example.scribbleshare.drawingpage;

import android.graphics.Path;

/**
 * Stroke information
 */
public class Stroke {
    // color of the stroke
    public int color;

    // width of the stroke
    public int strokeWidth;

    // a Path object to
    // represent the path drawn
    public Path path;

    /**
     * Sets stroke information
     * @param color Color of stroke
     * @param strokeWidth Width of stroke
     * @param path Drawing path of stroke
     */
    // constructor to initialise the attributes
    public Stroke(int color, int strokeWidth, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
