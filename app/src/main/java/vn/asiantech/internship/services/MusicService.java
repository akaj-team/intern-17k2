package vn.asiantech.internship.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Action;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.ui.fragments.PlayFragment;
import vn.asiantech.internship.ui.main.MusicActivity;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 07/02/2017
 */
public class MusicService extends Service {

    private List<Song> mSongs;
    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;
    private int mSongPosition;
    private boolean mStopForCall;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private RemoteViews mRemoteViewsBig;
    private RemoteViews mRemoteViewsSmall;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (Action.PAUSE.getValue().equals(action)) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    }
                    return;
                }
                if (Action.RESUME.getValue().equals(action)) {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.start();
                        starCountDownTimer();
                    }
                    return;
                }
                if (Action.NEXT_SONG.getValue().equals(action)) {
                    skipAndNextSong();
                    startSong();
                    return;
                }
                if (Action.PREVIOUS_SONG.getValue().equals(action)) {
                    skipPreviousSong();
                    startSong();
                    return;
                }
                if (Action.SEEK_TO.getValue().equals(action)) {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.seekTo(intent.getIntExtra(PlayFragment.KEY_SEEK, mMediaPlayer.getCurrentPosition()));
                    }
                }
                if (Action.STOP.getValue().equals(action)) {
                    stopSelf();
                }
                if (Action.STOP_SERVICE.getValue().equals(action)) {
                    if (mCountDownTimer != null) {
                        mCountDownTimer.cancel();
                    }
                    mNotificationManager.cancel(22);
                }
                if (Action.CALL.getValue().equals(action)) {
                    String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    if (mMediaPlayer.isPlaying()) {
                        if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING) || phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                            mMediaPlayer.pause();
                            mStopForCall = true;
                        }
                    } else {
                        if (mStopForCall && phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                            mMediaPlayer.start();
                            starCountDownTimer();
                            mStopForCall = false;
                        }
                    }
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mSongs = new ArrayList<>();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.PAUSE.getValue());
        intentFilter.addAction(Action.RESUME.getValue());
        intentFilter.addAction(Action.NEXT_SONG.getValue());
        intentFilter.addAction(Action.PREVIOUS_SONG.getValue());
        intentFilter.addAction(Action.SEEK_TO.getValue());
        intentFilter.addAction(Action.STOP.getValue());
        intentFilter.addAction(Action.STOP_SERVICE.getValue());
        intentFilter.addAction(Action.CALL.getValue());
        registerReceiver(mReceiver, intentFilter);
        initNotification();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if (intent != null) {
            List<Song> songs = intent.getParcelableArrayListExtra(MusicActivity.KEY_SONGS);
            if (songs != null) {
                mSongs = songs;
            }
            mSongPosition = intent.getIntExtra(MusicActivity.KEY_POSITION, -1);
            if (mSongPosition > -1) {
                startSong();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mCountDownTimer.cancel();
        unregisterReceiver(mReceiver);
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mNotificationManager.cancelAll();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initNotification() {

        // Create intent that will bring our app to the front, as if it was tapped in the app
        // Launcher
        Intent showTaskIntent = new Intent(getApplicationContext(), MusicActivity.class);
        showTaskIntent.putExtra(MusicActivity.KEY_POSITION, mSongPosition);
        showTaskIntent.putExtra(MusicActivity.KEY_STATUS, "running");
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                1000,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mRemoteViewsBig = new RemoteViews(getPackageName(), R.layout.notification_main);
        mRemoteViewsSmall = new RemoteViews(getPackageName(), R.layout.notification_main_small);

        Intent intentPreviousSong = new Intent(Action.PREVIOUS_SONG.getValue());
        PendingIntent previousSong = PendingIntent.getBroadcast(this, 0, intentPreviousSong, 0);
        mRemoteViewsBig.setOnClickPendingIntent(R.id.imgPrevious, previousSong);
        mRemoteViewsSmall.setOnClickPendingIntent(R.id.imgPrevious, previousSong);

        Intent intentNextSong = new Intent(Action.NEXT_SONG.getValue());
        PendingIntent nextSong = PendingIntent.getBroadcast(this, 0, intentNextSong, 0);
        mRemoteViewsBig.setOnClickPendingIntent(R.id.imgNext, nextSong);
        mRemoteViewsSmall.setOnClickPendingIntent(R.id.imgNext, nextSong);

        Intent intentTurnOff = new Intent(Action.STOP.getValue());
        PendingIntent turnOff = PendingIntent.getBroadcast(this, 0, intentTurnOff, 0);
        mRemoteViewsBig.setOnClickPendingIntent(R.id.imgTurnOff, turnOff);
        mRemoteViewsSmall.setOnClickPendingIntent(R.id.imgTurnOff, turnOff);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = new Notification.Builder(getApplicationContext())
                    .setContent(mRemoteViewsSmall)
                    .setSmallIcon(R.drawable.ic_music_note_red_700_24dp)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setContentIntent(contentIntent)
                    .build();
            mNotification.bigContentView = mRemoteViewsBig;
        }
    }

    private void updateNotification() {
        mRemoteViewsBig.setProgressBar(R.id.progressBar, mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition(), false);
        mRemoteViewsBig.setTextViewText(R.id.tvSongName, mSongs.get(mSongPosition).getName());
        mRemoteViewsBig.setTextViewText(R.id.tvSingerName, mSongs.get(mSongPosition).getSinger());
        mRemoteViewsSmall.setTextViewText(R.id.tvSongName, mSongs.get(mSongPosition).getName());

        Intent intentPlay = new Intent();
        if (mMediaPlayer.isPlaying()) {
            intentPlay.setAction(Action.PAUSE.getValue());
        } else {
            intentPlay.setAction(Action.RESUME.getValue());
        }
        PendingIntent play = PendingIntent.getBroadcast(MusicService.this, 0, intentPlay, 0);
        mRemoteViewsBig.setOnClickPendingIntent(R.id.imgPlay, play);
        mRemoteViewsSmall.setOnClickPendingIntent(R.id.imgPlay, play);

        if (mMediaPlayer.isPlaying()) {
            mRemoteViewsBig.setImageViewResource(R.id.imgPlay, R.drawable.ic_pause_circle_outline_red_a700_36dp);
            mRemoteViewsSmall.setImageViewResource(R.id.imgPlay, R.drawable.ic_pause_circle_outline_red_a700_36dp);
        } else {
            mRemoteViewsBig.setImageViewResource(R.id.imgPlay, R.drawable.ic_play_circle_outline_red_a700_36dp);
            mRemoteViewsSmall.setImageViewResource(R.id.imgPlay, R.drawable.ic_play_circle_outline_red_a700_36dp);
        }
    }

    private void nextSong() {
        SharedPreferences preferences = getSharedPreferences(MusicActivity.PREFERENCES_NAME, MusicActivity.MODE_PRIVATE);
        int replayType = preferences.getInt(MusicActivity.KEY_REPLAY, 0);
        boolean isShuffle = preferences.getBoolean(MusicActivity.KEY_SHUFFLE, false);
        if (mSongPosition == mSongs.size() - 1 && replayType == MusicActivity.NOT_REPLAY) {
            mSongPosition = -1;
            Intent stopService = new Intent(Action.STOP_SERVICE.getValue());
            sendBroadcast(stopService);
            return;
        }
        if (replayType == MusicActivity.REPLAY_ONE) {
            return;
        }
        if (isShuffle) {
            Random random = new Random();
            mSongPosition = random.nextInt(mSongs.size());
        } else {
            mSongPosition = (mSongPosition + 1) % mSongs.size();
        }
    }

    private void startSong() {
        if (mSongPosition < 0) {
            stopForeground(true);
            mCountDownTimer.cancel();
            return;
        }
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
        }
        mMediaPlayer = MediaPlayer.create(MusicService.this.getApplicationContext(), Uri.parse(mSongs.get(mSongPosition).getSongUrl()));
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Intent songChange = new Intent(Action.SONG_CHANGE.getValue());
        songChange.putExtra(MusicActivity.KEY_POSITION, mSongPosition);
        sendBroadcast(songChange);
        starCountDownTimer();
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextSong();
                startSong();
            }
        });
    }

    private void skipAndNextSong() {
        SharedPreferences preferences = getSharedPreferences(MusicActivity.PREFERENCES_NAME, MusicActivity.MODE_PRIVATE);
        boolean isShuffle = preferences.getBoolean(MusicActivity.KEY_SHUFFLE, false);
        if (isShuffle) {
            Random random = new Random();
            mSongPosition = random.nextInt(mSongs.size());
        } else {
            mSongPosition = (mSongPosition + 1) % mSongs.size();
        }
    }

    private void starCountDownTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration() - mMediaPlayer.getCurrentPosition(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (mSongPosition > -1) {
                    Intent intent1 = new Intent(Action.SEEK.getValue());
                    intent1.putExtra(PlayFragment.KEY_DURATION, mMediaPlayer.getDuration());
                    intent1.putExtra(PlayFragment.KEY_CURRENT, mMediaPlayer.getCurrentPosition());
                    intent1.putExtra(PlayFragment.KEY_PLAYING, mMediaPlayer.isPlaying());
                    sendBroadcast(intent1);
                    updateNotification();
                    mNotificationManager.notify(22, mNotification);
                }
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }

    private void skipPreviousSong() {
        mSongPosition = (mSongPosition - 1) % mSongs.size();
    }
}
