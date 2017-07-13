package vn.asiantech.internship.model;

/**
 * Used as a object.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-12
 */
public class DrawerItem {
    private String name;
    private boolean isCheck;

    public DrawerItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean isChecked) {
        this.isCheck = isChecked;
    }
}
