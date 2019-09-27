package com.khrisna.cataloguemovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.model.Movie;
import com.khrisna.cataloguemovie.model.MovieResponse;
import com.khrisna.cataloguemovie.network.GetDataService;
import com.khrisna.cataloguemovie.network.RetrofitClientInstance;
import com.khrisna.cataloguemovie.presenter.HomePresenter;
import com.khrisna.cataloguemovie.view.HomeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {

    private GetDataService service = RetrofitClientInstance
            .getRetrofitInstance()
            .create(GetDataService.class);
    private Call<MovieResponse> getNowPlaying = service.getNowPlaying();
    private Call<MovieResponse> getUpcoming = service.getUpcoming();
    private Context context;
    private ProgressBar progressBar;
    private ViewPager pager;
    private TabLayout tabs;
    private HomePagerAdapter adapter;
    private ArrayList<Movie> dataNowPlaying = new ArrayList<>();
    private ArrayList<Movie> dataUpcoming = new ArrayList<>();

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("DATA_NOW_PLAYING", dataNowPlaying);
        outState.putParcelableArrayList("DATA_UPCOMING", dataUpcoming);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity()))
                .getSupportActionBar()).setDisplayShowTitleEnabled(true);
        setHasOptionsMenu(true);

        context = getActivity();
        progressBar = view.findViewById(R.id.progressBar);
        pager = view.findViewById(R.id.pager);
        tabs = view.findViewById(R.id.tabs);

        if (savedInstanceState != null) {
            dataNowPlaying = savedInstanceState.getParcelableArrayList("DATA_NOW_PLAYING");
            dataUpcoming = savedInstanceState.getParcelableArrayList("DATA_UPCOMING");

            progressBar.setVisibility(View.INVISIBLE);

            adapter = new HomePagerAdapter(getChildFragmentManager(), dataNowPlaying, dataUpcoming);
            adapter.notifyDataSetChanged();

            setUpPagerAndTabs();
        } else {
            HomePresenter presenter = new HomePresenter(this, getNowPlaying, getUpcoming);
            presenter.getDataMovie();
        }

        return view;
    }

    private void setUpPagerAndTabs() {
        tabs.setTabTextColors(ContextCompat.getColor(context, R.color.greyBackground),
                ContextCompat.getColor(context, R.color.textWhite));
        tabs.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.colorPrimary));

        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
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
    public void showData(List<Movie> data1, List<Movie> data2) {
        dataNowPlaying.addAll(data1);
        dataUpcoming.addAll(data2);

        if (isAdded()) {
            adapter = new HomePagerAdapter(getChildFragmentManager(), dataNowPlaying, dataUpcoming);
            adapter.notifyDataSetChanged();

            setUpPagerAndTabs();
        }
    }

    private class HomePagerAdapter extends FragmentStatePagerAdapter {

        private String[] tabName = {getString(R.string.now_playing), getString(R.string.upcoming)};
        private ArrayList<Movie> dataNowPlaying = new ArrayList<>();
        private ArrayList<Movie> dataUpcoming = new ArrayList<>();
        private Bundle args = new Bundle();

        HomePagerAdapter(FragmentManager fm, ArrayList<Movie> dataNowPlaying,
                         ArrayList<Movie> dataUpcoming) {
            super(fm);

            this.dataNowPlaying.addAll(dataNowPlaying);
            this.dataUpcoming.addAll(dataUpcoming);
        }

        @Override
        public Fragment getItem(int i) {
            if (tabName[i].equals(getString(R.string.now_playing))) {
                args.putParcelableArrayList("ARG_DATA_NOW_PLAYING", dataNowPlaying);
                NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                nowPlayingFragment.setArguments(args);
                return nowPlayingFragment;
            } else {
                args.putParcelableArrayList("ARG_DATA_UPCOMING", dataUpcoming);
                UpcomingFragment upcomingFragment = new UpcomingFragment();
                upcomingFragment.setArguments(args);
                return upcomingFragment;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabName[position];
        }

        @Override
        public int getCount() {
            return tabName.length;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
