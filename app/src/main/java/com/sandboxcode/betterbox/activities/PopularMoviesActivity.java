package com.sandboxcode.betterbox.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.request.MovieApi;
import com.sandboxcode.betterbox.response.MovieSearchResponse;
import com.sandboxcode.betterbox.utils.Credentials;
import com.sandboxcode.betterbox.utils.RetrofitClient;
import com.sandboxcode.betterbox.viewmodels.PopularMoviesViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularMoviesActivity extends AppCompatActivity {

    private MovieApi movieApi;

    private PopularMoviesViewModel popularMoviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularMoviesViewModel =
                new ViewModelProvider(this).get(PopularMoviesViewModel.class);

        observeChanges();
//        createMovieApi();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view ->
                popularMoviesViewModel.loadPopularMovies(1));
    }

    private void observeChanges() {
        popularMoviesViewModel.getPopularMoviesLiveData().observe(this, movieModels -> {
            if (movieModels != null) {
                for (MovieModel movieModel : movieModels) {
                    Log.v("TAG", movieModel.toString());
                }
            }
        });
    }

}