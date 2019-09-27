package com.khrisna.cataloguemovie;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.khrisna.cataloguemovie.database.FavoriteHelper;
import com.khrisna.cataloguemovie.model.Favorite;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

public class StackRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Favorite> favorites = new ArrayList<>();
    private Context mContext;

    StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        getData(mContext);
    }

    @Override
    public void onDataSetChanged() {
        getData(mContext);
    }

    private void getData(Context context) {
        FavoriteHelper helper = new FavoriteHelper(context);
        helper.open();
        favorites = helper.query();
        helper.close();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Favorite favorite = favorites.get(position);

        Bitmap bitmap = null;

        try {
            String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BASE_POSTER_URL + favorite.getMovieBackdrop())
                    .into(SIZE_ORIGINAL, SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Load image error", e.toString());
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(ImagesBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
