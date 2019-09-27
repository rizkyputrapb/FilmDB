package com.khrisna.cataloguemovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.BACKDROP;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.IMAGE;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.RATING;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.TITLE;
import static com.khrisna.cataloguemovie.database.DatabaseContract.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbfavorite";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_FAVORITE = "create table " + TABLE_NAME
            + " (" + _ID + " integer primary key autoincrement, "
            + TITLE + " text not null, "
            + OVERVIEW + " text not null, "
            + RELEASE_DATE + " text not null, "
            + IMAGE + " text not null, "
            + BACKDROP + " text not null, "
            + RATING + " text not null);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
