package com.khrisna.cataloguemovie.view;

import com.khrisna.cataloguemovie.model.Movie;

import java.util.List;

public interface HomeView {

    void showLoading();

    void hideLoading();

    void showData(List<Movie> dataNowPlaying, List<Movie> dataUpcoming);
}
