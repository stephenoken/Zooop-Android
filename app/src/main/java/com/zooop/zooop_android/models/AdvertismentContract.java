package com.zooop.zooop_android.models;

import android.provider.BaseColumns;

/**
 * Created by harpreet on 01/04/16.
 */
public class AdvertismentContract {

    public static final String DATABASE_NAME = "facebookUser.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public AdvertismentContract() {}

    public static abstract class AdvertismentEntry implements BaseColumns {
        public static final String TABLE_NAME = "advertisment";
        public static final String COL_1 = "id";
        public static final String COL_2 = "type";
        public static final String COL_3 = "message";



        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_1 + " INTEGER PRIMARY KEY," +
                        COL_2 + TEXT_TYPE + COMMA_SEP +
                        COL_3 + TEXT_TYPE + " )";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
