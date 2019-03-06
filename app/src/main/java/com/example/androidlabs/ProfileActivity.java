package com.example.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;



public class ProfileActivity extends AppCompatActivity {

    ImageButton mImageButton;
    Button chatButton;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent login = getIntent();

        mImageButton = (ImageButton) findViewById(R.id.profileImageButton);


        String emailTyped = login.getStringExtra("emailTyped");
        EditText enterText = (EditText) findViewById(R.id.profileEmailEditText);
        enterText.setText(emailTyped);

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


        // Lab4 ChatButton onClickListener

        chatButton = (Button)findViewById(R.id.chatButton);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itLab4 = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                startActivityForResult(itLab4, 345);
            }
        });
    }

    // This will start Android’s Activity that is responsible for the MediaStore.ACTION_IMAGE_CAPTURE intent. Since you are using startActivityForResult( ), the activity will call your onActivityResult(int request, int result, Intent data ) when finished. If you clicked the checkbox button on the camera activity, then result will be Activity.RESULT_OK, and the data parameter will be an intent that has the picture saved under the name “data”:

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
