package com.khrisna.cataloguemovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.khrisna.cataloguemovie.model.Favorite;
import com.khrisna.cataloguemovie.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.BACKDROP;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.IMAGE;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.RATING;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.khrisna.cataloguemovie.database.DatabaseContract.FavoriteColumns.TITLE;
import static com.khrisna.cataloguemovie.database.DatabaseContract.TABLE_NAME;

public class FavoriteHelper {

    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<Favorite> selectByTitle(String title) {
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, new String[]{"title"}, "title =?",
                new String[]{title}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        Favorite favorite;

        if (cursor.getCount() > 0) {
            do {

                favorite = new Favorite();
                favorite.setMovieTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));

                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Favorite> query() {
        ArrayList<Favorite> arrayList = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null,
                null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {

                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setMovieTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favorite.setMovieOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favorite.setMovieReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                favorite.setMovieImage(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                favorite.setMovieBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                favorite.setMovieRating(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));

                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie movie) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, movie.getMovieTitle());
        initialValues.put(OVERVIEW, movie.getMovieOverview());
        initialValues.put(RELEASE_DATE, movie.getMovieReleaseDate());
        initialValues.put(IMAGE, movie.getMovieImage());
        initialValues.put(BACKDROP, movie.getMovieBackdrop());
        initialValues.put(RATING, movie.getMovieRating());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(Movie movie) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + TITLE + ", " + OVERVIEW
                + ", " + RELEASE_DATE + ", " + IMAGE + ", " + BACKDROP + ", " + RATING
                + ") VALUES (?, ?, ?, ?, ?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, movie.getMovieTitle());
        stmt.bindString(2, movie.getMovieOverview());
        stmt.bindString(3, movie.getMovieReleaseDate());
        stmt.bindString(4, movie.getMovieImage());
        stmt.bindString(5, movie.getMovieBackdrop());
        stmt.bindString(6, movie.getMovieRating());
        stmt.execute();
        stmt.clearBindings();

    }

    public int update(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(TITLE, favorite.getMovieTitle());
        args.put(OVERVIEW, favorite.getMovieOverview());
        args.put(RELEASE_DATE, favorite.getMovieReleaseDate());
        args.put(IMAGE, favorite.getMovieImage());
        args.put(BACKDROP, favorite.getMovieBackdrop());
        args.put(RATING, favorite.getMovieRating());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + favorite.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public void deleteByTitle(String title) {
        database.delete(TABLE_NAME, TITLE + " = '" + title + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
