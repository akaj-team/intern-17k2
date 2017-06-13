package vn.asiantech.internship.models;

import android.graphics.Bitmap;

/**
 * include data header
 */
public class User {
    private Bitmap imgUser;
    private String name;
    private String email;

    public User(Bitmap imgUser, String name, String email) {
        this.imgUser = imgUser;
        this.name = name;
        this.email = email;
    }

    public Bitmap getImgUser() {
        return imgUser;
    }

    public void setImgUser(Bitmap imgUser) {
        this.imgUser = imgUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
}
