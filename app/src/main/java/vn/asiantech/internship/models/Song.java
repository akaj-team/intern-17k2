package vn.asiantech.internship.models;

/**
 * Created by ducle on 04/07/2017.
 * Song model
 */
public class Song {
    private String title;
    private String artist;
    private String source;
    private int duration;

    public Song(String title, String artist, String source, int duration) {
        this.title = title;
        this.artist = artist;
        this.source = source;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
