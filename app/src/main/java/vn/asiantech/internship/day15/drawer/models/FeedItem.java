package vn.asiantech.internship.day15.drawer.models;

import java.util.List;

/**
 * Created by at-dinhvo on 01/07/2017.
 */
public class FeedItem {

    private String name;
    private List<Integer> images;
    private String comment;

    public FeedItem(String name, List<Integer> images, String comment) {
        this.name = name;
        this.images = images;
        this.comment = comment;
    }

    public List<Integer> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
