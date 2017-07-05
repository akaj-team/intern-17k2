package vn.asiantech.internship.day15.drawer.models;

/**
 * Created by at-dinhvo on 01/07/2017.
 */
public class FeedItem {

    private String name;
    private String[] images;
    private String comment;

    public FeedItem(String name, String[] images, String comment) {
        this.name = name;
        this.images = images;
        this.comment = comment;
    }

    public String[] getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
