package vn.asiantech.internship.models;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/21/2017
 */
public class Message {
    private String message;
    private boolean inbox;

    public Message(String message, boolean inbox) {
        this.message = message;
        this.inbox = inbox;
    }

    public Message() {
        this.inbox = true;
    }

    public String getMessage() {
        return message;
    }

    public boolean isInbox() {
        return inbox;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setInbox() {
        this.inbox = !this.inbox;
    }
}
