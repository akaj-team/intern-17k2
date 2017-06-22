package vn.asiantech.internship.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * model store data
 * <p>
 * Created by Hai on 6/19/2017.
 */

public class Note implements Parcelable {
    private String dayOfWeek;
    private String dayOfMonth;
    private String time;
    private String title;
    private String content;
    private String image;

    public Note() {
    }

    public Note(String dayOfWeek, String dayOfMonth, String time, String title, String content, String image) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.time = time;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    protected Note(Parcel in) {
        dayOfWeek = in.readString();
        dayOfMonth = in.readString();
        time = in.readString();
        title = in.readString();
        content = in.readString();
        image = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dayOfWeek);
        dest.writeString(dayOfMonth);
        dest.writeString(time);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(image);
    }
}
