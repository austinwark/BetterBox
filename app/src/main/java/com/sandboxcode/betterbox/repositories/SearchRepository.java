package com.sandboxcode.betterbox.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.request.MovieApi;
import com.sandboxcode.betterbox.response.PopularMoviesResponse;
import com.sandboxcode.betterbox.utils.Credentials;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRepository {


    private MovieApi movieApi; // TODO: follow singleton pattern
    private MutableLiveData<List<MovieModel>> moviesLiveData;

    public SearchRepository() {
        moviesLiveData = new MutableLiveData<>();

        movieApi = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi.class);
    }

    public void searchMovies(String query) {
        if (query == null)
            return;

        movieApi.searchMovies(Credentials.API_KEY, query)
                .enqueue(new Callback<PopularMoviesResponse>() {
                    @Override
                    public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            moviesLiveData.postValue(response.body().getResults());
                        }
                    }

                    @Override
                    public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {

                    }
                });
    }

    public MutableLiveData<List<MovieModel>> getMoviesLiveData() {
        return moviesLiveData;
    }
}
