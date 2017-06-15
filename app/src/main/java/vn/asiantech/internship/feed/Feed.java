package vn.asiantech.internship.feed;


/**
 * Created by sony on 15/06/2017.
 */

public class Feed {
    private int avatar;
    private String name;
    private String description;
    private int[] images;

    public Feed(int avatar, String name, String description, int[] images) {
        this.avatar = avatar;
        this.name = name;
        this.description = description;
        this.images = images;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
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

    public int[] getImages() {
        return images;
    }

    public void setImages(int[] images) {
        this.images = images;
    }
}
