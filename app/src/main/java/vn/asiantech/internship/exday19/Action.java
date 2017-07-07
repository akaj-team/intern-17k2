package vn.asiantech.internship.exday19;


/**
 * Created by datbu on 02-07-2017.
 */
enum Action {
    INTENT("INTENT"), PAUSE("PAUSE"), START("START"),
    TIME("TIME"), PLAY("PLAY"), STOP_SERVICE("STOP_SERVICE"),
    NEXT("NEXT"), PREV("PREV"), CHANGE_SONG("CHANGE_SONG"),
    REPEAT("REPEAT"), SHUFFLE("SHUFFLE"),
    RESUME("RESUME"), SEEK("SEEK"),
    SEEK_TO("SEEK_TO"), STOP("STOP");

    private String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
