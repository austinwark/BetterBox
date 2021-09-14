package com.sandboxcode.betterbox.request;

import com.sandboxcode.betterbox.models.MovieDetailsModel;
import com.sandboxcode.betterbox.response.CreditsResponse;
import com.sandboxcode.betterbox.response.PopularMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    /* Get list of movies by keyword(s) */
    @GET("/3/search/movie?")
    Call<PopularMoviesResponse> getMovies(@Query("api_key") String key,
                                          @Query("query") String query,
                                          @Query("page") int page);

    /* Get list of popular movies */
    @GET("/3/movie/popular?")
    Call<PopularMoviesResponse> getPopularMovies(@Query("api_key") String key,
                                                 @Query("page") int page);

    /* Get single movie by movie id */
    @GET("3/movie/{movie_id}?")
    Call<MovieDetailsModel> getMovieDetails(@Path("movie_id") int movie_id,
                                            @Query("api_key") String api_key);

    /* Get list of credits by movie id */
    @GET("3/movie/{movie_id}/credits?")
    Call<CreditsResponse> getCredits(@Path("movie_id") int movie_id,
                                     @Query("api_key") String api_key);

    @GET("3/search/movie?")
    Call<PopularMoviesResponse> searchMovies(@Query("api_key") String key,
                                             @Query("query") String query);

}
