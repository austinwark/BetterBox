package com.sandboxcode.betterbox.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sandboxcode.betterbox.models.CastModel;
import com.sandboxcode.betterbox.models.CrewModel;
import com.sandboxcode.betterbox.models.MovieDetailsModel;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.request.MovieApi;
import com.sandboxcode.betterbox.response.CreditsResponse;
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

    public void loadMovieDetails(int movieId) {
        movieApi.getMovieDetails(movieId, Credentials.API_KEY)
                .enqueue(new Callback<MovieDetailsModel>() {
                    @Override
                    public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.v("MOVIE DETAILS: ", response.body().toString());
//                            movieDetailsLiveData.postValue(response.body());
                            MovieDetailsModel movieDetailsModel = response.body();

                            loadCredits(movieDetailsModel);
                        }
                    }
                    @Override
                    public void onFailure(Call<MovieDetailsModel> call, Throwable t) {

                    }
                });
    }

    public void loadCredits(MovieDetailsModel movieDetailsModel) {
        movieApi.getCredits(movieDetailsModel.getId(), Credentials.API_KEY)
                .enqueue(new Callback<CreditsResponse>() {
                    @Override
                    public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            finishSettingDetails(movieDetailsModel, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CreditsResponse> call, Throwable t) {

                    }
                });
    }

    private void finishSettingDetails(MovieDetailsModel movieDetailsModel, CreditsResponse creditsResponse) {
        List<CastModel> castList = creditsResponse.getCastList();
        List<CrewModel> crewList = creditsResponse.getCrewList();
        CrewModel director = getMovieDirector(crewList);

        movieDetailsModel.setCast_list(castList);
        movieDetailsModel.setCrew_list(crewList);
        movieDetailsModel.setDirector(director);

        movieDetailsLiveData.postValue(movieDetailsModel);

    }

    /* Find director in list of crew. Returns default CrewModel if not found */
    public CrewModel getMovieDirector(List<CrewModel> crewList) {
        String jobName = "Director";
        CrewModel director =
                new CrewModel(0, "Undetermined", jobName, null, "Directing");

        for (CrewModel crew : crewList) {
            if (crew.getJob().equalsIgnoreCase(jobName)) {
                director = crew;
            }
        }

        return director;
    }

    public MutableLiveData<MovieDetailsModel> getMovieDetailsLiveData() {
        return movieDetailsLiveData;
    }

}
