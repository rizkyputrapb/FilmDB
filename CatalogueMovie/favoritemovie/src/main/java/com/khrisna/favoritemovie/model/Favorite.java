package com.khrisna.favoritemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.khrisna.favoritemovie.database.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.khrisna.favoritemovie.database.DatabaseContract.getColumnInt;
import static com.khrisna.favoritemovie.database.DatabaseContract.getColumnString;

public class Favorite implements Parcelable {

    private int id;
    private String movieTitle;
    private String movieOverview;
    private String movieReleaseDate;
    private String movieImage;
    private String movieBackdrop;
    private String movieRating;

    public Favorite() {
    }

    public Favorite(int id, String movieTitle, String movieOverview,
                    String movieReleaseDate, String movieImage, String movieBackdrop, String movieRating) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.movieImage = movieImage;
        this.movieBackdrop = movieBackdrop;
        this.movieRating = movieRating;
    }

    private Favorite(Parcel in) {
        id = in.readInt();
        movieTitle = in.readString();
        movieOverview = in.readString();
        movieReleaseDate = in.readString();
        movieImage = in.readString();
        movieBackdrop = in.readString();
        movieRating = in.readString();
    }

    public Favorite(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.movieTitle = getColumnString(cursor, DatabaseContract.FavoriteColumns.TITLE);
        this.movieOverview = getColumnString(cursor, DatabaseContract.FavoriteColumns.OVERVIEW);
        this.movieReleaseDate = getColumnString(cursor, DatabaseContract.FavoriteColumns.RELEASE_DATE);
        this.movieImage = getColumnString(cursor, DatabaseContract.FavoriteColumns.IMAGE);
        this.movieBackdrop = getColumnString(cursor, DatabaseContract.FavoriteColumns.BACKDROP);
        this.movieRating = getColumnString(cursor, DatabaseContract.FavoriteColumns.RATING);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
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

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setId(int id) {
        this.id = id;
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
}