package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText emailEditText;
    SharedPreferences sp;
    String typedEmail;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = (EditText)findViewById(R.id.EmailEditText);
        sp = getSharedPreferences("email", Context.MODE_PRIVATE);
        login = (Button)findViewById(R.id.loginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this, ProfileActivity.class);

                //EditText et = (EditText)findViewById(R.id.EmailEditText);

                it.putExtra("emailTyped", emailEditText.getText().toString());

                startActivityForResult(it, 345);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // This is an editor object
        SharedPreferences.Editor edit = sp.edit();


        // this was we are saving what user has type.
        // This is similar to Java String = next().
        typedEmail = emailEditText.getText().toString();

        // This edit.putString() method saves email that is typed by user
        edit.putString("",typedEmail);

        // It apply changes that are made
        edit.apply();


    }
}