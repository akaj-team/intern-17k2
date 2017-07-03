package vn.asiantech.internship.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ducle on 03/07/2017.
 *
 */
public class MusicService extends Service {
    private MediaPlayer mMediaPlayer;
    private String mUrl;
    private int mLength;
    private CountDownTimer mCountDownTimer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getStringExtra("url") != null) {
            mUrl = intent.getStringExtra("url");
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
