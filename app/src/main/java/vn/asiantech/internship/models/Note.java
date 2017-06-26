package vn.asiantech.internship.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 6/19/17.
 * Note thumb
 */
public class Note implements Parcelable {
    private int id;
    private String date;
    private String title;
    private String description;
    private String imagesThumb;

    public Note() {
    }

    public Note(int id, String date, String title, String description, String imagesThumb) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.imagesThumb = imagesThumb;
    }

    private Note(Parcel in) {
        id = in.readInt();
        date = in.readString();
        title = in.readString();
        description = in.readString();
        imagesThumb = in.readString();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getImagesThumb() {
        return imagesThumb;
    }

    public void setImagesThumb(String imagesThumb) {
        this.imagesThumb = imagesThumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imagesThumb);
    }
}
