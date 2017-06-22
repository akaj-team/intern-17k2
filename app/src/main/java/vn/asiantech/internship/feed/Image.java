package vn.asiantech.internship.feed;

/**
 * Used as a image project
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-15
 */
class Image {
    private int id;
    private String title;
    private String description;
    private String link;

    public Image(int id, String title, String description, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public Image(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public Image(String link) {
        this.link = link;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
