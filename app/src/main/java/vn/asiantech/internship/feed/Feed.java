package vn.asiantech.internship.feed;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */

class Feed {
    private String Name;
    private int[] Image;
    private String Title;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    int[] getImage() {
        return Image;
    }

    String getTitle() {
        return Title;
    }

    Feed(String name, int[] image, String title) {

        Name = name;
        Image = image;
        Title = title;
    }

}
