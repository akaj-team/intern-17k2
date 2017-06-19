package vn.asiantech.internship.drawer.models;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class DrawerItem {

    private String title;
    private boolean isPick;

    public DrawerItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean iSelected) {
        this.isPick = iSelected;
    }
}
