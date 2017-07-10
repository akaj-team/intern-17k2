package vn.asiantech.internship.music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ducle on 08/07/2017.
 * Song :model
 */
public class Song implements Parcelable {
    private String title;
    private String artist;
    private String source;

    public Song(String title, String artist, String source, int duration) {
        this.title = title;
        this.artist = artist;
        this.source = source;
    }

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

    protected Song(Parcel in) {
        title = in.readString();
        artist = in.readString();
        source = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(artist);
        parcel.writeString(source);
    }
}
