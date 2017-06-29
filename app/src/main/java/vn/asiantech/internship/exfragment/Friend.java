package vn.asiantech.internship.exfragment;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 10/06/2017.
 */
class Friend {
    private String name;
    private boolean isFriend;

    Friend(String name, boolean isFriend) {
        this.name = name;
        this.isFriend = isFriend;
    }

    boolean isFriend() {
        return isFriend;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void setFriend() {
        isFriend = !isFriend;
    }
}
