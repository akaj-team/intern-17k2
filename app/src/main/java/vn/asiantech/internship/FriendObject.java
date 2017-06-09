package vn.asiantech.internship;

public class FriendObject {

    private String friendName;
    private boolean isFriend;

    public FriendObject(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
