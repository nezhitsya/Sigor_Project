//package com.example.sigor.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.sigor.Model.User;
//
//import java.util.List;
//
//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
//
//    private Context mContext;
//    private List<User> mUser;
//
//    public UserAdapter(Context mContext, List<User> mUser) {
//        this.mContext = mContext;
//        this.mUser = mUser;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(mContext).inflate(android.support.v4.R.layout.user_item, viewGroup, flase);
//        return new UserAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//}
