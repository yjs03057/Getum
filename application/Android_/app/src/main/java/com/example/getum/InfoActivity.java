package com.example.getum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InfoActivity extends AppCompatActivity {
    private ImageButton close_button;
    private Button description;
    private Button howto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        description = findViewById(R.id.infotitle2);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, Howto.class);
                startActivity(intent);//액티비티 이동
            }
        });

        howto = findViewById(R.id.infotitle1);
        howto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, description.class);
                startActivity(intent);//액티비티 이동
            }
        });


        close_button = findViewById(R.id.close_btn);// btn_second 버튼 아이디을 찾아 와라!
        close_button.setOnClickListener(new View.OnClickListener() { //선언


            @Override
            public void onClick(View v) {//버튼을 눌렀을떄 second 이동 을 할꺼임
               finish();
            }

        });
    }
}