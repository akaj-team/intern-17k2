package vn.asiantech.internship.models;

import android.text.format.DateFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Model Note
 * Created by huypham on 22/06/2017.
 */
public class Note implements Serializable {
    private int id;
    private String title;
    private String content;
    private String image;
    private long time;

    public Note(int id, String title, String content, String image, long time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.time = time;
    }

    public Note(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.time = Calendar.getInstance().getTimeInMillis();
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.time = Calendar.getInstance().getTimeInMillis();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getTime() {
        return time;
    }

    public void setTime() {
        this.time = Calendar.getInstance().getTimeInMillis();
    }

    public String getStringTime() {
        Date date = new Date(time);
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        return dayOfWeekFormat.format(date) + "\n" + DateFormat.format("dd", date) + " " + monthFormat.format(date) + "\n" + DateFormat.format("hh:mm:ss", date);
    }
}
