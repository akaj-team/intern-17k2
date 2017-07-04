package vn.asiantech.internship.day19.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.day19.activity.MusicActivity;
import vn.asiantech.internship.day19.model.Song;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 01/07/2017.
 */
public class SongService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private static final String TAG = SongService.class.getName();

    // Intent Service
    public static final String ACTION_SONGS = "vn.asiantech.internship.SONGS";
    public static final String ACTION_PAUSE = "vn.asiantech.internship.PAUSE";
    public static final String ACTION_CHOOSE_PLAY = "vn.asiantech.internship.PLAY";
    public static final String ACTION_PLAY = "vn.asiantech.internship.CHOOSE_PLAY";
    public static final String ACTION_PREVIOUS = "vn.asiantech.internship.PREVIOUS";
    public static final String ACTION_NEXT = "vn.asiantech.internship.NEXT";
    public static final String ACTION_AUTO_NEXT = "vn.asiantech.internship.AUTO_NEXT";
    public static final String ACTION_AUTO_NEXT_SELECTED = "vn.asiantech.internship.AUTO_NEXT_SELECTED";

    // Intent Broadcast
    public static final String ACTION_SEND_POSITION = "vn.asiantech.internship.SEND_POSITION";
    public static final String TYPE_POSITION = "POSITION";

    public static MediaPlayer sMediaPlayer;
    private List<Song> mSongs;
    private int mCurrentPosition;
    private boolean mCheck;
    private boolean mCheckAutoNext;
    private boolean mCheckShuffle;

    // No-op
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            switch (intent.getAction()) {
                case ACTION_SONGS:
                    mSongs = intent.getParcelableArrayListExtra(MusicActivity.TYPE_SONGS);
                    break;
                case ACTION_CHOOSE_PLAY:
                    mCurrentPosition = intent.getIntExtra(MusicActivity.TYPE_INDEX, -1);
                    setSongPlay();
                    break;
                case ACTION_PAUSE:
                    if (!mCheck) {
                        mCurrentPosition = intent.getIntExtra(MusicActivity.TYPE_INDEX, -1);
                        setSongPlay();
                        mCheck = true;
                    } else {
                        if (sMediaPlayer != null) {
                            sMediaPlayer.start();
                        }
                    }

                    break;
                case ACTION_PLAY:
                    if (sMediaPlayer != null) {
                        if (sMediaPlayer.isPlaying()) {
                            sMediaPlayer.pause();
                        }
                    }
                    break;
                case ACTION_NEXT:
                    mCurrentPosition = intent.getIntExtra(MusicActivity.TYPE_INDEX, -1);
                    setSongPlay();
                    break;
                case ACTION_PREVIOUS:
                    mCurrentPosition = intent.getIntExtra(MusicActivity.TYPE_INDEX, -1);
                    setSongPlay();
                    break;
                case ACTION_AUTO_NEXT:
                    Log.d("bbbbbbbb2", "onClick: ");
                    mCheckAutoNext = intent.getBooleanExtra(MusicActivity.TYPE_AUTO_NEXT, false);
                    mCheckShuffle = intent.getBooleanExtra(MusicActivity.TYPE_SHUFFLE, false);
                    Log.d("bbbbbbbb3", "onClick: " + mCheckShuffle);
                    break;
                case ACTION_AUTO_NEXT_SELECTED:
                    mCheckAutoNext = false;
            }
        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sMediaPlayer != null) {
            sMediaPlayer.release();
            sMediaPlayer = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d("bbbbbbbb7", "onCompletion: " + mCheckAutoNext);
        Log.d("bbbbbbbb8", "onClick: " + mCheckShuffle);
        if (mCheckAutoNext) {
            if (mCheckShuffle) {
                mCurrentPosition = getRandomPosition();
            } else {
                if (mCurrentPosition == mSongs.size() - 1) {
                    mCurrentPosition = 0;
                } else {
                    mCurrentPosition++;
                }
            }
            setSongPlay();
            sendDataToActivity();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (sMediaPlayer != null) {
            sMediaPlayer.start();
        }
    }

    private void createSongIfNeed() {
        if (sMediaPlayer == null) {
            sMediaPlayer = new MediaPlayer();
            sMediaPlayer.setOnPreparedListener(this);
            sMediaPlayer.setOnCompletionListener(this);
        } else {
            sMediaPlayer.reset();
        }
    }

    private void setSongPlay() {
        try {
            createSongIfNeed();
            sMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            sMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(mSongs.get(mCurrentPosition).getSongUrl()));
            sMediaPlayer.prepare();
        } catch (IOException e) {
            Log.d(TAG, "IOException of MediaPlayer ");
        } catch (IllegalFormatException e) {
            Log.d(TAG, " IllegalFormatException of MediaPlayer");
        }
    }

    private void sendDataToActivity() {
        Intent i = new Intent();
        i.setAction(SongService.ACTION_SEND_POSITION);
        i.putExtra(SongService.TYPE_POSITION, mCurrentPosition);
        sendBroadcast(i);
    }

    private int getRandomPosition() {
        return new Random().nextInt(mSongs.size());
    }
}
