package vn.asiantech.internship.models;

/**
 * Created by PC on 6/15/2017.
 */

public class FeedItem {
    private String userName;
    private String[] photoList;
    private String text;

    public FeedItem(String userName, String[] photoList, String text) {
        this.userName = userName;
        this.photoList = photoList;
        this.text = text;
    }

    public FeedItem(String userName, String photoList, String text) {
        this.userName = userName;
        this.photoList = photoList.split(",");
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public String[] getPhotoList() {
        return photoList;
    }

    public String getText() {
        return text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhotoList(String[] photoList) {
        this.photoList = photoList;
    }

    public void setText(String text) {
        this.text = text;
    }
}
