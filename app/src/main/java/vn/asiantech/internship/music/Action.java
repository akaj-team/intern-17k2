package vn.asiantech.internship.music;

/**
 * Created by duc le on 08/07/2017.
 * Action contain actions transfer to broadcastReceiver
 */
enum Action {
    START("START"), PAUSE("PAUSE"), RESUME("RESUME"), SHOW("SHOW_NOTIFICATION"),
    SEEK("SEEK"), SEEK_TO("SEEK_TO"), STOP("STOP"), PLAY("PLAY"),
    SHUFFLE("SHUFFLE"), REPEAT("REPEAT"), PREVIOUS("PREVIOUS"), NEXT("NEXT"),
    CLEAR("CLEAR"), CHANGE("CHANGE"), FINISH("FINISH");

    public String getValue() {
        return mValue;
    }

    private String mValue;

    Action(String value) {
        mValue = value;
    }
}
