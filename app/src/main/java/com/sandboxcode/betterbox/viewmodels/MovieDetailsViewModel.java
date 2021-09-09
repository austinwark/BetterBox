package com.sandboxcode.betterbox.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sandboxcode.betterbox.models.MovieDetailsModel;
import com.sandboxcode.betterbox.repositories.MovieDetailsRepository;

public class MovieDetailsViewModel extends ViewModel {

    private MovieDetailsRepository movieDetailsRepository;
    private MutableLiveData<MovieDetailsModel> movieDetailsLiveData;

    private Boolean isOverviewTruncated;
    private MutableLiveData<Boolean> showMoreOverviewLiveData; // Expand overview
    private MutableLiveData<Boolean> showLessOverviewLiveData; // Collapse overview
    private MutableLiveData<Boolean> removeOverviewClickListener; // Remove overview click listener

    public MovieDetailsViewModel() {

        movieDetailsRepository = MovieDetailsRepository.getInstance();
        movieDetailsLiveData = movieDetailsRepository.getMovieDetailsLiveData();
        showMoreOverviewLiveData = new MutableLiveData<>();
        showLessOverviewLiveData = new MutableLiveData<>();
        removeOverviewClickListener = new MutableLiveData<>();
    }

    public void loadMovieDetails(int id) {
        movieDetailsRepository.loadMovieDetails(id);
    }

    public MutableLiveData<MovieDetailsModel> getMovieDetailsLiveData() {
        return movieDetailsLiveData;
    }

    public void checkOverviewSize(int numLines) {

        if (numLines == 0) // Protects against bug which calls checkOverSize and passed 0 as param
            return;

        if (numLines > 3) {
            showLessOverviewLiveData.setValue(true);
            isOverviewTruncated = true;
        } else {
            removeOverviewClickListener.setValue(true);
            isOverviewTruncated = false;
        }
    }

    public void toggleOverviewState() {
        if (isOverviewTruncated)
            showMoreOverviewLiveData.setValue(true);
        else
            showLessOverviewLiveData.setValue(true);

        isOverviewTruncated = !isOverviewTruncated;
    }

    public MutableLiveData<Boolean> getShowMoreOverviewLiveData() {
        return showMoreOverviewLiveData;
    }

    public MutableLiveData<Boolean> getShowLessOverviewLiveData() {
        return showLessOverviewLiveData;
    }

    public MutableLiveData<Boolean> getRemoveOverviewClickListener() {
        return removeOverviewClickListener;
    }

}
