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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        idEditText = (EditText) findViewById(R.id.user_id);
        pwEditText = (EditText) findViewById(R.id.user_password);

        helper = new SQLiteDBHelper(this);

        close_button = findViewById(R.id.back_button);
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

                int findMatchedUser = helper.findMatchedUser(id);
                if(findMatchedUser != 1) {
                    Toast toast = Toast.makeText(LoginActivity.this, "아이디가 올바르지 않습니다..", Toast.LENGTH_SHORT);
                    toast.show();
                }

                Cursor cursor = helper.findMatchedUserpassword(pw);

                if(cursor.getCount() != 1) {
                    Toast toast = Toast.makeText(LoginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast  = Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT);
                    toast.show();

                    ((MainActivity)MainActivity.context).setLogin_flag(1);

                    cursor.moveToNext();

                    int info_userno = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_USER_NO));
                    String info_id = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_ID));
                    String info_name = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_NAME));
                    String info_cardno = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_CARD_NO));
                    String info_phoneno = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_PHONE_NO));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userno", info_userno);
                    intent.putExtra("id", info_id);
                    intent.putExtra("name", info_name);
                    intent.putExtra("cardno", info_cardno);
                    intent.putExtra("phoneno", info_phoneno);
                    startActivity(intent);//액티비티 이동
                    finish();
                }
            }
        });


        close_button.setOnClickListener(new View.OnClickListener() { //선언
            @Override
            public void onClick(View v) {//버튼을 눌렀을떄 second 이동 을 할꺼임

                finish();
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