package vn.asiantech.internship.note;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by datbu on 19-06-2017.
 */
class ItemNote {
    private String title;
    private String note;
    private long time;
    private String image;
    private int id;


    public ItemNote(String title, String note) {
        this.title = title;
        this.note = note;
    }

    ItemNote(String title, String note, String image) {
        this.title = title;
        this.note = note;
        this.image = image;

    }

    ItemNote(int id, String title, String note, String image, long time) {
        this.image = image;
        this.title = title;
        this.note = note;
        this.id = id;
        this.time = time;
    }

    ItemNote() {

    }

    long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = Calendar.getInstance().getTimeInMillis();
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStringTime() {

        Date date = new Date(time);
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        return dayOfWeekFormat.format(date) + "\n" + DateFormat.format("dd", date) + " " + monthFormat.format(date) + "\n" + DateFormat.format("hh:mm:ss", date);
    }
}
