package com.khrisna.cataloguemovie.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.adapter.FavoriteAdapter;
import com.khrisna.cataloguemovie.presenter.FavoritePresenter;
import com.khrisna.cataloguemovie.view.FavoriteView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements FavoriteView {

    private Context context;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        context = getContext();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressBar);

        FavoritePresenter presenter = new FavoritePresenter(this, context);
        presenter.getData();

        return view;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showData(Cursor favorites) {
        if (favorites.getCount() == 0) {
            Toast.makeText(context, "No favorite data", Toast.LENGTH_SHORT).show();
        }

        FavoriteAdapter adapter = new FavoriteAdapter(context, favorites);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }
}
