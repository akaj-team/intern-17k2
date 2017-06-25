package vn.asiantech.internship.note;

/**
 * Created by datbu on 19-06-2017.
 */
class ItemNote {
    private String title;
    private String note;
    private String time;
    private String image;
    private int id;

    ItemNote(String title, String note, String image) {
        this.title = title;
        this.note = note;
        this.image = image;
    }

    public ItemNote(String title, String note, String time, String image) {
        this.title = title;
        this.note = note;
        this.time = time;
        this.image = image;
    }

    public ItemNote() {

    }

    public ItemNote(String title, String note) {
        this.title = title;
        this.note = note;
    }

    String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
