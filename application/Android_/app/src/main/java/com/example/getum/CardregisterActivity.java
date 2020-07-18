package com.example.getum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CardregisterActivity extends AppCompatActivity {
    private Button finish_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardregister);

        finish_button = (Button) findViewById(R.id.finish);// btn_second 버튼 아이디을 찾아 와라!
        finish_button.setOnClickListener(new View.OnClickListener() { //선언

            @Override
            public void onClick(View v) {//버튼을 눌렀을떄 second 이동 을 할꺼임

                Intent intent = new Intent(CardregisterActivity.this, MainActivity.class);
                startActivity(intent);//액티비티 이동
            }
        });

    }
}