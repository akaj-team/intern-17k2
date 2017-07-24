package vn.asiantech.internship.day19.service;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 04/07/2017.
 */
public enum Action {
    SONGS("SONGS"), CHOOSE_PLAY("CHOOSE_PLAY"),
    AUTO_NEXT("AUTO_NEXT"), SHUFFLE("SUFFLE"),
    AUTO_NEXT_SELETED("AUTO_NEXT_SELECTED"),
    SHUFFLE_SELECTED("SHUFFLE_SELECTED"),
    PAUSE("PAUSE"), PLAY("PLAY"),
    SEEK("SEEK"), SEEK_TO("SEEK_TO"),
    SEND_POSITION("SEND_POSITION"), NEXT("NEXT"),
    PREVIOUS("PREVIOUS"), CLOSE_NOTIFICATION("CLOSE_NOTIFICATION"),
    CLICK_NOTIFICATION("CLICK_NOTIFICATION"), CLOSE_ACTIVITY("CLOSE_ACTIVITY");

    private String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
