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
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
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

    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_RESUME = "resume";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_STOP = "stop";
    public static final String ACTION_PREVIOUS = "previous";
    public static final String ACTION_SHUFFLE = "shuffle";
    public static final String ACTION_AUTONEXT = "auto_next";
    public static final String POS_DATA = "pos_data";
    public static final String DURATION = "duration";

    public static final String KEY_TIME = "time";
    public static final String KEY_TIME_INT = "timeInt";
    public static final String KEY_SECOND_INT = "secondInt";

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
                        isPause = false;
                        mCurrentPosition = intent.getIntExtra(MusicActivity.KEY_POS, -1);
                        if (mCurrentPosition != -1) {
                            mSong = mSongs.get(mCurrentPosition);
                            Log.e(TAG, "onReceive: " + mSong.getName());
                        }
                        Log.e(TAG, "Media is playing..." + (mMediaPlayer.isPlaying()));
                        startMedia();
                        break;
                    case ACTION_PAUSE:
                        isPause = true;
                        pauseMusic();
                        break;
                    case ACTION_RESUME:
                        isPause = false;
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
                        break;
                    case ACTION_STOP:
                        Log.e(TAG, "ACTION_STOP");
                        stopForeground(true);
                        stopSelf();
                }
            }
        }
    };

    public MusicService() {
        // constructor
    }

    private void startMedia() {
        Log.e(TAG, "at-dinhvo: startMedia:");
        resetMusic();
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
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (isAutoNext) {
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
        if (isShuffle) {
            mCurrentPosition = shuffleMusic();
        } else {
            mCurrentPosition = (mCurrentPosition == mSongs.size() - 1) ? 0 : mCurrentPosition + 1;
        }
        mSong = mSongs.get(mCurrentPosition);
        Log.e(TAG, "nextMusic: " + mCurrentPosition + ":" + mSong.getName());
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
            Log.e(TAG, "nextMusic: " + mSong.getName());
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }
    }

    private void previousMusic() {
        resetMusic();
        if (isShuffle) {
            mCurrentPosition = shuffleMusic();
        } else {
            mCurrentPosition = (mCurrentPosition == 0) ? mSongs.size() - 1 : mCurrentPosition - 1;

            Log.e(TAG, "prevMusic: " + mCurrentPosition);
        }
        mSong = mSongs.get(mCurrentPosition);
        Log.e(TAG, "nextMusic: " + mCurrentPosition + ":" + mSong.getName());
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
            Log.e(TAG, "nextMusic: " + mSong.getName());
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }
    }

    private void resetMusic() {
        if (mCountDownTimer != null) {
            Log.e(TAG, "resetMusic: " + "aaaaaaaaa");
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = new MediaPlayer();
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
        mMediaPlayer.start();
    }

    private int shuffleMusic() {
        Random random = new Random();
        int ran;
        do {
            ran = random.nextInt(mSongs.size() - 1);
        } while (ran == mCurrentPosition);
        Log.e(TAG, "shuffle music: " + ran + ":" + mSong.getName());
        return ran;
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
                Log.e(TAG, "onTick: " + mMediaPlayer.getCurrentPosition());
                sendBroadcast(timeIntent);
                showNotification(mSong.getName(), mSong.getSinger(), mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition());
            }

            @Override
            public void onFinish() {
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

    private void sendSong(String action, int pos) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(POS_DATA, pos);
        sendBroadcast(intent);
    }

    private void sendDuration() {
        Intent durationIntent = new Intent();
        durationIntent.setAction(DURATION);
        durationIntent.putExtra(KEY_TIME, showTime(mMediaPlayer.getDuration() / 1000));
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
        views.setViewVisibility(R.id.imgNotifiMusic, View.GONE);
        bigViews.setProgressBar(R.id.progressBar, duration, currentTime, false);
        views.setTextViewText(R.id.tvNotifiSong, song);
        bigViews.setTextViewText(R.id.tvNotifiSong, song);
        views.setTextViewText(R.id.tvNotifiSinger, singer);
        bigViews.setTextViewText(R.id.tvNotifiSinger, singer);
        if (mMediaPlayer.isPlaying()) {
            bigViews.setImageViewResource(R.id.imgBtnNotifiPlay, R.drawable.apollo_holo_dark_pause);
            views.setImageViewResource(R.id.imgBtnNotifiPlay, R.drawable.apollo_holo_dark_pause);
        } else {
            bigViews.setImageViewResource(R.id.imgBtnNotifiPlay, R.drawable.apollo_holo_dark_play);
            views.setImageViewResource(R.id.imgBtnNotifiPlay, R.drawable.apollo_holo_dark_play);
        }
        bigViews.setImageViewBitmap(R.id.imgNotifiMusic, bitmap);
        Intent notificationIntent = new Intent(this, MusicActivity.class);
        // add a flag
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent intentPlay = new Intent();
        if (mMediaPlayer.isPlaying()) {
            intentPlay.setAction(ACTION_PAUSE);
        } else {
            intentPlay.setAction(ACTION_RESUME);
        }
        PendingIntent playSong = PendingIntent.getBroadcast(this, 0, intentPlay, 0);
        bigViews.setOnClickPendingIntent(R.id.imgBtnNotifiPlay, playSong);
        views.setOnClickPendingIntent(R.id.imgBtnNotifiPlay, playSong);

        Intent intentNextSong = new Intent(ACTION_NEXT);
        PendingIntent nextSong = PendingIntent.getBroadcast(this, 0, intentNextSong, 0);
        bigViews.setOnClickPendingIntent(R.id.imgBtnNotifiNext, nextSong);
        views.setOnClickPendingIntent(R.id.imgBtnNotifiNext, nextSong);

        Intent intentTurnOff = new Intent(ACTION_STOP);
        PendingIntent turnOff = PendingIntent.getBroadcast(this, 0, intentTurnOff, 0);
        bigViews.setOnClickPendingIntent(R.id.imgBtnNotifiExit, turnOff);
        views.setOnClickPendingIntent(R.id.imgBtnNotifiExit, turnOff);

        Intent intentPrevious = new Intent(ACTION_PREVIOUS);
        PendingIntent previous = PendingIntent.getBroadcast(this, 0, intentPrevious, 0);
        bigViews.setOnClickPendingIntent(R.id.imgBtnNotifiPrev, previous);
//        views.setOnClickPendingIntent(R.id.imgBtnNotifiExit, previous);
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
        filter.addAction(ACTION_PLAY);
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PREVIOUS);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_RESUME);
        filter.addAction(ACTION_SHUFFLE);
        filter.addAction(ACTION_AUTONEXT);
        filter.addAction(ACTION_STOP);
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
        return START_STICKY;
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
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
