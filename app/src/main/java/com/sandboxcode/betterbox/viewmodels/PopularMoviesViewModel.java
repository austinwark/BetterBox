package com.sandboxcode.betterbox.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.repositories.PopularMoviesRepository;

import java.util.List;

public class PopularMoviesViewModel extends ViewModel {

    private PopularMoviesRepository popularMoviesRepository;
    private LiveData<List<MovieModel>> popularMoviesLiveData;

    private MutableLiveData<Integer> fabVisibilityLiveData;

    public PopularMoviesViewModel() {

        popularMoviesRepository = PopularMoviesRepository.getInstance();
        popularMoviesLiveData = popularMoviesRepository.getPopularMoviesLiveData();
        fabVisibilityLiveData = new MutableLiveData<>(8);
    }

    public LiveData<List<MovieModel>> getPopularMoviesLiveData() {
        return popularMoviesRepository.getPopularMoviesLiveData();
    }

    public void loadPopularMovies(int pageNumber) {
        popularMoviesRepository.loadPopularMovies(pageNumber);
    }

    public void handleUserScrolled(int dy, int lastVisibleItem) {
        int currentVisibleState = fabVisibilityLiveData.getValue();

        boolean noChangeInScrollDirection =
                ((currentVisibleState == 0 && dy > 0) || (currentVisibleState == 8 && dy < 0));

        Log.v("DY: ", String.valueOf(noChangeInScrollDirection));
        // Prevents unnecessary postValue calls if scroll direction does not change
        if (noChangeInScrollDirection)
            return;

        // IF user scrolled approximately two rows down (comparing dy to 25 reduces sensitivity)
        if (dy > 25 && lastVisibleItem > 15)
            fabVisibilityLiveData.postValue(0); // visible
        else if (dy < -25)
            fabVisibilityLiveData.postValue(8); // gone
    }

    public MutableLiveData<Integer> getFabVisibilityLiveData() {
        return fabVisibilityLiveData;
    }

    public void loadMovieDetails(int id) {
        popularMoviesRepository.loadMovieDetails(id);
    }

//    public void getPopularMovies(String query, int pageNumber) {
//        popularMoviesRepository.searchPopularMovies(query, pageNumber);
//    }
}
