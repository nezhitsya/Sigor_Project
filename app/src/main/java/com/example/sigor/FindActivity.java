package com.example.sigor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FindActivity extends AppCompatActivity {

    EditText email, username;
    TextView confirm, back;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        email = findViewById(R.id.emailText);
        username = findViewById(R.id.usernameText);
        confirm = findViewById(R.id.confirm);
        back = findViewById(R.id.back);

        // 로그인 화면 전환
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = email.getText().toString().trim();
                String str_username = username.getText().toString();

                auth = FirebaseAuth.getInstance();

                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_username)) {
                    Toast.makeText(getApplicationContext(), "빈칸을 모두 작성해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(str_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(FindActivity.this, "새로운 비밀번호를 전송하였습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FindActivity.this, "이메일을 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Intent findIntent = new Intent(FindActivity.this, LoginActivity.class);
                FindActivity.this.startActivity(findIntent);
            }
        });
    }
}
