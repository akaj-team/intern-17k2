package vn.asiantech.internship.ui.feed;

import android.graphics.Bitmap;

/**
 * Created by ducle on 15/06/2017.
 */
public class Post {
    private Bitmap imageUSer;
    private String name;
    private String imageList;
    private String desription;

    public Post() {
    }

    public void setImageUSer(Bitmap imageUSer) {
        this.imageUSer = imageUSer;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public Bitmap getImageUSer() {
        return imageUSer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageList() {
        return imageList;
    }

    public String getDesription() {
        return desription;
    }
}
