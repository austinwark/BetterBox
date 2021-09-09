package com.sandboxcode.betterbox.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.models.CastModel;
import com.sandboxcode.betterbox.models.CrewModel;
import com.sandboxcode.betterbox.models.MovieDetailsModel;
import com.sandboxcode.betterbox.utils.Credentials;
import com.sandboxcode.betterbox.viewmodels.MovieDetailsViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieDetailsModel movieDetailsModel;

    private ImageView backDropImageView;
    private TextView titleTextView;
    private TextView directorTextView;
    private TextView genreTextView;
    private ImageView posterImageView;
    private TextView releaseTextView;
    private TextView runtimeTextView;
    private LinearLayout overviewLayout;
    private TextView overviewTextView;
    private ImageView moreIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        Intent intent = getIntent();
        int movieId = intent.getIntExtra(PopularMoviesActivity.MOVIE_ID_MESSAGE, 0);
        movieDetailsViewModel.loadMovieDetails(movieId);
        instantiateUI();

        observeChanges();

    }

    private void instantiateUI() {
        backDropImageView = findViewById(R.id.activity_movie_details_backdrop);
        titleTextView = findViewById(R.id.activity_movie_details_title);
        directorTextView = findViewById(R.id.activity_movie_details_director);
        genreTextView = findViewById(R.id.activity_movie_details_genre);
        posterImageView = findViewById(R.id.activity_movie_details_poster);
        releaseTextView = findViewById(R.id.activity_movie_details_release);
        runtimeTextView = findViewById(R.id.activity_movie_details_runtime);
        overviewLayout = findViewById(R.id.activity_movie_details_overview_layout);
        overviewTextView = findViewById(R.id.activity_movie_details_overview);
        moreIcon = findViewById(R.id.activity_movie_details_more);

        overviewLayout.setOnClickListener(view -> {
            movieDetailsViewModel.toggleOverviewState();
            //            overviewTextView.setMaxLines(15);
//            moreIcon.setVisibility(View.GONE);
        });
    }

    private void observeChanges() {
        movieDetailsViewModel.getMovieDetailsLiveData().observe(this, details -> {
            // TODO -- Check if details is NULL?
            updateUI(details);
        });

        movieDetailsViewModel.getShowMoreOverviewLiveData().observe(this, showMore -> {
            overviewTextView.setMaxLines(15);
            moreIcon.setVisibility(View.INVISIBLE);
        });

        movieDetailsViewModel.getShowLessOverviewLiveData().observe(this, showLess -> {
            overviewTextView.setEllipsize(TextUtils.TruncateAt.END);
            overviewTextView.setMaxLines(3);
            moreIcon.setVisibility(View.VISIBLE);
        });

        movieDetailsViewModel.getRemoveOverviewClickListener().observe(this, remove -> {
            overviewLayout.setOnClickListener(null);
            overviewLayout.setClickable(false);
        });
    }

    private void updateUI(MovieDetailsModel details) {
        titleTextView.setText(details.getTitle());
        directorTextView.setText(getString(R.string.director_textview, details.getDirector().getName()));
        genreTextView.setText(details.getFirstGenreName());
        releaseTextView.setText(details.getRelease_date().substring(0, 4)); // Only show year
        runtimeTextView.setText(getString(R.string.runtime_textview, String.valueOf(details.getRuntime())));
        overviewTextView.setText(details.getOverview());

        movieDetailsViewModel.checkOverviewSize(overviewTextView.getLineCount());

        // TODO -- Check for null in viewmodel and set placeholder if it is
        if (details.getBackdrop_path() != null)
            loadBackdropImage(details.getBackdrop_path());

        if (details.getPoster_path() != null)
            loadPosterImage(details.getPoster_path());

    }

    private void loadBackdropImage(String backdropPath) {
        String backdropUrl = Credentials.IMAGE_BASE_URL + "w300" + backdropPath;

        Glide.with(this)
                .load(backdropUrl)
                .placeholder(new ColorDrawable(0x000000))
                .error(R.drawable.ic_baseline_no_image_24)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(backDropImageView);
    }

    private void loadPosterImage(String posterPath) {

        String posterUrl = Credentials.IMAGE_BASE_URL + "w154" + posterPath;

        Glide.with(this)
                .load(posterUrl)
                .placeholder(new ColorDrawable(0x000000))
                .error(R.drawable.ic_baseline_no_image_24)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(posterImageView);
    }

}