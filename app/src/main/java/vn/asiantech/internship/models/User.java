package vn.asiantech.internship.models;

import android.graphics.Bitmap;

/**
 * Created by PC on 6/13/2017.
 */

public class User {
    private String name;
    private String email;
    private Bitmap avatar;

    public User() {
    }

    public User(String name, String email, Bitmap avatar) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}
