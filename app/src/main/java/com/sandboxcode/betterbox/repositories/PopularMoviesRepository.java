package com.sandboxcode.betterbox.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sandboxcode.betterbox.models.MovieDetailsModel;
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

public class PopularMoviesRepository {

    private static PopularMoviesRepository instance;

    private MovieApi movieApi;
    private MutableLiveData<List<MovieModel>> popularMoviesLiveData;

    public static PopularMoviesRepository getInstance() {
        if (instance == null)
            instance = new PopularMoviesRepository();

        return instance;
    }

    private PopularMoviesRepository() {
        popularMoviesLiveData = new MutableLiveData<>();

        movieApi = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi.class);
    }

    public void loadPopularMovies(int pageNumber) {
        movieApi.getPopularMovies(Credentials.API_KEY, pageNumber)
                .enqueue(new Callback<PopularMoviesResponse>() {
                    @Override
                    public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            popularMoviesLiveData.postValue(response.body().getResults());
                        }
                    }

                    @Override
                    public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {
                        popularMoviesLiveData.postValue(null); // TODO -- Handle failure
                    }
                });
    }

    public void loadMovieDetails(int id) {
        movieApi.getMovieDetails(id, Credentials.API_KEY)
                .enqueue(new Callback<MovieDetailsModel>() {
                    @Override
                    public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.v("MOVIE DETAILS: ", response.body().toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<MovieDetailsModel> call, Throwable t) {

                    }
                });
    }

    public LiveData<List<MovieModel>> getPopularMoviesLiveData() {
        return popularMoviesLiveData;
    }



}
