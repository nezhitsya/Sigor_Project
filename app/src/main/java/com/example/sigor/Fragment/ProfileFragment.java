package com.example.sigor.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sigor.LoginActivity;
import com.example.sigor.LoginRequest;
import com.example.sigor.MainActivity;
import com.example.sigor.R;
import com.example.sigor.UserRequest;

import org.json.JSONObject;


public class ProfileFragment extends Fragment {

    ImageView image_profile, options;
    TextView posts, followers, following, username, bio;
    Button edit_profile;
    ImageButton my_fotos, saved_fotos;
    String profileid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        image_profile = view.findViewById(R.id.image_profile);
        options = view.findViewById(R.id.options);
        posts = view.findViewById(R.id.posts);
        followers = view.findViewById(R.id.followers);
        following = view.findViewById(R.id.following);
        username = view.findViewById(R.id.username);
        bio = view.findViewById(R.id.bio);
        edit_profile = view.findViewById(R.id.edit_profile);
        my_fotos = view.findViewById(R.id.my_fotos);
        saved_fotos = view.findViewById(R.id.saved_fotos);

        userInfo();
        getFollowers();
        getNrPosts();

        if(profileid.equals(username)) {
            edit_profile.setText("Edit Profile");
        } else {
            checkFollow();
            saved_fotos.setVisibility(View.GONE);
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn = edit_profile.getText().toString();

                if(btn.equals("Edit Profile")) {
                    // go to EditProfile
                } else if(btn.equals("follow")) {
                    // db
                } else if(btn.equals("following")) {
                    // db
                }
            }
        });
        return view;
    }

    private void userInfo() {
        // db에서 유저네임, 바이오, 이미지 url 가져오기
    }

    private void checkFollow() {
        // db
    }

    private void getFollowers() {
        // db
    }

    private void getNrPosts() {
        // db
    }
}
