package vn.asiantech.internship.drawerlayout.model;

/**
 * Used as a object.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-12
 */
public class DrawerItem {
    private String name;
    private int image;
    private boolean state = false;

    public DrawerItem(int image, String name) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public DrawerItem() {
    }
}
