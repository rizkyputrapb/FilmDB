package com.khrisna.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.khrisna.cataloguemovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movies;

    public PosterAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_movie_poster, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Movie movie = movies.get(i);

        String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";
        Glide.with(context)
                .load(BASE_POSTER_URL + movie.getMovieImage())
                .into(viewHolder.movieImage);

        viewHolder.movieTitle.setText(movie.getMovieTitle());

        viewHolder.movieRating.setText(movie.getMovieRating());

        viewHolder.posterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Movie> arrayList = new ArrayList<>();
                arrayList.add(movie);

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("ARG_MOVIE_DATA", arrayList);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
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
