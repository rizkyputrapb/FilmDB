package com.khrisna.favoritemovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khrisna.favoritemovie.R;
import com.squareup.picasso.Picasso;

import static com.khrisna.favoritemovie.database.DatabaseContract.FavoriteColumns.IMAGE;
import static com.khrisna.favoritemovie.database.DatabaseContract.FavoriteColumns.RATING;
import static com.khrisna.favoritemovie.database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.khrisna.favoritemovie.database.DatabaseContract.FavoriteColumns.TITLE;
import static com.khrisna.favoritemovie.database.DatabaseContract.getColumnString;

public class FavoriteMovieAdapter extends CursorAdapter {

    public FavoriteMovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.content_movie_poster, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            LinearLayout posterLayout = view.findViewById(R.id.poster_layout);
            ImageView movieImage = view.findViewById(R.id.movie_image);
            TextView movieTitle = view.findViewById(R.id.movie_title);
            TextView movieRating = view.findViewById(R.id.movie_rating);
            TextView movieRelease = view.findViewById(R.id.movie_release_date);

            String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";
            Picasso.get().load(BASE_POSTER_URL + getColumnString(cursor, IMAGE))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(movieImage);

            movieTitle.setText(getColumnString(cursor, TITLE));
            movieRating.setText(getColumnString(cursor, RATING));
            movieRelease.setText(context.getString(R.string.release_date) + getColumnString(cursor, RELEASE_DATE));

        }
    }
}
