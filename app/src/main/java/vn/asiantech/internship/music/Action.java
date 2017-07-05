package vn.asiantech.internship.music;

/**
 * Author AsianTech Inc.
 * Created by at-hangtran on 04/07/2017.
 */
enum Action {
    PAUSE("PAUSE"), PLAY("PLAY"), AUTONEXT("AUTONEXT"), START("START"),
    REPLAY("REPLAY"), NOTREPLAY("NOTREPLAY"), SHUFFEL("SHUFFLE"), CALLING("CALLING"), ENDCALL("ENDCALL"),
    NOTSHUFFEL("NOTSHUFFLE"), UPDATE("UPDATE"), PREVIOUS("PREVIOUS"), ISPAUSE("ISPAUSE"), ISPLAYING("ISPLAYING"),
    SEEK("SEEK"), INTENT("INTENT"), CANCEL("CANCEL"), NEXT("NEXT"), SEEKTO("SEEKTO"), CHOOSESONGFROMLIST("CHOOSESONGFROMLIST");

    private final String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
