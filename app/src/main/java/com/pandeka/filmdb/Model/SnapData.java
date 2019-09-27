package com.pandeka.filmdb.Model;

import java.util.List;

/**
 * Created by khris on 11/01/2018.
 */

public class SnapData {

    private String mType;
    private String mText;
    private List<MovieData> mApps;
    private List<TrailerData> mApps2;

    public SnapData(String type, String text, List<MovieData> apps) {
        mType = type;
        mText = text;
        mApps = apps;
    }

    public SnapData(String text, List<TrailerData> apps) {
        mType = "Trailer";
        mText = text;
        mApps2 = apps;
    }

    public String getText() {
        return mText;
    }

    public String getType() {
        return mType;
    }

    public List<MovieData> getApps() {
        return mApps;
    }

    public List<TrailerData> getmApps2() {
        return mApps2;
    }
}
