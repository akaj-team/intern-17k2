package vn.asiantech.internship.ui.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;

import java.io.IOException;
import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Music;

/**
 * NotificationServiceMusic create by thanh Thien
 */
public class NotificationServiceMusic extends Service {
    private static final String TAG = NotificationServiceMusic.class.getSimpleName();
    private static final String REQUEST_CODE = "REQUEST_CODE";
    private static final int NOTIF_ID = 1234;
    private String mUrl;
    private int mPosition = 0;
    private int mLength;
    private int mCurrentTime;
    private boolean mIsRepeat;
    private boolean mIsShuffle;
    private boolean mIsPause;

    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;
    private RemoteViews mRemoteViews;
    private Notification mNotification;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private ArrayList<Music> mMusics = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getParcelableArrayListExtra(Action.INTENT.getValue()) != null) {
            mMusics = intent.getParcelableArrayListExtra(Action.INTENT.getValue());
        }

        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(Action.PAUSE.getValue())) {
                pauseMedia();
            } else if (intent.getAction().equals(Action.START.getValue())) {
                startMedia();
            } else if (intent.getAction().equals(Action.STOP.getValue())) {
                stopMedia();
            } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                seekToMedia(intent);
            } else if (intent.getAction().equals(Action.PROGRESSBAR.getValue())) {
                setProgressBar(intent);
            }
        }
        return START_STICKY;
    }

    private void seekToMedia(Intent intent) {
        int time = intent.getIntExtra("chooseTime", 0);
        mMediaPlayer.seekTo(time);
        mMediaPlayer.start();
    }

    private void stopMedia() {
        mNotificationManager.cancel(NOTIF_ID);
        stopForeground(true);
        stopSelf();
    }

    private void pauseMedia() {
        if (!mIsPause) {
            mIsPause = true;
            mMediaPlayer.pause();
            mCurrentTime = mMediaPlayer.getCurrentPosition();
            upDateIcon(R.id.imgPause, R.drawable.vector_play);
        } else {
            mIsPause = false;
            mMediaPlayer.seekTo(mCurrentTime);
            mMediaPlayer.start();
            upDateIcon(R.id.imgPause, R.drawable.vector_pause);
        }
    }

    private void startMedia() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(mMusics.get(mPosition).getUrlMp3());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, "startMedia: " + e.toString());
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer.start();
                showNotification();
            }
        });
        final Intent timeIntent = new Intent(Action.SEEK.getValue());
        mLength = mMediaPlayer.getDuration();
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
            @Override
            public void onTick(long l) {
                timeIntent.putExtra("time", mLength + "");
                timeIntent.putExtra("second", mMediaPlayer.getCurrentPosition() + "");
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {
                if (mIsRepeat) {
                    mPosition++;
                    playOtherSong();
                }
            }
        };
        mCountDownTimer.start();
    }

    private void playOtherSong() {
        try {
            mMediaPlayer.setDataSource(mMusics.get(mPosition).getUrlMp3());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, "startMedia: " + e.toString());
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer.start();
            }
        });
    }

    private void showNotification() {
        // Open MainActivity when click remoteViews
        Intent notificationIntent = new Intent(this, MusicActivity.class);
        notificationIntent.setAction(Action.INTENT.getValue());
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Intent pause action, it included play action
        Intent pauseIntent = new Intent(this, NotificationServiceMusic.class);
        pauseIntent.setAction(Action.PAUSE.getValue());
        PendingIntent pausePIntent = PendingIntent.getService(this, 0, pauseIntent, 0);

        // Intent stop action
        Intent stopIntent = new Intent(this, NotificationServiceMusic.class);
        stopIntent.setAction(Action.STOP.getValue());
        PendingIntent stopPIntent = PendingIntent.getService(this, 0, stopIntent, 0);

        // Get Music object
        Music music = mMusics.get(mPosition);
        String singerName = music.getNameSinger();
        String songName = music.getNameSong();

        // Using RemoteViews to bind custom layouts into Notification
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.remote_view_notification);
        mRemoteViews.setTextViewText(R.id.tvSongSingerName, singerName);
        mRemoteViews.setTextViewText(R.id.tvSongName, songName);
        mRemoteViews.setOnClickPendingIntent(R.id.lnShowName, pendingIntent);
        mRemoteViews.setOnClickPendingIntent(R.id.imgPause, pausePIntent);
        mRemoteViews.setOnClickPendingIntent(R.id.imgNext, pausePIntent);
        mRemoteViews.setOnClickPendingIntent(R.id.imgClose, stopPIntent);

        // Create builder for mNotification
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_icon)
                .setTicker(songName)
                .setOngoing(true)
                .setCustomBigContentView(mRemoteViews);

        mNotification = mBuilder.build();
        setIcon();
        setDurationTime();
    }

    private void setDurationTime() {
        mRemoteViews.setTextViewText(R.id.tvDurationTime, Action.TIME.getTime(mLength));
        updateData();
    }

    private void setProgressBar(Intent intent) {
        int position = intent.getIntExtra("position", 0);
        mRemoteViews.setProgressBar(R.id.progressBar, mLength, position, false);
        mRemoteViews.setTextViewText(R.id.tvCurrentTime, Action.TIME.getTime(position));
        updateData();
    }

    private void setIcon() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIF_ID, mNotification);
        NotificationTarget notificationTarget = new NotificationTarget(
                this,
                mRemoteViews,
                R.id.imgAvatar,
                mNotification,
                NOTIF_ID);
        Glide
                .with(getApplicationContext())
                .load(mMusics.get(mPosition).getUrlAvatar())
                .asBitmap()
                .into(notificationTarget);
    }

    private void upDateIcon(int id, int idTo) {
        mRemoteViews.setImageViewResource(id, idTo);
        updateData();
    }

    private void updateData() {
        mNotificationManager.notify(NOTIF_ID, mBuilder.build());
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
