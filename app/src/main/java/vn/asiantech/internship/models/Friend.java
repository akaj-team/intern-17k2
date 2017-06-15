package vn.asiantech.internship.models;

/**
 * Created by root on 6/9/17.
 * Friends
 */
public class Friend {
    private int idFriend;
    private String nameFriend;
    private boolean isFriend;

    public Friend(int idFriend, String nameFriend, boolean isFriend) {
        this.idFriend = idFriend;
        this.nameFriend = nameFriend;
        this.isFriend = isFriend;
    }

    public int getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(int idFriend) {
        this.idFriend = idFriend;
    }

    public String getNameFriend() {
        return nameFriend;
    }

    public void setNameFriend(String nameFriend) {
        this.nameFriend = nameFriend;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
