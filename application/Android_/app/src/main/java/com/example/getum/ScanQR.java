package com.example.getum;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getum.SQLite.SQLiteDBHelper;
import com.example.getum.SQLite.StorageContract;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
    private Button payButton;
    private BottomSheetBehavior bottomSheetBehavior;
    private Button proceedPayButton;
    private RelativeLayout activityMain;
    private Intent beforeIntent;
    private RadioButton payCardNo;
    public String user_id;
    public String name;
    public String cardno;
    public String phoneno;
    public String storage_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeIntent = getIntent();
        getExtras(beforeIntent);

        setContentView(R.layout.activity_scan_qr);
        backButton = (ImageButton) findViewById(R.id.back_button);
        qrImage = (ImageView) findViewById(R.id.qr_image);
        payInfo = (TextView) findViewById(R.id.pay_info);
        locationText = (TextView) findViewById(R.id.location);
        qrBack = (Button) findViewById(R.id.qr_backbutton);
        payButton = (Button) findViewById(R.id.qr_paybutton);
        activityMain = (RelativeLayout) findViewById(R.id.activity_main);
        proceedPayButton = (Button) findViewById(R.id.proceed_pay);
        payCardNo = (RadioButton) findViewById(R.id.pay_card_no);
        payCardNo.setText(cardno);

        dbHelper = new SQLiteDBHelper(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScanQR.this, MainActivity.class);
                setExtras(intent);
                startActivity(intent);
                finish();
            }
        });

        qrBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScanQR.this, MainActivity.class);
                setExtras(intent);
                startActivity(intent);
                finish();
            }
        });

        LinearLayout bottomsheetLayout = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheetLayout);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }
            @Override public void onSlide(View bottomSheet, float slideOffset) {
            }
        });

        activityMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        proceedPayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScanQR.this, ProceedPay.class);
                setExtras(intent);
                intent.putExtra("storage_id", Integer.parseInt(storage_id));
                startActivity(intent);
                finish();
            }
        });

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.setPrompt("QR코드/바코드를 인식시켜주세요");
        qrScan.initiateScan();
    }

    protected void getExtras(Intent beforeIntent){
        user_id = beforeIntent.getExtras().getString("id");
        name = beforeIntent.getExtras().getString("name");
        cardno = beforeIntent.getExtras().getString("cardno");
        phoneno = beforeIntent.getExtras().getString("phoneno");
    }

    protected void setExtras(Intent newIntent){
        newIntent.putExtra("id", user_id);
        newIntent.putExtra("name", name);
        newIntent.putExtra("cardno", cardno);
        newIntent.putExtra("phoneno", phoneno);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    storage_id = obj.getString("id");
                    String type = obj.getString("type");
                    String location = "";
                    if(type.equals("rental")){
                        Cursor cursor = dbHelper.findStorageById(Integer.parseInt(storage_id));
                        while(cursor.moveToNext()){
                            location = cursor.getString(cursor.getColumnIndexOrThrow(StorageContract.Storage.COLUMN_LOCATION));
                            Toast.makeText(this, "스캔 완료", Toast.LENGTH_LONG).show();
                            cursor.close();
                        }
                        String img = type + "_" + storage_id;
                        qrImage.setImageResource(getResources().getIdentifier(img, "drawable", getPackageName()));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date());
                        cal.add(Calendar.DATE, 7);
                        String currentTimeStamp = dateFormat.format(cal.getTime());
                        payInfo.setText("반납기한 : " + currentTimeStamp);
                        locationText.setText("대여 우산함 위치 : " + location);
                    }
                    else if(type.equals("return")){
                        Intent intent = new Intent(ScanQR.this, ReturnActivity.class);
                        setExtras(intent);
                        intent.putExtra("storage_id", Integer.parseInt(storage_id));
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        finish();
    }
}