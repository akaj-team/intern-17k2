package vn.asiantech.internship.day20.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import vn.asiantech.internship.day20.model.Song;
import vn.asiantech.internship.day20.ui.MusicFragment;

public class MusicService extends Service {

    public static final String TAG = "at-dinhvo";

    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_RESUME = "resume";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREVIOUS = "previous";
    public static final String ACTION_SHUFFLE = "shuffle";
    public static final String ACTION_AUTONEXT = "auto_next";

    private ArrayList<Integer> raws = new ArrayList();
    private ArrayList<Integer> shuffles = new ArrayList<>();
    private Song mSong;
    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;
    private int mLength;
    private boolean isPause = false;
    private boolean isAutoNext = false;
    private boolean isShuffle = false;
    private int currentPost = 0;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getParcelableExtra(MusicFragment.KEY_SONG) != null) {
                mSong = intent.getParcelableExtra(MusicFragment.KEY_SONG);
                switch (intent.getAction()) {
                    case ACTION_PLAY:
                        Log.e(TAG, "ACTION_PLAY" + mSong.getName());
//                        startMusic();
                        startMedia();
                        break;
                    case ACTION_PAUSE:
                        Log.e(TAG, "ACTION_PAUSE" + mSong.getName());
//                        pauseMusic();
                        pauseMedia();
                        break;
                    case ACTION_RESUME:
                        Log.e(TAG, "ACTION_RESUME" + mSong.getName());
//                        resumeMusic();
                        resumeMedia();
                        break;
                    case ACTION_NEXT:
                        Log.e(TAG, "ACTION_NEXT" + mSong.getName());
//                        nextMusic();
                        nextMedia();
                        break;
                    case ACTION_PREVIOUS:
                        Log.e(TAG, "ACTION_PREVIOUS" + mSong.getName());
//                        previousMusic();
                        nextMedia();
                        break;
                    case ACTION_SHUFFLE:
//                        shuffleMusic();
                        break;
                    case ACTION_AUTONEXT:
//                        autoNextMusic();
                        break;
                    case Intent.ACTION_SCREEN_OFF:
                        Log.e(TAG, "ACTION_SCREEN_OFF");
                    default:

                }
            }
        }
    };

    public MusicService() {
    }

    private void startMedia() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setDataSource(mSong.getUrl());
                mMediaPlayer.prepare();
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.toString());
            }
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mMediaPlayer.start();
                    handlerProgress();
                }
            });
        }
    }

    private void nextMedia() {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(mSong.getUrl());
            mMediaPlayer.prepare();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IOException e) {
            Log.e(TAG, "nextMedia: " + mSong.getName());
        }
    }

    private void previousMedia() {
//        mMediaPlayer.reset();
        startMedia();
    }

    private void pauseMedia() {
        mMediaPlayer.pause();
        isPause = true;
        mLength = mMediaPlayer.getCurrentPosition();
    }

    private void resumeMedia() {
        resumeMusic();
    }

    private void shuffleMedia() {

    }

    private void autoNextMedia() {

    }

    private void pauseMusic() {
        mMediaPlayer.pause();
        isPause = true;
        mLength = mMediaPlayer.getCurrentPosition();
    }

    private void resumeMusic() {
        mMediaPlayer.seekTo(mLength);
        startMusic();
    }

    private void startMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            handlerProgress();
        }
    }

    private void nextMusic() {
        mMediaPlayer.reset();
        currentPost = (currentPost == raws.size() - 1) ? 0 : currentPost + 1;
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), raws.get(currentPost));
        startMusic();
    }

    private void previousMusic() {
        mMediaPlayer.reset();
        currentPost = (currentPost == 0) ? raws.size() - 1 : currentPost - 1;
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), raws.get(currentPost));
        startMusic();
    }

    private void handlerProgress() {
        final Intent timeIntent = new Intent();
        timeIntent.setAction(MusicFragment.CURRENT_TIME);
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
            @Override
            public void onTick(long l) {
                timeIntent.putExtra("time", mMediaPlayer.getDuration());
                timeIntent.putExtra("second", mMediaPlayer.getCurrentPosition());
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {
                if (isAutoNext) {
                    Intent intentAutoNext = new Intent();
                    intentAutoNext.setAction(ACTION_AUTONEXT);
                    sendBroadcast(intentAutoNext);
                }
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_PLAY);
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PREVIOUS);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_RESUME);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMediaPlayer = new MediaPlayer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}