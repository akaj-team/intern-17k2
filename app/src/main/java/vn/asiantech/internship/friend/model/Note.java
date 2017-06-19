package vn.asiantech.internship.friend.model;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class Note {

    private String name;
    private boolean isFriend;

    public Note(String name) {
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
