package com.example.getum.SQLite;

import android.provider.BaseColumns;

public final class StorageContract {
    private StorageContract(){}

    public static class Storage implements BaseColumns {
        public static final String TABLE_NAME = "storage";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_LOCATION + " TEXT," +
                        COLUMN_LATITUDE + " DOUBLE," +
                        COLUMN_LONGITUDE + " DOUBLE, " +
                        COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        COLUMN_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String SQL_READ_TABLE =
                "SELECT *" +
                "FROM " + TABLE_NAME + " "+
                "ORDER BY " + COLUMN_ID;
    }
}
