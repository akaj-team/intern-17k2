package vn.asiantech.internship.models;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 07/01/2017
 */
public enum Action {
    PAUSE("PAUSE"), RESUME("RESUME"), SEEK("SEEK"),
    SEEK_TO("SEEK_TO"), STOP("STOP"), SONG_COMPLETED("COMPLETE"), SONG_CHANGE("CHANGE"),
    NEXT_SONG("NEXT"), PREVIOUS_SONG("PRE"), STOP_SERVICE("STOP_SERVICE"),
    CALL("android.intent.action.PHONE_STATE");
    private String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
