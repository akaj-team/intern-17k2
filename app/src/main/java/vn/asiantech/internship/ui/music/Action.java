package vn.asiantech.internship.ui.music;

/**
 * Created by quanghai on 02/07/2017.
 */

public enum Action {
    INTENT("INTENT"), PAUSE("PAUSE"), START("START"),
    RESUME("RESUME"), SEEK("SEEK"),
    SEEK_TO("SEEK_TO"), SHUFFLE("SHUFFLE"), REPLAY("REPLAY"), STOP("STOP");

    private String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
