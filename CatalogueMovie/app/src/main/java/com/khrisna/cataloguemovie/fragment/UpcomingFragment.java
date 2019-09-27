package com.khrisna.cataloguemovie.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.adapter.PosterAdapter;
import com.khrisna.cataloguemovie.model.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    private ArrayList<Movie> movies = new ArrayList<>();

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ARG_DATA_UPCOMING", movies);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("ARG_DATA_UPCOMING");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        Context context = getActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        assert getArguments() != null;
        movies = getArguments().getParcelableArrayList("ARG_DATA_UPCOMING");

        PosterAdapter adapter = new PosterAdapter(context, movies);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        return view;
    }
}
