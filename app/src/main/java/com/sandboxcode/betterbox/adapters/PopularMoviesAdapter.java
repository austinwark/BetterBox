package com.sandboxcode.betterbox.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.utils.Credentials;
import com.sandboxcode.betterbox.utils.OnPopularMovieClickListener;


import java.util.ArrayList;
import java.util.List;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesHolder> {

    private List<MovieModel> results;
    public Context context;
    OnPopularMovieClickListener clickListener;

    public PopularMoviesAdapter(Context context, List<MovieModel> results, OnPopularMovieClickListener clickListener) {
        this.context = context;
        this.results = results;
        this.clickListener = clickListener;
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
        holder.titleTextView.setText(movieModel.getTitle());

        holder.getItemView().setOnClickListener(v ->
                clickListener.onPopularMovieClicked(movieModel.getId()));

        if (movieModel.getPoster_path() != null) {
//            String imageUrl = Credentials.IMAGE_BASE_URL + "original" + movieModel.getPoster_path();
            String imageUrl = Credentials.IMAGE_BASE_URL + "w154" + movieModel.getPoster_path();


            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .placeholder(new ColorDrawable(0xFF018786))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.titleTextView.setVisibility(View.INVISIBLE);
                            holder.thumbnailImageView.setVisibility(View.VISIBLE);
                            holder.progressBar.setVisibility(View.VISIBLE);
                            holder.progressBar.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                            holder.progressBar.setProgress((int) movieModel.getVote_average() * 10);
                            return false;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.thumbnailImageView);
        }



    }

    @Override
    public int getItemCount() {
        return results.size();
    }

//    public void setResults(List<MovieModel> results) {
//        this.results = results;
//        notifyDataSetChanged();
//    }

    class PopularMoviesHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView titleTextView;
        private ImageView thumbnailImageView;
        private ProgressBar progressBar;

        public PopularMoviesHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            titleTextView = itemView.findViewById(R.id.popular_movie_item_title);
            thumbnailImageView = itemView.findViewById(R.id.popular_movie_item_thumbnail);
            progressBar = itemView.findViewById(R.id.popular_movie_item_rating_bar);
        }

        public View getItemView() {
            return itemView;
        }
    }

}
