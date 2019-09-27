package com.khrisna.cataloguemovie.network;

import com.khrisna.cataloguemovie.BuildConfig;
import com.khrisna.cataloguemovie.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/3/movie/now_playing?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US&page=1")
    Call<MovieResponse> getNowPlaying();

    @GET("/3/movie/upcoming?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US&page=1")
    Call<MovieResponse> getUpcoming();

    @GET("/3/search/movie?api_key=b61314b033364a6eb60be7b725e73d69&language=en-US&page=1&include_adult=false")
    Call<MovieResponse> getMovieSearch(@Query("query") String movieName);
}
