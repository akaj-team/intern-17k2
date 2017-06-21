package vn.asiantech.internship.models;

/**
 * Created by ducle on 20/06/2017.
 */

public class Note {
    private String date;
    private String title;
    private String content;
    private String urlImage;

    public Note() {

    }

    public Note(String date, String title, String content, String urlImage) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.urlImage = urlImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
