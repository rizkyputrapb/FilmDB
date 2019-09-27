package com.khrisna.cataloguemovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.khrisna.cataloguemovie.database.DatabaseContract;
import com.khrisna.cataloguemovie.database.FavoriteHelper;

import java.util.Objects;

import static com.khrisna.cataloguemovie.database.DatabaseContract.AUTHORITY;
import static com.khrisna.cataloguemovie.database.DatabaseContract.CONTENT_URI;

public class CatologueProvider extends ContentProvider {
    private static final int CATALOGUEMOVIE = 1;
    private static final int CATALOGUEMOVIE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_NAME, CATALOGUEMOVIE);

        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_NAME + "/#",
                CATALOGUEMOVIE_ID);
    }

    private FavoriteHelper favoriteHelper;

    @Override
    public boolean onCreate() {
        favoriteHelper = new FavoriteHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case CATALOGUEMOVIE:
                cursor = favoriteHelper.queryProvider();
                break;
            case CATALOGUEMOVIE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added;

        if (sUriMatcher.match(uri) == CATALOGUEMOVIE) {
            added = favoriteHelper.insertProvider(contentValues);
        } else {
            added = 0;
        }

        if (added > 0) {
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        if (sUriMatcher.match(uri) == CATALOGUEMOVIE_ID) {
            updated = favoriteHelper.updateProvider(uri.getLastPathSegment(), contentValues);
        } else {
            updated = 0;
        }

        if (updated > 0) {
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        if (sUriMatcher.match(uri) == CATALOGUEMOVIE_ID) {
            deleted = favoriteHelper.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        if (deleted > 0) {
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }

        return deleted;
    }
}
