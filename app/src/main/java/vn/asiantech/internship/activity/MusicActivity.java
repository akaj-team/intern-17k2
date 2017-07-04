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
        mUrls.add("http://api.mp3.zing.vn/api/mobile/song/getsonginfo?requestdata={%22id%22:%22IW9AAAEA%22}");

    }
}
