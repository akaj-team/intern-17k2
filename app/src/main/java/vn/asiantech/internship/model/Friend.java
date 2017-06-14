package vn.asiantech.internship.model;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class Friend {

    private String name;
    private boolean isFriend;

    public Friend(String name) {
        this.name = name;
    }

    public String getFriendName() {
        return name;
    }

    public void setFriendName(String name) {
        this.name = name;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
