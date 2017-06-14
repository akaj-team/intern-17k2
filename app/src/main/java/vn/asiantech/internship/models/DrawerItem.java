package vn.asiantech.internship.models;

/**
 * Item for LeftMenu
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 6/12/2017.
 **/

public class DrawerItem {
    private String name;
    private boolean isSelected;

    public DrawerItem(String name) {
        this.name = name;
        this.isSelected = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setSelected() {
        isSelected = !isSelected;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
