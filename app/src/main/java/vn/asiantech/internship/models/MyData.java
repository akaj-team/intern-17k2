package vn.asiantech.internship.models;

import java.io.Serializable;

import vn.asiantech.internship.ui.main.PassDataActivity;

/**
 * Created by PC on 6/13/2017.
 */
public class MyData implements Serializable {
    private PassDataActivity.OnClick listener;

    public MyData(PassDataActivity.OnClick listener) {
        this.listener = listener;
    }

    public PassDataActivity.OnClick getListener() {
        return listener;
    }

    public void setListener(PassDataActivity.OnClick listener) {
        this.listener = listener;
    }
}
