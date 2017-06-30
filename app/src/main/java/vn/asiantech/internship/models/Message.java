package vn.asiantech.internship.models;

/**
 * Created by Hai on 6/26/2017.
 */

public class Message {
    private String text;
    private int type;

    public Message() {
    }

    public Message(String text, int type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
