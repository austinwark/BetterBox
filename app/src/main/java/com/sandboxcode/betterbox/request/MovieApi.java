package com.sandboxcode.betterbox.request;

import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/3/search/movie?")
    Call<MovieSearchResponse> getMovies(@Query("api_key") String key,
                                        @Query("query") String query,
                                        @Query("page") int page);

    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(@Path("movie_id") int movie_id,
                              @Query("api_key") String api_key);

}
