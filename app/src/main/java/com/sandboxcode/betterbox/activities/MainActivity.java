package com.sandboxcode.betterbox.activities;

import androidx.appcompat.app.AppCompatActivity;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private MovieApi movieApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMovieApi();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view ->
                movieApi.getMovies(Credentials.API_KEY,
                "Snatch",
                1).enqueue(movieSearchResponseCallback));
    }

    private void createMovieApi() {

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(MovieApi.class);

    }

    Callback<MovieSearchResponse> movieSearchResponseCallback = new Callback<MovieSearchResponse>() {
        @Override
        public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
            if (response.isSuccessful()) {
                MovieSearchResponse movieSearchResponse = response.body();
                for (MovieModel movieModel : movieSearchResponse.getResults())
                    Log.v("TAG", movieModel.toString());
            } else {
                Log.v("TAG", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
            t.printStackTrace();
        }
    };
}