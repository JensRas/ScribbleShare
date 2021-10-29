package com.example.scribbleshare.drawingpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.google.android.material.slider.RangeSlider;

//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.MultipartEntityBuilder;

import petrov.kristiyan.colorpicker.ColorPicker;

public class DrawingPage extends AppCompatActivity implements DrawingPageView {

    // creating the object of type DrawView
    // in order to get the reference of the View
    private DrawView paint;

    // creating objects of type button
    private ImageButton save, color, stroke, undo;

    // creating a RangeSlider object, which will
    // help in selecting the width of the Stroke
    private RangeSlider rangeSlider;

    private CreatePostPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        presenter = new CreatePostPresenter(this, getApplicationContext());

        // getting the reference of the views from their ids
        paint = (DrawView) findViewById(R.id.draw_view);
        rangeSlider = (RangeSlider) findViewById(R.id.rangebar);
        undo = (ImageButton) findViewById(R.id.btn_undo);
        save = (ImageButton) findViewById(R.id.btn_save);
        color = (ImageButton) findViewById(R.id.btn_color);
        stroke = (ImageButton) findViewById(R.id.btn_stroke);

        // creating a OnClickListener for each button,
        // to perform certain actions

        // the undo button will remove the most
        // recent stroke from the canvas
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.undo();
            }
        });

        // the save button will save the current
        // canvas which is actually a bitmap
        // in form of PNG, in the storage
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting the bitmap from DrawView class
                Bitmap bitmap = paint.save();
                String username = MySingleton.getInstance(view.getContext()).getApplicationUser().getUsername();
                presenter.createPost(username, bitmap); //TODO use a stored username value
            }
        });

        // the color button will allow the user
        // to select the color of his brush
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPicker colorPicker = new ColorPicker(DrawingPage.this);
                colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
                        // get the integer value of color
                        // selected from the dialog box and
                        // set it as the stroke color
                        paint.setColor(color);
                    }
                    @Override
                    public void onCancel() {
                        colorPicker.dismissDialog();
                    }
                })
                        // set the number of color columns
                        // you want  to show in dialog.
                        .setColumns(5)
                        // set a default color selected
                        // in the dialog
                        .setDefaultColorButton(Color.parseColor("#000000"))
                        .show();
            }
        });

        // the button will toggle the visibility of the RangeBar/RangeSlider
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rangeSlider.getVisibility() == View.VISIBLE)
                    rangeSlider.setVisibility(View.GONE);
                else
                    rangeSlider.setVisibility(View.VISIBLE);

//                //for testing something else
//                Log.d("debug", "clicked stroke button");
//                presenter.getPost("18");
            }
        });

        // set the range of the RangeSlider
        rangeSlider.setValueFrom(0.0f);
        rangeSlider.setValueTo(100.0f);

        // adding a OnChangeListener which will
        // change the stroke width
        // as soon as the user slides the slider
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                paint.setStrokeWidth((int) value);
            }
        });

        // pass the height and width of the custom view
        // to the init method of the DrawView object
        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });
    }

    @Override
    public void setDrawingImage(byte[] data) {
        Log.d("debug", "calling paint.setBitmap()");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data.length, options);
        paint.setmBitmap(bitmap);
    }
}