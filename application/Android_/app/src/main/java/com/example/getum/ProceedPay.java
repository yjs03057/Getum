package com.example.getum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProceedPay extends AppCompatActivity {
    private ImageButton backButton;
    private Button backToMenuButton;
    private Intent beforeIntent;
    public String id;
    public String name;
    public String cardno;
    public String phoneno;
    public String storage_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_pay);

        backButton = (ImageButton) findViewById(R.id.pay_back_button);
        backToMenuButton = (Button) findViewById(R.id.back_to_menu);
        beforeIntent = getIntent();
        getExtras(beforeIntent);
        insertToDB();
    }

    protected void insertToDB(){

    }

    protected void getExtras(Intent beforeIntent){
        id = beforeIntent.getExtras().getString("id");
        name = beforeIntent.getExtras().getString("name");
        cardno = beforeIntent.getExtras().getString("cardno");
        phoneno = beforeIntent.getExtras().getString("phoneno");
        storage_id = beforeIntent.getExtras().getString("storage_id");
    }
}