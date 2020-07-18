package com.example.getum.SQLite;

import android.provider.BaseColumns;

public final class UmbrellaContract {
    private UmbrellaContract(){}

    public static class Umbrella implements BaseColumns {
        public static final String TABLE_NAME = "umbrella";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_STORAGE_ID = "storage_id";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY," +
                        COLUMN_STORAGE_ID + " INTEGER," +
                        COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        COLUMN_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "CONSTRAINT storageId_fk FOREIGN KEY(" + COLUMN_STORAGE_ID + ")" +
                        "REFERENCES storage(id))";
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String SQL_READ_TABLE =
                "SELECT *" +
                        "FROM " + TABLE_NAME + " "+
                        "ORDER BY " + COLUMN_ID;
    }
}
