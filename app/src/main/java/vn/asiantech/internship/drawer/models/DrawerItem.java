package vn.asiantech.internship.drawer.models;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class DrawerItem {

    private String title;
    private boolean isChoose;

    public DrawerItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean iSelected) {
        this.isChoose = iSelected;
    }
}
