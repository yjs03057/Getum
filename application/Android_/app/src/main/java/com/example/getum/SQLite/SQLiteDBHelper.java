package com.example.getum.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static String TAG = "SQLiteDBHelper";
    private static String DATABASE_PATH = "";
    public static final String DATABASE_NAME = "getum.db";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void createDataBase() throws IOException
    {
        this.getReadableDatabase();
        this.close();
        try
        {
            copyDataBase();
            Log.e(TAG, "createDatabase database created");
        }
        catch (IOException mIOException)
        {
            throw new Error("ErrorCopyingDataBase");
        }
    }

    private boolean checkDataBase()
    {
        File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException
    {
        String mPath = DATABASE_PATH + DATABASE_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public Cursor readStorageRecord() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(StorageContract.Storage.SQL_READ_TABLE, null);
        return cursor;
    }

    public Cursor readUmbrellaRecord() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(UmbrellaContract.Umbrella.SQL_READ_TABLE, null);
        return cursor;
    }

    public void insertUserRecord(String id, String pw, String name, String card_no, String phone_no) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.User.COLUMN_ID, id);
        values.put(UserContract.User.COLUMN_PW, pw);
        values.put(UserContract.User.COLUMN_NAME, name);
        values.put(UserContract.User.COLUMN_CARD_NO, card_no);
        values.put(UserContract.User.COLUMN_PHONE_NO, phone_no);

        db.insert(UserContract.User.TABLE_NAME, null, values);
    }

    public Cursor readUserRecord() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(UserContract.User.SQL_READ_TABLE, null);
        return cursor;
    }

    public void insertRentalLogRecord(String type, Integer user_no, String umbrella_id, Integer storage_id
            , String timestamp) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(RentalLogContract.RentalLog.COLUMN_TYPE, type);
        values.put(RentalLogContract.RentalLog.COLUMN_USER_NO, user_no);
        values.put(RentalLogContract.RentalLog.COLUMN_UMBRELLA_ID, umbrella_id);
        values.put(RentalLogContract.RentalLog.COLUMN_STORAGE_ID, storage_id);
        values.put(RentalLogContract.RentalLog.COLUMN_TIMESTAMP, timestamp);

        db.insert(RentalLogContract.RentalLog.TABLE_NAME, null, values);
    }

    public Cursor readRentalLogRecord() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(RentalLogContract.RentalLog.SQL_READ_TABLE, null);
        return cursor;
    }
}
