package vn.asiantech.internship.models;

/**
 * Created by ducle on 15/06/2017.
 */

public class Title {
    private String name;
    private boolean selected;

    public Title(String name) {
        this.name = name;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
