package com.example.sigor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView sign_up, txt_find;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        login = findViewById(R.id.login);
        sign_up = findViewById(R.id.sign_up);
        txt_find = findViewById(R.id.txt_find);

        auth = FirebaseAuth.getInstance();

        // 회원가입 창으로 이동
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        // 비밀번호 찾기 창으로 이동
        txt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent findIntent = new Intent(LoginActivity.this, FindActivity.class);
                LoginActivity.this.startActivity(findIntent);
            }
        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String email = emailText.getText().toString();
//                final String password = passwordText.getText().toString();
//
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//                            if (success) {
//                                String email = jsonResponse.getString("email");
//                                String password = jsonResponse.getString("password");
//                                // 로그인 성공 시 메인화면 이동
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                intent.putExtra("email", email);
//                                intent.putExtra("password", password);
//                                LoginActivity.this.startActivity(intent);
//                                finish();
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                builder.setMessage("LogIn Failed!");
//                                builder.setNegativeButton("OK", null);
//                                builder.create();
//                                builder.show();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(loginRequest);
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Loading ... ");
                pd.show();

                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(LoginActivity.this, "빈 칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(str_email, str_password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        pd.dismiss();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        pd.dismiss();
                                    }
                                });
                            } else {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "로그인이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}