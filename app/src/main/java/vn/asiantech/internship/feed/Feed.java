package vn.asiantech.internship.feed;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
class Feed {
    private int id;
    private String name;
    private int[] images;
    private String title;
    private String strimages;

    Feed(String name, int[] images, String title) {
        this.name = name;
        this.images = images;
        this.title = title;
    }

    Feed(String name, String strimages, String title) {
        this.name = name;
        this.title = title;
        this.strimages = strimages;
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

    public String getStrimages() {
        return strimages;
    }

    public void setStrimages(String strimages) {
        this.strimages = strimages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
