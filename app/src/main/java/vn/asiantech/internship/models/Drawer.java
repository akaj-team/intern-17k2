package vn.asiantech.internship.models;

/**
 * Created by anhhuy on 13/06/2017.
 */

public class Drawer {
    private String drawerName;
    private boolean isChoose;

    public Drawer(String drawerName) {
        this.drawerName = drawerName;
        this.isChoose = false;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public boolean isChoosed() {
        return isChoose;
    }

    public void setChoose() {
        isChoose = !isChoose;
    }
}

