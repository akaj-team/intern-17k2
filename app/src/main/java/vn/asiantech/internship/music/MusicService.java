package vn.asiantech.internship.music;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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

import static android.support.v4.app.NotificationCompat.FLAG_HIGH_PRIORITY;

/**
 * Created by ducle on 08/07/2017.
 * MusicService to play music
 */
public class MusicService extends Service {
    private static final String TAG = MusicService.class.getSimpleName();
    public static final String KEY_TIME = "time";
    public static final String KEY_CURRENT_TIME = "current_time";
    private List<String> mUrls;
    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;
    private int mLength;
    private int mPosition;
    private int mPlayStatus = MusicActivity.STOP_STATUS;
    private int mShuffleStatus = MusicActivity.NO_SHUFFLE;
    private int mRepeatStatus = MusicActivity.NO_REPEAT;
    private StatusBroadcastReceiver mStatusBroadcastReceiver;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;
    private String[] mSongNames;
    private String[] mArtists;
    private PendingIntent mPendingIntent;
    private int mNotificationId = 100;

    @Override
    public void onCreate() {
        super.onCreate();
        mUrls = new ArrayList<>();
        mUrls.add("http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNXDXELJGTLDJTDGLG");
        mUrls.add("http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNVNALJXATLDJTDGLG");
        mUrls.add("http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNVNXADVNTLDJTDGLG");
        mUrls.add("http://api.mp3.zing.vn/api/mobile/source/song/LGJGTDXDLAQTLDJTDGLG");

        mSongNames = new String[]{"1234", "Cay bang", "chi la giac mo", "Mot dieu la mai mai"};
        mArtists = new String[]{"Chi Dan", "Buc tuong", "Microwave", "RoseWood"};
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
            if (intent.getAction().equals(Action.START.getValue())) {
                mPosition = intent.getIntExtra(MusicActivity.KEY_POSITION, 0);
                startNew(mPosition);
            } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                mMediaPlayer.pause();
                mLength = mMediaPlayer.getCurrentPosition();
                mCountDownTimer.cancel();
            } else if (intent.getAction().equals(Action.RESUME.getValue())) {
                mMediaPlayer.seekTo(mLength);
                mMediaPlayer.start();
                startCountDownTimer(mMediaPlayer.getDuration() - mLength);
            } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                mLength = intent.getIntExtra(MusicActivity.KEY_CHOOSE_TIME, 0);
                mMediaPlayer.seekTo(mLength);
                mCountDownTimer.cancel();
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
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mUrls.get(position));
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
                if (mRepeatStatus != MusicActivity.REPEAT_ONE) {
                    if (mShuffleStatus == MusicActivity.SHUFFLE) {
                        Random random = new Random();
                        int position;
                        do {
                            position = random.nextInt(mUrls.size());
                        } while (position == mPosition);
                        mPosition = position;
                    } else if (mRepeatStatus == MusicActivity.REPEAT) {
                        if (mPosition == mUrls.size() - 1) {
                            mPosition = 0;
                        } else {
                            mPosition++;
                        }
                    } else if (mRepeatStatus == MusicActivity.NO_REPEAT) {
                        if (mPosition != mUrls.size() - 1) {
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
                timeIntent.putExtra(KEY_TIME, mMediaPlayer.getDuration());
                Log.d(TAG, "onTick: " + mMediaPlayer.getDuration());
                timeIntent.putExtra(KEY_CURRENT_TIME, mMediaPlayer.getCurrentPosition());
                sendBroadcast(timeIntent);
                mRemoteViews.setProgressBar(R.id.progressBar, mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition(), false);
                mRemoteViews.setTextViewText(R.id.tvCurrentTime, Utils.getTime(mMediaPlayer.getCurrentPosition()));
                mBuilder.setCustomBigContentView(mRemoteViews);
                mNotificationManager.notify(mNotificationId, mBuilder.build());
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mPlayStatus == MusicActivity.PAUSE_STATUS) {
            mMediaPlayer.release();
        }
        if (mStatusBroadcastReceiver != null) {
            unregisterReceiver(mStatusBroadcastReceiver);
        }
        super.onDestroy();
    }

    private void showNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);

        mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_music);
        mRemoteViews.setProgressBar(R.id.progressBar, mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition(), false);
        mRemoteViews.setTextViewText(R.id.tvSong, mSongNames[mPosition]);
        mRemoteViews.setTextViewText(R.id.tvArtist, mArtists[mPosition]);
        mRemoteViews.setTextViewText(R.id.tvTime, Utils.getTime(mMediaPlayer.getDuration()));
        mRemoteViews.setTextViewText(R.id.tvCurrentTime, Utils.getTime(mMediaPlayer.getCurrentPosition()));

        Intent clearIntent = new Intent();
        clearIntent.setAction(Action.CLEAR.getValue());
        PendingIntent clearPendingIntent = PendingIntent.getBroadcast(this, 0, clearIntent, 0);
        mRemoteViews.setOnClickPendingIntent(R.id.imgClear, clearPendingIntent);

        Intent notificationIntent = new Intent(getApplicationContext(), MusicActivity.class);
        notificationIntent.putExtra(MusicActivity.KEY_POSITION, mPosition);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setSmallIcon(R.mipmap.ic_music)
                .setAutoCancel(true)
                .setPriority(FLAG_HIGH_PRIORITY)
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
                    mPlayStatus = intent.getIntExtra(MusicActivity.KEY_PLAY_STATUS, 0);
                } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                    mShuffleStatus = intent.getIntExtra(MusicActivity.KEY_SHUFFLE_STATUS, 0);
                } else if (intent.getAction().equals(Action.REPEAT.getValue())) {
                    mRepeatStatus = intent.getIntExtra(MusicActivity.KEY_REPEAT_STATUS, 0);
                } else if (intent.getAction().equals(Action.PREVIOUS.getValue())) {
                    if (mShuffleStatus == MusicActivity.SHUFFLE) {
                        Random random = new Random();
                        int position;
                        do {
                            position = random.nextInt(mUrls.size());
                        } while (position == mPosition);
                        mPosition = position;
                    } else {
                        if (mPosition == 0) {
                            mPosition = mUrls.size() - 1;
                        } else {
                            mPosition--;
                        }
                    }
                    mMediaPlayer.stop();
                    startNew(mPosition);
                    if (mPlayStatus == MusicActivity.PAUSE_STATUS) {
                        mLength = 0;
                        mMediaPlayer.seekTo(mLength);
                        mMediaPlayer.pause();
                    }

                } else if (intent.getAction().equals(Action.NEXT.getValue())) {
                    if (mShuffleStatus == MusicActivity.SHUFFLE) {
                        Random random = new Random();
                        int position;
                        do {
                            position = random.nextInt(mUrls.size());
                        } while (position == mPosition);
                        mPosition = position;
                    } else {
                        if (mPosition == mUrls.size() - 1) {
                            mPosition = 0;
                        } else {
                            mPosition++;
                        }
                    }
                    mMediaPlayer.stop();
                    startNew(mPosition);
                    if (mPlayStatus == MusicActivity.PAUSE_STATUS) {
                        mLength = 0;
                        mMediaPlayer.seekTo(mLength);
                        mMediaPlayer.pause();
                    }
                } else if (intent.getAction().equals(Action.CLEAR.getValue())) {
                    mMediaPlayer.stop();
                    stopForeground(true);
                    stopSelf();
                    mNotificationManager.cancelAll();
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("status", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(MusicActivity.KEY_PLAY_STATUS, MusicActivity.STOP_STATUS);
                    editor.apply();
                    editor.commit();
                    Intent finishIntent = new Intent();
                    finishIntent.setAction(Action.FINISH.getValue());
                    sendBroadcast(finishIntent);

                } else if (intent.getAction().equals(Action.SHOW.getValue())) {
                    if (mNotificationManager == null) {
                        showNotification();
                    }
                } else if (intent.getAction().equals(Action.CHANGE.getValue())) {
                    if (mNotificationManager == null) {
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("status", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt(MusicActivity.KEY_PLAY_STATUS, MusicActivity.STOP_STATUS);
                        editor.apply();
                        editor.commit();
                    }
                }
            }
        }
    }
}
