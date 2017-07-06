package vn.asiantech.internship.day19.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 01/07/2017.
 */
public class Song implements Parcelable {
    private String name;
    private String artist;
    private int image;
    private String url;
    private int duration;

    public Song() {
    }

    public Song(String name, String artist, int image, String url, int duration) {
        this.name = name;
        this.artist = artist;
        this.image = image;
        this.url = url;
        this.duration = duration;
    }

    protected Song(Parcel in) {
        name = in.readString();
        artist = in.readString();
        image = in.readInt();
        url = in.readString();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        dest.writeString(name);
        dest.writeString(artist);
        dest.writeInt(image);
        dest.writeString(url);
        dest.writeInt(duration);
    }
}
