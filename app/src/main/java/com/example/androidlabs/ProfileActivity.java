package com.example.androidlabs;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    ImageButton mImageButton;
    Button chatButton;
    Button toolButton;
    Button weatherForecastButton;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent login = getIntent();
        String msg=login.getStringExtra("defaultEmail");
        EditText text=findViewById(R.id.editText4);
        text.setText(msg);


        mImageButton = (ImageButton) findViewById(R.id.imageButton);


        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        Log.e(ACTIVITY_NAME,"in function: onCreate()");

        chatButton = (Button)findViewById(R.id.chatButton);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itLab4 = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                startActivity(itLab4);
            }
        });

        toolButton = (Button)findViewById(R.id.toolButton);

        toolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itLab4 = new Intent(ProfileActivity.this, TestToolbar.class);
                startActivity(itLab4);
            }
        });

        weatherForecastButton = (Button)findViewById(R.id.weatherForecastButton);

        weatherForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itLab7 = new Intent(ProfileActivity.this, WeatherForecast.class);
                startActivity(itLab7);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.e(ACTIVITY_NAME,"in function: onStart()");

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.e(ACTIVITY_NAME,"in function: onResume()");


    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.e(ACTIVITY_NAME,"in function: onPause()");

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.e(ACTIVITY_NAME,"in function: onStop()");

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e(ACTIVITY_NAME,"in function: onDestroy()");

    }
}
