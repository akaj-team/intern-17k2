package vn.asiantech.internship.models;

/**
 * Created by root on 6/15/17.
 */

public class Feed {
    private int idImgAvatar;
    private String name;
    private int idImgThumb;

    public Feed(int idImgAvatar, String name, int idImgThumb) {
        this.idImgAvatar = idImgAvatar;
        this.name = name;
        this.idImgThumb = idImgThumb;
    }

    public int getIdImgAvatar() {
        return idImgAvatar;
    }

    public void setIdImgAvatar(int idImgAvatar) {
        this.idImgAvatar = idImgAvatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdImgThumb() {
        return idImgThumb;
    }

    public void setIdImgThumb(int idImgThumb) {
        this.idImgThumb = idImgThumb;
    }
}
