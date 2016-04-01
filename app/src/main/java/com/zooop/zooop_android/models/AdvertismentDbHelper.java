package com.zooop.zooop_android.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by harpreet on 01/04/16.
 */
public class AdvertismentDbHelper extends SQLiteOpenHelper {

    public AdvertismentDbHelper(Context context) {
        super(context, AdvertismentContract.DATABASE_NAME, null, AdvertismentContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AdvertismentContract.AdvertismentEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AdvertismentContract.AdvertismentEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insert(String type, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AdvertismentContract.AdvertismentEntry.COL_2, type);
        values.put(AdvertismentContract.AdvertismentEntry.COL_3, message);
        long result = db.insert(AdvertismentContract.AdvertismentEntry.TABLE_NAME, null, values);
        if (result == -1)
            return false;

        else
            return true;
    }

    public String[] readReturn(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + "advertisment";
        Cursor cursor   = db.rawQuery(selectQuery, null);
        cursor.moveToLast();

        String ans[] = {cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2)};
        Log.d("readReturn--", ans[0] + ans[1] + ans[2]);

        return ans;
    }

    public boolean update(String id, String type, String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Log.d("updateDB", id + type + message);
        cv.put(AdvertismentContract.AdvertismentEntry.COL_1, id);
        cv.put(AdvertismentContract.AdvertismentEntry.COL_2, type);
        cv.put(AdvertismentContract.AdvertismentEntry.COL_3, message);
        db.update(AdvertismentContract.AdvertismentEntry.TABLE_NAME, cv, "id = ?", new String[]{id});
        read();
        return true;

    }

    public void read(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + "advertisment";
        Cursor cursor   = db.rawQuery(selectQuery, null);
        cursor.moveToLast();

        System.out.println("Advert--->" + cursor.getInt(0)
                + " " + cursor.getString(1)
                + " " + cursor.getString(2));
    }

}


