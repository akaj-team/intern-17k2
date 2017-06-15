package vn.asiantech.internship.drawer.models;

/**
 * Created by at-dinhvo on 15/06/2017.
 */
public class FeedItem {

    private String name;
    private String comment;

    public FeedItem(String name, String comment) {
        this.name = name;
        this.comment = comment;
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
