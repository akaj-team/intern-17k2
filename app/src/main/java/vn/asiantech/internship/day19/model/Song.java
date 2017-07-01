package vn.asiantech.internship.day19.model;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 01/07/2017.
 */

public class Song {
    private String songName;
    private String songArtist;
    private String songAlbum;
    private String songUrl;
    private int songDuration;

    public Song() {
    }

    public Song(String songName, String songArtist, String songAlbum, String songUrl, int songDuration) {
        this.songName = songName;
        this.songArtist = songArtist;
        this.songAlbum = songAlbum;
        this.songUrl = songUrl;
        this.songDuration = songDuration;
    }

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

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
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
}
