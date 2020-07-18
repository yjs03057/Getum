package com.example.getum.SQLite;

import android.provider.BaseColumns;

public final class RentalLogContract {
    private RentalLogContract(){}

    public static class RentalLog implements BaseColumns {
        public static final String TABLE_NAME = "rental_log";
        public static final String COLUMN_LOG_NO = "log_no";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_USER_NO = "user_no";
        public static final String COLUMN_UMBRELLA_ID = "umbrella_id";
        public static final String COLUMN_STORAGE_ID = "storage_id";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_LOG_NO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_TYPE + " TEXT," +
                        COLUMN_USER_NO + " INTEGER," +
                        COLUMN_UMBRELLA_ID + " TEXT," +
                        COLUMN_STORAGE_ID + " INTEGER," +
                        COLUMN_TIMESTAMP + " TIMESTAMP," +
                        COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        COLUMN_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String SQL_READ_TABLE =
                "SELECT *" +
                        "FROM " + TABLE_NAME + " "+
                        "ORDER BY " + COLUMN_LOG_NO;
    }
}
