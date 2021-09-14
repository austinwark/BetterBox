package com.sandboxcode.betterbox.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.adapters.SearchAdapter;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.utils.OnMovieClickListener;
import com.sandboxcode.betterbox.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel searchViewModel;
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private TextInputLayout searchTextInputLayout;

    private List<MovieModel> results;
    private int pageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        pageCount = 1;

        results = new ArrayList<>();

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        adapter = new SearchAdapter(results, new OnMovieClickListener() {
            @Override
            public void onMovieClicked(int id) {
                startMovieDetailsActivity(id);
            }
        });

        instantiateUI();

        observeChanges();
    }

    private void instantiateUI() {
        bottomNavigationView = findViewById(R.id.activity_search_bottom_nav);
        recyclerView = findViewById(R.id.activity_search_recyclerView);
        searchTextInputLayout = findViewById(R.id.activity_search_input_layout);

        bottomNavigationView.setSelectedItemId(R.id.item_bottom_nav_search);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            searchViewModel.handleOnBottomNavItemSelected(item.getItemId());
            return true;
        });

        searchTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handleOnTextChanged();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void observeChanges() {
        searchViewModel.getMoviesLiveData().observe(this, results -> {
            if (results != null && !results.isEmpty()) {

                int currentPosition = adapter.getItemCount();
                this.results.addAll(results);
                adapter.notifyItemRangeInserted(currentPosition, results.size());
            }
        });

        searchViewModel.getOpenHomeActivity().observe(this, openActivity -> {
            Intent intent = new Intent(this, PopularMoviesActivity.class);
            startActivity(intent);
        });
    }

    private void handleOnTextChanged() {
        String currentText = searchTextInputLayout.getEditText().getText().toString();
        Log.v("ONTEXTCHANGED", " " + currentText);
        int currentSize = results.size();
        results.clear();
        adapter.notifyItemRangeRemoved(0, currentSize);
        searchViewModel.searchMovies(currentText);
    }

    private void startMovieDetailsActivity(int id) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(PopularMoviesActivity.MOVIE_ID_MESSAGE, id);
        startActivity(intent);
    }
}