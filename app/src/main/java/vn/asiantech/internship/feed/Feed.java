package vn.asiantech.internship.feed;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
class Feed {
    private String name;
    private int[] images;
    private String title;

    Feed(String name, int[] images, String title) {
        this.name = name;
        this.images = images;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int[] getImages() {
        return images;
    }

    String getTitle() {
        return title;
    }
}
