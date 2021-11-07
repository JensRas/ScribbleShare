package com.example.scribbleshare.drawingpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.postpage.PostPage;
import com.google.android.material.slider.RangeSlider;

import petrov.kristiyan.colorpicker.ColorPicker;

import org.json.JSONException;
import org.json.JSONObject;

public class DrawingPage extends AppCompatActivity implements DrawingPageView {

    // creating the object of type DrawView
    // in order to get the reference of the View
    private DrawView paint;

    // creating objects of type button
    private ImageButton save, color, stroke, undo;

    // creating a RangeSlider object, which will
    // help in selecting the width of the Stroke
    private RangeSlider rangeSlider;

    private CreatePostPresenter createPostPresenter;
    private CreateCommentPresenter createCommentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        createPostPresenter = new CreatePostPresenter(this, getApplicationContext());
        createCommentPresenter = new CreateCommentPresenter(this, getApplicationContext());

        String drawContext = "";

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            Log.e("ERROR", "Please set a bundle when switching to the drawing page so it knows the context");
        }else{
            drawContext = bundle.getString("drawContext");
        }

        // getting the reference of the views from their ids
        paint = (DrawView) findViewById(R.id.draw_view);
        rangeSlider = (RangeSlider) findViewById(R.id.rangebar);
        undo = (ImageButton) findViewById(R.id.btn_undo);
        save = (ImageButton) findViewById(R.id.btn_save);
        color = (ImageButton) findViewById(R.id.btn_color);
        stroke = (ImageButton) findViewById(R.id.btn_stroke);
        ImageButton back_button = (ImageButton) findViewById(R.id.back_button_draw);

        // creating a OnClickListener for each button,
        // to perform certain actions

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change this based on drawContext
                startActivity(new Intent(view.getContext(), HomePage.class));
            }
        });

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
        String finalDrawContext = drawContext;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting the bitmap from DrawView class
                Bitmap bitmap = paint.save();
                String username = MySingleton.getInstance(view.getContext()).getApplicationUser().getUsername();
                switch(finalDrawContext){
                    case "newPost":
                        createPostPresenter.createPost(username, bitmap);
                        break;
                    case "newComment":
                        int frameId = bundle.getInt("frameId"); //TODO add error handling if this doesn't exist?
                        createCommentPresenter.createComment(username, frameId, bitmap);
                        break;
                }
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

    @Override
    public void onCreateCommentSuccess(JSONObject o) {
        Log.i("info", "onCreatecommentSuccess: " + o.toString());
        Intent intent = new Intent(this, PostPage.class);
        try {
            intent.putExtra("postId", o.getString("id"));
        } catch (JSONException e) {
            Log.e("ERROR", "Error parsing response: " + o.toString());
            e.printStackTrace();
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onCreatePostSuccess(JSONObject o) {

    }

    @Override
    public void makeToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    public void switchView(Class c) {
        startActivity(new Intent(this, c));
    }
}