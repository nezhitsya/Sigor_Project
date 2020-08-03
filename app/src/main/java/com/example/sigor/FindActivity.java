package com.example.sigor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FindActivity extends AppCompatActivity {

    EditText email, username, nickname;
    TextView confirm, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        email = findViewById(R.id.emailText);
        username = findViewById(R.id.usernameText);
        nickname = findViewById(R.id.nickname);
        confirm = findViewById(R.id.confirm);
        back = findViewById(R.id.back);

        // 로그인 화면 전환
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
