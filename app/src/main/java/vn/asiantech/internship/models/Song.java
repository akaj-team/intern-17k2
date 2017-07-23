package vn.asiantech.internship.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by quanghai on 30/06/2017.
 */
public class Song implements Parcelable {
    private int id;
    private String title;
    private String artist;
    private String url;
    private String imageUrl;
    private int time;
    private boolean isPlaying;

    public Song() {
    }

    public Song(int id, String title, String artist, String url, String imageUrl, int time) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.url = url;
        this.imageUrl = imageUrl;
        this.time = time;
    }

    public Song(int id, String title, String artist, String url, String imageUrl, int time, boolean isPlaying) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.url = url;
        this.imageUrl = imageUrl;
        this.time = time;
        this.isPlaying = isPlaying;
    }

    protected Song(Parcel in) {
        id = in.readInt();
        title = in.readString();
        artist = in.readString();
        url = in.readString();
        imageUrl = in.readString();
        time = in.readInt();
        isPlaying = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(url);
        dest.writeString(imageUrl);
        dest.writeInt(time);
        dest.writeByte((byte) (isPlaying ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
