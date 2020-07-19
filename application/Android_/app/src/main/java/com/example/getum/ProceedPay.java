package com.example.getum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.getum.SQLite.SQLiteDBHelper;

public class ProceedPay extends AppCompatActivity {
    private ImageButton backButton;
    private Button backToMenuButton;
    private Intent beforeIntent;
    private SQLiteDBHelper dbHelper;
    public String id;
    public String name;
    public String cardno;
    public String phoneno;
    public String storage_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_pay);

        dbHelper = new SQLiteDBHelper(this);

        backButton = (ImageButton) findViewById(R.id.pay_back_button);
        backToMenuButton = (Button) findViewById(R.id.back_to_menu);
        beforeIntent = getIntent();
        getExtras(beforeIntent);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProceedPay.this, MainActivity.class);
                setExtras(intent);
                startActivity(intent);
                finish();
            }
        });

        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProceedPay.this, MainActivity.class);
                setExtras(intent);
                startActivity(intent);
                finish();
            }
        });
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

    protected void setExtras(Intent newIntent){
        newIntent.putExtra("id", id);
        newIntent.putExtra("name", name);
        newIntent.putExtra("cardno", cardno);
        newIntent.putExtra("phoneno", phoneno);
    }
}