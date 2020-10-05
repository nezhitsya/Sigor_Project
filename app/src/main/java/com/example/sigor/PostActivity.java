package com.example.sigor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sigor.Adapter.ConnectFTP;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Random;

public class PostActivity extends AppCompatActivity {

    Uri imageUri;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView close, image_added;
    TextView post;
    EditText description;

//    int py_r, py_b, py_g, py_ratio;
//    String textFilePath;
//    String imageFilePath;
//    String currentPath;

//    private ConnectFTP ConnectFTP;
//    private final String TAG = "Connect FTP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        close = findViewById(R.id.close);
        image_added = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);

//        ConnectFTP = new ConnectFTP();

        storageReference = FirebaseStorage.getInstance().getReference("posts");

        //FTP와 연결하는 Thread 생성
//        boolean status = false;
//        status = ConnectFTP.ftpConnect("34.64.97.82", "sigor", "1120", 21);
//        currentPath = ConnectFTP.ftpGetDirectory();
//        if(status == true) {
//            Log.d(TAG, "Connection Success" + currentPath);
//        } else {
//            Log.d(TAG,"Connection Failed");
//        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostActivity.this, MainActivity.class));
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        CropImage.activity().setAspectRatio(1,1).start(PostActivity.this);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.show();

        if(imageUri != null) {
            final StorageReference filereference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            uploadTask = filereference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isComplete()) {
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

//                        String upFilePath = getExternalCacheDir().getAbsolutePath();
//                        ConnectFTP.ftpUploadFile(upFilePath + myUrl, "searchimage.jpg", currentPath);
//
//                        String newFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Image";
//                        File file = new File(newFilePath);
//                        file.mkdirs();
//                        newFilePath += "/result.txt";
//                        try {
//                            file = new File(newFilePath);
//                            file.createNewFile();
//                        } catch (Exception e) {
//                            ConnectFTP.ftpDownloadFile(currentPath + "result.txt", newFilePath);
//                        }

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postid = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postid);
                        hashMap.put("postimage", myUrl);
                        hashMap.put("description", description.getText().toString());
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                        hashMap.put("R", py_r);
//                        hashMap.put("G", py_g);
//                        hashMap.put("B", py_b);
//                        hashMap.put("Ratio", py_ratio);

                        reference.child(postid).setValue(hashMap);

                        progressDialog.dismiss();

//                        ConnectFTP.ftpDeleteFile("result.txt");
//                        disconnect();
                        startActivity(new Intent(PostActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(PostActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            image_added.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this, MainActivity.class));
            finish();
        }
    }

//    public void disconnect() {
//        new Thread(new Runnable() {
//            public void run() {
//                boolean result = ConnectFTP.ftpDisconnect();
//                if(result == true)
//                    Log.d(TAG, "DisConnection Success");
//                else
//                    Log.d(TAG, "DisConnection Success");
//            }
//        }).start();
//    }
}
