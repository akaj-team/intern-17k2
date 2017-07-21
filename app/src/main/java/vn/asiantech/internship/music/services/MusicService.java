package vn.asiantech.internship.music.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.music.models.Action;
import vn.asiantech.internship.music.models.Song;
import vn.asiantech.internship.music.ui.home.SongActivity;
import vn.asiantech.internship.music.utils.Utils;

/**
 * Created by ducle on 08/07/2017.
 * MusicService to play music
 */
public class MusicService extends Service {
    private static final String TAG = MusicService.class.getSimpleName();
    public static final String KEY_TIME = "time";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CURRENT_TIME = "current_time";
    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;
    private int mLength;
    private int mPosition;
    private int mPlayStatus = SongActivity.STOP_STATUS;
    private int mShuffleStatus = SongActivity.NO_SHUFFLE;
    private int mRepeatStatus = SongActivity.NO_REPEAT;
    private StatusBroadcastReceiver mStatusBroadcastReceiver;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;
    private List<Song> mSongs;
    private PendingIntent mPendingIntent;
    private int mNotificationId = 100;

    @Override
    public void onCreate() {
        super.onCreate();
        mSongs = new ArrayList<>();
        mStatusBroadcastReceiver = new StatusBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.PLAY.getValue());
        intentFilter.addAction(Action.SHUFFLE.getValue());
        intentFilter.addAction(Action.REPEAT.getValue());
        intentFilter.addAction(Action.PREVIOUS.getValue());
        intentFilter.addAction(Action.NEXT.getValue());
        intentFilter.addAction(Action.CLEAR.getValue());
        intentFilter.addAction(Action.SHOW.getValue());
        registerReceiver(mStatusBroadcastReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(Action.INTENT.getValue())) {
                Song song = intent.getParcelableExtra(SongActivity.KEY_SONG);
                Log.d(TAG, "song: " + song.getTitle());
                mSongs.add(song);
            }
            if (intent.getAction().equals(Action.START.getValue())) {
                mPosition = intent.getIntExtra(SongActivity.KEY_POSITION, 0);
                if (mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                    }
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                startNew(mPosition);
            } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                mMediaPlayer.pause();
                mLength = mMediaPlayer.getCurrentPosition();
                mCountDownTimer.cancel();
                Log.d(TAG, "CountDownTimer: " + "cancel");
            } else if (intent.getAction().equals(Action.RESUME.getValue())) {
                mMediaPlayer.seekTo(mLength);
                mMediaPlayer.start();
                startCountDownTimer(mMediaPlayer.getDuration() - mLength);
            } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                mLength = intent.getIntExtra(SongActivity.KEY_CHOOSE_TIME, 0);
                mMediaPlayer.seekTo(mLength);
                mCountDownTimer.cancel();
                Log.d(TAG, "CountDownTimer: " + "cancel");
                startCountDownTimer(mMediaPlayer.getDuration() - mLength);
                Log.d(TAG, "onStartCommand: seek to " + mLength);
            } else if (intent.getAction().equals(Action.STOP.getValue())) {
                if (mNotificationManager == null) {
                    stopForeground(true);
                    stopSelf();
                }
            }
        }
        return START_STICKY;
    }

    private void startNew(int position) {
        mMediaPlayer = new MediaPlayer();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            Log.d(TAG, "CountDownTimer: " + "cancel");
        }

        Intent infoIntent = new Intent(Action.SEND_INFO.getValue());
        Bundle bundle = new Bundle();
        bundle.putParcelable(SongActivity.KEY_SONG, mSongs.get(position));
        infoIntent.putExtras(bundle);
        sendBroadcast(infoIntent);

        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mSongs.get(position).getSource());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.d(TAG, "startNew: IOException " + e.getMessage());
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                showNotification();
                mMediaPlayer.start();
            }
        });
        Log.d(TAG, "startNew: " + mMediaPlayer.getDuration());
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mRepeatStatus != SongActivity.REPEAT_ONE) {
                    if (mShuffleStatus == SongActivity.SHUFFLE) {
                        Random random = new Random();
                        int position;
                        do {
                            position = random.nextInt(mSongs.size());
                        } while (position == mPosition);
                        mPosition = position;
                    } else if (mRepeatStatus == SongActivity.REPEAT) {
                        if (mPosition == mSongs.size() - 1) {
                            mPosition = 0;
                        } else {
                            mPosition++;
                        }
                    } else if (mRepeatStatus == SongActivity.NO_REPEAT) {
                        if (mPosition != mSongs.size() - 1) {
                            mPosition++;
                        }
                    }
                }
                startNew(mPosition);
            }
        });
        startCountDownTimer(mMediaPlayer.getDuration());
    }

    private void startCountDownTimer(int millisInFuture) {
        final Intent timeIntent = new Intent(Action.SEEK.getValue());
        mCountDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long l) {
                Log.d(TAG, "onTick: " + l);
                timeIntent.putExtra(KEY_TIME, mMediaPlayer.getDuration());
                timeIntent.putExtra(KEY_TITLE, mSongs.get(mPosition).getTitle() + " --- " + mSongs.get(mPosition).getArtist());
                timeIntent.putExtra(KEY_CURRENT_TIME, mMediaPlayer.getCurrentPosition());
                sendBroadcast(timeIntent);
                mRemoteViews.setProgressBar(R.id.progressBar, mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition(), false);
                mRemoteViews.setTextViewText(R.id.tvCurrentTime, Utils.getTime(mMediaPlayer.getCurrentPosition()));
                mBuilder.setCustomBigContentView(mRemoteViews);
                if (mNotificationManager != null) {
                    mNotificationManager.notify(mNotificationId, mBuilder.build());
                }
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
        Log.d(TAG, "CountDownTimer: " + "start");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void showNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);

        mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_music);
        mRemoteViews.setProgressBar(R.id.progressBar, mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition(), false);
        mRemoteViews.setTextViewText(R.id.tvSong, mSongs.get(mPosition).getTitle());
        mRemoteViews.setTextViewText(R.id.tvArtist, mSongs.get(mPosition).getArtist());
        mRemoteViews.setTextViewText(R.id.tvTime, Utils.getTime(mMediaPlayer.getDuration()));
        mRemoteViews.setTextViewText(R.id.tvCurrentTime, Utils.getTime(mMediaPlayer.getCurrentPosition()));

        Intent clearIntent = new Intent();
        clearIntent.setAction(Action.CLEAR.getValue());
        PendingIntent clearPendingIntent = PendingIntent.getBroadcast(this, 0, clearIntent, 0);
        mRemoteViews.setOnClickPendingIntent(R.id.imgClear, clearPendingIntent);

        Intent notificationIntent = new Intent(getApplicationContext(), SongActivity.class);
        notificationIntent.putExtra(SongActivity.KEY_POSITION, mPosition);
        notificationIntent.putExtra("play_fragment", "play_fragment");
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setSmallIcon(R.mipmap.ic_music)
                .setAutoCancel(true)
                .setCustomBigContentView(mRemoteViews)
                .setContentIntent(mPendingIntent);
        startForeground(mNotificationId, mBuilder.build());
        mNotificationManager.notify(mNotificationId, mBuilder.build());
    }

    /**
     * StatusBroadcastReceiver receive status
     */
    class StatusBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null) {
                if (intent.getAction().equals(Action.START.getValue())) {
                    mPlayStatus = intent.getIntExtra(SongActivity.KEY_PLAY_STATUS, 0);
                } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                    mShuffleStatus = intent.getIntExtra(SongActivity.KEY_SHUFFLE_STATUS, 0);
                } else if (intent.getAction().equals(Action.REPEAT.getValue())) {
                    mRepeatStatus = intent.getIntExtra(SongActivity.KEY_REPEAT_STATUS, 0);
                } else if (intent.getAction().equals(Action.PREVIOUS.getValue())) {
                    if (mShuffleStatus == SongActivity.SHUFFLE) {
                        Random random = new Random();
                        int position;
                        do {
                            position = random.nextInt(mSongs.size());
                        } while (position == mPosition);
                        mPosition = position;
                    } else {
                        if (mPosition == 0) {
                            mPosition = mSongs.size() - 1;
                        } else {
                            mPosition--;
                        }
                    }
                    mMediaPlayer.stop();
                    startNew(mPosition);
                    if (mPlayStatus == SongActivity.PAUSE_STATUS) {
                        mLength = 0;
                        mMediaPlayer.seekTo(mLength);
                        mMediaPlayer.pause();
                    }

                } else if (intent.getAction().equals(Action.NEXT.getValue())) {
                    if (mShuffleStatus == SongActivity.SHUFFLE) {
                        Random random = new Random();
                        int position;
                        do {
                            position = random.nextInt(mSongs.size());
                        } while (position == mPosition);
                        mPosition = position;
                    } else {
                        Log.d(TAG, "size list: " + (mSongs == null));
                        if (mPosition == mSongs.size() - 1) {
                            mPosition = 0;
                        } else {
                            mPosition++;
                        }
                    }
                    mMediaPlayer.stop();
                    startNew(mPosition);
                    if (mPlayStatus == SongActivity.PAUSE_STATUS) {
                        mLength = 0;
                        mMediaPlayer.seekTo(mLength);
                        mMediaPlayer.pause();
                    }
                } else if (intent.getAction().equals(Action.CLEAR.getValue())) {
                    stopForeground(true);
                    stopSelf();
                    mNotificationManager = null;

                    Intent finishIntent = new Intent();
                    finishIntent.setAction(Action.FINISH.getValue());
                    sendBroadcast(finishIntent);

                    SharedPreferences sp = getApplicationContext().getSharedPreferences("status", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(SongActivity.KEY_PLAY_STATUS, SongActivity.PLAY_STATUS);
                    editor.apply();
                    editor.commit();

                } else if (intent.getAction().equals(Action.SHOW.getValue())) {
                    if (mNotificationManager == null) {
                        showNotification();
                    }
                } else if (intent.getAction().equals(Action.CHANGE.getValue())) {
                    if (mNotificationManager == null) {
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("status", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt(SongActivity.KEY_PLAY_STATUS, SongActivity.PLAY_STATUS);
                        editor.apply();
                        editor.commit();
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            Log.d(TAG, "CountDownTimer: " + "cancel");
        }
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mStatusBroadcastReceiver != null) {
            unregisterReceiver(mStatusBroadcastReceiver);
        }
        super.onDestroy();
    }
}
