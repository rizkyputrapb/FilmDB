package com.pandeka.filmdb.Model;

/**
 * Created by khris on 11/01/2018.
 */

public class MovieData {

    private String title;
    private String image;
    private String rating;
    private String vote;
    private String id;
    private String video;
    private String popularity;
    private String original_language;
    private String original_title;
    private String genre_ids;
    private String backdrop_path;
    private String adult;
    private String overview;
    private String release_date;

    public MovieData(String title, String image, String rating, String vote, String id,
                     String video, String popularity, String original_language,
                     String original_title, String genre_ids, String backdrop_path,
                     String adult, String overview, String release_date) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.vote = vote;
        this.id = id;
        this.video = video;
        this.popularity = popularity;
        this.original_language = original_language;
        this.original_title = original_title;
        this.genre_ids = genre_ids;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getVote() {
        return vote;
    }

    public String getId() {
        return id;
    }

    public String getVideo() {
        return video;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getGenre_ids() {
        return genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

}
