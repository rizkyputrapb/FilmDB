package com.khrisna.cataloguemovie.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    private String KEY_LANGUAGE = "language";
    private String KEY_DAILY_REMINDER = "daily_reminder";
    private String KEY_TODAY_REMINDER = "today_reminder";

    private SharedPreferences prefs;

    public AppPreference(Context context) {
        String PREFS_NAME = "AppPreference";
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setLanguage(String language) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LANGUAGE, language);
        editor.apply();
    }

    public void setDailyReminder(String language) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_DAILY_REMINDER, language);
        editor.apply();
    }

    public void setTodayReminder(String language) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_TODAY_REMINDER, language);
        editor.apply();
    }

    public String getLanguage() {
        return prefs.getString(KEY_LANGUAGE, "english");
    }

    public String getDailyReminder() {
        return prefs.getString(KEY_DAILY_REMINDER, "not_daily");
    }

    public String getTodayReminder() {
        return prefs.getString(KEY_TODAY_REMINDER, "not_today");
    }
}
