package com.sandboxcode.betterbox.adapters;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.utils.Credentials;
import com.sandboxcode.betterbox.utils.OnMovieClickListener;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    private List<MovieModel> results;
    OnMovieClickListener clickListener;

    public SearchAdapter(List<MovieModel> results, OnMovieClickListener clickListener) {
        this.results = results;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);

        return new SearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        MovieModel movieModel = results.get(position);
        holder.titleTextView.setText(movieModel.getTitle());

        holder.itemView.setOnClickListener(v ->
                clickListener.onMovieClicked(movieModel.getId()));

        if (movieModel.getPoster_path() != null) {

            String imageUrl = Credentials.IMAGE_BASE_URL + "w154" + movieModel.getPoster_path();

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .placeholder(new ColorDrawable(0xFF018786))
                    .error(R.drawable.ic_baseline_no_image_24)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.thumbnailImageView);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnailImageView;
        private TextView titleTextView;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);

            thumbnailImageView = itemView.findViewById(R.id.activity_search_thumbnail);
            titleTextView = itemView.findViewById(R.id.activity_search_title);
        }
    }
}
