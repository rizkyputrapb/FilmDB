package com.khrisna.cataloguemovie.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.fragment.FavoriteFragment;
import com.khrisna.cataloguemovie.fragment.HomeFragment;
import com.khrisna.cataloguemovie.fragment.SearchFragment;
import com.khrisna.cataloguemovie.utils.AppPreference;
import com.khrisna.cataloguemovie.utils.Utils;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_FRAGMENT = "key_fragment";
    private FragmentManager manager = getSupportFragmentManager();
    private Locale locale;
    private Fragment home = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppPreference preferences = new AppPreference(this);

        String language = preferences.getLanguage();

        setLanguage(language);

        BottomNavigationView navigation = findViewById(R.id.navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home).commit();
        } else {
            home = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
        }

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setLanguage(String language) {

        if (language.equals("indonesia")) {
            locale = new Locale("in");
        } else if (language.equals("english")) {
            locale = new Locale("en");
        }

        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    home = new HomeFragment();
                    Utils.addFragment(manager, home, R.id.fragment_container)
                            .commit();
                    return true;

                case R.id.navigation_search:
                    SearchFragment searchFragment = new SearchFragment();
                    Utils.addFragment(manager, searchFragment, R.id.fragment_container)
                            .commit();
                    return true;

                case R.id.navigation_favorite:
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    Utils.addFragment(manager, favoriteFragment, R.id.fragment_container)
                            .commit();
                    return true;

                default:
                    return false;
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            home = new HomeFragment();
            Utils.addFragment(manager, home, R.id.fragment_container)
                    .commit();
        } else if (id == R.id.nav_search) {
            SearchFragment searchFragment = new SearchFragment();
            Utils.addFragment(manager, searchFragment, R.id.fragment_container)
                    .commit();
        } else if (id == R.id.nav_favorite) {
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            Utils.addFragment(manager, favoriteFragment, R.id.fragment_container)
                    .commit();
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, home);
        super.onSaveInstanceState(outState);
    }
}
