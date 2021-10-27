package com.example.scribbleshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scribbleshare.drawingpage.DrawingPage;

public class test_homescreen extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_homescreen);
//
//        Button create_account=(Button) findViewById(R.id.draw_button);
//        Context context = getApplicationContext();
//        CharSequence text = "Signed Out";
//        int duration = Toast.LENGTH_SHORT;
//        create_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(view.getContext(), MainActivity.class));
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_homescreen);

        Button create_account=(Button) findViewById(R.id.draw_button);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), DrawingPage.class));
            }
        });
    }
}