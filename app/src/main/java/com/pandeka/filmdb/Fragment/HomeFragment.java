package com.pandeka.filmdb.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pandeka.filmdb.Adapter.SnapAdapter;
import com.pandeka.filmdb.BuildConfig;
import com.pandeka.filmdb.R;
import com.pandeka.filmdb.Utility.FetchData;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    String TMDB_API_KEY = BuildConfig.TheMovieDbApiKey;
    SnapAdapter snapAdapter = new SnapAdapter();
    String url_nowPlaying = "https://api.themoviedb.org/3/movie/now_playing?api_key="
            + TMDB_API_KEY + "&language=en-US&page=1";
    String url_popular = "https://api.themoviedb.org/3/movie/popular?api_key="
            + TMDB_API_KEY + "&language=en-US&page=1";
    String url_topRated = "https://api.themoviedb.org/3/movie/top_rated?api_key="
            + TMDB_API_KEY + "&language=en-US&page=1";
    String url_upcoming = "https://api.themoviedb.org/3/movie/upcoming?api_key="
            + TMDB_API_KEY + "&language=en-US&page=1";
    String base_url = "w500";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheQuality(RecyclerView.DRAWING_CACHE_QUALITY_HIGH);

        context = getActivity();

        FetchData nowPlaying = new FetchData("Linear", "Now Playing", url_nowPlaying, base_url
                , context, recyclerView, snapAdapter);
        FetchData popular = new FetchData("Linear", "Popular", url_popular, base_url
                , context, recyclerView, snapAdapter);
        FetchData topRated = new FetchData("Linear", "Top Rated", url_topRated, base_url
                , context, recyclerView, snapAdapter);
        FetchData upcoming = new FetchData("Linear", "Upcoming", url_upcoming, base_url
                , context, recyclerView, snapAdapter);

        nowPlaying.execute();
        popular.execute();
        topRated.execute();
        upcoming.execute();

        return view;
    }

}
