package vn.asiantech.internship.ui.feed;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by ducle on 15/06/2017.
 */

public class Post {
    private Bitmap imageUSer;
    private String name;
    private List<Bitmap> imageList;
    private String desription;

    public Post(Bitmap imageUSer, String name, List<Bitmap> imageList, String desription) {
        this.imageUSer = imageUSer;
        this.name = name;
        this.imageList = imageList;
        this.desription = desription;
    }

    public Bitmap getImageUSer() {
        return imageUSer;
    }

    public void setImageUSer(Bitmap imageUSer) {
        this.imageUSer = imageUSer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bitmap> getImageList() {
        return imageList;
    }

    public void setImageList(List<Bitmap> imageList) {
        this.imageList = imageList;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }
}
