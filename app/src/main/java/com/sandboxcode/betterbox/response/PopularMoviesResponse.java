package com.sandboxcode.betterbox.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sandboxcode.betterbox.models.MovieModel;

import java.util.List;

public class PopularMoviesResponse {

    @SerializedName("total_results")
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> results;

    public int getTotal_count() { return total_count; }

    public List<MovieModel> getResults() { return results; }

    @Override
    public String toString() {
        return "PopularMoviesResponse{" +
                "total_count=" + total_count +
                ", results=" + results +
                '}';
    }
}
