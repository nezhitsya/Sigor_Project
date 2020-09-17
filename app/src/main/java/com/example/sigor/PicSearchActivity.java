package com.example.sigor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sigor.Adapter.MyFotoAdapter;
import com.example.sigor.Fragment.SearchFragment;
import com.example.sigor.Model.Post;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PicSearchActivity extends AppCompatActivity {

    Uri searchUri;

    ImageView close;
    CircleImageView image_search;

    RecyclerView recyclerView_search;
    MyFotoAdapter myFotoAdapter;
    ArrayList<Post> searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_search);

        close = findViewById(R.id.close);
        image_search = findViewById(R.id.image_search);

        recyclerView_search = findViewById(R.id.recyclerView_search);
        recyclerView_search.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView_search.setLayoutManager(linearLayoutManager);
        searchList = new ArrayList<>();
        myFotoAdapter = new MyFotoAdapter(getApplicationContext(), searchList);
        recyclerView_search.setAdapter(myFotoAdapter);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CropImage.activity().setAspectRatio(1,1).start(PicSearchActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            searchUri = result.getUri();
            image_search.setImageURI(searchUri);
        } else {
            Toast.makeText(this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
    // 파이썬 파일에 searchUri 경로 input
    // output을 받아와 firebase 데이터와 비교 >> how???

    private void getPicture() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                searchList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
//                    if (post.getR().equals(R)||post.getG().equals(G)||post.getB().equals(B)||post.getRatio().equals(Ratio)) {
//                        searchList.add(post);
//                    }
                }
                Collections.reverse(searchList);
                myFotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
