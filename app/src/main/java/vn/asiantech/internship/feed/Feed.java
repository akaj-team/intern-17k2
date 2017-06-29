package vn.asiantech.internship.feed;


import java.util.List;

/**
 * Used as a feed project
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-15
 */
class Feed {
    private String name;
    private String description;
    private List<Image> images;

    Feed(String name, String description, List<Image> images) {
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
