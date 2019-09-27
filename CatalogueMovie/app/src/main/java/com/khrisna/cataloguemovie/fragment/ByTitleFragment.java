package com.khrisna.cataloguemovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.adapter.PosterAdapter;
import com.khrisna.cataloguemovie.model.Movie;
import com.khrisna.cataloguemovie.model.MovieResponse;
import com.khrisna.cataloguemovie.network.GetDataService;
import com.khrisna.cataloguemovie.network.RetrofitClientInstance;
import com.khrisna.cataloguemovie.presenter.SearchPresenter;
import com.khrisna.cataloguemovie.view.SearchView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ByTitleFragment extends Fragment implements SearchView {

    private GetDataService service = RetrofitClientInstance
            .getRetrofitInstance()
            .create(GetDataService.class);
    private Context context;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public ByTitleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_by_title, container, false);

        context = getActivity();

        assert getArguments() != null;
        String query;
        if (getArguments().getString("ARG_QUERY") == null) {
            query = "";
        } else {
            query = getArguments().getString("ARG_QUERY");
            Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity()))
                    .getSupportActionBar())
                    .setDisplayShowTitleEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(getString(R.string.search_result) + query);
        }

        Call<MovieResponse> call = service.getMovieSearch(query);

        progressBar = view.findViewById(R.id.progressBar);

        recyclerView = view.findViewById(R.id.byTitle_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setHasFixedSize(true);

        SearchPresenter presenter = new SearchPresenter(this, call);
        presenter.getDataMovie();

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
    public void showData(List<Movie> dataMovie) {

        PosterAdapter adapter = new PosterAdapter(context, dataMovie);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
