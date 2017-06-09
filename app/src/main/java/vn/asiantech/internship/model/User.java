package vn.asiantech.internship.model;

/**
 * Created by Hai on 6/9/2017.
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
