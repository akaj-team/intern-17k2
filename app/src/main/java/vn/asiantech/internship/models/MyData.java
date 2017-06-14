package vn.asiantech.internship.models;

import java.io.Serializable;

import vn.asiantech.internship.ui.main.MainActivity;

/**
 * Created by PC on 6/13/2017.
 */
public class MyData implements Serializable {
    private MainActivity.OnClick listener;

    public MyData(MainActivity.OnClick listener) {
        this.listener = listener;
    }

    public MainActivity.OnClick getListener() {
        return listener;
    }

    public void setListener(MainActivity.OnClick listener) {
        this.listener = listener;
    }
}
