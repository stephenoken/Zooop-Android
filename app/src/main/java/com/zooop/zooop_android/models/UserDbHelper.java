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

    public boolean insert(String name, String preference, String gcmID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COL_2, name);
        values.put(UserContract.UserEntry.COL_3, preference);
        values.put(UserContract.UserEntry.COL_4, gcmID);
        long result = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        if (result == -1)
            return false;

        else
            return true;
    }

    public String[] readReturn(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + "user";
        Cursor cursor   = db.rawQuery(selectQuery, null);
        cursor.moveToLast();

        String ans[] = {cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)};
        Log.d("readReturn--", ans[0] + ans[1] + ans[2]);

        return ans;
    }

    public boolean update(String id, String name, String preference, String gcmID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Log.d("updateDB", id+name+preference);
        cv.put(UserContract.UserEntry.COL_1, id);
        cv.put(UserContract.UserEntry.COL_2, name);
        cv.put(UserContract.UserEntry.COL_3, preference);
        cv.put(UserContract.UserEntry.COL_4, gcmID);
        db.update(UserContract.UserEntry.TABLE_NAME, cv, "id = ?", new String[]{id});
        read();
        return true;

    }

    public void read(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + "user";
        Cursor cursor   = db.rawQuery(selectQuery, null);
        cursor.moveToLast();

        System.out.println("User--->" + cursor.getInt(0)
                + " " + cursor.getString(1)
                + " " + cursor.getString(2));
    }

}
