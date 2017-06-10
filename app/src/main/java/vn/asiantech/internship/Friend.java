package vn.asiantech.internship;

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
