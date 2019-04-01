package com.example.androidlabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        Log.e("Empty Act pos","");


        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setArguments( dataToPass ); //pass data to the the fragment
        messageFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, messageFragment)
                .addToBackStack("AnyName")
                .commit();
    }
}