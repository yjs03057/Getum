package com.example.getum;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
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
import java.text.SimpleDateFormat;
import java.util.Date;

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTimeStamp = dateFormat.format(new Date());
        dbHelper.insertRentalLogRecord("rental", 1, "00001", 1, currentTimeStamp);
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
        cursor.close();
        textView.setText(result);
    }
}