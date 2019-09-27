package com.pandeka.filmdb.Model;

/**
 * Created by khris on 15/01/2018.
 */

public class CategoryData {
    private String id;
    private String category;

    public CategoryData(String id, String category) {
        this.id = id;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }
}
