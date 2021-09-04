package com.sandboxcode.betterbox.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.utils.Credentials;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesHolder> {

    private List<MovieModel> results = new ArrayList<>();
    public Context context;

    public PopularMoviesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PopularMoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_movie_item, parent, false);

        return new PopularMoviesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMoviesHolder holder, int position) {
        MovieModel movieModel = results.get(position);
        Log.v("TAG: ", String.valueOf(position));

        if (movieModel.getPoster_path() != null) {
            String imageUrl = Credentials.IMAGE_BASE_URL + "original" + movieModel.getPoster_path();
            Log.v("IMAGE URL: ", imageUrl);

//            Picasso.Builder builder = new Picasso.Builder(context);
//            builder.listener(new Picasso.Listener() {
//                @Override
//                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                    exception.printStackTrace();
//                }});
//
//            builder.build().load(imageUrl).into(holder.thumbnailImageView);


//            Picasso.get().load(imageUrl).into(holder.thumbnailImageView);


            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.thumbnailImageView);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<MovieModel> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class PopularMoviesHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailImageView;

        public PopularMoviesHolder(@NonNull View itemView) {
            super(itemView);

            thumbnailImageView = itemView.findViewById(R.id.popular_movie_item_thumbnail);
        }
    }

}
