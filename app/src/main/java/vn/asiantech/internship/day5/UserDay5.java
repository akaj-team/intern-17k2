package vn.asiantech.internship.day5;

public class UserDay5 {
    private String name;
    private String description;
    private boolean isFriend;

    public UserDay5(String name, boolean isFriend) {
        this.name = name;
        this.isFriend = isFriend;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public UserDay5(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
