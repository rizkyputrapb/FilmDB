package com.pandeka.filmdb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pandeka.filmdb.Model.TrailerData;
import com.pandeka.filmdb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by khris on 3/1/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private List<TrailerData> mContent;
    private Context context;

    TrailerAdapter(Context context, List<TrailerData> content) {
        mContent = content;
        this.context = context;
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailerAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_trailer, parent, false));
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {
        final TrailerData content = mContent.get(position);

        Picasso.get().load("https://img.youtube.com/vi/" + content.getKey() + "/mqdefault.jpg")
                .into(holder.imageView);

        holder.title.setText(content.getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + content.getKey())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_movie);
            title = itemView.findViewById(R.id.tv_title);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
