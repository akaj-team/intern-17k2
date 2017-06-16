package vn.asiantech.internship.exday7;

import java.io.Serializable;

/**
 * Javadoc
 * Created by datbu on 14-06-2017.
 */
class SendData implements Serializable {

    private MainActivity.OnSendDataListener listener;

    SendData(MainActivity.OnSendDataListener listener) {
        this.listener = listener;
    }

    MainActivity.OnSendDataListener getListener() {
        return listener;
    }

}
