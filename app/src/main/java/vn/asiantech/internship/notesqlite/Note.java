package vn.asiantech.internship.notesqlite;

/**
 * Created by sony on 19/06/2017.
 */

public class Note {
    private int id;
    private String date;
    private String day;
    private String month;
    private String hour;
    private String title;
    private String content;
    private String pathImage;

    public Note(int id, String date, String day, String month, String hour, String title, String content, String pathImage) {
        this.id = id;
        this.date = date;
        this.day = day;
        this.month = month;
        this.hour = hour;
        this.title = title;
        this.content = content;
        this.pathImage = pathImage;
    }

    public Note(String date, String day, String month, String hour, String title, String content, String pathImage) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.hour = hour;
        this.title = title;
        this.content = content;
        this.pathImage = pathImage;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
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

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
