package com.example.androidlabs;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    Toolbar toolbar;
    String str1 = "This is initial message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//disappear the title

        //add back navigation button
        if (getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.MenuItems_delete:
                //Show the toast immediately:
                Toast.makeText(this, str1, Toast.LENGTH_LONG).show();
                break;
            case R.id.MenuItems_edit:
                alertExample();
                break;
            case R.id.MenuItems_share:
                //Snackbar code:
                Snackbar sb = Snackbar.make(toolbar, "This is the Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Go Back?", e -> finish());
                sb.show();
                break;
            //what to do when the menu item is selected:
            case R.id.MenuItems_overflow:
                //Show the toast immediately:
                Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.dialog, null);
        EditText et = (EditText)middle.findViewById(R.id.view_edit_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Message")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        str1 = et.getText().toString();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }
}
