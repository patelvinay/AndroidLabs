package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    EditText editText1;
    Button loginButton;
    String email;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3layout);
        editText1 = findViewById(R.id.editText1);

        prefs = getSharedPreferences("name", Context.MODE_PRIVATE);
         String userName = prefs.getString("defaultEmail","");
        editText1.setText(userName);

        loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this, ProfileActivity.class);
                it.putExtra("defaultEmail",editText1.getText().toString());
                startActivity(it);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor edit = prefs.edit();

        email = editText1.getText().toString();

        edit.putString("defaultEmail",email);

        edit.apply();
    }
}
