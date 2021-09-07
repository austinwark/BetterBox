package com.sandboxcode.betterbox.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.adapters.PopularMoviesAdapter;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.viewmodels.PopularMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class PopularMoviesActivity extends AppCompatActivity {

    private PopularMoviesViewModel popularMoviesViewModel;
    private PopularMoviesAdapter popularMoviesAdapter;

    private List<MovieModel> results;
    private int pageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        pageCount = 1;

        results = new ArrayList<>(); // TODO - handle on config change

        popularMoviesAdapter = new PopularMoviesAdapter(this, results);

        popularMoviesViewModel =
                new ViewModelProvider(this).get(PopularMoviesViewModel.class);

        instantiateUI();

        observeChanges();


        /* ViewModel updates LiveData which is observed in observeChanges() */
        popularMoviesViewModel.loadPopularMovies(pageCount);
        pageCount++;
//        createMovieApi();1

//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(view ->
//                popularMoviesViewModel.loadPopularMovies(1));
    }

    private void instantiateUI() {
        RecyclerView recyclerView = findViewById(R.id.activity_popular_movies_recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    final int VISIBLE_THRESHOLD = 12;
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                    int currentTotalCount = gridLayoutManager.getItemCount();

                    if (currentTotalCount <= lastItem + VISIBLE_THRESHOLD) {
                        Log.v("TAG ----", " " + lastItem);
                        popularMoviesViewModel.loadPopularMovies(pageCount);
                        pageCount++;
                    }
                }
            }
        });

//        GravitySnapHelper gravitySnapHelper = new GravitySnapHelper(Gravity.TOP);
//        gravitySnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(popularMoviesAdapter);
    }

    private void observeChanges() {
        popularMoviesViewModel.getPopularMoviesLiveData().observe(this, movieModels -> {
            if (movieModels != null && !movieModels.isEmpty()) {

                int currentPosition = popularMoviesAdapter.getItemCount();
                results.addAll(movieModels);
                Log.v("Current: ", "current: " + currentPosition);
                Log.v("Size: ", "new: " + results.size());
                popularMoviesAdapter.notifyItemRangeChanged(currentPosition, movieModels.size());
//                popularMoviesAdapter.notifyDataSetChanged();
            }
        });
    }

}