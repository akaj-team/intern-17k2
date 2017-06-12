package vn.asiantech.internship.drawerlayout;

/**
 * Created by PC on 6/12/2017.
 */

public class MenuItem {
    private String name;
    private boolean isSelected;

    public MenuItem(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
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
