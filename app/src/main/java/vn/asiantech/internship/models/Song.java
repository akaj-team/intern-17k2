package vn.asiantech.internship.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ducle on 04/07/2017.
 * Song model
 */
public class Song implements Parcelable{
    private String title;
    private String artist;
    private String source;
    private int duration;

    public Song(String title, String artist, String source, int duration) {
        this.title = title;
        this.artist = artist;
        this.source = source;
        this.duration = duration;
    }

    protected Song(Parcel in) {
        title = in.readString();
        artist = in.readString();
        source = in.readString();
        duration = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(source);
        dest.writeInt(duration);
    }
}
