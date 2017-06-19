package vn.asiantech.internship.note;

/**
 * Created by datbu on 19-06-2017.
 */

public class ItemNote {
    private String title;
    private String note;
    private int image;

    public ItemNote(String title, String note, int image) {
        this.title = title;
        this.note = note;
        this.image = image;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
