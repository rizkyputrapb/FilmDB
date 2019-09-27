package com.khrisna.cataloguemovie.view;

import com.khrisna.cataloguemovie.model.Movie;

import java.util.List;

public interface SearchView {

    void showLoading();

    void hideLoading();

    void showData(List<Movie> dataMovie);
}
