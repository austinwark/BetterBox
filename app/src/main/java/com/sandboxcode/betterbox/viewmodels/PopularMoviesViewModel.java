package com.sandboxcode.betterbox.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.repositories.PopularMoviesRepository;

import java.util.List;

public class PopularMoviesViewModel extends ViewModel {

    private PopularMoviesRepository popularMoviesRepository;
    private LiveData<List<MovieModel>> popularMoviesLiveData;

    public PopularMoviesViewModel() {

        popularMoviesRepository = PopularMoviesRepository.getInstance();
        popularMoviesLiveData = popularMoviesRepository.getPopularMoviesLiveData();
    }

    public LiveData<List<MovieModel>> getPopularMoviesLiveData() {
        return popularMoviesRepository.getPopularMoviesLiveData();
    }

    public void loadPopularMovies(int pageNumber) {
        popularMoviesRepository.loadPopularMovies(pageNumber);
    }

//    public void getPopularMovies(String query, int pageNumber) {
//        popularMoviesRepository.searchPopularMovies(query, pageNumber);
//    }
}
