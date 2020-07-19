package com.example.getum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CustomcenterActivity extends AppCompatActivity {
    private ImageButton close_button;
    private Button description_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customcenter);


        close_button = findViewById(R.id.back_button);// btn_second 버튼 아이디을 찾아 와라!
        close_button.setOnClickListener(new View.OnClickListener() { //선언


            @Override
            public void onClick(View v) {//버튼을 눌렀을떄 second 이동 을 할꺼임
                finish();
            }
        });
    }
}