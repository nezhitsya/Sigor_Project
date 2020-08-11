//package com.example.sigor.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.sigor.Model.Post;
//import com.example.sigor.R;
//
//import java.util.List;
//
//public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
//
//    public Context mContext;
//    public List<Post> mPost;
//
//    public PostAdapter(Context mContext, List<Post> mPost) {
//        this.mContext = mContext;
//        this.mPost = mPost;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, viewGroup, false);
//        return new PostAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//
//        Post post = mPost.get(i);
//
//        Glide.with(mContext).load(post.getPostid()).into(viewHolder.post_image);
//
//        if(post.getDescription().equals("")) {
//            viewHolder.description.setVisibility(View.GONE);
//        } else {
//            viewHolder.description.setVisibility(View.VISIBLE);
//            viewHolder.description.setText(post.getDescription());
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mPost.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        public ImageView image_profile, post_image, like, comment, save;
//        public TextView username, likes, publisher, description, comments;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            image_profile = itemView.findViewById(R.id.image_profile);
//            post_image = itemView.findViewById(R.id.post_image);
//            like = itemView.findViewById(R.id.like);
//            comment = itemView.findViewById(R.id.comment);
//            save = itemView.findViewById(R.id.save);
//            username = itemView.findViewById(R.id.username);
//            likes = itemView.findViewById(R.id.likes);
//            publisher = itemView.findViewById(R.id.publisher);
//            description = itemView.findViewById(R.id.description);
//            comments = itemView.findViewById(R.id.comments);
//        }
//    }
//
//    private void isLikes(String postid, ImageView imageView) {
//        //DB
//    }
//
//    private void publisherInfo(ImageView image_profile, TextView username, TextView publisher, String email) {
//        // DB
//    }
//}
