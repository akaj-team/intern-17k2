package vn.asiantech.internship.models;

import android.text.format.DateFormat;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Data of Item for Note RecyclerView
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/19/2017
 */

public class NoteItem implements Serializable {
    private String title;
    private String content;
    private String image;
    private String time;

    public NoteItem(String title, String content, String image, String time) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.time = time;
    }

    public NoteItem(String title, String content) {
        this.title = title;
        this.content = content;
        Date date = new Date();
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        time = dayOfWeekFormat.format(date) + "\n" + DateFormat.format("dd", date) + " " + monthFormat.format(date) + "\n" + DateFormat.format("hh:mm:ss", date);
        Log.i("tag11", time);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
