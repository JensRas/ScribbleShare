package com.example.scribbleshare.drawingpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Holds data for drawing
 */
public class DrawView extends View {
    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;

    // the Paint class encapsulates the color
    // and style information about
    // how to draw the geometries,text and bitmaps
    private Paint mPaint;

    // ArrayList to store all the strokes
    // drawn by the user on the Canvas
    public ArrayList<Stroke> paths = new ArrayList<>();
    private int currentColor;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    /**
     * Constructor to initialise all the attributes
     * @param context given context
     */
    public DrawView(Context context) {
        this(context, null);
    }

    /**
     * Defaults for drawing
     * @param context context for drawing
     * @param attrs attributes for drawing
     */
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        // the below methods smoothens
        // the drawings of the user
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        // 0xff=255 in decimal
        mPaint.setAlpha(0xff);

    }

    /**
     * Instantiates the bitmap and object
     * @param height height for bitmap
     * @param width width for bitmap
     */
    public void init(int height, int width) {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        // set an initial color of the brush
        currentColor = Color.GREEN;

        // set an initial brush size
        strokeWidth = 20;
    }

    /**
     * Sets the current color of stroke
     * @param color color for drawing
     */
    public void setColor(int color) {
        currentColor = color;
    }

    /**
     * Sets the stroke width
     * @param width width of the stroke
     */
    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }

    /**
     * Checks whether the List is empty or not and if empty, the remove method will return an error
     */
    public void undo() {
        if (paths.size() != 0) {
            paths.remove(paths.size() - 1);
            invalidate();
        }
    }

    /**
     * This methods returns the current bitmap
     * @return returns current bitmap
     */
    public Bitmap save() {
        return mBitmap;
    }

    /**
     * Main method where drawing takes place
     * @param canvas Canvas of the drawing
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // save the current state of the canvas before,
        // to draw the background of the canvas
        canvas.save();

        // DEFAULT color of the canvas
        int backgroundColor = Color.WHITE;
        mCanvas.drawColor(backgroundColor);

        // now, we iterate over the list of paths
        // and draw each path on the canvas
        for (Stroke fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mCanvas.drawPath(fp.path, mPaint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    /**
     * Creates new stroke at the (x,y) coordinate that the user touches and adds to paths list
     * @param x x coordinate on the bitmap
     * @param y y coordinate on the bitmap
     */
    private void touchStart(float x, float y) {
        mPath = new Path();
        Stroke fp = new Stroke(currentColor, strokeWidth, mPath);
        paths.add(fp);

        // finally remove any curve
        // or line from the path
        mPath.reset();

        // this methods sets the starting
        // point of the line being drawn
        mPath.moveTo(x, y);

        // we save the current
        // coordinates of the finger
        mX = x;
        mY = y;
    }

    /**
     * Calculates the movement of the users finger
     * @param x x coordinate on the bitmap
     * @param y y coordinate on the bitmap
     */
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    /**
     * Draws the line from start of position to the end of the position
     */
    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    /**
     * Provides information about the touch event (Down, Move, Up)
     * @param event touch event
     * @return the type of the touch (up, down, move)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}


