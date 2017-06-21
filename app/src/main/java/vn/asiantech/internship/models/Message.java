package vn.asiantech.internship.models;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/21/2017
 */
public class Message {
    private String text;
    private boolean inbox;

    public Message(String text, boolean inbox) {
        this.text = text;
        this.inbox = inbox;
    }

    public Message() {
        this.inbox = true;
    }

    public String getText() {
        return text;
    }

    public boolean isInbox() {
        return inbox;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setInbox() {
        this.inbox = !this.inbox;
    }
}
