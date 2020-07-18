package com.example.getum.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "getum.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL(StorageContract.Storage.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(UmbrellaContract.Umbrella.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(StorageContract.Storage.SQL_DELETE_TABLE);
        sqLiteDatabase.execSQL(UmbrellaContract.Umbrella.SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertStorageRecord(String location, Double latitude, Double longitude) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(StorageContract.Storage.COLUMN_LOCATION, location);
        values.put(StorageContract.Storage.COLUMN_LATITUDE, latitude);
        values.put(StorageContract.Storage.COLUMN_LONGITUDE, longitude);

        db.insert(StorageContract.Storage.TABLE_NAME, null, values);
    }

    public Cursor readStorageRecord() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(StorageContract.Storage.SQL_READ_TABLE, null);
        return cursor;
    }

    public void insertUmbrellaRecord(String id, Integer storage_id) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(UmbrellaContract.Umbrella.COLUMN_ID, id);
        values.put(UmbrellaContract.Umbrella.COLUMN_STORAGE_ID, storage_id);

        db.insert(UmbrellaContract.Umbrella.TABLE_NAME, null, values);
    }

    public Cursor readUmbrellaRecord() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(UmbrellaContract.Umbrella.SQL_READ_TABLE, null);
        return cursor;
    }
}
