package vn.asiantech.internship.music;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private int mNotificationId = 1;
    private RemoteViews mRemoteViews;

    @Override
    public void onCreate() {
        super.onCreate();
        mUrls = new ArrayList<>();
        mUrls.add("http://zmp3-mp3-s1-te-zmp3-fpthcm-1.zadn.vn/e7563f83e7c70e9957d6/727637092126384063?key=FaBy9qizMNA8fL04XHgnwQ&expires=1499727453");
        mUrls.add("http://zmp3-mp3-s1-te-zmp3-fpthcm-1.zadn.vn/bd48c43e657a8c24d56b/8699665750036675704?key=XIVM7F8HoFaJXHoJHtMNXw&expires=1499729390");
        mUrls.add("http://zmp3-mp3-s1-te-vnso-tn-8.zadn.vn/8fcd13c21b86f2d8ab97/8208839230443218911?key=ciN9WIlgrK7moANpgD0etA&expires=1499729440");
        mUrls.add("http://zmp3-mp3-s1-te-zmp3-fpthcm-1.zadn.vn/233f19a477e09ebec7f1/7720256713557916913?key=8GLqedajvi-o0x-BB2jGww&expires=1499729505");

        mStatusBroadcastReceiver = new StatusBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.PLAY.getValue());
        intentFilter.addAction(Action.SHUFFLE.getValue());
        intentFilter.addAction(Action.REPEAT.getValue());
        intentFilter.addAction(Action.PREVIOUS.getValue());
        intentFilter.addAction(Action.NEXT.getValue());
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
                stopForeground(true);
                stopSelf();
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
                //showNotification();
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
        if (mMediaPlayer != null) {
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
        mRemoteViews.setTextViewText(R.id.tvSong, "song name");
        Intent notificationIntent = new Intent(this, MusicActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
                .setCustomContentView(mRemoteViews)
                .setContentIntent(pendingIntent);
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
                    mRepeatStatus = intent.getIntExtra(MusicActivity.KEY_LOOP_STATUS, 0);
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
                }
            }
        }
    }
}
