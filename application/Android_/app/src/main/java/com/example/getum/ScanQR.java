package com.example.getum;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getum.SQLite.SQLiteDBHelper;
import com.example.getum.SQLite.StorageContract;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanQR extends AppCompatActivity {
    private IntentIntegrator qrScan;
    private SQLiteDBHelper dbHelper;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        backButton = (ImageButton) findViewById(R.id.back_button);
        dbHelper = new SQLiteDBHelper(this);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.setPrompt("QR코드/바코드를 인식시켜주세요");
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    String id = obj.getString("id");
                    String type = obj.getString("type");

                    if(type.equals("rental")){
                        Cursor cursor = dbHelper.findStorageById(Integer.parseInt(id));
                        while(cursor.moveToNext()){
                            String location = cursor.getString(cursor.getColumnIndexOrThrow(StorageContract.Storage.COLUMN_LOCATION));
                            Toast.makeText(this, "스캔 완료!", Toast.LENGTH_LONG).show();
                            cursor.close();
                            textView.setText("우산함 아이디 : " + id + "\n우산함 주소 : " + location);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}