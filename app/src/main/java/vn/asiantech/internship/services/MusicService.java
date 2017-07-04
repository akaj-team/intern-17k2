package vn.asiantech.internship.services;

import android.app.Notification;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.ui.music.Action;
import vn.asiantech.internship.ui.music.MusicActivity;

/**
 *
 * Created by quanghai on 30/06/2017.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPLayer;
    private List<Song> mSongs;
    private int mPosition;
    private boolean mIsShuffle;
    private boolean mIsAutoNext;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSongs = new ArrayList<>();
        mMediaPLayer = new MediaPlayer();
        mMediaPLayer.setOnPreparedListener(this);
        mMediaPLayer.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getAction().equals(Action.START.getValue())) {
                if (mIsShuffle){
                    Log.d("xxx", "onStartCommand: shuffle");
                } else {
                    Bundle bundle = intent.getBundleExtra(MusicActivity.KEY_INTENT);
                    mSongs = bundle.getParcelableArrayList(MusicActivity.KEY_BUNDLE_ARRAYLIST);
                    mPosition = bundle.getInt(MusicActivity.KEY_BUNDLE_POSITION);
                }
                playSong(mPosition);
            } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                mMediaPLayer.pause();
            } else if (intent.getAction().equals(Action.RESUME.getValue())) {
                mMediaPLayer.start();
            } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                int position = intent.getIntExtra("chooseTime", 0);
                mMediaPLayer.seekTo(position);
                mMediaPLayer.start();
            } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                mIsShuffle = intent.getBooleanExtra("shuffle", false);
                Log.d("xxxx", "onStartCommand: " + mIsShuffle);
            } else if (intent.getAction().equals(Action.AUTO_NEXT.getValue())) {
                mIsAutoNext = intent.getBooleanExtra("autonext", false);
            }
        }
        return START_STICKY;
    }

    private void playSong(int position) {
        mMediaPLayer.reset();
        Song song = mSongs.get(position);
        int id = song.getId();
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        try {
            mMediaPLayer.setDataSource(this, uri);
        } catch (IOException e) {
            e.getMessage();
        }
        mMediaPLayer.prepareAsync();
    }

    private void showNotification() {
        Notification builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_play_arrow_black_24dp)
                .setContentTitle("Music player")
                .setContentText(mSongs.get(mPosition).getTitle())
                .build();
        startForeground(1, builder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPLayer.stop();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        showNotification();
        mediaPlayer.start();
        final Intent timeIntent = new Intent(Action.SEEK.getValue());
        CountDownTimer countDownTimer = new CountDownTimer(mMediaPLayer.getDuration(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeIntent.putExtra("time", mMediaPLayer.getDuration());
                timeIntent.putExtra("second", mMediaPLayer.getCurrentPosition());
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mIsAutoNext) {
            mPosition++;
            playSong(mPosition);
        }
    }
}