package com.example.sigor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.tv.TvContract;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LogoActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            Handler handler = new Handler() {
                public void handleMessage (Message msg) {
                    super.handleMessage(msg);
                    startActivity(new Intent(LogoActivity.this, MainActivity.class));
                    finish();
                }
            };
            handler.sendEmptyMessageDelayed(0, 3000);
        } else {

            Handler handler = new Handler() {
                public void handleMessage (Message msg) {
                    super.handleMessage(msg);
                    // startActivity(new Intent(LogoActivity.this, LoginActivity.class));
                    startActivity(new Intent(LogoActivity.this, OnBoardingActivity.class));
                    finish();
                }
            };
            handler.sendEmptyMessageDelayed(0, 3000);

        }

    }
}
