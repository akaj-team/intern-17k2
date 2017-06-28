package vn.asiantech.internship.models;

/**
 * Drawer
 * Created by huypham on 13/06/2017.
 */
public class Drawer {
    private String drawerName;
    private boolean isChoose;

    public Drawer(String drawerName) {
        this.drawerName = drawerName;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose() {
        isChoose = !isChoose;
    }
}
