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

//    TCP_Client tc;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

         // TCP
//         tc = new TCP_Client();
//         tc.execute(this);

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

//    public static class TCP_Client extends AsyncTask {
//        protected static String SERVER_IP = "192.168.200.152";
//        protected static int PORT = 5786;
//
//        protected Object doInBackground(Object... params) {
//            try {
//                Log.d("TCP", "Server Connecting");
//                InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
//                Socket socket = new Socket(serverAddress, PORT);
//
//                try {
//                    System.out.println("Searching data");
//                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/");
//                    DataInputStream dis = new DataInputStream(new FileInputStream(file));
//                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//
//                    long fileSize = file.length();
//                    byte[] buf = new byte[1024];
//
//                    long totalReadBytes = 0;
//                    int readBytes;
//                    System.out.println("End Searching");
//
//                    while ((readBytes = dis.read(buf)) > 0) {
//                        System.out.println("while");
//                        dos.write(buf, 0, readBytes);
//                        totalReadBytes += readBytes;
//                    }
//                    System.out.println("Sending Data");
//                    dos.close();
//                    System.out.println("End Sending");
//                } catch (Exception e) {
//                    Log.d("TCP", "don't send message");
//                    e.printStackTrace();
//                }
//            } catch (UnknownHostException e) {
//                Log.d("TCP", "Cannot connect");
//                e.printStackTrace();
//            } catch (IOException e) {
//                Log.d("TCP", "Fail");
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
}
