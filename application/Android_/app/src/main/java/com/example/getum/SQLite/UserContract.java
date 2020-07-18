package com.example.getum.SQLite;

import android.provider.BaseColumns;

public final class UserContract {
    private UserContract(){}

    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER_NO = "user_no";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PW = "pw";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CARD_NO = "card_no";
        public static final String COLUMN_PHONE_NO = "phone_no";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_USER_NO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_ID + " TEXT," +
                        COLUMN_PW + " TEXT," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_CARD_NO + " TEXT," +
                        COLUMN_PHONE_NO + " TEXT," +
                        COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        COLUMN_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String SQL_READ_TABLE =
                "SELECT *" +
                        "FROM " + TABLE_NAME + " "+
                        "ORDER BY " + COLUMN_USER_NO;
    }
}
