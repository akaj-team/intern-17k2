package vn.asiantech.internship.drawer.models;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class DrawerItem {

    private String title;
    private boolean isSelect;

    public DrawerItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean iSelected) {
        this.isSelect = iSelected;
    }
}
