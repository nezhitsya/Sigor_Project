package com.example.sigor.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sigor.Fragment.PostDetailFragment;
import com.example.sigor.Model.Post;
import com.example.sigor.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MyFotoAdapter extends RecyclerView.Adapter<MyFotoAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Post> mPosts;

    public MyFotoAdapter(Context context, List<Post> posts) {
        mContext = context;
        mPosts = posts;
    }

    @NonNull
    @Override
    public MyFotoAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fotos_item, viewGroup, false);
        return new MyFotoAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyFotoAdapter.ImageViewHolder viewHolder, final int i) {
        final Post post = mPosts.get(i);
        Glide.with(mContext).load(post.getPostimage()).into(viewHolder.post_image);

        viewHolder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("postid", post.getPostid());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostDetailFragment()).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView post_image;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image);
        }
    }
}
