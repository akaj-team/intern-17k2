package vn.asiantech.internship.feed;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
class Feed {
    private String name;
    private int[] image;
    private String title;

    Feed(String name, int[] image, String title) {
        this.name = name;
        this.image = image;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int[] getImage() {
        return image;
    }

    String getTitle() {
        return title;
    }
}
