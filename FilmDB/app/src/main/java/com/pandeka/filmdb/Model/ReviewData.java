package com.pandeka.filmdb.Model;

/**
 * Created by khris on 3/1/2018.
 */

public class ReviewData {
    private String id;
    private String author;
    private String content;
    private String url;

    public ReviewData(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
