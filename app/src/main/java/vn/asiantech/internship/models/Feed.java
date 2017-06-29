package vn.asiantech.internship.models;

/**
 * Model Feed
 * Created by anhhuy on 20/06/2017.
 */
public class Feed {
    private int id;
    private String title;
    private String[] imageList;
    private String description;

    public Feed(String title, String imageList, String description) {
        this.title = title;
        this.imageList = imageList.split(",");
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getImageList() {
        return imageList;
    }

    public void setImageList(String[] imageList) {
        this.imageList = imageList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
