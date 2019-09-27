package com.khrisna.cataloguemovie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.database.FavoriteHelper;
import com.khrisna.cataloguemovie.model.Favorite;
import com.khrisna.cataloguemovie.model.Movie;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private FavoriteHelper favoriteHelper;

    private Menu menuItem = null;
    private Boolean isFavorite = false;
    private String title;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ARG_MOVIE_DATA", movies);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("ARG_MOVIE_DATA");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView movieBackdrop = findViewById(R.id.backdrop_image);
        ImageView moviePoster = findViewById(R.id.poster_image);
        TextView movieTitle = findViewById(R.id.movie_title);
        TextView movieRating = findViewById(R.id.movie_rating);
        TextView movieOverview = findViewById(R.id.movie_overview);
        TextView movieReleaseDate = findViewById(R.id.movie_releaseDate);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        movies = bundle.getParcelableArrayList("ARG_MOVIE_DATA");

        String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";
        Glide.with(this)
                .load(BASE_POSTER_URL + movies.get(0).getMovieBackdrop())
                .into(movieBackdrop);

        Glide.with(this)
                .load(BASE_POSTER_URL + movies.get(0).getMovieImage())
                .into(moviePoster);

        movieTitle.setText(movies.get(0).getMovieTitle());
        movieRating.setText(movies.get(0).getMovieRating());
        movieOverview.setText(movies.get(0).getMovieOverview());
        movieReleaseDate.setText(movies.get(0).getMovieReleaseDate());

        title = movies.get(0).getMovieTitle();

        favoriteHelper = new FavoriteHelper(this);

        favoriteState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_add_favorite, menu);
        menuItem = menu;

        setFavorite();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_to_favorite) {
            if (isFavorite) {
                removeFromFavorite();
            } else {
                addToFavorite();
            }

            isFavorite = !isFavorite;
            setFavorite();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void favoriteState() {
        ArrayList<Favorite> favorites;
        favoriteHelper.open();

        favorites = favoriteHelper.selectByTitle(title);
        if (!favorites.isEmpty()) {
            isFavorite = true;
        }

        favoriteHelper.close();
    }

    private void setFavorite() {
        if (isFavorite) {
            menuItem.getItem(0).setIcon(R.drawable.ic_added_to_favorite);
        } else {
            menuItem.getItem(0).setIcon(R.drawable.ic_add_to_favorite);
        }
    }

    private void addToFavorite() {
        favoriteHelper.open();

        favoriteHelper.beginTransaction();

        try {
            for (Movie model : movies) {
                favoriteHelper.insertTransaction(model);
            }
            favoriteHelper.setTransactionSuccess();
        } catch (Exception e) {
            Log.e("ERROR INSERT DATA", "doInBackground: Exception");
        }

        favoriteHelper.endTransaction();

        favoriteHelper.close();

        Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show();
    }

    private void removeFromFavorite() {
        favoriteHelper.open();

        favoriteHelper.deleteByTitle(title);

        favoriteHelper.close();

        Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
