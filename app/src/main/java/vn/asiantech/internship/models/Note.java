package vn.asiantech.internship.models;

import android.net.Uri;

/**
 * model store data
 * <p>
 * Created by Hai on 6/19/2017.
 */

public class Note {
    private String dayOfWeek;
    private String dayOfMonth;
    private String time;
    private String content;
    private String image;

    public Note() {
    }

    public Note(String dayOfWeek, String dayOfMonth, String time, String content, String image) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.time = time;
        this.content = content;
        this.image = image;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
