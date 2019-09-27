package com.khrisna.cataloguemovie.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class Utils {

    public Utils() {

    }

    public static FragmentTransaction addFragment(FragmentManager manager, Fragment fragment, int frameId) {

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(frameId, fragment);

        return transaction;
    }
}
