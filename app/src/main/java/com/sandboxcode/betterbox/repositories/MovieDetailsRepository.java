package com.sandboxcode.betterbox.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sandboxcode.betterbox.models.MovieDetailsModel;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.request.MovieApi;
import com.sandboxcode.betterbox.utils.Credentials;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsRepository {

    private static MovieDetailsRepository instance;

    private MovieApi movieApi;
    private MutableLiveData<MovieDetailsModel> movieDetailsLiveData;

    public static MovieDetailsRepository getInstance() {
        if (instance == null)
            instance = new MovieDetailsRepository();

        return instance;
    }

    private MovieDetailsRepository() {
        movieDetailsLiveData = new MutableLiveData<>();

        movieApi = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi.class);
    }

    public void loadMovieDetails(int id) {
        movieApi.getMovieDetails(id, Credentials.API_KEY)
                .enqueue(new Callback<MovieDetailsModel>() {
                    @Override
                    public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.v("MOVIE DETAILS: ", response.body().toString());
                            movieDetailsLiveData.postValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<MovieDetailsModel> call, Throwable t) {

                    }
                });
    }

    public MutableLiveData<MovieDetailsModel> getMovieDetailsLiveData() {
        return movieDetailsLiveData;
    }
}
