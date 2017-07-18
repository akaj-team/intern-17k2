package vn.asiantech.internship.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 07/01/2017
 */
public class Song implements Parcelable {
    private String name;
    private String singer;
    private String songUrl;
    private String imageUrl;
    private Bitmap iconBitmap;
    private boolean isLoaded;
    private boolean isPlaying;

    public Song(String name, String singer, String songUrl, String imageUrl) {
        this.name = name;
        this.singer = singer;
        this.songUrl = songUrl;
        this.imageUrl = imageUrl;
    }

    public Song(String name, String singer) {
        this.name = name;
        this.singer = singer;
    }

    protected Song(Parcel in) {
        name = in.readString();
        singer = in.readString();
        songUrl = in.readString();
        imageUrl = in.readString();
        iconBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        isLoaded = in.readByte() != 0;
        isPlaying = in.readByte() != 0;
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

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying() {
        isPlaying = !isPlaying;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }

    public void setLoaded() {
        isLoaded = !isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(singer);
        dest.writeString(songUrl);
        dest.writeString(imageUrl);
        dest.writeParcelable(iconBitmap, flags);
        dest.writeByte((byte) (isLoaded ? 1 : 0));
        dest.writeByte((byte) (isPlaying ? 1 : 0));
    }
}
