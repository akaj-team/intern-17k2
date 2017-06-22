package vn.asiantech.internship.day13.model;

/**
 * Created by at-hoavo on 22/06/2017.
 */
public class Chat {
    private String text;
    private boolean check;

    public Chat() {
    }

    public Chat(String text, boolean check) {
        this.text = text;
        this.check = check;
    }

    public String getText() {
        return text;
    }

    public boolean isCheck() {
        return check;
    }
}
