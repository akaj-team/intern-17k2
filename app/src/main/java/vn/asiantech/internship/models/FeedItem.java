package vn.asiantech.internship.models;

/**
 * Created by PC on 6/15/2017.
 */

public class FeedItem {
    private String userName;
    private int[] photoList;
    private String text;

    public FeedItem(String userName, int[] photoList, String text) {
        this.userName = userName;
        this.photoList = photoList;
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public int[] getPhotoList() {
        return photoList;
    }

    public String getText() {
        return text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhotoList(int[] photoList) {
        this.photoList = photoList;
    }

    public void setText(String text) {
        this.text = text;
    }
}
