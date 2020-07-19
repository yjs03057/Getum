package com.example.getum;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.getum.SQLite.SQLiteDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProceedPay extends AppCompatActivity {
    private ImageButton backButton;
    private Button backToMenuButton;
    private Intent beforeIntent;
    private SQLiteDBHelper dbHelper;
    public Integer user_no;
    public String id;
    public String name;
    public String cardno;
    public String phoneno;
    public Integer storage_id;
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
        Cursor user_cursor = dbHelper.findUserNoByUserId(id);
        user_cursor.moveToNext();
        Integer user_no = user_cursor.getInt(user_cursor.getColumnIndexOrThrow("user_no"));

        Cursor umbrella_cursor = dbHelper.getUmbrellaFromStorage(storage_id);
        umbrella_cursor.moveToNext();
        String umbrella_id = umbrella_cursor.getString(umbrella_cursor.getColumnIndexOrThrow("id"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTimeStamp = dateFormat.format(new Date());

        dbHelper.insertRentalLogRecord("rental", user_no, umbrella_id, storage_id, currentTimeStamp);
        dbHelper.updateUmbrellaWithStorage(umbrella_id, -1, currentTimeStamp);

    }

    protected void getExtras(Intent beforeIntent){
        user_no = beforeIntent.getExtras().getInt("userno");
        id = beforeIntent.getExtras().getString("id");
        name = beforeIntent.getExtras().getString("name");
        cardno = beforeIntent.getExtras().getString("cardno");
        phoneno = beforeIntent.getExtras().getString("phoneno");
        storage_id = beforeIntent.getExtras().getInt("storage_id");
    }

    protected void setExtras(Intent newIntent){
        newIntent.putExtra("userno", user_no);
        newIntent.putExtra("id", id);
        newIntent.putExtra("name", name);
        newIntent.putExtra("cardno", cardno);
        newIntent.putExtra("phoneno", phoneno);
    }

}