package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

public class ChatRoomHelper extends SQLiteOpenHelper{
    //database table
    public static final String DATABASE_NAME="chatroomdb";
    public static final String TABLE_NAME = "ChatRoom_Table";
    public static final String CHAT_ID = "_id";
    public static final String MESSAGE = "message";
    public static final String COL_ISSEND = "Is_Send";

    public static final int  DATABASE_VERSION=1;

    ChatRoomHelper(Context cv) {
        super(cv,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + CHAT_ID + " integer primary key autoincrement, "
            + MESSAGE+ " text, "
            + COL_ISSEND + " BIT);";

    public void onCreate(SQLiteDatabase database) {
        try{
            database.execSQL(DATABASE_CREATE);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        //Log.w(ChatRoomHelper.class.getName(), "Upgrading database from version "
          //      + oldVersion + " to " + newVersion+ ", which will destroy all old data");
        //database.execSQL("DROP Table If Exists" + "todo");
        database.execSQL("DROP Table If EXISTS "+TABLE_NAME);
        onCreate(database);
    }

    public long insertData(String message, boolean is_send)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        //database.insert(database,);
        ContentValues contentValues = new ContentValues();

        contentValues.put(MESSAGE, message);

        if (is_send)
            contentValues.put(COL_ISSEND, 0);
        else
            contentValues.put(COL_ISSEND, 1);

        long result = database.insert(TABLE_NAME, null, contentValues);

        return result;
    }

    public int deleteData(long id){
        SQLiteDatabase database = this.getWritableDatabase();
        //database.insert(database,);
        //String parameter[]={String.valueOf(id)};
        //String deleteQuery="Delete from "+TABLE_NAME+" where "+CHAT_ID+" = "+id;
        int result=database.delete(TABLE_NAME,CHAT_ID+"=?",new String[]{Long.toString(id)});
        return result;
    }

    public Cursor printCursor(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "Select * from "+ TABLE_NAME;

        Cursor cursor = database.rawQuery(query, null);

        Log.e("Database Version", Integer.toString(database.getVersion()));

        Log.e("Column Numbers", Integer.toString(cursor.getColumnCount()));

        Log.v("Column Names", Arrays.toString(cursor.getColumnNames()));

        Log.v("Total Rows", Integer.toString(cursor.getCount()));

        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));

        return cursor;
    }

    public Cursor printCursor(String m_id){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "Select * from "+ TABLE_NAME+" where "+CHAT_ID + "= \'" + m_id + "\'";


        Cursor cursor = database.rawQuery(query, null);

//        Log.e("Database Version", Integer.toString(database.getVersion()));
//
//        Log.e("Column Numbers", Integer.toString(cursor.getColumnCount()));
//
//        Log.v("Column Names", Arrays.toString(cursor.getColumnNames()));
//
//        Log.v("Total Rows", Integer.toString(cursor.getCount()));
//
//        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));

        return cursor;
    }
}
