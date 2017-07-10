package vn.asiantech.internship.day20.model;

/**
 * Created by at-dinhvo on 07/07/2017.
 */
public enum Action {
    PLAY("play"), PAUSE("pause"),
    RESUME("resume"), NEXT("next"),
    PREVIOUS("previous"), STOP("stop"),
    SHUFFLE("shuffle"), AUTONEXT("auto_next"),
    EXIT("exit");

    private String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
