package com.pandeka.filmdb.Utility;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pandeka.filmdb.Adapter.CategoryAdapter;
import com.pandeka.filmdb.Adapter.ReviewAdapter;
import com.pandeka.filmdb.Adapter.SnapAdapter;
import com.pandeka.filmdb.Adapter.TrailerAdapter;
import com.pandeka.filmdb.Model.CategoryData;
import com.pandeka.filmdb.Model.MovieData;
import com.pandeka.filmdb.Model.ReviewData;
import com.pandeka.filmdb.Model.SnapData;
import com.pandeka.filmdb.Model.TrailerData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khris on 11/01/2018.
 */

public class FetchData extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String type;
    private String title;
    private String url;
    private String base_url = "https://image.tmdb.org/t/p/";
    private List<MovieData> movieData = new ArrayList<>();
    private List<CategoryData> genres = new ArrayList<>();
    private List<TrailerData> trailerData = new ArrayList<>();
    private List<ReviewData> reviewData = new ArrayList<>();
    private SnapAdapter snapAdapter;
    private CategoryAdapter categoryAdapter;
    private ReviewAdapter reviewAdapter;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;

    public FetchData(String type, String title, String url, String base_url, Context context,
                     RecyclerView recyclerView, SnapAdapter snapAdapter) {

        this.type = type;
        this.title = title;
        this.url = url;
        this.base_url += base_url;
        this.context = context;
        this.recyclerView = recyclerView;
        this.snapAdapter = snapAdapter;

    }

    public FetchData(String type, String title, String url, String base_url, Context context,
                     RecyclerView recyclerView, CategoryAdapter categoryAdapter) {

        this.type = type;
        this.title = title;
        this.url = url;
        this.base_url += base_url;
        this.context = context;
        this.recyclerView = recyclerView;
        this.categoryAdapter = categoryAdapter;

    }

    public FetchData(String type, String title, String url, String base_url, Context context,
                     RecyclerView recyclerView, ReviewAdapter reviewAdapter) {

        this.type = type;
        this.title = title;
        this.url = url;
        this.base_url += base_url;
        this.context = context;
        this.recyclerView = recyclerView;
        this.reviewAdapter = reviewAdapter;

    }

    public FetchData(String type, String title, String url, String base_url, Context context,
                     RecyclerView recyclerView, TrailerAdapter trailerAdapter) {

        this.type = type;
        this.title = title;
        this.url = url;
        this.base_url += base_url;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        requestQueue = Volley.newRequestQueue(context);

        switch (type) {
            case "Linear":
                jsonObjRequestMovie(title, url, base_url, movieData);

                break;

            case "Grid":
                jsonObjRequestMovie(title, url, base_url, movieData);

                break;

            case "List":
                jsonObjRequestGenre(url, genres);
                break;

            case "Review":
                jsonObjRequestReview(url);
                break;

            case "Trailer":
                jsonObjRequestTrailer(url);
                break;
        }

        return null;
    }

    private void jsonObjRequestMovie(final String name, String url, final String base_url,
                                     final List<MovieData> listData) {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jresponse = jsonArray.getJSONObject(i);
                                String title = jresponse.getString("title");
                                String rating = jresponse.getString("vote_average");
                                String poster = jresponse.getString("poster_path");
                                String vote = jresponse.getString("vote_count");
                                String id = jresponse.getString("id");
                                String video = jresponse.getString("video");
                                String popularity = jresponse.getString("popularity");
                                String original_language = jresponse.getString("original_language");
                                String original_title = jresponse.getString("original_title");
                                String genre_ids = jresponse.getString("genre_ids");
                                String backdrop_path = jresponse.getString("backdrop_path");
                                String adult = jresponse.getString("adult");
                                String overview = jresponse.getString("overview");
                                String release_date = jresponse.getString("release_date");

                                listData.add(new MovieData(title, base_url + poster
                                        , rating, vote, id, video, popularity, original_language,
                                        original_title, genre_ids, backdrop_path,
                                        adult, overview, release_date));

                            }
                            if (type.equalsIgnoreCase("Linear")) {
                                snapAdapter.addSnap(new SnapData("Linear",
                                        name, listData));
                            } else if (type.equalsIgnoreCase("Grid")) {
                                snapAdapter.addSnap(new SnapData("Grid",
                                        name, listData));
                            }

                            recyclerView.setAdapter(snapAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsObjRequest);
    }

    private void jsonObjRequestGenre(String url, final List<CategoryData> listData) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("genres");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jresponse = jsonArray.getJSONObject(i);
                                String id = jresponse.getString("id");
                                String name = jresponse.getString("name");

                                listData.add(new CategoryData(id, name));
                            }

                            categoryAdapter = new CategoryAdapter(context, listData);

                            recyclerView.setAdapter(categoryAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsObjRequest);
    }

    private void jsonObjRequestReview(String url) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jresponse = jsonArray.getJSONObject(i);
                                String id = jresponse.getString("id");
                                String author = jresponse.getString("author");
                                String content = jresponse.getString("content");
                                String url = jresponse.getString("url");

                                reviewData.add(new ReviewData(id, author
                                        , content, url));
                            }

                            if (reviewData.size() == 0) {
                                reviewData.add(new ReviewData("", "No Reviews"
                                        , "", ""));

                                reviewAdapter = new ReviewAdapter(context,
                                        reviewData);
                            } else {
                                reviewAdapter = new ReviewAdapter(context,
                                        reviewData);
                            }

                            recyclerView.setAdapter(reviewAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsObjRequest);
    }

    private void jsonObjRequestTrailer(String url) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length() - 1; i++) {
                                JSONObject jresponse = jsonArray.getJSONObject(i);
                                String id = jresponse.getString("id");
                                String iso_639_1 = jresponse.getString("iso_639_1");
                                String iso_3166_1 = jresponse.getString("iso_3166_1");
                                String key = jresponse.getString("key");
                                String name = jresponse.getString("name");
                                String site = jresponse.getString("site");
                                String size = jresponse.getString("size");
                                String type = jresponse.getString("type");

                                trailerData.add(new TrailerData(id, iso_639_1
                                        , iso_3166_1, key, name, site, size, type));
                            }

                            snapAdapter.addSnap(new SnapData("Trailer", trailerData));

                            recyclerView.setAdapter(snapAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsObjRequest);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
