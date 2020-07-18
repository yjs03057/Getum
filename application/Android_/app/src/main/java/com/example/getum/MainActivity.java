package com.example.getum;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.getum.SQLite.SQLiteDBHelper;
import com.example.getum.SQLite.StorageContract;
import com.example.getum.SQLite.UmbrellaContract;
import com.google.zxing.integration.android.IntentIntegrator;

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
        Cursor cursor = dbHelper.readUmbrellaRecord();
        String result = "";

        result += "row 개수 : " + cursor.getCount() + "\n";
        while (cursor.moveToNext()) {
            String itemId = cursor.getString(cursor.getColumnIndexOrThrow(UmbrellaContract.Umbrella.COLUMN_ID));
            Integer storageId = cursor.getInt(cursor.getColumnIndexOrThrow(UmbrellaContract.Umbrella.COLUMN_STORAGE_ID));

            result += itemId + " " + storageId + "\n";
        }

        textView.setText(result);
        cursor.close();
    }
}