package com.example.getum;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    ArrayList<RecordData> recordDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        this.InitializeRecordData();

        ListView userRecord = (ListView)findViewById(R.id.record_listview);
        final MyAdapter myAdapter = new MyAdapter(this, recordDataList);

        userRecord.setAdapter(myAdapter);


    }

    public void InitializeRecordData(){

        recordDataList = new ArrayList<RecordData>();

        //recordDataList.add(new RecordData());

    }

}
