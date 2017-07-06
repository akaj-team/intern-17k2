package vn.asiantech.internship.day19.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day19.activity.MusicActivity;
import vn.asiantech.internship.day19.model.Song;
import vn.asiantech.internship.day19.model.Utils;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 01/07/2017.
 */
public class SongService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private static final String TAG = SongService.class.getName();
    private static final int MY_NOTIFICATION_ID = 12;

    private MediaPlayer mMediaPlayer;
    private List<Song> mSongs;
    private int mCurrentPosition;
    private boolean mCheck;
    private boolean mCheckAutoNext;
    private boolean mCheckShuffle;
    private CountDownTimer mCountDownTimer;
    private NotificationBroadcast mNotificationBroadcast;
    private Handler mHander = new Handler();
    private Runnable mRunnable;

    // No-op
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationBroadcast = new NotificationBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Action.SEEK.getValue());
        registerReceiver(mNotificationBroadcast, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getAction().equals(Action.SONGS.getValue())) {
                mSongs = intent.getParcelableArrayListExtra(MusicActivity.TYPE_SONGS);
            } else if (intent.getAction().equals(Action.CHOOSE_PLAY.getValue())) {
                mCurrentPosition = intent.getIntExtra(MusicActivity.TYPE_POSITION, 0);
                setSongPlay();
            } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                if (!mCheck) {
                    setSongPlay();
                    mCheck = true;
                } else {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.start();
                    }
                }
            } else if (intent.getAction().equals(Action.PLAY.getValue())) {
                if (mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    }
                }
            } else if (intent.getAction().equals(Action.NEXT.getValue())) {
                if (mCheckShuffle) {
                    mCurrentPosition = getRandomPosition();
                } else {
                    mCurrentPosition++;
                    if (mCurrentPosition == mSongs.size() - 1) {
                        mCurrentPosition = 0;
                    }
                }
                setSongPlay();
                sendPositionToActivity();
            } else if (intent.getAction().equals(Action.PREVIOUS.getValue())) {
                if (mCheckShuffle) {
                    mCurrentPosition = getRandomPosition();
                } else {
                    mCurrentPosition--;
                    if (mCurrentPosition < 0) {
                        mCurrentPosition = mSongs.size() - 1;
                    }
                }
                setSongPlay();
                sendPositionToActivity();
            } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                int time = intent.getIntExtra(MusicActivity.TYPE_CHOOSE_TIME, 0);
                mMediaPlayer.seekTo(time);
                mMediaPlayer.start();
            } else if (intent.getAction().equals(Action.AUTO_NEXT.getValue())) {
                mCheckAutoNext = true;
            } else if (intent.getAction().equals(Action.AUTO_NEXT_SELETED.getValue())) {
                mCheckAutoNext = false;
            } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                mCheckShuffle = true;
            } else if (intent.getAction().equals(Action.SHUFFLE_SELECTED.getValue())) {
                mCheckShuffle = false;
            } else if (intent.getAction().equals(Action.CLOSE_NOTIFICATION.getValue())) {
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                mHander.removeCallbacks(mRunnable);
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(MY_NOTIFICATION_ID);

                Intent i = new Intent();
                i.setAction(Action.CLOSE_ACTIVITY.getValue());
                sendBroadcast(i);

                Intent myIntent = new Intent(this, SongService.class);
                stopService(myIntent);

            } else if (intent.getAction().equals(Action.CLICK_NOTIFICATION.getValue())) {
                mHander.removeCallbacks(mRunnable);
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(MY_NOTIFICATION_ID);
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        unregisterReceiver(mNotificationBroadcast);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
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
            sendPositionToActivity();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            setProgress();
        }
    }

    // Init Mediaplayer
    private void createSongIfNeed() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
        } else {
            mMediaPlayer.reset();
        }
    }

    private void setSongPlay() {
        try {
            createSongIfNeed();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(mSongs.get(mCurrentPosition).getUrl()));
            mMediaPlayer.prepare();
            Log.d(TAG, "onStartCommand: " + mCurrentPosition);
        } catch (IOException e) {
            Log.d(TAG, "IOException of MediaPlayer ");
        } catch (IllegalFormatException e) {
            Log.d(TAG, " IllegalFormatException of MediaPlayer");
        }
    }

    // Update current position of Activity
    private void sendPositionToActivity() {
        Intent i = new Intent();
        i.setAction(Action.SEND_POSITION.getValue());
        i.putExtra(MusicActivity.TYPE_POSITION, mCurrentPosition);
        sendBroadcast(i);
    }

    private int getRandomPosition() {
        return new Random().nextInt(mSongs.size());
    }

    // Update Seekbar
    private void setProgress() {
        final Intent timeIntent = new Intent(Action.SEEK.getValue());
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
            @Override
            public void onTick(long l) {
                timeIntent.putExtra(MusicActivity.TYPE_TIME, mMediaPlayer.getDuration() + "");
                timeIntent.putExtra(MusicActivity.TYPE_SECOND, mMediaPlayer.getCurrentPosition() + "");
                timeIntent.putExtra(MusicActivity.TYPE_POSITION, mCurrentPosition);
                sendBroadcast(timeIntent);
            }

            // No-op
            @Override
            public void onFinish() {
            }
        };
        mCountDownTimer.start();
    }

    private void initNotification() {
        final RemoteViews views = new RemoteViews(getPackageName(), R.layout.notification_music);
        views.setImageViewResource(R.id.imgBtnCloseNotification, R.mipmap.ic_close);
        views.setProgressBar(R.id.progressBarNotification, 100, 0, false);

        Intent intent = new Intent(this, MusicActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setAction(Action.CLICK_NOTIFICATION.getValue());
        if (mMediaPlayer.isPlaying()) {
            intent.putExtra(MusicActivity.TYPE_ISPLAYING, true);
        }
        intent.putExtra(MusicActivity.TYPE_POSITION, mCurrentPosition);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent closeIntent = new Intent(this, SongService.class);
        closeIntent.setAction(Action.CLOSE_NOTIFICATION.getValue());
        PendingIntent closePendingIntent = PendingIntent.getService(getApplicationContext(), 0, closeIntent, 0);
        views.setOnClickPendingIntent(R.id.imgBtnCloseNotification, closePendingIntent);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_music_notification);
        builder.setOngoing(true);
        builder.setAutoCancel(false);
        builder.setContentIntent(pendingIntent);

        final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                views.setImageViewResource(R.id.imgSongNotification, mSongs.get(mCurrentPosition).getImage());
                views.setTextViewText(R.id.tvSongNotification, mSongs.get(mCurrentPosition).getName());
                views.setTextViewText(R.id.tvArtistNotification, mSongs.get(mCurrentPosition).getArtist());
                views.setTextViewText(R.id.tvTimeNowNotification, String.valueOf(Utils.showTime(mMediaPlayer.getCurrentPosition())));
                views.setTextViewText(R.id.tvTimeTotalNotification, String.valueOf(Utils.showTime(mMediaPlayer.getDuration())));
                views.setProgressBar(R.id.progressBarNotification, mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition(), false);

                builder.setCustomBigContentView(views);

                Notification notification = builder.build();
                notificationManager.notify(MY_NOTIFICATION_ID, notification);
                mHander.postDelayed(this, 1000);
            }
        };
        mHander.postDelayed(mRunnable, 1000);
    }

    /**
     * Create NotificationBroadcast
     */
    class NotificationBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                initNotification();
            }
        }
    }
}
