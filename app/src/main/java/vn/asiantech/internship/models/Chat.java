package vn.asiantech.internship.models;

/**
 * Created by anhhuy on 22/06/2017.
 */

public class Chat {
    private String message;
    private boolean index;

    public Chat(String message, boolean index) {
        this.message = message;
        this.index = index;
    }

    public Chat() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIndex() {
        return index;
    }

    public void setIndex() {
        this.index = !index;
    }
}
