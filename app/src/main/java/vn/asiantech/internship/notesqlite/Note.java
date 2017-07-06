package vn.asiantech.internship.notesqlite;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Used as a object.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-20
 */
class Note implements Parcelable {
    private int id;
    private String dayOfWeek;
    private String day;
    private String month;
    private String hour;
    private String title;
    private String content;
    private String pathImage;

    public Note(String dayOfWeek, String day, String month, String hour, String title, String content, String pathImage) {
        this.dayOfWeek = dayOfWeek;
        this.day = day;
        this.month = month;
        this.hour = hour;
        this.title = title;
        this.content = content;
        this.pathImage = pathImage;
    }

    private Note(Parcel in) {
        id = in.readInt();
        dayOfWeek = in.readString();
        day = in.readString();
        month = in.readString();
        hour = in.readString();
        title = in.readString();
        content = in.readString();
        pathImage = in.readString();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(dayOfWeek);
        parcel.writeString(day);
        parcel.writeString(month);
        parcel.writeString(hour);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(pathImage);
    }
}
