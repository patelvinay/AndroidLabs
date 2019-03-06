package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseClass extends SQLiteOpenHelper {

    // Main System class Reference Object
    SQLiteDatabase SQLd;

    // DATABASE NAME
    private static final String DATABASE_NAME = "lab5Database";

    //DATABASE VERSION
    private static final int DATABAS_VERSION = 1;

    // DATABASE TABLE
    // NOTE: WE CAN HAVE MULTIPLE TABLES,BUT FOR NOW, WE JUST NEED ONE TABLE TO STORE DATA
    private static final String TABLE_NAME = "messages";

    // COLUMN NAMES
    public static final String ROW_ID= "id";
    public static final String SEDN_MSG="sendMsg";
    public static final String RECEIVE_MSG="receiveMsg";


    // THIS IS A CURRENT CLASS CONSTRUCTOR THAT ARE EXPENDING SUPER CLASS PARAMETERS.
    public DatabaseClass(Context context) {
        super(context, DATABASE_NAME, null, DATABAS_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // This veriable, CREATE_TABLE, will store the table create query, so that we can use it later to create table.
        String MESSAGE_CREATE_TABLE = (
                " CREATE TABLE " + TABLE_NAME +
                        " ( " + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + SEDN_MSG + " varchar, "
                        + RECEIVE_MSG + " varchar);");

        db.execSQL(MESSAGE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String MESSGAE_TABLE_DROP = (
                " DROP TABLE IF EXISTS " + TABLE_NAME
        );

        db.execSQL(MESSGAE_TABLE_DROP);
        onCreate(db);
    }

    public long insertMessage(String sendMessage) {

        // This method allow us permission to store data into database
        SQLd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SEDN_MSG, sendMessage);
        return SQLd.insert(TABLE_NAME,null, cv);

    }

    // THIS METHOD IS INCOMPLETE //
    public void printCursor(Cursor c){

        SQLd = this.getReadableDatabase();

        c = SQLd.rawQuery("select * from " + TABLE_NAME,null);
        Log.d("Cursor Object", DatabaseUtils.dumpCursorToString(c));

    }
}