package com.example.getum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.getum.MainActivity;
import com.example.getum.R;

public class SignInActivity extends AppCompatActivity {

    private Button nextbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        nextbutton = (Button) findViewById(R.id.next_button);// btn_second 버튼 아이디을 찾아 와라!
        nextbutton.setOnClickListener(new View.OnClickListener() { //선언

            @Override
            public void onClick(View v) {//버튼을 눌렀을떄 second 이동 을 할꺼임

                Intent intent = new Intent(SignInActivity.this, CardregisterActivity.class);
                startActivity(intent);//액티비티 이동
            }
        });


    }
}