package com.sandboxcode.betterbox.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;

import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.adapters.PopularMoviesAdapter;
import com.sandboxcode.betterbox.utils.GravitySnapHelper;
import com.sandboxcode.betterbox.viewmodels.PopularMoviesViewModel;
import com.squareup.picasso.Picasso;

public class PopularMoviesActivity extends AppCompatActivity {

    private PopularMoviesViewModel popularMoviesViewModel;
    private PopularMoviesAdapter popularMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        popularMoviesAdapter = new PopularMoviesAdapter(this);

        popularMoviesViewModel =
                new ViewModelProvider(this).get(PopularMoviesViewModel.class);

        instantiateUI();

        observeChanges();



        popularMoviesViewModel.loadPopularMovies(1);
//        createMovieApi();1

//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(view ->
//                popularMoviesViewModel.loadPopularMovies(1));
    }

    private void instantiateUI() {
        RecyclerView recyclerView = findViewById(R.id.activity_popular_movies_recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
//        GravitySnapHelper gravitySnapHelper = new GravitySnapHelper(Gravity.TOP);
//        gravitySnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(popularMoviesAdapter);
    }

    private void observeChanges() {
        popularMoviesViewModel.getPopularMoviesLiveData().observe(this, movieModels -> {
            if (movieModels != null) {
                popularMoviesAdapter.setResults(movieModels);
            }
        });
    }

}