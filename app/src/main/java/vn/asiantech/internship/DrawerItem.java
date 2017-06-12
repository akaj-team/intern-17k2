package vn.asiantech.internship;

/**
 * Created by datbui on 12-06-2017.
 */

public class DrawerItem {
    private int icon;
    private String title;
    private int avatar;
    private String name;
    private String email;

    DrawerItem(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    DrawerItem(int avatar, String name, String email) {
        this.avatar = avatar;
        this.name = name;
        this.email = email;

    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
