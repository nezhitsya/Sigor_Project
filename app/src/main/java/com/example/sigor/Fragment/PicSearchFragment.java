package com.example.sigor.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sigor.Adapter.MyFotoAdapter;
import com.example.sigor.MainActivity;
import com.example.sigor.Model.Post;
import com.example.sigor.PythonRequest;
import com.example.sigor.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PicSearchFragment extends Fragment {

    Uri searchUri;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView close;
    CircleImageView image_search;

    private RecyclerView recyclerView;
    private MyFotoAdapter myFotoAdapter;
    private ArrayList<Post> finalList;
    private String[] searchList;

    private Handler mHandler;
    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket socket;
    private String html = "";
    private String piclist = "";

    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_search, container, false);

        close = view.findViewById(R.id.close);
        image_search = view.findViewById(R.id.image_search);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        finalList = new ArrayList<>();
        myFotoAdapter = new MyFotoAdapter(getContext(), finalList);
        recyclerView.setAdapter(myFotoAdapter);

        progressBar = view.findViewById(R.id.progress_circular);

        storageReference = FirebaseStorage.getInstance().getReference("search");
        storageReference.child("search_pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                Glide.with(getContext()).load(uri).into(image_search);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new SearchFragment();
                ((MainActivity)getActivity()).onFragmentChanged(selectedFragment);
            }
        });

        // socket
       ClientThread thread = new ClientThread();
       thread.start();

        return view;
    }

    // socket
    class ClientThread extends Thread {
        @Override
        public void run() {
            try {
                socket = new Socket("169.254.11.228", 5786);
                Log.d("TCP", "Success");
            } catch (Exception e) {
                Log.d("TCP", "Fail");
                e.printStackTrace();
            }
            try {
                dis = new DataInputStream(socket.getInputStream());
            } catch (Exception e) {
                Log.d("TCP", "Buffer Error");
                e.printStackTrace();
            }
            Log.d("TCP", "Buffer Success");

            while (true) {
                try {
                    while (dis.available() > 0) {
                        piclist = dis.readUTF();
                        upload();
                        Log.d("TCP", "" + piclist);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TCP", "Finish");
                }
            }
        }
    }

    // socket
    public void upload() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                finalList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    searchList = piclist.split(", ");

                    for(String spic : searchList) {
                        spic = spic.replaceAll("'", "");
                        spic = spic.replaceAll(" ", "");
                        spic = spic.replaceAll("\\[", "");
                        spic = spic.replaceAll("]", "");
                        spic = spic.replaceAll(",", "");
                        spic = spic.replaceAll("\"", "");
                        spic = spic.replaceAll(System.getProperty("line.separator"), "");
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