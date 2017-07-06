package vn.asiantech.internship.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.ui.music.Action;
import vn.asiantech.internship.ui.music.MusicActivity;

/**
 * Created by quanghai on 30/06/2017.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPLayer;
    private List<Song> mSongs;
    private int mCurrentPosition;
    private boolean mIsShuffle;
    private boolean mIsReplay;
    private int mShufflePosition = 0;
    private boolean mIsPlaying;
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
                if (mIsShuffle) {
                    Log.d("xxx", "onStartCommand: shuffle");
                    playSong(shuffleSongs(), mShufflePosition);
                    if (mShufflePosition < shuffleSongs().size()) {
                        mShufflePosition++;
                    }
                } else {
                    getBundle(intent);
                }
                playSong(mSongs, mCurrentPosition);
            } else if (intent.getAction().equals(Action.PREVIOUS_SONG.getValue())) {
                if (mCurrentPosition > 0) {
                    mCurrentPosition--;
                    playSong(mSongs, mCurrentPosition);
                }
            } else if (intent.getAction().equals(Action.NEXT_SONG.getValue())) {
                if (mCurrentPosition < mSongs.size() - 1) {
                    mCurrentPosition++;
                    playSong(mSongs, mCurrentPosition);
                }
            } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                if (mMediaPLayer.isPlaying()) {
                    mMediaPLayer.pause();
                    Log.d("xxx", "onStartCommand: pause ");
                }
            } else if (intent.getAction().equals(Action.RESUME.getValue())) {
                if (!mMediaPLayer.isPlaying()) {
                    mMediaPLayer.start();
                    Log.d("xxx", "onStartCommand: resume");
                }
            } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                int position = intent.getIntExtra("chooseTime", 0);
                mMediaPLayer.seekTo(position);
                mMediaPLayer.start();
            } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                mIsShuffle = intent.getBooleanExtra("shuffle", false);
                Log.d("xxxx", "onStartCommand: " + mIsShuffle);
            } else if (intent.getAction().equals(Action.REPLAY.getValue())) {
                mIsReplay = intent.getBooleanExtra("replay", false);
            }
        }
        return START_STICKY;
    }

    private void getBundle(Intent intent) {
        mSongs = intent.getParcelableArrayListExtra(MusicActivity.KEY_BUNDLE_ARRAYLIST);
        mCurrentPosition = intent.getIntExtra(MusicActivity.KEY_BUNDLE_POSITION, -1);
        Log.d("xxx", "getBundle: " + mCurrentPosition);
    }

    private List<Song> shuffleSongs() {
        List<Song> songs = mSongs;
        songs.remove(mCurrentPosition);
        Collections.shuffle(songs);
        return songs;
    }

    private void playSong(List<Song> songs, int position) {
        mMediaPLayer.reset();
        Song song = songs.get(position);
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
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.tvNotificationSongName, mSongs.get(mCurrentPosition).getTitle());
        remoteViews.setTextViewText(R.id.tvNotificationArtist, mSongs.get(mCurrentPosition).getArtist());
        remoteViews.setTextColor(R.id.tvNotificationSongName, Color.BLACK);
        remoteViews.setTextColor(R.id.tvNotificationArtist, Color.BLACK);

        Intent nextIntent = new Intent(this, MusicService.class);
        nextIntent.setAction(Action.NEXT_SONG.getValue());
        PendingIntent nextPendingIntent = PendingIntent.getService(this, 0, nextIntent, 0);

        Intent previousIntent = new Intent(this, MusicService.class);
        previousIntent.setAction(Action.PREVIOUS_SONG.getValue());
        PendingIntent previousPendingIntent = PendingIntent.getService(this, 0, previousIntent, 0);

        Intent playIntent = new Intent(this, MusicService.class);
        if (mMediaPLayer.isPlaying()) {
            Log.d("xxx", "showNotification: playing");
            playIntent.setAction(Action.PAUSE.getValue());
        } else {
            Log.d("xxx", "showNotification: nononono");
            playIntent.setAction(Action.RESUME.getValue());
        }
        PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent, 0);

        Notification builder = new NotificationCompat.Builder(this)
//                .setCustomBigContentView(remoteViews)
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.ic_play_arrow_black_24dp)
                .setContentTitle("Music player")
                .addAction(R.drawable.ic_skip_previous_black_24dp, null, previousPendingIntent)
                .addAction(R.drawable.ic_play_arrow_black_24dp, null, playPendingIntent)
                .addAction(R.drawable.ic_skip_next_black_24dp, null, nextPendingIntent)
                .build();
        startForeground(1, builder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPLayer.stop();
        stopForeground(true);
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
        if (mIsReplay) {
            mp.setLooping(true);
        } else {
            if (mIsShuffle) {
                playSong(shuffleSongs(), mShufflePosition);
                return;
            }
            mCurrentPosition++;
            playSong(mSongs, mCurrentPosition);
        }
    }
}