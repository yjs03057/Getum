package com.example.getum.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

        boolean dbExist = checkDataBase();

        if(dbExist)
        {
            Log.v("DB Exists", "db exists");
            // By calling this method here onUpgrade will be called on a
            // writeable database, but only if the version number has been
            // bumped
            //onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
        }

        boolean dbExist1 = checkDataBase();
        if(!dbExist1)
        {
            this.getReadableDatabase();
            try
            {
                this.close();
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }

    }
    //Check database already exist or not
    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try
        {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(SQLiteException e)
        {
        }
        return checkDB;
    }
    //Copies your database from your local assets-folder to the just created empty database in the system folder
    private void copyDataBase() throws IOException
    {

        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[2024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
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

    public Cursor findStorageById(Integer id){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + StorageContract.Storage.TABLE_NAME + " WHERE id=" + id;
        Cursor cursor = db.rawQuery(query, null);
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

    public int findMatchedUser(String id){
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM user " + " WHERE id= '" + id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor.getCount();
    }

    public Cursor findUserNoByUserId(String user_id){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM user " + "WHERE id='"+ user_id+"'";
        Cursor cursor = db.rawQuery(query, null);
        Log.d("query: ", query + ", " + cursor.getCount());

        return cursor;
    }

    public int UmbrellaInStorage(String storage_id) { //현재 Storage에 있는 Umbrella 개수
        SQLiteDatabase db = getReadableDatabase();
        String query1 = "SELECT * FROM umbrella WHERE storage_id =" + storage_id;

        Cursor cursor = db.rawQuery(query1, null);

        return cursor.getCount();
    }

    public Cursor findMatchedUserpassword(String password){
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM user "  + " WHERE pw= '" + password+"'";
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }

    public Cursor getRentalLog(String user_no){
        SQLiteDatabase db = getReadableDatabase();
        String query1 = "SELECT * FROM rental_log WHERE user_no = "+user_no;
        Cursor cursor = db.rawQuery(query1, null);

        Log.d("query",query1);
        return cursor;
    }

    public Cursor getUmbrellaFromStorage(Integer storage_id){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM umbrella "+"WHERE storage_id="+storage_id+" ORDER BY updated_at";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getUmbrellaFromRentalLog(Integer user_no){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM rental_log "+"WHERE user_no="+user_no+" ORDER BY timestamp DESC";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public void updateUmbrellaWithStorage(String umbrella_id, Integer storage_id, String updated_at){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(UmbrellaContract.Umbrella.COLUMN_ID, umbrella_id);
        values.put(UmbrellaContract.Umbrella.COLUMN_STORAGE_ID, storage_id);
        values.put(UmbrellaContract.Umbrella.COLUMN_UPDATED_AT, updated_at);
        db.update(UmbrellaContract.Umbrella.TABLE_NAME, values, "id='" + umbrella_id + "'", null);
    }

    public String getStoragename(int storage_id){
        SQLiteDatabase db = getReadableDatabase();
        String query1 = "SELECT * FROM storage WHERE id = " + Integer.toString(storage_id);
        Cursor cursor = db.rawQuery(query1, null);

        cursor.moveToNext();
        Log.d("query",query1);
        Log.d("locname",cursor.getString(cursor.getColumnIndexOrThrow(StorageContract.Storage.COLUMN_LOCATION)));
        return cursor.getString(cursor.getColumnIndexOrThrow(StorageContract.Storage.COLUMN_LOCATION));
    }
}