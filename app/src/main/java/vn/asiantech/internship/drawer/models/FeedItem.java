package vn.asiantech.internship.drawer.models;

import java.util.List;

/**
 * Created by at-dinhvo on 15/06/2017.
 */
public class FeedItem {

    private String name;
    private List<String> images;
    private String comment;

    public FeedItem(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public FeedItem(String name, List<String> images, String comment) {
        this.name = name;
        this.images = images;
        this.comment = comment;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
