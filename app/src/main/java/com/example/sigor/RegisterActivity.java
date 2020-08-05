package com.example.sigor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private String email;
    private String password;
    private String nickname;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usernameText = (EditText) findViewById(R.id.usernameText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText password2Text = (EditText) findViewById(R.id.password2Text);
        TextView register = findViewById(R.id.register);
        TextView txt_login = findViewById(R.id.txt_login);

        // 로그인 화면 전환
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 회원가입
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                String password2 = password2Text.getText().toString();

                // 입력하지 않은 칸 존재시 발생하는 이벤트
                if (email.equals("") & password.equals("") & username.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("빈 칸을 모두 입력해주세요.");
                    builder.setPositiveButton("OK", null);
                    builder.create();
                    builder.show();
                    return;
                }
                // 비밀번호가 일치하지 않을 시 발생하는 이벤트
                if (!(password.equals(password2))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("비밀번호가 일치하지 않습니다.");
                    builder.setPositiveButton("OK", null);
                    builder.create();
                    builder.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Sign up!");
                                builder.setPositiveButton("OK", null);
                                builder.create();
                                builder.show();
                                Intent intent = new Intent(RegisterActivity.this, chProfileActivity.class);
                                RegisterActivity.this.startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Sign up Failed!");
                                builder.setNegativeButton("OK", null);
                                builder.create();
                                builder.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(email, password, username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
