package com.example.getum;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.res.AssetManager;
import com.example.getum.SQLite.SQLiteDBHelper;
import com.example.getum.SQLite.StorageContract;
import com.example.getum.SQLite.UmbrellaContract;
import com.example.getum.SQLite.UserContract;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button createQRBtn;
    private Button scanQRBtn;
    private TextView textView;
    private SQLiteDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createQRBtn = (Button) findViewById(R.id.createQR);
        scanQRBtn = (Button) findViewById(R.id.scanQR);
        textView = (TextView) findViewById(R.id.tblStorage);

        dbHelper = new SQLiteDBHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printTable();

        createQRBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateQR.class);
                startActivity(intent);
            }
        });

        scanQRBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanQR.class);
                startActivity(intent);
            }
        });
    }

    protected void printTable(){
        Cursor cursor = dbHelper.readUserRecord();
        String result = "";

        result += "row 개수 : " + cursor.getCount() + "\n";
        while (cursor.moveToNext()) {
            Integer userNo = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_USER_NO));
            Integer itemId = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_ID));
            Integer pw = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_PW));
            Integer name = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_NAME));
            Integer cardNo = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_CARD_NO));
            Integer phoneNo = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_PHONE_NO));
            result += userNo + " " + itemId + " " + pw + " " + name + " " + cardNo + " " + phoneNo + "\n";
        }

        textView.setText(result);
        cursor.close();
    }
}