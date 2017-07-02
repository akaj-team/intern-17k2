package vn.asiantech.internship.day19.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 01/07/2017.
 */
public class Song implements Parcelable {
    private String songName;
    private String songArtist;
    private int songImage;
    private String songUrl;
    private int songDuration;

    public Song() {
    }

    public Song(String songName, String songArtist, int songImage, String songUrl, int songDuration) {
        this.songName = songName;
        this.songArtist = songArtist;
        this.songImage = songImage;
        this.songUrl = songUrl;
        this.songDuration = songDuration;
    }

    protected Song(Parcel in) {
        songName = in.readString();
        songArtist = in.readString();
        songImage = in.readInt();
        songUrl = in.readString();
        songDuration = in.readInt();
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

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public int getSongImage() {
        return songImage;
    }

    public void setSongImage(int songImage) {
        this.songImage = songImage;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public int getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeString(songArtist);
        dest.writeInt(songImage);
        dest.writeString(songUrl);
        dest.writeInt(songDuration);
    }
}
