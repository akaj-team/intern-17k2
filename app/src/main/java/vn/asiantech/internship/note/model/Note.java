package vn.asiantech.internship.note.model;

/**
 * Created by at-dinhvo on 19/06/2017.
 */
public class Note {

    private int id;
    private String title;
    private String content;
    private String path;
    private String datetime;

    public Note() {
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(String title, String content, String path, String datetime) {
        this.title = title;
        this.content = content;
        this.path = path;
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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
