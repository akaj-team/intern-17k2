package vn.asiantech.internship.models;

/**
 * Created by root on 6/19/17.
 * Note thumb
 */
public class Note {
    private int id;
    private String date;
    private String tile;
    private String description;
    private String imagesThumb;

    public Note() {
    }

    public Note(int id, String date, String tile, String description, String imagesThumb) {
        this.id = id;
        this.date = date;
        this.tile = tile;
        this.description = description;
        this.imagesThumb = imagesThumb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagesThumb() {
        return imagesThumb;
    }

    public void setImagesThumb(String imagesThumb) {
        this.imagesThumb = imagesThumb;
    }
}
