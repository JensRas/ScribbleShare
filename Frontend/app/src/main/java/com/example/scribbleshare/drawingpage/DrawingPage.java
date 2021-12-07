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
import com.example.scribbleshare.User;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.postpage.PostPage;
import com.google.android.material.slider.RangeSlider;

import org.json.JSONException;
import org.json.JSONObject;

import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * Handles UI for drawing page and the button logic
 * Color of stroke, stroke size, undo button, and saving of image
 */
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
    private GetUserPresenter getUserPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        createPostPresenter = new CreatePostPresenter(this, getApplicationContext());
        createCommentPresenter = new CreateCommentPresenter(this, getApplicationContext());

        //update the user object stored in the singleton with their user stored on the server
        getUserPresenter = new GetUserPresenter(this, getApplicationContext());
        getUserPresenter.updateUserInSingleton(MySingleton.getInstance(this).getApplicationUser().getUsername());

        String drawContext = "";

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            Log.e("ERROR", "Please set a bundle when switching to the drawing page so it knows the context");
            return;
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

        //set onclick listeners
        String finalDrawContext = drawContext;
        Context context = this;
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(view.getContext(), HomePage.class));
                switch(finalDrawContext){
                    case "newPost":
                        startActivity(new Intent(view.getContext(), HomePage.class));
                        break;
                    case "newComment":
                        int frameId = bundle.getInt("frameId");
                        if(frameId == 0){
                            Log.e("scribbleshare", "no frameId was passed into the bundle when switching to the drawing page!");
                            return;
                        }
                        Intent intent = new Intent(context, PostPage.class);
                        intent.putExtra("postId", bundle.getInt("postId"));
                        Log.d("Debug", "switching to the post page with postId: " + bundle.getInt("postId"));
                        context.startActivity(intent);
                        break;
                }
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = MySingleton.getInstance(view.getContext()).getApplicationUser();
                getUserPresenter.updateUserInSingleton(user.getUsername());
                if(user.isBanned()){
                    makeToast("You are banned from drawing");
                    return;
                }
                if(!(paint.paths.size() == 0)){ //don't save unless something is drawn
                    // getting the bitmap from DrawView class
                    Bitmap bitmap = paint.save();
                    String username = user.getUsername();
                    Log.e("DEBUG", "finalDrawContext: " + finalDrawContext);
                    switch(finalDrawContext){
                        case "newPost":
                            createPostPresenter.createPost(username, bitmap);
                            break;
                        case "newComment":
                            int frameId = bundle.getInt("frameId");
                            if(frameId == 0){
                                Log.e("scribbleshare", "no frameId was passed into the bundle when switching to the drawing page!");
                                return;
                            }
                            createCommentPresenter.createComment(username, frameId, bitmap);
                            break;
                    }
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
                    /**
                     * Sets the paint color when drawing
                     * @param position gets position of canvas
                     * @param color color chosen on the color picker gets an int value
                     */
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
                        // get the integer value of color
                        // selected from the dialog box and
                        // set it as the stroke color
                        paint.setColor(color);
                    }

                    /**
                     * Dismisses the color picker
                     */
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
            /**
             * Shows/hides range slider
             * @param view Is either Visible or Gone, then based off that value shows/hides range slider
             */
            @Override
            public void onClick(View view) {
                if (rangeSlider.getVisibility() == View.VISIBLE)
                    rangeSlider.setVisibility(View.GONE);
                else
                    rangeSlider.setVisibility(View.VISIBLE);
            }
        });

        // set the range of the RangeSlider
        rangeSlider.setValueFrom(0.0f);
        rangeSlider.setValueTo(100.0f);

        // adding a OnChangeListener which will
        // change the stroke width
        // as soon as the user slides the slider
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            /**
             * Set's the stroke width to the chose thickness from the user
             * @param slider range slider
             * @param value value of the thickness chosen from slider
             * @param fromUser gets input from user
             */
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
    public void onCreateCommentSuccess(JSONObject jsonObject) {
        Intent intent = new Intent(this, PostPage.class);
        try {
            intent.putExtra("postId", jsonObject.getInt("id"));
        } catch (JSONException e) {
            Log.e("ERROR", "Error parsing response: " + jsonObject.toString());
            e.printStackTrace();
            return;
        }
        startActivity(intent);
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