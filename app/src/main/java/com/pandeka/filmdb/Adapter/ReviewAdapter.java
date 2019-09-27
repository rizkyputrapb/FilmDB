package com.pandeka.filmdb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pandeka.filmdb.Model.ReviewData;
import com.pandeka.filmdb.R;

import java.util.List;

/**
 * Created by khris on 3/1/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ReviewData> kontenModels;

    public ReviewAdapter(Context context, List<ReviewData> list) {
        this.kontenModels = list;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.content_review, parent, false);
        return new ReviewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        final ReviewData mModel = kontenModels.get(position);

        holder.author.setText(mModel.getAuthor());
        holder.content.setText(mModel.getContent());
        holder.url.setText(mModel.getUrl());

    }

    @Override
    public int getItemCount() {
        return kontenModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView content;
        private TextView url;
        private LinearLayout linear;

        ViewHolder(View itemView) {
            super(itemView);

            linear = itemView.findViewById(R.id.linear);
            author = itemView.findViewById(R.id.tv_author);
            content = itemView.findViewById(R.id.tv_content);
            url = itemView.findViewById(R.id.tv_url);
        }
    }
}
