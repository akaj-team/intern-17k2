package vn.asiantech.internship.Buoi5.Model;

/**
 * Created by root on 6/9/17.
 */

public class User {
    private int idFriend;
    private String nameUser;
    private boolean isFriend;

    public User(int idFriend, String nameUser, boolean isFriend) {
        this.idFriend = idFriend;
        this.nameUser = nameUser;
        this.isFriend = isFriend;
    }

    public int getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(int idFriend) {
        this.idFriend = idFriend;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
