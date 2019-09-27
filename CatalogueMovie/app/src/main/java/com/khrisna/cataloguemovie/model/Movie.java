package com.khrisna.cataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    @SerializedName("id")
    private String movieId;

    @SerializedName("title")
    private String movieTitle;

    @SerializedName("overview")
    private String movieOverview;

    @SerializedName("release_date")
    private String movieReleaseDate;

    @SerializedName("poster_path")
    private String movieImage;

    @SerializedName("backdrop_path")
    private String movieBackdrop;

    @SerializedName("vote_average")
    private String movieRating;

    public Movie() {
    }

    protected Movie(Parcel in) {
        movieId = in.readString();
        movieTitle = in.readString();
        movieOverview = in.readString();
        movieReleaseDate = in.readString();
        movieImage = in.readString();
        movieBackdrop = in.readString();
        movieRating = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(movieTitle);
        dest.writeString(movieOverview);
        dest.writeString(movieReleaseDate);
        dest.writeString(movieImage);
        dest.writeString(movieBackdrop);
        dest.writeString(movieRating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public Movie(String movieId, String movieTitle, String movieOverview, String movieReleaseDate, String movieImage, String movieBackdrop, String movieRating) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.movieImage = movieImage;
        this.movieBackdrop = movieBackdrop;
        this.movieRating = movieRating;
    }
}
