package com.sandboxcode.betterbox.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsModel implements Parcelable {

    @SerializedName("id")
    @Expose()
    private int id;

    @SerializedName("title")
    @Expose()
    private String title;

    @SerializedName("overview")
    @Expose()
    private String overview;

    @SerializedName("release_date")
    @Expose()
    private String release_date;

    @SerializedName("vote_average")
    @Expose()
    private float vote_average;

    @SerializedName("poster_path")
    @Expose()
    private String poster_path;

    @SerializedName("backdrop_path")
    @Expose()
    private String backdrop_path;

    @SerializedName("genres")
    @Expose()
    private List<GenreModel> genres;

    @SerializedName("popularity")
    @Expose()
    private float popularity;

    @SerializedName("runtime")
    @Expose()
    private int runtime;

    public MovieDetailsModel(int id, String title, String overview, String release_date,
                             float vote_average, String poster_path, String backdrop_path,
                             List<GenreModel> genres, float popularity, int runtime) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.genres = genres;
        this.popularity = popularity;
        this.runtime = runtime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeInt(id);
        dest.writeFloat(vote_average);
        dest.writeString(overview);
        dest.writeString(backdrop_path);
        dest.writeList(genres);
        dest.writeFloat(popularity);
        dest.writeInt(runtime);
    }

    protected MovieDetailsModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        id = in.readInt();
        vote_average = in.readFloat();
        overview = in.readString();
        backdrop_path = in.readString();
        genres = in.readArrayList(GenreModel.class.getClassLoader());
        popularity = in.readFloat();
        runtime = in.readInt();
    }

    public static final Creator<MovieDetailsModel> CREATOR = new Creator<MovieDetailsModel>() {
        @Override
        public MovieDetailsModel createFromParcel(Parcel in) {
            return new MovieDetailsModel(in);
        }

        @Override
        public MovieDetailsModel[] newArray(int size) {
            return new MovieDetailsModel[size];
        }
    };

    @Override
    public String toString() {
        return "MovieDetailsModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average=" + vote_average +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", genres=" + genres +
                ", popularity=" + popularity +
                ", runtime=" + runtime +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public List<GenreModel> getGenres() {
        return genres;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getRuntime() {
        return runtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
