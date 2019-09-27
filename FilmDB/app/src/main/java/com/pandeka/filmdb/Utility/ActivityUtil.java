package com.pandeka.filmdb.Utility;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by khris on 11/01/2018.
 */

public class ActivityUtil {

    public ActivityUtil() {
    }

    public static void addFragmentToActivity(FragmentManager manager, Fragment fragment, int frameId) {

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(frameId, fragment).addToBackStack("fragmentStack");
        transaction.commit();

    }

    public static void addFragmentBottomBar(FragmentManager manager, Fragment fragment, int frameId, String tag) {

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();

    }
}
