package vn.asiantech.internship.models;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 15/06/2017.
 */
public class DrawerItem {

    private String title;
    private boolean Choose;

    public DrawerItem(String title, boolean isChoose) {
        this.title = title;
        this.Choose = isChoose;
    }

    public DrawerItem(String title) {
        this.title = title;
    }

    public void setChoose() {
        Choose = !Choose;
    }

    public boolean isChoose() {
        return Choose;
    }

    public String getTitle() {
        return title;
    }

}
