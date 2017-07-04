package vn.asiantech.internship.day20.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MusicService extends Service {

    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_RESUME = "resume";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREVIOUS = "previous";
    public static final String ACTION_SHUFFLE = "shuffle";
    public static final String ACTION_AUTONEXT = "auto_next";

    private String mUrl;
    private MediaPlayer mMediaPlayer;
    private int mLength;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getStringExtra("url") != null) {
                mUrl = intent.getStringExtra("url");
                Log.e("AAAAA", "onReceive: " + mUrl);
            }
            switch (intent.getAction()) {
                case ACTION_PLAY:
                    Log.e("AAAAA", "ACTION_PLAY: ");
                    mMediaPlayer = new MediaPlayer();
                    try {
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mMediaPlayer.setDataSource(mUrl);
                        mMediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mMediaPlayer.start();
                        }
                    });
                    break;
                case ACTION_PAUSE:
                    break;
                case ACTION_NEXT:
                    break;
                case ACTION_PREVIOUS:
                    break;
                case ACTION_SHUFFLE:
                    break;
                case ACTION_AUTONEXT:
                    break;
                default:

            }
        }
    };

    public MusicService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_PLAY);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PREVIOUS);
        filter.addAction(ACTION_RESUME);
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("AAAAA", "onStartCommand: ");
        /*if (intent != null && intent.getStringExtra("url") != null) {
            mUrl = intent.getStringExtra("url");
        }
        Log.d(TAG, "onStartCommand: " + mUrl);
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_PAUSE)) {
                mMediaPlayer.pause();
                mLength = mMediaPlayer.getCurrentPosition();
            } else if (intent.getAction().equals(ACTION_PLAY)) {
                mMediaPlayer = new MediaPlayer();
                try {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setDataSource(mUrl);
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
//                        showNotification();
                        mMediaPlayer.start();
                    }
                });
                *//*final Intent timeIntent = new Intent(Action.SEEK.getValue());
                mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
                    @Override
                    public void onTick(long l) {
                        timeIntent.putExtra("time", mMediaPlayer.getDuration() + "");
                        timeIntent.putExtra("second", mMediaPlayer.getCurrentPosition() + "");
                        sendBroadcast(timeIntent);
                    }

                    @Override
                    public void onFinish() {
                    }
                };
                mCountDownTimer.start();*//*
            } else if (intent.getAction().equals(ACTION_RESUME)) {
                mMediaPlayer.seekTo(mLength);
                mMediaPlayer.start();
            } else if (intent.getAction().equals(
                    ACTION_NEXT)) {
                stopForeground(true);
                stopSelf();
            } else if (intent.getAction().equals(ACTION_PREVIOUS)) {
                int time = intent.getIntExtra("chooseTime", 0);
                mMediaPlayer.seekTo(time);
                mMediaPlayer.start();
            }
        }*/
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}