package com.example.graduation_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHOlder> {

    private Context context;
    private List<ModelImage> imageList;

    private OnClickl listener;


    public MyAdapter(Context context, List<ModelImage> imageList,OnClickl listener) {
        this.context = context;
        this.imageList = imageList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_item,parent,false);
        return new ViewHOlder(view,listener);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder holder, int position) {


        Glide.with(context).load(imageList.get(position).getImageurl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface OnClickl{
       void onClick(int position);


    }

    class ViewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        OnClickl onClickl;
        public ViewHOlder(@NonNull View itemView,OnClickl onClickl) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
            this.onClickl=onClickl;
        }

        @Override
        public void onClick(View view) {
            listener.onClick(getAdapterPosition());
        }

    }

}
