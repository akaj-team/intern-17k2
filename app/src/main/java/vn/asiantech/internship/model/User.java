package vn.asiantech.internship.model;

public class User {
    private String name;
    private boolean isFriend;

    public User(String name, boolean isFriend) {
        this.name = name;
        this.isFriend = isFriend;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public String getName() {
        return name;
    }
}
