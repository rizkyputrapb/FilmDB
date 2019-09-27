package com.pandeka.filmdb.Activity;

import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pandeka.filmdb.Fragment.HomeFragment;
import com.pandeka.filmdb.Fragment.SearchFragment;
import com.pandeka.filmdb.R;
import com.pandeka.filmdb.Utility.ActivityUtil;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView navigation;
    FragmentManager fragmentManager = getFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment home_fragment = new HomeFragment();
                    ActivityUtil.addFragmentBottomBar(getFragmentManager(), home_fragment,
                            R.id.fragment_container, "home");

                    fragmentManager.popBackStack("fragmentStack", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;

                case R.id.navigation_search:
                    SearchFragment search_fragment = new SearchFragment();
                    ActivityUtil.addFragmentBottomBar(getFragmentManager(), search_fragment,
                            R.id.fragment_container, "search");

                    fragmentManager.popBackStack("fragmentStack", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        }

        HomeFragment home_fragment = new HomeFragment();
        ActivityUtil.addFragmentToActivity(getFragmentManager(), home_fragment,
                R.id.fragment_container);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
