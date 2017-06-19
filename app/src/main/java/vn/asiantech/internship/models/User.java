package vn.asiantech.internship.models;

/**
 * Model class get and set data to User object
 *
 * @author at-haingo
 * @version 1.0
 * @since 2017-6-9
 */
public class User {
    String name;
    boolean isFriend;

    public User() {
    }

    public User(String name, boolean isFriend) {
        this.name = name;
        this.isFriend = isFriend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
