package vn.asiantech.internship.models;

/**
 * Created by datbui on 12-06-2017.
 */

public class DrawerItem {

    private String title;
    private boolean isChoose;

    public DrawerItem(String title, boolean isChoose) {
        this.title = title;
        this.isChoose = isChoose;
    }

    public DrawerItem(String title) {
        this.title = title;
    }

    public void setChoose() {
        isChoose = !isChoose;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public String getTitle() {
        return title;
    }

}
