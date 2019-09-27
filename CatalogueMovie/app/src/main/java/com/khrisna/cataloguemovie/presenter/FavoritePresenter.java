package com.khrisna.cataloguemovie.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.khrisna.cataloguemovie.view.FavoriteView;

import static com.khrisna.cataloguemovie.database.DatabaseContract.CONTENT_URI;

public class FavoritePresenter {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static FavoriteView view;

    public FavoritePresenter(FavoriteView view, Context context) {
        FavoritePresenter.context = context;
        FavoritePresenter.view = view;
    }

    public void getData() {
        new LoadFavoriteAsync().execute();
    }

    private static class LoadFavoriteAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.showLoading();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorites) {
            super.onPostExecute(favorites);
            view.hideLoading();

            view.showData(favorites);
        }
    }
}
