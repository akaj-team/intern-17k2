package vn.asiantech.internship.models;

/**
 * Created by ducle on 22/06/2017.
 */
public class Message {
    private String substance;
    private int type;

    public Message(String substance, int type) {
        this.substance = substance;
        this.type = type;
    }

    public String getSubstance() {
        return substance;
    }

    public int getType() {
        return type;
    }
}
