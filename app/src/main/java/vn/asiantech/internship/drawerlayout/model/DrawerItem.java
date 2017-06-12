package vn.asiantech.internship.drawerlayout.model;

/**
 * Created by sony on 12/06/2017.
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
