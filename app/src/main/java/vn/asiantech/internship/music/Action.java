package vn.asiantech.internship.music;

/**
 * Author AsianTech Inc.
 * Created by at-hangtran on 04/07/2017.
 */
enum Action {
    PAUSE("PAUSE"), PLAY("PLAY"), AUTO_NEXT("AUTO_NEXT"), START("START"),
    REPLAY("REPLAY"), NOT_REPLAY("NOT_REPLAY"), SHUFFLE("SHUFFLE"), CALLING("CALLING"), END_CALL("END_CALL"),
    NOT_SHUFFLE("NOT_SHUFFLE"), UPDATE("UPDATE"), PREVIOUS("PREVIOUS"), IS_PAUSE("IS_PAUSE"), IS_PLAYING("IS_PLAYING"),
    SEEK("SEEK"), INTENT("INTENT"), CANCEL("CANCEL"), NEXT("NEXT"), SEEK_TO("SEEK_TO"), CHOOSE_SONG_FROM_LIST("CHOOSE_SONG_FROM_LIST");

    private final String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
