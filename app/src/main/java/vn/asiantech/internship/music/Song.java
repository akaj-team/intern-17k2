package vn.asiantech.internship.music;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * As a song project.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
public class Song implements Parcelable {
    private String name;
    private String singer;
    private String url;
    private int image;

    public Song(String name, String singer, String url, int image) {
        this.name = name;
        this.singer = singer;
        this.url = url;
        this.image = image;
    }

    public Song(String name, String url) {
        this.name = name;
        this.url = url;
    }

    private Song(Parcel in) {
        name = in.readString();
        singer = in.readString();
        url = in.readString();
        image = in.readInt();
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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
        parcel.writeInt(image);
    }
}
