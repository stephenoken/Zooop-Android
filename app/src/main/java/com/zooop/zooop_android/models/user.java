package com.zooop.zooop_android.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by harpreet on 22/03/16.
 */
public class user extends SQLiteOpenHelper {
    public static final String DB_NAME = "user";
    public static final String COL_1 = "id";
    public static final String COL_2 = "token";
    public static final String COL_3 = "email";
    public static final String COL_4 = "name";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DB_NAME + " (" +
            COL_1 + " INTEGER PRIMARY KEY," +
            COL_2 + TEXT_TYPE + COMMA_SEP +
            COL_3 + TEXT_TYPE + COMMA_SEP +
            COL_4 + TEXT_TYPE + COMMA_SEP + " )";

    public user(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
