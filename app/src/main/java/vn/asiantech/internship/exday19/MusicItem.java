package vn.asiantech.internship.exday19;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by datbu on 02-07-2017.
 */
class MusicItem implements Serializable, Parcelable{
    private String url;
    private String songName;
    private String image;

    MusicItem(String url, String songName, String image) {
        this.url = url;
        this.songName = songName;
        this.image = image;
    }

    private MusicItem(Parcel in) {
        url = in.readString();
        songName = in.readString();
        image = in.readString();
    }

    public static final Creator<MusicItem> CREATOR = new Creator<MusicItem>() {
        @Override
        public MusicItem createFromParcel(Parcel in) {
            return new MusicItem(in);
        }

        @Override
        public MusicItem[] newArray(int size) {
            return new MusicItem[size];
        }
    };

    String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    String getImage() {
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(songName);
        dest.writeString(image);
    }
}
