package com.khrisna.cataloguemovie.view;

import android.database.Cursor;

public interface FavoriteView {

    void showLoading();

    void hideLoading();

    void showData(Cursor movies);
}
