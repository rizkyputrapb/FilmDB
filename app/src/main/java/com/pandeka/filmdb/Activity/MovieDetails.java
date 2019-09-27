package com.pandeka.filmdb.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pandeka.filmdb.Adapter.ReviewAdapter;
import com.pandeka.filmdb.Adapter.SnapAdapter;
import com.pandeka.filmdb.BuildConfig;
import com.pandeka.filmdb.R;
import com.pandeka.filmdb.Utility.FetchData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetails extends AppCompatActivity {

    private TextView mGenre;
    private String movie_id;
    private Bundle intentExtra;
    String TMDB_API_KEY = BuildConfig.TheMovieDbApiKey;

    RecyclerView recyclerView, recyclerView_review;
    ReviewAdapter reviewAdapter;
    SnapAdapter snapAdapter = new SnapAdapter();

    String base_url = "w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("FilmDb");
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            intentExtra = getIntent().getExtras();
            movie_id = intentExtra.getString("Movie_id");
        }

        setTitle();
        setPosterImage();
        setBackdropImage();
        setRating();
        setOverview();
        setRelease();

        mGenre = findViewById(R.id.tv_genre_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheQuality(RecyclerView.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView_review = findViewById(R.id.rv_review);
        recyclerView_review.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_review.setHasFixedSize(true);

        String url_review = "https://api.themoviedb.org/3/movie/" + movie_id + "/reviews?api_key=" +
                TMDB_API_KEY + "&language=en-US&page=1";

        String url_trailer = "https://api.themoviedb.org/3/movie/" + movie_id + "/videos?api_key=" +
                TMDB_API_KEY + "&language=en-US";

        String url_similar = "https://api.themoviedb.org/3/movie/" + movie_id + "/similar?api_key=" +
                TMDB_API_KEY + "&language=en-US&page=1";

        FetchData review = new FetchData("Review", "Review", url_review, base_url
                , getApplicationContext(), recyclerView_review, reviewAdapter);
        review.execute();

        FetchData trailer = new FetchData("Trailer", "Trailer", url_trailer, base_url
                , getApplicationContext(), recyclerView, snapAdapter);
        trailer.execute();

        FetchData similar = new FetchData("Linear", "Similar", url_similar, base_url
                , getApplicationContext(), recyclerView, snapAdapter);
        similar.execute();

        FetchDataGenre genre = new FetchDataGenre();
        genre.execute();

    }

    public void setTitle() {
        TextView mTitle = findViewById(R.id.tv_title_movie);
        mTitle.setText(intentExtra.getString("Title"));
    }

    public void setOverview() {
        TextView mOverview = findViewById(R.id.tv_overview);
        mOverview.setText(intentExtra.getString("Overview"));
    }

    public void setRelease() {
        TextView mRelease = findViewById(R.id.tv_release_date);
        mRelease.setText(intentExtra.getString("Release_date"));
    }

    public void setPosterImage() {
        ImageView mPoster_image = findViewById(R.id.img_poster);
        Picasso.get()
                .load(intentExtra.getString("Poster_image"))
                .into(mPoster_image);
    }

    public void setRating() {
        RatingBar rb_rating = findViewById(R.id.rb_rating);
        TextView mRating = findViewById(R.id.tv_rating);
        rb_rating.setRating(Float.valueOf(intentExtra.getString("Rating")));
        mRating.setText(intentExtra.getString("Rating"));
    }

    public void setBackdropImage() {
        ImageView mBackdrop_image = findViewById(R.id.img_backdrop);
        Picasso.get()
                .load("https://image.tmdb.org/t/p/original"
                        + intentExtra.getString("Backdrop_image"))
                .into(mBackdrop_image);
    }

    public class FetchDataGenre extends AsyncTask<Void, Void, Void> {

        String url_genres = "https://api.themoviedb.org/3/movie/" + movie_id + "?api_key=" +
                "b61314b033364a6eb60be7b725e73d69&language=en-US";

        String data = "";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            jsonObjRequestGenre(url_genres);

            return null;
        }

        void jsonObjRequestGenre(String url) {
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        String data = "";

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("genres");

                                for (int i = 0; i < jsonArray.length() - 1; i++) {
                                    JSONObject jresponse = jsonArray.getJSONObject(i);
                                    String id = jresponse.getString("id");
                                    String name = jresponse.getString("name");

                                    data += name + ", ";
                                }

                                JSONObject jresponse = jsonArray.getJSONObject(jsonArray.length() - 1);
                                String name2 = jresponse.getString("name");
                                String data2 = data + name2;

                                mGenre.setText(data2);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_share, menu);

        MenuItem item = menu.findItem(R.id.item_share);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "The fastest way to find movies";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "FilmDb : Movies Database");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
