package vn.asiantech.internship.day9.model;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 15/06/2017.
 */
public class User {
    private String name;
    private String description;
    private int image;

    public User() {
    }

    public User(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
