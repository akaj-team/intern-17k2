package vn.asiantech.internship;

/**
 *
 * Created by datbui on 12-06-2017.
 */

class DrawerItem {


    private String title;
    private String name;
    private String email;
    private boolean isChoose;

    DrawerItem(String title, boolean isChoose) {
        this.title = title;
        this.isChoose = isChoose;
    }

    void setChoose() {
        isChoose = !isChoose;
    }

    boolean isChoose() {
        return isChoose;
    }

    String getTitle() {
        return title;
    }

}
