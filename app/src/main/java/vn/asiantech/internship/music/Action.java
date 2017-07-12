package vn.asiantech.internship.music;

/**
 * Created by ducle on 08/07/2017.
 * Action contain actions transfer to broadcastReceiver
 */
public enum Action {
    START("START"), PAUSE("PAUSE"), RESUME("RESUME"), SHOW("SHOWNOTIFICATION"),
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
