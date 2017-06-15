package vn.asiantech.internship.models;

/**
 * Created by ducle on 15/06/2017.
 */

public class DrawerItem {
    private String name;
    private boolean isSelected;

    public DrawerItem(String name) {
        this.name = name;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
