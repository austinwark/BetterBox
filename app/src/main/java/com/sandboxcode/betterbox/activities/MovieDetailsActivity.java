package com.sandboxcode.betterbox.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.models.MovieDetailsModel;
import com.sandboxcode.betterbox.viewmodels.MovieDetailsViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieDetailsModel movieDetailsModel;

    private ImageView backDropImageView;
    private TextView titleTextView;
    private TextView genreTextView;
    private ImageView posterImageView;
    private TextView releaseTextView;
    private TextView runtimeTextView;

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
        genreTextView = findViewById(R.id.activity_movie_details_genre);
        posterImageView = findViewById(R.id.activity_movie_details_poster);
        releaseTextView = findViewById(R.id.activity_movie_details_release);
        runtimeTextView = findViewById(R.id.activity_movie_details_runtime);
    }

    private void observeChanges() {
        movieDetailsViewModel.getMovieDetailsLiveData().observe(this, details -> {
            // TODO -- Check if details is NULL?
            updateUI(details);
        });
    }

    private void updateUI(MovieDetailsModel details) {
        titleTextView.setText(details.getTitle());
        genreTextView.setText(details.getFirstGenreName());
        releaseTextView.setText(details.getRelease_date());
        runtimeTextView.setText(details.getRuntime());
    }
}