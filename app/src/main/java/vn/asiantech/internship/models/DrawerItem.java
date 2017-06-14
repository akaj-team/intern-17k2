package vn.asiantech.internship.models;

/**
 * Used as a object.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-12
 */
public class DrawerItem {
    private String name;
    private boolean checked = false;

    public DrawerItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
