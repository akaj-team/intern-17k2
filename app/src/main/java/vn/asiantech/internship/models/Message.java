package vn.asiantech.internship.models;

/**
 * Created by Thanh Thien on 6/22/17.
 */
public class Message {
    private String content;
    private int state;

    public Message(String content, int state) {
        this.content = content;
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
