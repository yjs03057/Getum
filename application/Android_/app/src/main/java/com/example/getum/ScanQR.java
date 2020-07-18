package com.example.getum;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getum.SQLite.SQLiteDBHelper;
import com.example.getum.SQLite.StorageContract;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScanQR extends AppCompatActivity {
    private IntentIntegrator qrScan;
    private SQLiteDBHelper dbHelper;
    private ImageButton backButton;
    private ImageView qrImage;
    private TextView payInfo;
    private TextView locationText;
    private Button qrBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        backButton = (ImageButton) findViewById(R.id.back_button);
        qrImage = (ImageView) findViewById(R.id.qr_image);
        payInfo = (TextView) findViewById(R.id.pay_info);
        locationText = (TextView) findViewById(R.id.location);
        qrBack = (Button) findViewById(R.id.qr_backbutton);
        dbHelper = new SQLiteDBHelper(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScanQR.this, MainActivity.class);
                startActivity(intent);
            }
        });

        qrBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScanQR.this, MainActivity.class);
                startActivity(intent);
            }
        });

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
                    String location = "";
                    if(type.equals("rental")){
                        Cursor cursor = dbHelper.findStorageById(Integer.parseInt(id));
                        while(cursor.moveToNext()){
                            location = cursor.getString(cursor.getColumnIndexOrThrow(StorageContract.Storage.COLUMN_LOCATION));
                            Toast.makeText(this, "스캔 완료", Toast.LENGTH_LONG).show();
                            cursor.close();
                        }
                        String img = type + "_" + id;
                        qrImage.setImageResource(getResources().getIdentifier(img, "drawable", getPackageName()));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date());
                        cal.add(Calendar.DATE, 7);
                        String currentTimeStamp = dateFormat.format(cal.getTime());
                        payInfo.setText("반납기한 : " + currentTimeStamp);
                        locationText.setText("대여 우산함 위치 : " + location);
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