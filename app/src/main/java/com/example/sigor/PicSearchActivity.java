package com.example.sigor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sigor.Adapter.MyFotoAdapter;
import com.example.sigor.Model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PicSearchActivity extends AppCompatActivity {

    Uri searchUri;

    ImageView close;
    CircleImageView image_search;

    RecyclerView recyclerView_search;
    MyFotoAdapter myFotoAdapter;
    ArrayList<Post> finalList;
    String[] searchList;

    ProgressBar progressBar;

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
        finalList = new ArrayList<>();
        myFotoAdapter = new MyFotoAdapter(getApplicationContext(), finalList);
        recyclerView_search.setAdapter(myFotoAdapter);

        progressBar = findViewById(R.id.progress_circular);

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
            final String search_img = searchUri.toString();
            Log.d("TCP", search_img);

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String out_img = jsonResponse.getString("out_img");
                            searchList = out_img.split(", ");
                            myFotos(searchList);

//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
//                            reference.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    finalList.clear();
//                                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                        Post search = snapshot.getValue(Post.class);
//                                        String searchlist = searchList[1];
//                                        if(search.getPostimage().equals(searchlist)) {
//                                            finalList.add(search);
//                                        }
//                                    }
//                                    Collections.reverse(finalList);
//                                    myFotoAdapter.notifyDataSetChanged();
//                                    progressBar.setVisibility(View.GONE);
//                                }
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                PythonRequest pythonRequest = new PythonRequest(search_img, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PicSearchActivity.this);
                queue.add(pythonRequest);
            } else {
                Toast.makeText(this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                onBackPressed();
        }
    }

    private void myFotos(final String [] searchList) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                finalList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                    for(String spic : searchList) {
                        spic = spic.replaceAll("'", "");
                        spic = spic.replaceAll(" ", "");
                        spic = spic.replaceAll("\\[", "");
                        spic = spic.replaceAll("\\]", "");
                        if(post.getPostimage().equals(spic)) {
                            finalList.add(post);
                        }
                    }
                }
                myFotoAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
