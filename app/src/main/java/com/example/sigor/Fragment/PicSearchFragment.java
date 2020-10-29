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
import com.example.sigor.Adapter.MyFotoAdapter;
import com.example.sigor.MainActivity;
import com.example.sigor.Model.Post;
import com.example.sigor.PythonRequest;
import com.example.sigor.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new SearchFragment();
                ((MainActivity)getActivity()).onFragmentChanged(selectedFragment);
            }
        });

        CropImage.activity().setAspectRatio(1,1).start(getContext(), PicSearchFragment.this);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            searchUri = result.getUri();
            uploadImage();
            image_search.setImageURI(searchUri);
            final String search_img =  searchUri.toString();

            // socket
//            ClientThread thread = new ClientThread();
//            thread.start();

            // cafe24
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String out_img = jsonResponse.getString("out_img");
                        searchList = out_img.split(", ");
                        myFotos(searchList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            PythonRequest pythonRequest = new PythonRequest(search_img, responseListener);
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(pythonRequest);
        } else {
            Toast.makeText(this.getContext(), "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        if(searchUri != null) {
            final StorageReference filereference = storageReference.child("search_pic");
            uploadTask = filereference.putFile(searchUri);
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
                    } else {

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {

        }
    }

    // cafe24
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
                        spic = spic.replaceAll("]", "");
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

    // socket
//    class ClientThread extends Thread {
//        @Override
//        public void run() {
//            try {
//                // 10.0.2.2
//                socket = new Socket("192.168.200.197", 5786);
//                Log.d("TCP", "Success");
//            } catch (Exception e) {
//                Log.d("TCP", "Fail");
//                e.printStackTrace();
//            }
//            try {
//                dis = new DataInputStream(socket.getInputStream());
//            } catch (Exception e) {
//                Log.d("TCP", "Buffer Error");
//                e.printStackTrace();
//            }
//            Log.d("TCP", "Buffer Success");
//
//            while (true) {
//                try {
//                    while (dis.available() > 0) {
//                        piclist = dis.readUTF();
//                        upload();
//                        Log.d("TCP", "" + piclist);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.d("TCP", "Finish");
//                }
//            }
//        }
//    }

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
