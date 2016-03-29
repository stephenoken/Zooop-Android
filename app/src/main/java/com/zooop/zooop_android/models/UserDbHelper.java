package com.zooop.zooop_android.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by harpreet on 22/03/16.
 */

public class UserDbHelper extends SQLiteOpenHelper {

    public UserDbHelper(Context context) {
        super(context, UserContract.DATABASE_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.UserEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserContract.UserEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insert(String name, String preference, int flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (flag==1)
           values.put(UserContract.UserEntry.COL_2, name);
        else if(flag==0)
           values.put(UserContract.UserEntry.COL_3, preference);
        long result = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        System.out.print("result---------------" + result);
        Log.d("namehaha", name);
        read();
        if (result == -1)
            return false;

        else
            return true;
    }

    public void read(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                UserContract.UserEntry.COL_1,
                UserContract.UserEntry.COL_2,
                UserContract.UserEntry.COL_3

        };


        String selectQuery = "SELECT  * FROM " + "user";
        Cursor cursor   = db.rawQuery(selectQuery, null);
        cursor.moveToLast();

        System.out.println("cursor=-------------------"+cursor.getString(1));
    }

}
