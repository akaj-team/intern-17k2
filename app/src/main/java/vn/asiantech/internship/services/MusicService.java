package vn.asiantech.internship.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import vn.asiantech.internship.MusicBroadcastReceiver;
import vn.asiantech.internship.fragment.PlayMusicFragment;
import vn.asiantech.internship.models.Action;

/**
 * Created by ducle on 03/07/2017.
 */
public class MusicService extends Service implements MusicBroadcastReceiver.OnChangeMediaPlayerListener {
    public static final String KEY_TIME = "time";
    public static final String KEY_CURRENT_TIME = "current_time";
    private MediaPlayer mMediaPlayer;
    private String mUrl;
    private CountDownTimer mCountDownTimer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MusicBroadcastReceiver musicBroadcastReceiver = new MusicBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.START.getValue());
        intentFilter.addAction(Action.PAUSE.getValue());
        intentFilter.addAction(Action.RESUME.getValue());
        registerReceiver(musicBroadcastReceiver, intentFilter);
        if (intent != null && intent.getStringExtra("url") != null) {
            mUrl = intent.getStringExtra(PlayMusicFragment.KEY_URL);
        }
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(Action.INTENT.getValue())) {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                doStart(mUrl);
            }
        }
        return START_STICKY;
    }

    //    private void showNotification() {
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(String url) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        doStart(url);
    }

    private void doStart(String url) {
        try {
            mMediaPlayer.setDataSource(mUrl);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer.start();
            }
        });
        final Intent timeIntent = new Intent();
        timeIntent.setAction(Action.SEEK.getValue());
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeIntent.putExtra(KEY_TIME, mMediaPlayer.getDuration());
                timeIntent.putExtra(KEY_CURRENT_TIME, mMediaPlayer.getCurrentPosition());
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }
}
