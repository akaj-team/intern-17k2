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
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.day20.model.Song;
import vn.asiantech.internship.day20.ui.MusicActivity;
import vn.asiantech.internship.day20.ui.MusicFragment;

/**
 * Service
 */
public class MusicService extends Service {

    public static final String TAG = "at-dinhvo";

    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_RESUME = "resume";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREVIOUS = "previous";
    public static final String ACTION_SHUFFLE = "shuffle";
    public static final String ACTION_AUTONEXT = "auto_next";
    public static final String SONG_SHUFFLE = "song_shuffle";
    public static final String POS_SHUFFLE = "posion_shuffle";
    public static final String DURATION = "duration";

    private List<Song> mSongs;
    private Song mSong;
    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;
    private int mLength;
    private boolean isPause = false;
    private boolean isAutoNext = false;
    private boolean isShuffle = false;
    private int mCurrentPosition;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: intent :" + (intent != null));
            if (intent != null) {
                switch (intent.getAction()) {
                    case ACTION_PLAY:
                        mCurrentPosition = intent.getIntExtra(MusicActivity.KEY_POS, -1);
                        if (mCurrentPosition != -1) {
                            mSong = mSongs.get(mCurrentPosition);
                            Log.e(TAG, "onReceive: " + mSong.getName());
                        }
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.stop();
                            mMediaPlayer.reset();
                        }
                        startMedia();
                        break;
                    case ACTION_PAUSE:
                        pauseMusic();
                        break;
                    case ACTION_RESUME:
                        resumeMusic();
                        break;
                    case ACTION_NEXT:
                        Log.e(TAG, "ACTION_NEXT");
                        nextMusic();
                        break;
                    case ACTION_PREVIOUS:
                        Log.e(TAG, "ACTION_PREVIOUS");
                        previousMusic();
                        break;
                    case ACTION_SHUFFLE:
                        isShuffle = (!isShuffle);
                        Log.e(TAG, "ACTION_SHUFFLE" + isShuffle);
                        break;
                    case ACTION_AUTONEXT:
                        isAutoNext = (!isAutoNext);
                        Log.e(TAG, "ACTION_AUTONEXT" + isAutoNext);
                        break;
                    case Intent.ACTION_SCREEN_OFF:
                        Log.e(TAG, "ACTION_SCREEN_OFF");
                    default:

                }
            }
        }
    };

    private void shuffleMusic() {
        Random random = new Random();
        int ran;
        do {
            ran = random.nextInt(mSongs.size() - 1);
        } while (ran == mCurrentPosition);
        mSong = mSongs.get(ran);
        Intent intentShuffle = new Intent();
        intentShuffle.setAction(SONG_SHUFFLE);
        intentShuffle.putExtra(POS_SHUFFLE, ran);
        sendBroadcast(intentShuffle);
        Log.e(TAG, "shuffle music: " + ran + ":" + mSong.getName());
    }

    public MusicService() {

    }

    private void startMedia() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        Log.e(TAG, "at-dinhvo: startMedia:");
        try {
            mMediaPlayer.setDataSource(mSong.getUrl());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.toString());
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer.start();
                Intent durationIntent = new Intent();
                durationIntent.setAction(MusicService.DURATION);
                durationIntent.putExtra("time", showTime(mMediaPlayer.getDuration() / 1000));
                durationIntent.putExtra("timeInt", mMediaPlayer.getDuration() / 1000);
                sendBroadcast(durationIntent);
                handlerProgress();
            }
        });
    }

    private void nextMusic() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        if (isShuffle) {
            shuffleMusic();
            Log.e(TAG, "nextMusic: " + mSong.getName());
        } else {
            mCurrentPosition = (mCurrentPosition == mSongs.size() - 1) ? 0 : mCurrentPosition + 1;
            mSong = mSongs.get(mCurrentPosition);
            Log.e(TAG, "nextMusic: " + mCurrentPosition);
        }
        try {
            mMediaPlayer.setDataSource(mSong.getUrl());
            mMediaPlayer.prepare();
            startMusic();
            Log.e(TAG, "nextMusic: " + mSong.getName());
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }
    }

    private void showNotification() {
        // TODO: 07/07/2017
    }

    private void previousMusic() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        if (isShuffle) {
            shuffleMusic();
        } else {
            mCurrentPosition = (mCurrentPosition == 0) ? mSongs.size() - 1 : mCurrentPosition - 1;
            mSong = mSongs.get(mCurrentPosition);
            Log.e(TAG, "prevMusic: " + mCurrentPosition);
        }
        try {
            mMediaPlayer.setDataSource(mSong.getUrl());
            mMediaPlayer.prepare();
            startMusic();
            Log.e(TAG, "nextMusic: " + mSong.getName());
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }
    }

    private void resetMusic() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
        }
        Log.e(TAG, "at-dinhvo: resetMusic:");
    }

    private void pauseMusic() {
        Log.e(TAG, "at-dinhvo: pauseMusic:");
        mMediaPlayer.pause();
        isPause = true;
        mLength = mMediaPlayer.getCurrentPosition();
    }

    private void resumeMusic() {
        Log.e(TAG, "at-dinhvo: resumeMusic:");
        mMediaPlayer.seekTo(mLength);
        startMusic();
    }

    private void startMusic() {
        Log.e(TAG, "at-dinhvo: startMusic:");
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            handlerProgress();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (isAutoNext) {
                        if (isShuffle) {
                            shuffleMusic();
                        }
                        mMediaPlayer.reset();
                        try {
                            mMediaPlayer.setDataSource(mSong.getUrl());
                            mMediaPlayer.prepare();
                            mMediaPlayer.start();
                        } catch (IOException e) {
                            Log.e(TAG, "IOException: " + e.toString());
                        }
                    } else {
                        mMediaPlayer.release();
                    }
                }
            });
        }
    }

    private void handlerProgress() {
        final Intent timeIntent = new Intent();
        timeIntent.setAction(MusicFragment.CURRENT_TIME);
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
            @Override
            public void onTick(long l) {
                timeIntent.putExtra("second", showTime(mMediaPlayer.getCurrentPosition() / 1000));
                timeIntent.putExtra("secondInt", mMediaPlayer.getCurrentPosition() / 1000);
                Log.e(TAG, "onTick: " + mMediaPlayer.getCurrentPosition());
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {
                if (isAutoNext) {
                    nextMusic();
                }
            }
        };
        mCountDownTimer.start();
    }

    private String showTime(int duration) {
        int min = duration / 60;
        int sec = duration % 60;
        String minute = (min < 10) ? "0" + min + ":" : min + ":";
        String second = (sec < 10) ? "0" + sec : "" + sec;
        return minute + second;
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
        filter.addAction(ACTION_SHUFFLE);
        filter.addAction(ACTION_AUTONEXT);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (intent != null && intent.getParcelableArrayListExtra(MusicActivity.KEY_LIST) != null) {
            mSongs = intent.getParcelableArrayListExtra(MusicActivity.KEY_LIST);
            Log.e(TAG, "onStartCommand: " + mSongs.size());
        } else {
            Log.e(TAG, "onStartCommand: " + mSongs.size());
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "at-dinhvo: onDestroy:");
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
