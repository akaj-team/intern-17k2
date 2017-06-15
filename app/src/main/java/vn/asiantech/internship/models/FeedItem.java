package vn.asiantech.internship.models;

/**
 *
 * Created by Hai on 6/15/2017.
 */

public class FeedItem {
    private String name;
    private int[] imageArray;
    private String status;

    public FeedItem() {
    }

    public FeedItem(String name, int[] imageArray, String status) {
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

    public int[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(int[] imageArray) {
        this.imageArray = imageArray;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
