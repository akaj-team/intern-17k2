package vn.asiantech.internship.day15.drawer.models;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class DrawerItem {

    private String title;
    private boolean isSelected;

    public DrawerItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean iSelected) {
        this.isSelected = iSelected;
    }
}
