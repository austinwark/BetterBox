package com.sandboxcode.betterbox.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.models.MovieModel;
import com.sandboxcode.betterbox.repositories.SearchRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private SearchRepository searchRepository;
    private LiveData<List<MovieModel>> moviesLiveData;
    private MutableLiveData<Boolean> openHomeActivity;

    public SearchViewModel() {
        searchRepository = new SearchRepository();
        moviesLiveData = searchRepository.getMoviesLiveData();
        openHomeActivity = new MutableLiveData<>();
    }

    public void searchMovies(String query) {
        searchRepository.searchMovies(query);
    }

    public LiveData<List<MovieModel>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public void handleOnBottomNavItemSelected(int id) {
        if (id == R.id.item_bottom_nav_home) {
            openHomeActivity.postValue(true);
        }
    }

    public LiveData<Boolean> getOpenHomeActivity() {
        return openHomeActivity;
    }
}
