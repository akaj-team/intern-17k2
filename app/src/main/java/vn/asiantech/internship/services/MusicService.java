package vn.asiantech.internship.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.ArrayList;
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
    private int mCurrentPosition;
    private boolean mIsShuffle;
    private boolean mIsReplay;
    private CountDownTimer mCountDownTimer;
    private int mPositionPlaying = -1;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(Action.PREVIOUS_SONG.getValue())) {
                    if (mIsShuffle) {
                        shuffleSongs();
                    } else {
                        if (mCurrentPosition > 0) {
                            mCurrentPosition--;
                        }
                    }
                    playSongOnline(mSongs, mCurrentPosition);
                    sendBroadcastSong(Action.UPDATE_VIEW.getValue());
                } else if (intent.getAction().equals(Action.NEXT_SONG.getValue())) {
                    if (mIsShuffle) {
                        shuffleSongs();
                    } else {
                        if (mCurrentPosition < mSongs.size() - 1) {
                            mCurrentPosition++;
                        }
                    }
                    playSongOnline(mSongs, mCurrentPosition);
                    sendBroadcastSong(Action.UPDATE_VIEW.getValue());
                } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                    if (mMediaPLayer.isPlaying()) {
                        mMediaPLayer.pause();
                    }
                } else if (intent.getAction().equals(Action.RESUME.getValue())) {
                    if (!mMediaPLayer.isPlaying()) {
                        mMediaPLayer.start();
                    }
                } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                    int position = intent.getIntExtra(Action.SEEK_TO.getValue(), 0);
                    mMediaPLayer.seekTo(position);
                    mMediaPLayer.start();
                } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                    mIsShuffle = intent.getBooleanExtra(Action.SHUFFLE.getValue(), false);
                } else if (intent.getAction().equals(Action.REPLAY.getValue())) {
                    mIsReplay = intent.getBooleanExtra(Action.REPLAY.getValue(), false);
                    Log.d("xxx", "replay: " + mIsReplay);
                } else if (intent.getAction().equals(Action.CLOSE.getValue())) {
                    onDestroy();
                    sendBroadcast(new Intent(Action.CLOSE.getValue()));
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initIntentFilter();
        mSongs = new ArrayList<>();
        mMediaPLayer = new MediaPlayer();
        mMediaPLayer.setOnPreparedListener(this);
        mMediaPLayer.setOnCompletionListener(this);
    }

    private void initIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Action.START.getValue());
        filter.addAction(Action.RESUME.getValue());
        filter.addAction(Action.PAUSE.getValue());
        filter.addAction(Action.SEEK.getValue());
        filter.addAction(Action.AUTO_NEXT.getValue());
        filter.addAction(Action.SHUFFLE.getValue());
        filter.addAction(Action.PREVIOUS_SONG.getValue());
        filter.addAction(Action.NEXT_SONG.getValue());
        filter.addAction(Action.CLOSE.getValue());
        filter.addAction(Action.REPLAY.getValue());
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            getBundle(intent);
            if (mPositionPlaying == -1 ||  mPositionPlaying != mCurrentPosition) {
                playSongOnline(mSongs, mCurrentPosition);
            }
        }
        return START_STICKY;
    }

    private void getBundle(Intent intent) {
        mSongs = intent.getParcelableArrayListExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue());
        mCurrentPosition = intent.getIntExtra(Action.KEY_BUNDLE_POSITION.getValue(), -1);
    }

    private void shuffleSongs() {
        Random random = new Random();
        mCurrentPosition = random.nextInt(mSongs.size() - 1);
    }

    private void playSongOnline(List<Song> songs, int position) {
        mMediaPLayer.reset();
        Song song = songs.get(position);
        try {
            mMediaPLayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPLayer.setDataSource(song.getUrl());
            mMediaPLayer.prepare();
        } catch (IOException e) {
            Log.e("IOException", "IOException" + e.getMessage());
        }
        mPositionPlaying = position;
    }

    private void showNotification() {
        Song song = mSongs.get(mCurrentPosition);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.tvNotificationSongName, song.getTitle());
        remoteViews.setTextViewText(R.id.tvNotificationArtist, song.getArtist());
        remoteViews.setTextColor(R.id.tvNotificationSongName, Color.BLACK);
        remoteViews.setTextColor(R.id.tvNotificationArtist, Color.BLACK);

        Intent intent = new Intent(this, MusicActivity.class);
        PendingIntent activityIntent = PendingIntent.getActivity(this, 0 , intent, 0);

        Intent nextIntent = new Intent(Action.NEXT_SONG.getValue());
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextIntent, 0);

        Intent previousIntent = new Intent(Action.PREVIOUS_SONG.getValue());
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(this, 0, previousIntent, 0);

        Intent closeNotificationIntent = new Intent(Action.CLOSE.getValue());
        PendingIntent closeNotificationPendingIntent = PendingIntent.getBroadcast(this, 0, closeNotificationIntent, 0);

        Notification builder = new NotificationCompat.Builder(this)
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.ic_play_arrow_black_24dp)
                .setContentTitle(mSongs.get(mCurrentPosition).getTitle())
                .addAction(R.drawable.ic_skip_previous_black_24dp, null, previousPendingIntent)
                .addAction(R.drawable.ic_skip_next_black_24dp, null, nextPendingIntent)
                .addAction(R.drawable.ic_close_black_24dp, null, closeNotificationPendingIntent)
                .setContentIntent(activityIntent)
                .build();
        startForeground(1, builder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mMediaPLayer.stop();
        stopForeground(true);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        showNotification();
        mediaPlayer.start();
        final Intent intent = new Intent(Action.SEEK.getValue());
        mCountDownTimer = new CountDownTimer(mMediaPLayer.getDuration(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                intent.putExtra(Action.KEY_DURATION.getValue(), mMediaPLayer.getDuration());
                intent.putExtra(Action.KEY_CURRENT_TIME.getValue(), mMediaPLayer.getCurrentPosition());
                sendBroadcast(intent);
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }

    private void sendBroadcastSong(String action) {
        Intent intent = new Intent(action);
        intent.putExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue(), mSongs.get(mCurrentPosition));
        sendBroadcast(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mIsReplay) {
            mp.setLooping(true);
            mp.start();
        } else {
            if (!mIsShuffle) {
                if (mCurrentPosition < mSongs.size() - 1) {
                    mCurrentPosition++;
                }
            }
            shuffleSongs();
            playSongOnline(mSongs, mCurrentPosition);
            Intent intent = new Intent(Action.AUTO_NEXT.getValue());
            intent.putExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue(), mSongs.get(mCurrentPosition));
            sendBroadcast(intent);
        }
    }
}
