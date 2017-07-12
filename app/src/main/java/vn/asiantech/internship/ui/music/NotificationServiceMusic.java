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
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Music;

/**
 * NotificationServiceMusic create by Thanh Thien
 */
public class NotificationServiceMusic extends Service {
    private static final String TAG = NotificationServiceMusic.class.getSimpleName();
    private static final int NOTIFICATION_ID = 1234;

    private int mPosition;
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
                startMedia(true, intent.getIntExtra(Action.POSITION.getValue(), 0));
            } else if (intent.getAction().equals(Action.STOP.getValue())) {
                stopMediaAndCloseNotification();
            } else if (intent.getAction().equals(Action.NEXT.getValue())) {
                goNextMedia();
            } else if (intent.getAction().equals(Action.PREVIOUS.getValue())) {
                goPreviousMedia();
            } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                setShuffleMedia();
            } else if (intent.getAction().equals(Action.REPEAT.getValue())) {
                setRepeatMedia();
            } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                seekToMedia(intent);
            } else if (intent.getAction().equals(Action.PROGRESSBAR.getValue())) {
                setProgressBar(intent);
            } else if (intent.getAction().equals(Action.SHOW.getValue())) {
                showNotification();
            }
        }
        return START_STICKY;
    }

    private void setRepeatMedia() {
        mIsRepeat = !mIsRepeat;
    }

    private void setShuffleMedia() {
        mIsShuffle = !mIsShuffle;
    }

    private void goPreviousMedia() {
        if (mIsShuffle) {
            mPosition = new Random().nextInt(mMusics.size());
        } else {
            mPosition--;
        }
        if (mPosition < 0) {
            mPosition = mMusics.size() - 1;
        }
        stopMediaInUi();
        startMedia(false, mPosition);
    }

    private void goNextMedia() {
        if (mIsShuffle) {
            mPosition = new Random().nextInt(mMusics.size());
        } else {
            mPosition++;
        }
        if (mPosition == mMusics.size()) {
            mPosition = 0;
        }
        stopMediaInUi();
        startMedia(false, mPosition);
    }

    private void stopMediaInUi() {
        Intent timeIntent = new Intent(Action.SEEK.getValue());
        timeIntent.putExtra("stop", true);
        timeIntent.putExtra("time", 0);
        timeIntent.putExtra("second", 0);
        sendBroadcast(timeIntent);
    }

    private void seekToMedia(Intent intent) {
        int time = intent.getIntExtra("chooseTime", 0);
        mMediaPlayer.seekTo(time);
        mMediaPlayer.start();
    }

    private void stopMediaAndCloseNotification() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mNotificationManager.cancel(NOTIFICATION_ID);
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

    private void startMedia(final boolean isFirst, int position) {
        stopMedia();
        mPosition = position;
        mMediaPlayer = new MediaPlayer();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setDataSource(mMusics.get(mPosition).getUrlMp3());
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                thread.stop();
                mediaPlayer.start();
                startCountDownTimer(mediaPlayer);
                // TODO Clear that below line if you  want to disable show notification
                if (isFirst) {
                    showNotification();
                } else {
                    updateRemoteViewsData();
                }
            }
        });
    }

    // TODO: 7/11/2017  Fix duration time
    // TODO: 7/11/2017 Fix destroy
    private void startCountDownTimer(final MediaPlayer mediaPlayer) {
        mLength = mediaPlayer.getDuration();
        final Intent timeIntent = new Intent(Action.SEEK.getValue());
        mCountDownTimer = new CountDownTimer(mLength, 1000) {
            @Override
            public void onTick(long l) {
                timeIntent.putExtra("stop", false);
                timeIntent.putExtra("time", mLength);
                timeIntent.putExtra("second", mediaPlayer.getCurrentPosition());
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {
            }
        };
        mCountDownTimer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mIsRepeat) {
                    mPosition++;
                    startMedia(false, mPosition);
                } else if (mIsShuffle) {
                    mPosition = new Random().nextInt(mMusics.size());
                    startMedia(false, mPosition);
                }
                stopMediaInUi();
                mCountDownTimer.cancel();
            }
        });
    }

    private void updateRemoteViewsData() {
        final Intent timeIntent = new Intent(Action.PLAY_OTHER.getValue());
        sendBroadcast(timeIntent);

        // Get Music object
        Music music = mMusics.get(mPosition);
        String singerName = music.getNameSinger();
        String songName = music.getNameSong();

        // Reset mRemoteViews
        mRemoteViews.setTextViewText(R.id.tvSongSingerName, singerName);
        mRemoteViews.setTextViewText(R.id.tvSongName, songName);

        // Update time and icon, after then update to views
        setIcon();
        setDurationTime();
        updateData();
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

        // Intent next action
        Intent nextIntent = new Intent(this, NotificationServiceMusic.class);
        nextIntent.setAction(Action.NEXT.getValue());
        PendingIntent nextPIntent = PendingIntent.getService(this, 0, nextIntent, 0);

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
        mRemoteViews.setOnClickPendingIntent(R.id.imgNext, nextPIntent);
        mRemoteViews.setOnClickPendingIntent(R.id.imgClose, stopPIntent);

        // Create builder for mNotification
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_icon)
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
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        NotificationTarget notificationTarget = new NotificationTarget(
                this,
                mRemoteViews,
                R.id.imgAvatar,
                mNotification,
                NOTIFICATION_ID);
        Glide
                .with(getApplicationContext())
                .load(mMusics.get(mPosition).getUrlAvatar())
                .asBitmap()
                .into(notificationTarget);
    }

    private void updateData() {
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void upDateIcon(int id, int idTo) {
        mRemoteViews.setImageViewResource(id, idTo);
        updateData();
    }

    private void stopMedia() {
        if (mMediaPlayer != null) {
            mCountDownTimer.cancel();
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.release();
            } catch (Exception e) {
                Log.e(TAG, "onDestroy: " + e.toString());
            }
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
