package vn.asiantech.internship.day11.model;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 19/06/2017.
 */
public class Note {
    private int id;
    private String title;
    private String description;
    private String imageNote;
    private String time;

    public Note() {
    }

    public Note(int id, String title, String description, String imageNote, String time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageNote = imageNote;
        this.time = time;
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
