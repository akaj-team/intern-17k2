package vn.asiantech.internship.models;

/**
 * model store data
 *
 * Created by Hai on 6/15/2017.
 */

public class FeedItem {
    private String name;
    private String[] imageArray;
    private String status;

    public FeedItem() {
    }

    public FeedItem(String name, String[] imageArray, String status) {
        this.name = name;
        this.imageArray = imageArray;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(String[] imageArray) {
        this.imageArray = imageArray;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
