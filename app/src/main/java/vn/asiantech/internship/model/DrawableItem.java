package vn.asiantech.internship.model;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class DrawableItem {

    private String title;
    private boolean isSelect;

    public DrawableItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
