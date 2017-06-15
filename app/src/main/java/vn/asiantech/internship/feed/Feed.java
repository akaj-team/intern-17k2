package vn.asiantech.internship.feed;


import java.util.List;

/**
 * Created by sony on 15/06/2017.
 */

public class Feed {
    private String name;
    private String description;
    private List<Integer> images;

    public Feed(String name, String description, List<Integer> images) {
        this.name = name;
        this.description = description;
        this.images = images;
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

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }
}
