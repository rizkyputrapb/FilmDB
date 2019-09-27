package com.khrisna.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.activity.DetailsActivity;
import com.khrisna.cataloguemovie.model.Favorite;
import com.khrisna.cataloguemovie.model.Movie;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private Cursor favorites;

    public FavoriteAdapter(Context context, Cursor favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FavoriteAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_movie_poster, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder viewHolder, int i) {
        final Favorite favorite = getItem(i);

        String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";
        Glide.with(context)
                .load(BASE_POSTER_URL + favorite.getMovieImage())
                .into(viewHolder.movieImage);

        viewHolder.movieTitle.setText(favorite.getMovieTitle());

        viewHolder.movieRating.setText(favorite.getMovieRating());

        viewHolder.posterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Movie> arrayList = new ArrayList<>();
                Movie movie;

                movie = new Movie();
                movie.setMovieTitle(favorite.getMovieTitle());
                movie.setMovieOverview(favorite.getMovieOverview());
                movie.setMovieReleaseDate(favorite.getMovieReleaseDate());
                movie.setMovieImage(favorite.getMovieImage());
                movie.setMovieBackdrop(favorite.getMovieBackdrop());
                movie.setMovieRating(favorite.getMovieRating());
                arrayList.add(movie);

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("ARG_MOVIE_DATA", arrayList);
                context.startActivity(intent);
            }
        });
    }

    private Favorite getItem(int position) {
        if (!favorites.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(favorites);
    }

    @Override
    public int getItemCount() {
        return favorites.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView posterLayout;
        private ImageView movieImage;
        private TextView movieTitle;
        private TextView movieRating;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            posterLayout = itemView.findViewById(R.id.poster_layout);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieRating = itemView.findViewById(R.id.movie_rating);
        }
    }
}
