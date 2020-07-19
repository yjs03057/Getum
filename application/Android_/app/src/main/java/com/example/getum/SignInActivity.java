package com.example.getum;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.getum.MainActivity;
import com.example.getum.R;
import com.example.getum.SQLite.SQLiteDBHelper;
import com.example.getum.SQLite.UserContract;

import java.text.DecimalFormat;

public class SignInActivity extends AppCompatActivity {
    SQLiteDBHelper helper;

    private ImageButton close_button;
    private Button finished;

    EditText nameEditText;
    EditText idEditText;
    EditText pwEditText;
    EditText cardnumEditText;
    EditText phoneEditText;
    private Button nextbutton;
    DecimalFormat card_df = new DecimalFormat("####,####,####,####");
    String result_card = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        helper = new SQLiteDBHelper(this);


        nameEditText = (EditText) findViewById(R.id.name);
        idEditText = (EditText) findViewById(R.id.sign_in_id);
        pwEditText = (EditText) findViewById(R.id.sign_in_pw);
        cardnumEditText = (EditText) findViewById(R.id.cardnumber);
        phoneEditText = (EditText) findViewById(R.id.phonenumber);

        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        close_button = findViewById(R.id.back_button);
        finished = findViewById(R.id.finished_button);

        finished.setOnClickListener(new View.OnClickListener() { //선언
            @Override
            public void onClick(View v) {
            // 아이디 중복확인
            String id = idEditText.getText().toString();
            int id_check = helper.findMatchedUser(id);
            if(id_check == 1) {
                Toast toast  = Toast.makeText(SignInActivity.this, "중복되는 아이디입니다.", Toast.LENGTH_SHORT);
                toast.show();
            }

            else{
                //helper에 값 넣기
                helper.insertUserRecord(idEditText.getText().toString(), pwEditText.getText().toString(), nameEditText.getText().toString(), cardnumEditText.getText().toString(), phoneEditText.getText().toString());
                Toast toast  = Toast.makeText(SignInActivity.this, "회원가입 완료", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);//액티비티 이동
            }
            }
        });

        close_button.setOnClickListener(new View.OnClickListener() { //선언
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);//액티비티 이동
            }
        });

        close_button = findViewById(R.id.back_button);
        close_button.setOnClickListener(new View.OnClickListener() { //선언
            @Override
            public void onClick(View v) {//버튼을 눌렀을떄 second 이동 을 할꺼임

                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);//액티비티 이동
                finish();
            }
        });

    }
}