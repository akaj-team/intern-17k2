package vn.asiantech.internship.models;

/**
 * Created by root on 6/15/17
 * Feed moldes
 */
public class Feed {
    private int id;
    private String name;
    private String[] idImgThumbs;
    private String description;

    public Feed(int id, String name, String[] idImgThumbs, String description) {
        this.id = id;
        this.name = name;
        this.idImgThumbs = idImgThumbs;
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

    public String[] getIdImgThumbs() {
        return idImgThumbs;
    }

    public void setIdImgThumbs(String[] idImgThumbs) {
        this.idImgThumbs = idImgThumbs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
