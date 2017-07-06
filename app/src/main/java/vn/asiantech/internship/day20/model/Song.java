package vn.asiantech.internship.day20.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by at-dinhvo on 06/07/2017.
 */
public class Song implements Parcelable{
    private String name;
    private String singer;
    private String url;

    public Song(String name, String singer, String url) {
        this.name = name;
        this.singer = singer;
        this.url = url;
    }

    protected Song(Parcel in) {
        name = in.readString();
        singer = in.readString();
        url = in.readString();
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

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(singer);
        parcel.writeString(url);
    }
}
