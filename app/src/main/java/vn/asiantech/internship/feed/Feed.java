package vn.asiantech.internship.feed;

/**
 * Created by datbu on 15-06-2017.
 */

public class Feed {
    String Name;
    int[] image;
    String title;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int[] getImage() {
        return image;
    }

    public void setImage(int[] image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Feed(String name, int[] image, String title) {

        Name = name;
        this.image = image;
        this.title = title;
    }

}
