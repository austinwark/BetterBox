package com.sandboxcode.betterbox.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.adapters.PopularMoviesAdapter;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.viewmodels.PopularMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class PopularMoviesActivity extends AppCompatActivity {

    private PopularMoviesViewModel popularMoviesViewModel;
    private PopularMoviesAdapter popularMoviesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

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

        swipeRefreshLayout = findViewById(R.id.activity_popular_movies_refresh_layout);
        swipeRefreshLayout.setDistanceToTriggerSync((int) (24 * getResources().getDisplayMetrics().density));
        swipeRefreshLayout.setSlingshotDistance((int) (48 * getResources().getDisplayMetrics().density));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }

    private void instantiateUI() {
        recyclerView = findViewById(R.id.activity_popular_movies_recyclerView);
        floatingActionButton = findViewById(R.id.activity_popular_movies_fab);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();

                popularMoviesViewModel.handleUserScrolled(dy, lastItem);
                if (dy > 0) {

                    final int VISIBLE_THRESHOLD = 12;

                    int currentTotalCount = gridLayoutManager.getItemCount();

                    if (currentTotalCount <= lastItem + VISIBLE_THRESHOLD) {
                        popularMoviesViewModel.loadPopularMovies(pageCount);
                        pageCount++;
                    }
                }
            }
        });

//        GravitySnapHelper gravitySnapHelper = new GravitySnapHelper(Gravity.TOP);
//        gravitySnapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(popularMoviesAdapter);
        floatingActionButton.setOnClickListener(v -> {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            if (gridLayoutManager != null) {
                gridLayoutManager.scrollToPosition(0);
                popularMoviesViewModel.handleUserScrolled(-30, gridLayoutManager.findLastCompletelyVisibleItemPosition());
            }
        });
    }

    private void refreshData() {
        pageCount = 1;
        int currentPosition = popularMoviesAdapter.getItemCount();
        results.clear();
        popularMoviesAdapter.notifyItemRangeRemoved(currentPosition, currentPosition + 1);
        popularMoviesViewModel.loadPopularMovies(pageCount);
    }

    private void observeChanges() {

        popularMoviesViewModel.getPopularMoviesLiveData().observe(this, movieModels -> {
            if (movieModels != null && !movieModels.isEmpty()) {

                int currentPosition = popularMoviesAdapter.getItemCount();
                results.addAll(movieModels);
                popularMoviesAdapter.notifyItemRangeChanged(currentPosition, movieModels.size());
            }

            swipeRefreshLayout.setRefreshing(false);
        });

        // TODO -- Move logic to ViewModel
        popularMoviesViewModel.getFabVisibilityLiveData().observe(this, fabVisibility -> {
            if (fabVisibility == 0)
                fadeViewIn(floatingActionButton);
            else
                fadeViewOut(floatingActionButton);

        });
    }

    private void fadeViewIn(View view) {
        Log.v("FADE", "IN");
        int shortAnimationDuration =
                getResources().getInteger(android.R.integer.config_shortAnimTime);
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
    }

    private void fadeViewOut(View view) {
        Log.v("FADE", "OUT");

        int shortAnimationDuration =
                getResources().getInteger(android.R.integer.config_shortAnimTime);
        view.animate().alpha(0f).setDuration(shortAnimationDuration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                floatingActionButton.setVisibility(View.GONE);
            }
        });
    }

}