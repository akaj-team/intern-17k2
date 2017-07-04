package vn.asiantech.internship.music;

/**
 * Author AsianTech Inc.
 * Created by at-hangtran on 04/07/2017.
 */
enum Action {
    PAUSE("PAUSE"), PLAY("PLAY"), PLAYNEXT("PLAYNEXT"), START("START"),
    AUTONEXT("AUTONEXT"), NOTAUTONEXT("NOTAUTONEXT"), SHUFFEL("SHUFFEL"), NOTSHUFFEL("NOTSHUFFEL"),
    UPDATE("UPDATE"), PREVIOUS("PREVIOUS"),
    SEEK("SEEK"),
    SEEK_TO("SEEK_TO"), NEXT("NEXT");

    private String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
