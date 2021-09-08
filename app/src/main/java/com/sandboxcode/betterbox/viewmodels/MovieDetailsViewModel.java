package com.sandboxcode.betterbox.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sandboxcode.betterbox.models.MovieDetailsModel;
import com.sandboxcode.betterbox.repositories.MovieDetailsRepository;

public class MovieDetailsViewModel extends ViewModel {

    private MovieDetailsRepository movieDetailsRepository;
    private MutableLiveData<MovieDetailsModel> movieDetailsLiveData;

    public MovieDetailsViewModel() {

        movieDetailsRepository = MovieDetailsRepository.getInstance();
        movieDetailsLiveData = movieDetailsRepository.getMovieDetailsLiveData();

    }

    public void loadMovieDetails(int id) {
        movieDetailsRepository.loadMovieDetails(id);
    }

    public MutableLiveData<MovieDetailsModel> getMovieDetailsLiveData() {
        return movieDetailsLiveData;
    }

}
