package com.khrisna.cataloguemovie.presenter;

import android.util.Log;

import com.khrisna.cataloguemovie.model.Movie;
import com.khrisna.cataloguemovie.model.MovieResponse;
import com.khrisna.cataloguemovie.view.SearchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {

    private SearchView view;
    private Call<MovieResponse> call;

    public SearchPresenter(SearchView view, Call<MovieResponse> call) {
        this.view = view;
        this.call = call;
    }

    public void getDataMovie() {
        view.showLoading();

        call.clone().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                List<Movie> movies = response.body() != null ? response.body().getMovieResponse() : null;
                view.showData(movies);
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Error message", t.toString());
            }
        });
    }
}
