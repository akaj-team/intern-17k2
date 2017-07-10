package vn.asiantech.internship.ui.music;

/**
 *
 * Created by quanghai on 02/07/2017.
 */

public enum Action {
    PAUSE("PAUSE"), START("START"),
    NEXT_SONG("NEXT_SONG"), PREVIOUS_SONG("PREVIOUS_SONG"),
    RESUME("RESUME"), SEEK("SEEK"), AUTO_NEXT("AUTO_NEXT"),
    SEEK_TO("SEEK_TO"), SHUFFLE("SHUFFLE"), REPLAY("REPLAY"),
    KEY_BUNDLE_ARRAYLIST("ARRAY_LIST"), KEY_BUNDLE_POSITION("POSITION"),
    KEY_DURATION("DURATION"), KEY_CURRENT_TIME("CURRENT_TIME"),
    UPDATE_VIEW("UPDATE_VIEW"), CLOSE("CLOSE");

    private String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
