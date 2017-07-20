package vn.asiantech.internship.note;

import java.io.Serializable;

/**
 * Created by datbu on 19-06-2017.
 */
public class ItemNote implements Serializable {
    private String title;
    private String note;
    private String time;
    private String image;
    private int id;

    ItemNote(String time, String title, String note, String image) {
        this.title = title;
        this.note = note;
        this.time = time;
        this.image = image;
    }

    ItemNote() {
    }

    ItemNote(String time, String title, String note) {
        this.note = note;
        this.time = time;
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    String getImage() {
        return image;
    }

    void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
