package vn.asiantech.internship.day20.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by at-dinhvo on 06/07/2017.
 */
public class Song implements Parcelable {
    private String name;
    private String singer;
    private String url;
    private String image;

    public Song(String name, String singer, String url, String image) {
        this.name = name;
        this.singer = singer;
        this.url = url;
        this.image = image;
    }

    protected Song(Parcel in) {
        name = in.readString();
        singer = in.readString();
        url = in.readString();
        image = in.readString();
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(singer);
        parcel.writeString(url);
        parcel.writeString(image);
    }
}
