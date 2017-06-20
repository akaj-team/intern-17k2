package vn.asiantech.internship.models;

/**
 * Created by root on 6/19/17.
 * Note thumb
 */
public class Note {
    private int id;
    private String noteDate;
    private String noteTile;
    private String noteDescription;
    private String noteImagesThumb;

    public Note() {
    }

    public Note(int id, String noteDate, String noteTile, String noteDescription, String noteImagesThumb) {
        this.id = id;
        this.noteDate = noteDate;
        this.noteTile = noteTile;
        this.noteDescription = noteDescription;
        this.noteImagesThumb = noteImagesThumb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteTile() {
        return noteTile;
    }

    public void setNoteTile(String noteTile) {
        this.noteTile = noteTile;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteImagesThumb() {
        return noteImagesThumb;
    }

    public void setNoteImagesThumb(String noteImagesThumb) {
        this.noteImagesThumb = noteImagesThumb;
    }
}
