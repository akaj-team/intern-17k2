package vn.asiantech.internship.day11.model;

/**
 * Created by rimoka on 19/06/2017.
 */
public class Note {
    private String title;
    private String description;
    private String imageNote;
    private String time;

    public Note() {
    }

    public Note(String title, String description, String imageNote,String time) {
        this.title = title;
        this.description = description;
        this.imageNote = imageNote;
        this.time=time;
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

    public String getImageNote() {
        return imageNote;
    }

    public void setImageNote(String imageNote) {
        this.imageNote = imageNote;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
