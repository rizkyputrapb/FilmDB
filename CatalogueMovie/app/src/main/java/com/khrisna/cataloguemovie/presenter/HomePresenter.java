package com.khrisna.cataloguemovie.presenter;

import android.util.Log;

import com.khrisna.cataloguemovie.model.Movie;
import com.khrisna.cataloguemovie.model.MovieResponse;
import com.khrisna.cataloguemovie.view.HomeView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {

    private HomeView view;
    private Call<MovieResponse> getNowPlaying;
    private Call<MovieResponse> getUpcoming;
    private List<Movie> nowPlaying = Collections.emptyList();
    private List<Movie> upcoming = Collections.emptyList();

    public HomePresenter(HomeView view, Call<MovieResponse> getNowPlaying, Call<MovieResponse> getUpcoming) {
        this.view = view;
        this.getNowPlaying = getNowPlaying;
        this.getUpcoming = getUpcoming;
    }

    public void getDataMovie() {
        view.showLoading();

        getNowPlaying.clone().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                nowPlaying = response.body() != null ? response.body().getMovieResponse() : null;

                if (response.body() == null) {
                    nowPlaying = null;
                } else {
                    nowPlaying = response.body().getMovieResponse();
                }

                view.showData(nowPlaying, upcoming);
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Error message", t.toString());
            }
        });

        getUpcoming.clone().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                upcoming = response.body() != null ? response.body().getMovieResponse() : null;
                view.showData(nowPlaying, upcoming);
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Error message", t.toString());
            }
        });
    }
}
