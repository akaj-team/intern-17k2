package vn.asiantech.internship.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thanh Thien on 7/2/2017.
 * Music Class
 */
public class Music implements Parcelable {
    private String nameSong;
    private String nameSinger;
    private String urlAvatar;
    private String urlMp3;

    public Music(String nameSong, String nameSinger, String urlAvatar, String urlMp3) {
        this.nameSong = nameSong;
        this.nameSinger = nameSinger;
        this.urlAvatar = urlAvatar;
        this.urlMp3 = urlMp3;
    }

    private Music(Parcel in) {
        nameSong = in.readString();
        nameSinger = in.readString();
        urlAvatar = in.readString();
        urlMp3 = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getUrlMp3() {
        return urlMp3;
    }

    public void setUrlMp3(String urlMp3) {
        this.urlMp3 = urlMp3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameSong);
        dest.writeString(nameSinger);
        dest.writeString(urlAvatar);
        dest.writeString(urlMp3);
    }
}
