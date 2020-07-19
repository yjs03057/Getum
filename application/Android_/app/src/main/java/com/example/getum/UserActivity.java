package com.example.getum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    ArrayList<RecordData> recordDataList;

    ImageButton backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        this.InitializeRecordData();

        ListView userRecord = (ListView)findViewById(R.id.record_listview);
        final MyAdapter myAdapter = new MyAdapter(this, recordDataList);

        userRecord.setAdapter(myAdapter);

        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(((MainActivity)MainActivity.context).getLogin_flag() == 1){
            //로그인 완료
        }

    }

    public void InitializeRecordData(){

        recordDataList = new ArrayList<RecordData>();

        //recordDataList.add(new RecordData());

    }

}
