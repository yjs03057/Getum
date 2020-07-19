package com.example.getum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class InfoActivity extends AppCompatActivity {
    private ImageButton close_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        close_button = findViewById(R.id.close_btn);// btn_second 버튼 아이디을 찾아 와라!
        close_button.setOnClickListener(new View.OnClickListener() { //선언


            @Override
            public void onClick(View v) {//버튼을 눌렀을떄 second 이동 을 할꺼임
               finish();
            }
        });
    }
}