package vn.asiantech.internship.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 03/07/2017.
 */

public class MusicActivity  extends AppCompatActivity{
    private List<String> mUrls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mUrls=new ArrayList<>();
        mUrls.add("http://s1mp3.r9s70.vcdn.vn/e7563f83e7c70e9957d6/727637092126384063?key=juRJjrbnnqCVlaagLaqfQQ&expires=1499125423&filename=1234-Chi-Dan.mp3");

    }
}
