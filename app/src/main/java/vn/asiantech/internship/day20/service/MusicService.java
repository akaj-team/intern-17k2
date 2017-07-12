package vn.asiantech.internship.day20.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.model.Action;
import vn.asiantech.internship.day20.model.Song;
import vn.asiantech.internship.day20.ui.MusicActivity;
import vn.asiantech.internship.day20.ui.MusicFragment;

import static vn.asiantech.internship.day20.ui.MusicFragment.SONG_NEXT;
import static vn.asiantech.internship.day20.ui.MusicFragment.SONG_PREVIOUS;

/**
 * Service
 */
public class MusicService extends Service {

    public static final String TAG = "at-dinhvo";
    public static final String POS_DATA = "pos_data";
    public static final String DURATION = "duration";
    public static final String KEY_TIME_INT = "timeInt";
    public static final String KEY_SECOND_INT = "secondInt";

    private List<Song> mSongs;
    private Song mSong;
    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;
    private int mLength;
    private boolean mIsPause;
    private boolean mIsAutoNext;
    private boolean mIsShuffle;
    private int mCurrentPosition;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(Action.PLAY.getValue())) {
                    mIsPause = false;
                    mCurrentPosition = intent.getIntExtra(MusicActivity.KEY_POS, -1);
                    if (mCurrentPosition != -1) {
                        mSong = mSongs.get(mCurrentPosition);
                    }
                    startMedia();
                } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                    mIsPause = true;
                    pauseMusic();
                } else if (intent.getAction().equals(Action.RESUME.getValue())) {
                    mIsPause = false;
                    resumeMusic();
                } else if (intent.getAction().equals(Action.NEXT.getValue())) {
                    nextMusic();
                } else if (intent.getAction().equals(Action.PREVIOUS.getValue())) {
                    previousMusic();
                } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                    mIsShuffle = (!mIsShuffle);
                } else if (intent.getAction().equals(Action.AUTONEXT.getValue())) {
                    mIsAutoNext = (!mIsAutoNext);
                } else if (intent.getAction().equals(Action.STOP.getValue())) {
                    exitApp();
                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    showNotification(mSong.getName(), mSong.getSinger(), mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition());
                } else if (intent.getAction().equals(String.valueOf(TelephonyManager.CALL_STATE_RINGING))) {
                    mIsPause = true;
                    pauseMusic();
                } else if (intent.getAction().equals(String.valueOf(TelephonyManager.CALL_STATE_IDLE))) {
                    mIsPause = false;
                    resumeMusic();
                }
            }
        }
    };

    public MusicService() {
        // No-op
    }

    private void startMedia() {
        resetMusic();
        try {
            mMediaPlayer.setDataSource(mSong.getUrl());
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mMediaPlayer.start();
                    sendDuration();
                    handlerProgress();
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (mIsAutoNext) {
                        nextMusic();
                    }
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.toString());
        }
    }

    private void nextMusic() {
        resetMusic();
        if (mIsShuffle) {
            mCurrentPosition = shuffleMusic();
        } else {
            mCurrentPosition = (mCurrentPosition == mSongs.size() - 1) ? 0 : mCurrentPosition + 1;
        }
        mSong = mSongs.get(mCurrentPosition);
        sendSong(SONG_NEXT, mCurrentPosition);
        try {
            mMediaPlayer.setDataSource(mSong.getUrl());
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mMediaPlayer.start();
                    sendDuration();
                    handlerProgress();
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }
    }

    private void previousMusic() {
        resetMusic();
        if (mIsShuffle) {
            mCurrentPosition = shuffleMusic();
        } else {
            mCurrentPosition = (mCurrentPosition == 0) ? mSongs.size() - 1 : mCurrentPosition - 1;
        }
        mSong = mSongs.get(mCurrentPosition);
        sendSong(SONG_PREVIOUS, mCurrentPosition);
        try {
            mMediaPlayer.setDataSource(mSong.getUrl());
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mMediaPlayer.start();
                    sendDuration();
                    handlerProgress();
                }
            });
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }
    }

    private void resetMusic() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = new MediaPlayer();
        }
    }

    private void pauseMusic() {
        mMediaPlayer.pause();
        mIsPause = true;
        mLength = mMediaPlayer.getCurrentPosition();
    }

    private void resumeMusic() {
        mMediaPlayer.seekTo(mLength);
        mMediaPlayer.start();
    }

    private int shuffleMusic() {
        Random random = new Random();
        int ran;
        do {
            ran = random.nextInt(mSongs.size() - 1);
        } while (ran == mCurrentPosition);
        return ran;
    }

    private void exitApp() {
        stopForeground(true);
        stopSelf();
        Intent intent = new Intent(Action.EXIT.getValue());
        sendBroadcast(intent);
    }

    private void handlerProgress() {
        final Intent timeIntent = new Intent();
        timeIntent.setAction(MusicFragment.CURRENT_TIME);
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration() - mMediaPlayer.getCurrentPosition(), 1000) {
            @Override
            public void onTick(long l) {
                timeIntent.putExtra(KEY_SECOND_INT, mMediaPlayer.getCurrentPosition() / 1000);
                sendBroadcast(timeIntent);
                showNotification(mSong.getName(), mSong.getSinger(), mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition());
            }

            @Override
            public void onFinish() {
            }
        };
        mCountDownTimer.start();
    }

    private void sendSong(String action, int pos) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(POS_DATA, pos);
        sendBroadcast(intent);
    }

    private void sendDuration() {
        Intent durationIntent = new Intent();
        durationIntent.setAction(DURATION);
        durationIntent.putExtra(KEY_TIME_INT, mMediaPlayer.getDuration() / 1000);
        sendBroadcast(durationIntent);
    }

    private void showNotification(String song, String singer, int duration, int currentTime) {
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.custom_notification);
        RemoteViews bigViews = new RemoteViews(getPackageName(), R.layout.custom_notification_expanse);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.img_music, options);
        views.setViewVisibility(R.id.imgSmallIconMusic, View.VISIBLE);
        views.setViewVisibility(R.id.imgNotificationMusic, View.GONE);
        bigViews.setProgressBar(R.id.progressBar, duration, currentTime, false);
        views.setTextViewText(R.id.tvNotificationSong, song);
        bigViews.setTextViewText(R.id.tvNotificationSong, song);
        views.setTextViewText(R.id.tvNotificationSinger, singer);
        bigViews.setTextViewText(R.id.tvNotificationSinger, singer);
        if (mMediaPlayer.isPlaying()) {
            bigViews.setImageViewResource(R.id.imgBtnNotificationPlay, R.drawable.apollo_holo_dark_pause);
            views.setImageViewResource(R.id.imgBtnNotificationPlay, R.drawable.apollo_holo_dark_pause);
        } else {
            bigViews.setImageViewResource(R.id.imgBtnNotificationPlay, R.drawable.apollo_holo_dark_play);
            views.setImageViewResource(R.id.imgBtnNotificationPlay, R.drawable.apollo_holo_dark_play);
        }
        bigViews.setImageViewBitmap(R.id.imgNotificationMusic, bitmap);
        Intent notificationIntent = new Intent(this, MusicActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        Intent intentPlay = new Intent();
        if (mMediaPlayer.isPlaying()) {
            intentPlay.setAction(Action.PAUSE.getValue());
        } else {
            intentPlay.setAction(Action.RESUME.getValue());
        }
        PendingIntent playSong = PendingIntent.getBroadcast(this, 0, intentPlay, 0);
        bigViews.setOnClickPendingIntent(R.id.imgBtnNotificationPlay, playSong);
        views.setOnClickPendingIntent(R.id.imgBtnNotificationPlay, playSong);
        Intent intentNextSong = new Intent(Action.NEXT.getValue());
        PendingIntent nextSong = PendingIntent.getBroadcast(this, 0, intentNextSong, 0);
        bigViews.setOnClickPendingIntent(R.id.imgBtnNotificationNext, nextSong);
        views.setOnClickPendingIntent(R.id.imgBtnNotificationNext, nextSong);
        Intent intentTurnOff = new Intent(Action.STOP.getValue());
        PendingIntent turnOff = PendingIntent.getBroadcast(this, 0, intentTurnOff, 0);
        bigViews.setOnClickPendingIntent(R.id.imgBtnNotificationExit, turnOff);
        views.setOnClickPendingIntent(R.id.imgBtnNotificationExit, turnOff);
        Intent intentPrevious = new Intent(Action.PREVIOUS.getValue());
        PendingIntent previous = PendingIntent.getBroadcast(this, 0, intentPrevious, 0);
        bigViews.setOnClickPendingIntent(R.id.imgBtnNotificationPrev, previous);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(this).build();
            notification.contentView = views;
            notification.bigContentView = bigViews;
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            notification.icon = R.drawable.ic_music_note_white_48dp;
            notification.contentIntent = pendingIntent;
        }
        startForeground(111, notification);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Action.PLAY.getValue());
        filter.addAction(Action.NEXT.getValue());
        filter.addAction(Action.PREVIOUS.getValue());
        filter.addAction(Action.PAUSE.getValue());
        filter.addAction(Action.RESUME.getValue());
        filter.addAction(Action.SHUFFLE.getValue());
        filter.addAction(Action.AUTONEXT.getValue());
        filter.addAction(Action.STOP.getValue());
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(String.valueOf(TelephonyManager.CALL_STATE_OFFHOOK));
        filter.addAction(String.valueOf(TelephonyManager.CALL_STATE_IDLE));
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (intent != null && intent.getParcelableArrayListExtra(MusicActivity.KEY_LIST) != null) {
            mSongs = intent.getParcelableArrayListExtra(MusicActivity.KEY_LIST);
        } else {
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
