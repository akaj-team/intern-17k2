package vn.asiantech.internship.exday7;

import java.io.Serializable;

/**
 * Javadoc
 * Created by datbu on 14-06-2017.
 */

class SendData implements Serializable {

    private MainActivity.OnClick listener;

    SendData(MainActivity.OnClick listener) {
        this.listener = listener;
    }

    MainActivity.OnClick getListener() {
        return listener;
    }

}
