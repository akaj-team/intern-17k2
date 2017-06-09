package vn.asiantech.internship.friend;

/**
 * Created by PC on 6/9/2017.
 */

public class Friend {
    private String name;
    private boolean isFriend;

    public Friend() {
    }

    public Friend(String name, boolean isFriend) {
        this.name = name;
        this.isFriend = isFriend;
    }

    public String getName() {
        return name;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public void setFriend() {
        isFriend = !isFriend;
    }
}
