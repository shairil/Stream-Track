package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication1.MoviesDetails;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    ArrayList<String> images, titles, id;
    Context context;
    ProgressBar progressBar;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.getTextView().setText(titles.get(position));
        Glide.with(context)
                .load(images.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImageView());
        progressBar.setVisibility(View.INVISIBLE);
        holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context, MoviesDetails.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        private LinearLayout linearLayout;

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.poster);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    public MovieAdapter(Context context, ArrayList<String> titles, ArrayList<String> images, ProgressBar progressBar){
        this.titles = titles;
        this.images = images;
        this.context = context;
        this.progressBar = progressBar;
    }

}
