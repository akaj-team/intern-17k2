package vn.asiantech.internship.feed;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
class Feed {
    private int id;
    private String name;
    private String title;
    private String[] strImages;

    Feed(String name, String strImages, String title) {
        this.name = name;
        this.title = title;
        this.strImages = strImages.split(",");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String[] getStrImages() {
        return strImages;
    }

    String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
