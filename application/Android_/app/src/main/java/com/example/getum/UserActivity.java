package com.example.getum;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.getum.SQLite.RentalLogContract;
import com.example.getum.SQLite.SQLiteDBHelper;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    ArrayList<RecordData> recordDataList;

    ImageButton backButton;

    SQLiteDBHelper helper;
    SQLiteDBHelper helper_loc;

    private int info_userno;
    private String info_id;
    private String info_name;
    private String info_cardno;
    private String info_phoneno;

    private TextView tv_user_id;
    private TextView tv_user_name;
    private TextView tv_cardnum;
    private TextView tv_user_phoneno;

    private int logNo;
    private int rentflag;
    private String location;
    private String timestamp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        info_userno = -1;
        info_id = "";
        info_name = "";
        info_cardno = "";
        info_phoneno = "";

        tv_user_id = (TextView)findViewById(R.id.tv_user_id);
        tv_user_name = (TextView)findViewById(R.id.tv_user_name);
        tv_cardnum = (TextView)findViewById(R.id.tv_cardnum);
        tv_user_phoneno = (TextView)findViewById(R.id.tv_user_phoneno);

        logNo = -1;
        rentflag = -1;
        location = "";
        timestamp = "";

        helper = new SQLiteDBHelper(this);
        helper_loc = new SQLiteDBHelper(this);

        if(((MainActivity)MainActivity.context).getLogin_flag() == 1){
            //로그인 완료

            Intent user_intent = getIntent();
            info_userno = user_intent.getExtras().getInt("userno");
            info_id = user_intent.getExtras().getString("id");
            info_name = user_intent.getExtras().getString("name");
            info_cardno = user_intent.getExtras().getString("cardno");
            info_phoneno = user_intent.getExtras().getString("phoneno");
            Log.d("user",info_name +" " +info_userno + " "+ info_cardno);

        }

        this.InitializeRecordData();

        ListView userRecord = (ListView)findViewById(R.id.record_listview);
        final MyAdapter myAdapter = new MyAdapter(this, recordDataList);
        userRecord.setAdapter(myAdapter);



        tv_user_id.setText(info_id);
        tv_user_name.setText(info_name);
        tv_cardnum.setText(info_cardno);
        tv_user_phoneno.setText(info_phoneno);



        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });
    }

    public void InitializeRecordData(){

        recordDataList = new ArrayList<RecordData>();
        Log.d("user_log","호출됨");

        helper = new SQLiteDBHelper(this);
        helper_loc = new SQLiteDBHelper(this);

        Cursor cursor = helper.getRentalLog(Integer.toString(info_userno));
        while(cursor.moveToNext()){

            Log.d("user_log_num", Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow(RentalLogContract.RentalLog.COLUMN_USER_NO))));
            Log.d("user_log_num", Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow(RentalLogContract.RentalLog.COLUMN_STORAGE_ID))));

            logNo = cursor.getInt(cursor.getColumnIndexOrThrow(RentalLogContract.RentalLog.COLUMN_LOG_NO));

            if(cursor.getString(cursor.getColumnIndexOrThrow(RentalLogContract.RentalLog.COLUMN_TYPE)).equals("return")){
                rentflag = 1;
            }
            else{
                rentflag = 0;
            }

            location = helper_loc.getStoragename(cursor.getInt(cursor.getColumnIndexOrThrow(RentalLogContract.RentalLog.COLUMN_STORAGE_ID)));
            timestamp = cursor.getString(cursor.getColumnIndexOrThrow(RentalLogContract.RentalLog.COLUMN_TIMESTAMP));

            recordDataList.add(new RecordData(logNo, rentflag, location, timestamp));

        }

        //recordDataList.add(new RecordData());

    }

}
