package com.pandeka.filmdb.Adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pandeka.filmdb.Model.SnapData;
import com.pandeka.filmdb.R;

import java.util.ArrayList;

/**
 * Created by khris on 11/01/2018.
 */

public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.ViewHolder> {
    private static final int HORIZONTAL = 1;

    private ArrayList<SnapData> mSnaps;

    public SnapAdapter() {
        mSnaps = new ArrayList<>();
    }

    public void addSnap(SnapData snap) {
        mSnaps.add(snap);
    }

    @Override
    public int getItemViewType(int position) {
        return HORIZONTAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_snap, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SnapData snap = mSnaps.get(position);

        holder.snapText.setText(snap.getText());

        holder.recyclerView.setOnFlingListener(null);
        new LinearSnapHelper().attachToRecyclerView(holder.recyclerView);

        switch (snap.getType()) {
            case "Linear":
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                        .recyclerView.getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                holder.recyclerView.setAdapter(new PosterAdapter(holder.recyclerView.getContext(), snap.getApps()));
                break;

            case "Grid":
                holder.recyclerView.setLayoutManager(new GridLayoutManager(holder.recyclerView.getContext(),
                        3));
                holder.recyclerView.setAdapter(new PosterAdapter(holder.recyclerView.getContext(), snap.getApps()));
                break;

            case "Trailer":
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                        .recyclerView.getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                holder.recyclerView.setAdapter(new TrailerAdapter(holder.recyclerView.getContext(), snap.getmApps2()));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mSnaps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView snapText;
        private RecyclerView recyclerView;

        ViewHolder(View itemView) {
            super(itemView);

            snapText = itemView.findViewById(R.id.snap_textView);
            recyclerView = itemView.findViewById(R.id.snap_recyclerView);
        }
    }
}
