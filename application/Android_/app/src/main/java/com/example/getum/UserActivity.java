package com.example.getum;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.getum.SQLite.RentalLogContract;
import com.example.getum.SQLite.SQLiteDBHelper;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    ArrayList<RecordData> recordDataList;

    ImageButton backButton;

    SQLiteDBHelper helper;

    private int info_id;
    private String info_name;
    private String info_cardno;
    private String info_phoneno;

    private TextView tv_user_id;
    private TextView tv_user_name;
    private TextView tv_cardnum;
    private TextView tv_user_phoneno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        info_id = 0;
        info_name = "";
        info_cardno = "";
        info_phoneno = "";

        tv_user_id = (TextView)findViewById(R.id.tv_user_id);
        tv_user_name = (TextView)findViewById(R.id.tv_user_name);
        tv_cardnum = (TextView)findViewById(R.id.tv_cardnum);
        tv_user_phoneno = (TextView)findViewById(R.id.tv_user_phoneno);

        helper = new SQLiteDBHelper(this);

        this.InitializeRecordData();

        ListView userRecord = (ListView)findViewById(R.id.record_listview);
        final MyAdapter myAdapter = new MyAdapter(this, recordDataList);

        userRecord.setAdapter(myAdapter);

        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });

        if(((MainActivity)MainActivity.context).getLogin_flag() == 1){
            //로그인 완료

            Intent user_intent = getIntent();
            info_id = user_intent.getExtras().getInt("id");
            info_name = user_intent.getExtras().getString("name");
            info_cardno = user_intent.getExtras().getString("cardno");
            info_phoneno = user_intent.getExtras().getString("phoneno");
            Log.d("user",info_name +" " + info_cardno);

        }

        tv_user_id.setText(String.valueOf(info_id));
        tv_user_name.setText(info_name);
        tv_cardnum.setText(info_cardno);
        tv_user_phoneno.setText(info_phoneno);

    }

    public void InitializeRecordData(){

        recordDataList = new ArrayList<RecordData>();

        Cursor cursor = helper.getRentalLog(String.valueOf(info_id));
        while(cursor.moveToNext()){

            Log.d("user_log",cursor.getString(cursor.getColumnIndexOrThrow(RentalLogContract.RentalLog.COLUMN_LOG_NO)));
        }

        //recordDataList.add(new RecordData());

    }

}
