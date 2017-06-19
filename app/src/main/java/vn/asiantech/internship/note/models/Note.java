package vn.asiantech.internship.note.models;

/**
 * Created by at-dinhvo on 19/06/2017.
 */

public class Note {

    private int id;
    private String title;
    private String content;
    private String path;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(int id, String title, String content, String path) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.path = path;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
