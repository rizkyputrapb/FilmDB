package com.pandeka.filmdb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pandeka.filmdb.Activity.MovieDetails;
import com.pandeka.filmdb.Model.MovieData;
import com.pandeka.filmdb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by khris on 11/01/2018.
 */

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {

    private List<MovieData> movieData;
    private Context context;

    PosterAdapter(Context context, List<MovieData> content) {
        this.movieData = content;
        this.context = context;
    }

    @Override
    public PosterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_poster, parent, false));
    }

    @Override
    public void onBindViewHolder(PosterAdapter.ViewHolder holder, int position) {
        final MovieData content = movieData.get(position);

        Picasso.get().load(content.getImage())
                .into(holder.poster);

        holder.title.setText(content.getTitle());
        holder.rating.setText(content.getRating());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("Backdrop_image", content.getBackdrop_path());
                intent.putExtra("Title", content.getOriginal_title());
                intent.putExtra("Poster_image", content.getImage());
                intent.putExtra("Rating", content.getRating());
                intent.putExtra("Overview", content.getOverview());
                intent.putExtra("Release_date", content.getRelease_date());
                intent.putExtra("Movie_id", content.getId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView poster;
        private TextView title;
        private TextView rating;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.poster_imageView);
            title = itemView.findViewById(R.id.title_textView);
            rating = itemView.findViewById(R.id.rating_textView);
            cardView = itemView.findViewById(R.id.poster_cardView);
        }
    }
}
