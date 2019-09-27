package com.pandeka.filmdb.Fragment;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pandeka.filmdb.Adapter.SnapAdapter;
import com.pandeka.filmdb.BuildConfig;
import com.pandeka.filmdb.R;
import com.pandeka.filmdb.Utility.ActivityUtil;
import com.pandeka.filmdb.Utility.FetchData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ByTitleFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    SnapAdapter snapAdapter = new SnapAdapter();
    String TMDB_API_KEY = BuildConfig.TheMovieDbApiKey;
    String movie_name;
    String base_url = "w500";

    public ByTitleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_by_title, container, false);
        setHasOptionsMenu(true);

        movie_name = getArguments().getString("Movie name");

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheQuality(RecyclerView.DRAWING_CACHE_QUALITY_HIGH);

        context = getActivity();

        String url_byTitle = "https://api.themoviedb.org/3/search/movie?api_key=" + TMDB_API_KEY +
                "&language=en-US&query=" + movie_name + "&page=1&include_adult=false";

        FetchData byCategory = new FetchData("Grid", "Search : " + movie_name, url_byTitle, base_url
                , context, recyclerView, snapAdapter);
        byCategory.execute();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                FragmentManager fragmentManager = ((Activity) context).getFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putString("Movie name", query);

                ByTitleFragment byTitleFragment = new ByTitleFragment();
                byTitleFragment.setArguments(bundle);
                ActivityUtil.addFragmentToActivity(fragmentManager, byTitleFragment,
                        R.id.fragment_container);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

}
