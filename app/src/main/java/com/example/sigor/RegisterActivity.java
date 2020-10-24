package com.example.sigor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, password2;
    TextView register, txt_login;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        register = findViewById(R.id.register);
        txt_login = findViewById(R.id.txt_login);

        auth = FirebaseAuth.getInstance();

        // 로그인 화면 전환
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Loading ... ");
                pd.show();

                String str_email = email.getText().toString();
                String str_username = username.getText().toString();
                String str_password = password.getText().toString();
                String str_password2 = password2.getText().toString();

                if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(RegisterActivity.this, "모든 칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(str_password.length() < 6 || !(str_password.equals(str_password2))) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 재설정해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    register(str_username, str_email, str_password);
                }
            }
        });

        // 회원가입
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = emailText.getText().toString();
//                String username = usernameText.getText().toString();
//                String password = passwordText.getText().toString();
//                String password2 = password2Text.getText().toString();
//
//                // 입력하지 않은 칸 존재시 발생하는 이벤트
//                if (email.equals("") & password.equals("") & username.equals("")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                    builder.setMessage("빈 칸을 모두 입력해주세요.");
//                    builder.setPositiveButton("OK", null);
//                    builder.create();
//                    builder.show();
//                    return;
//                }
//                // 비밀번호가 일치하지 않을 시 발생하는 이벤트
//                if (!(password.equals(password2))) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                    builder.setMessage("비밀번호가 일치하지 않습니다.");
//                    builder.setPositiveButton("OK", null);
//                    builder.create();
//                    builder.show();
//                    return;
//                }
//
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//                            if (success) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                builder.setMessage("Sign up!");
//                                builder.setPositiveButton("OK", null);
//                                builder.create();
//                                builder.show();
//                                Intent intent = new Intent(RegisterActivity.this, chProfileActivity.class);
//                                RegisterActivity.this.startActivity(intent);
//                                finish();
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                builder.setMessage("Sign up Failed!");
//                                builder.setNegativeButton("OK", null);
//                                builder.create();
//                                builder.show();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                RegisterRequest registerRequest = new RegisterRequest(email, password, username, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
//                queue.add(registerRequest);
//            }
//        });
    }

    private void register(final String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username.toLowerCase());
                    hashMap.put("bio", "");
                    hashMap.put("status", "offline");
                    hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/sigor-1dc3c.appspot.com/o/person-icon.png?alt=media&token=4c6ae521-6dcc-4d70-a831-908f92d7e6ce");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                pd.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, chProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "회원가입을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
