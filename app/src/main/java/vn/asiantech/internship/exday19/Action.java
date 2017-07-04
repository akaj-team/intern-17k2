package vn.asiantech.internship.exday19;


/**
 * Created by datbu on 02-07-2017.
 */
enum Action {
    INTENT("INTENT"), PAUSE("PAUSE"), START("START"),
    TIME("TIME"), PLAY("PLAY"),
    NEXT("NEXT"), PREV("PREV"),
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
