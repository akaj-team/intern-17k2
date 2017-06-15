package vn.asiantech.internship.models;

import java.io.Serializable;

import vn.asiantech.internship.ui.main.PassDataActivity;

/**
 * Created by PC on 6/13/2017.
 */
public class MyData implements Serializable {
    private PassDataActivity.OnClickListener listener;

    public MyData(PassDataActivity.OnClickListener listener) {
        this.listener = listener;
    }

    public PassDataActivity.OnClickListener getListener() {
        return listener;
    }

    public void setListener(PassDataActivity.OnClickListener listener) {
        this.listener = listener;
    }
}
