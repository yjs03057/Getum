package com.example.getum;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.getum.SQLite.SQLiteDBHelper;
import com.example.getum.SQLite.UserContract;

public class LoginActivity extends AppCompatActivity {

    SQLiteDBHelper helper;

    private Button login_button;
    EditText idEditText;
    EditText pwEditText;

    private ImageButton close_button;
    private Button sign_in_button;

    String sql;
    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idEditText = (EditText) findViewById(R.id.user_id);
        idEditText = (EditText) findViewById(R.id.user_password);

        close_button = findViewById(R.id.close_btn);
        sign_in_button = (Button) findViewById(R.id.sign_in);

        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() { //선언
            @Override
            public void onClick(View v) {

                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                if(id.length() == 0 || pw.length() == 0) {
                    Toast toast = Toast.makeText(LoginActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                sql = "SELECT id FROM user WHERE id = '" + id + "'";
                //cursor =


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);//액티비티 이동
            }
        });

        // helper에서 불러오기
        helper = new SQLiteDBHelper(this);
        // helper에 값 넣기
        //helper.insertUserRecord("""""""""""");




        close_button.setOnClickListener(new View.OnClickListener() { //선언
            @Override
            public void onClick(View v) {//버튼을 눌렀을떄 second 이동 을 할꺼임

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);//액티비티 이동
            }
        });

        sign_in_button.setOnClickListener(new View.OnClickListener() { //선언
            @Override
            public void onClick(View v) {//버튼을 눌렀을떄 second 이동 을 할꺼임

                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);//액티비티 이동
            }
        });
    }
}