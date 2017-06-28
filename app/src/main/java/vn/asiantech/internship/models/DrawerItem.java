package vn.asiantech.internship.models;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 15/06/2017.
 */
public class DrawerItem {

    private String title;
    private boolean choose;

    public DrawerItem(String title, boolean isChoose) {
        this.title = title;
        this.choose = isChoose;
    }

    public DrawerItem(String title) {
        this.title = title;
    }

    public void setChoose() {
        choose = !choose;
    }

    public boolean isChoose() {
        return choose;
    }

    public String getTitle() {
        return title;
    }

}
