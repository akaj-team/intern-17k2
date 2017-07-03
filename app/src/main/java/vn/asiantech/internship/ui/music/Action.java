package vn.asiantech.internship.ui.music;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by Thanh Thien
 */
enum Action {
    INTENT("INTENT"), PAUSE("PAUSE"), START("START"),
    PREVIOUS("PREVIOUS"), NEXT("NEXT"), TIME("TIME"),
    PROGRESSBAR("PROGRESSBAR"), SHUFFLE("SHUFFLE"),
    REPEAT("REPEAT"), SEEK("SEEK"), PLAYOTHER("PLAY_OTHER"),
    SEEK_TO("SEEK_TO"), STOP("STOP"), SHOW("SHOW");

    private String mValue;

    Action(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    public String getTime(int time) {
        int i = time / 1000;
        if (i < 10) {
            return "00:" + "0" + i;
        } else if (i < 60) {
            return "00:" + i;
        } else {
            int p = i / 60;
            String s = (i % 60) + "";
            if (s.length() == 1) {
                s = "0" + s;
            }
            return p + ":" + s;
        }
    }
}
