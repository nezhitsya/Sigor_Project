package com.example.sigor.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

// import com.example.sigor.Adapter.PostAdapter;
import com.example.sigor.Model.Post;
import com.example.sigor.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    // private PostAdapter postAdapter;
    private List<Post> postLists;

    private List<String> followingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        postLists = new ArrayList<>();
        //postAdapter = new PostAdapter(getContext(), postLists);
        //recyclerView.setAdapter(postAdapter);

        return view;
    }

//    private void checkFollowing() {
//        followingList = new ArrayList<>();
//
//        // DB
//    }

//    private void readPosts() {
//        // DB 연동
//    }
}
