package vn.asiantech.internship.models;

/**
 * Created by root on 6/15/17
 * Feed moldes
 */
public class Feed {
    private int id;
    private String name;
    private String[] idImgThumb;
    private String description;

    public Feed() {
    }

    public Feed(int id, String name, String[] idImgThumb, String description) {
        this.id = id;
        this.name = name;
        this.idImgThumb = idImgThumb;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIdImgThumb() {
        return idImgThumb;
    }

    public void setIdImgThumb(String[] idImgThumb) {
        this.idImgThumb = idImgThumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
