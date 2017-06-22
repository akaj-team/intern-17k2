package vn.asiantech.internship.exday13;

/**
 * Created by datbu on 21-06-2017.
 */

class ItemChat {
    private String text;
    private boolean check;

    ItemChat(String text, boolean check) {

        this.text = text;
        this.check = check;
    }

    ItemChat() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    boolean isCheck() {
        return check;
    }

    void setCheck() {
        this.check = !this.check;
    }
}
